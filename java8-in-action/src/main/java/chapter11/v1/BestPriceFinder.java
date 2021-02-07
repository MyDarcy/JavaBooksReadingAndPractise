package chapter11.v1;

import chapter11.ExchangeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class BestPriceFinder {

  private final List<Shop> shops = Arrays.asList(
      new Shop("BestPrice"),
      new Shop("LetsSaveBig"),
      new Shop("MyFavoriteShop"),
      new Shop("BuyItAll"),
      new Shop("ShopEasy"),

      new Shop("BestPrice222"),
      new Shop("LetsSaveBig222"),
      new Shop("MyFavoriteShop222"),
      new Shop("BuyItAll222"),

      new Shop("BestPrice333"),
      new Shop("LetsSaveBig333"),
      new Shop("MyFavoriteShop333"),
      new Shop("BuyItAll333"),

      new Shop("BestPrice444"),
      new Shop("LetsSaveBig444"),
      new Shop("MyFavoriteShop444"),
      new Shop("BuyItAll444")/*,

      new Shop("BestPrice555"),
      new Shop("LetsSaveBig555"),
      new Shop("MyFavoriteShop555"),
      new Shop("BuyItAll555")*/
  );

  private final Executor executor = Executors.newFixedThreadPool(shops.size() * 3, new ThreadFactory() {
    @Override
    public Thread newThread(Runnable r) {
      Thread t = new Thread(r);
      t.setDaemon(true);
      return t;
    }
  });

  public List<String> findPricesSequential(String product) {
    return shops.stream()
        .map(shop -> shop.getName() + " price is " + shop.getPrice(product))
        .collect(Collectors.toList());
  }

  public List<String> findPricesParallel(String product) {
    return shops.parallelStream()
        .map(shop -> shop.getName() + " price is " + shop.getPrice(product))
        .collect(Collectors.toList());
  }

  public List<String> findPricesFuture(String product) {
    List<CompletableFuture<String>> priceFutures =
        shops.stream()
            .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is "
                + shop.getPrice(product)/*, executor*/))
            .collect(Collectors.toList());

    List<String> prices = priceFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return prices;
  }

  public List<String> findPricesFutureOnePipeline(String product) {
    List<String> result = shops.stream()
        .map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + " price is "
            + shop.getPrice(product), executor))
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return result;
  }

  public List<String> findPricesInUSD(String product) {
    List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
    for (Shop shop : shops) {
      // Start of Listing 10.20.
      // Only the type of futurePriceInUSD has been changed to
      // CompletableFuture so that it is compatible with the
      // CompletableFuture::join operation below.
      CompletableFuture<Double> futurePriceInUSD =
          // 创建第一个任务查询商店取得商品的价格
          CompletableFuture.supplyAsync(() -> shop.getPrice(product))
              // 没有使用异步版本的thenCombineAsync, 因为组合计算直接可以利用第二个获取完汇率的线程来执行．
              .thenCombine(
                  // 因为不依赖于上一个CompletableFuture的值，所以不需要一个额外的Lambda，参见上一个例子,
                  // 创建第二个独立任务，查询美元和欧元之间的转换汇率
                  CompletableFuture.supplyAsync(
                      () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                  (price, rate) -> price * rate // 组合计算.
              );
      priceFutures.add(futurePriceInUSD);
    }
    // Drawback: The shop is not accessible anymore outside the loop,
    // so the getName() call below has been commented out.
    List<String> prices = priceFutures
        .stream()
        .map(CompletableFuture::join)
        .map(price -> /*shop.getName() +*/ " price is " + price)
        .collect(Collectors.toList());
    return prices;
  }

  public List<String> findPricesInUSDJava7(String product) {
    // 创建一个ExecutorService将任务提交到线程池
    ExecutorService executor = Executors.newCachedThreadPool();
    List<Future<Double>> priceFutures = new ArrayList<>();
    for (Shop shop : shops) {
      // 创建一个查询欧元到美元转换汇率的Future
      final Future<Double> futureRate = executor.submit(new Callable<Double>() {
        public Double call() {
          return ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD);
        }
      });

      // 在第二个Future中查询指定商店中特定商品的价格
      Future<Double> futurePriceInUSD = executor.submit(new Callable<Double>() {
        public Double call() {
          try {
            double priceInEUR = shop.getPrice(product);
            return priceInEUR * futureRate.get(); // 在同一个线程中进行计算.
          } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
          }
        }
      });
      priceFutures.add(futurePriceInUSD);
    }
    List<String> prices = new ArrayList<>();
    for (Future<Double> priceFuture : priceFutures) {
      try {
        // 获取异步计算的结果.
        prices.add(/*shop.getName() +*/ " price is " + priceFuture.get());
      } catch (ExecutionException | InterruptedException e) {
        e.printStackTrace();
      }
    }
    return prices;
  }


  public List<String> findPricesInUSD2(String product) {
    List<CompletableFuture<String>> priceFutures = new ArrayList<>();
    for (Shop shop : shops) {
      // Here, an extra operation has been added so that the shop name
      // is retrieved within the loop. As a result, we now deal with
      // CompletableFuture<String> instances.
      CompletableFuture<String> futurePriceInUSD =
          CompletableFuture.supplyAsync(() -> shop.getPrice(product))
              .thenCombine(
                  CompletableFuture.supplyAsync(
                      () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                  (price, rate) -> price * rate
              ).thenApply(price -> shop.getName() + " price is " + price);
      priceFutures.add(futurePriceInUSD);
    }
    List<String> prices = priceFutures
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return prices;
  }

  public List<String> findPricesInUSD3(String product) {
    // Here, the for loop has been replaced by a mapping function...
    Stream<CompletableFuture<String>> priceFuturesStream = shops
        .stream()
        .map(shop -> CompletableFuture
            .supplyAsync(() -> shop.getPrice(product))
            .thenCombine(
                CompletableFuture.supplyAsync(() -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                (price, rate) -> price * rate)
            .thenApply(price -> shop.getName() + " price is " + price));
    // However, we should gather the CompletableFutures into a List so that the asynchronous
    // operations are triggered before being "joined."
    List<CompletableFuture<String>> priceFutures = priceFuturesStream.collect(Collectors.toList());
    List<String> prices = priceFutures
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return prices;
  }


}
