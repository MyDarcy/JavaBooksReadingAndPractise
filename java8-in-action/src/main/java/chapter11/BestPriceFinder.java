package chapter11;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
                                                   new Shop("LetsSaveBig"),
                                                   new Shop("MyFavoriteShop"),
                                                   new Shop("BuyItAll"),
                                                   new Shop("ShopEasy"));

    private final Executor executor = Executors.newFixedThreadPool(shops.size(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    });

    public List<String> findPricesSequential(String product) {
        return shops.stream()
                .map(shop -> shop.getPrice(product)) // name + ":" + price + ":" + code;
                .map(Quote::parse) // return new Quote(shopName, price, discountCode);
                .map(Discount::applyDiscount) // 应用价格和折扣.
                .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream()
                .map(shop -> shop.getPrice(product))
                .map(Quote::parse)
                .map(Discount::applyDiscount)
                .collect(Collectors.toList());
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = findPricesStream(product)
                .collect(Collectors.<CompletableFuture<String>>toList());

        return priceFutures.stream()
                // 等待流中的所有Future执行完毕，并提取各自的返回值
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public Stream<CompletableFuture<String>> findPricesStream(String product) {

        return shops.stream()
            // 以异步方式取得每个shop中指定产品的原始价格, 返回值　Stream<CompletableFuture<String>>
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
                // 不需要异步, 因为只是price+折扣字符串->Quota对象，返回　Stream<CompletableFuture<Quote>>
                .map(future -> future.thenApply(Quote::parse)) // 取future中的string进行解析.
                // 使用另一个异步任务构造期望的Future，申请折扣
                // Discount.apply的实现: return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
                .map(future -> future.thenCompose(quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)));
    }

    public void printPricesStream(String product) {
        long start = System.nanoTime();
        CompletableFuture[] futures = findPricesStream(product)
                // 在每个CompletableFuture上注册一个操作，该操作会在CompletableFuture完成执行后使用它的返回值 。
                //  thenAccept方法接收CompletableFuture执行完毕后的返回值做参数。
                .map(f -> f.thenAccept(s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " msecs)")))
                .toArray(size -> new CompletableFuture[size]);
        CompletableFuture.allOf(futures).join();
        System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " msecs");
    }

}
