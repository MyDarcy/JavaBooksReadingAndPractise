package com.darcy;

/**
 * author:hezhiqiang05
 * datetime:2021-04-21 20:13
 * description:
 */
public class CaffeineDemo {

  public static void main(String[] args) {
    AsyncLoadingCache<Integer, Object> cache = Caffeine.newBuilder()
        .maximumSize(10_000)
        .expireAfterWrite(10, TimeUnit.MINUTES)
        // 你可以选择: 去异步的封装一段同步操作来生成缓存元素
        .buildAsync(key -> createExpensiveGraph(key));
    // 你也可以选择: 构建一个异步缓存元素操作并返回一个future
    .buildAsync((key, executor) -> createExpensiveGraphAsync(key, executor));
  }

}
