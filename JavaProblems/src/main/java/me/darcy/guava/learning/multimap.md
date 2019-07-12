
1. MultiMap
- [MultiMap接口方法释义](https://blog.csdn.net/yaomingyang/article/details/80955872)
```java
@GwtCompatible
public interface Multimap<K, V> {
  //返回Multimap集合的key、value pair的数量  
  int size();
  //判断Multimap是否包含key、value pair
  boolean isEmpty();
  //判断Multimap中是否包含指定key的value值
  boolean containsKey(@Nullable Object key);
  //判断Multimap中是否包含指定value的key
  boolean containsValue(@Nullable Object value);
  //判断Multimap中是否包含指定的key-value pair的数据
  boolean containsEntry(@Nullable Object key, @Nullable Object value);
  //将数据加入到Multimap中
  boolean put(@Nullable K key, @Nullable V value);
  //删除Multimap中指定key-value pair
  boolean remove(@Nullable Object key, @Nullable Object value);
  //将指定的key-集合数据加入Multimap中
  boolean putAll(@Nullable K key, Iterable<? extends V> values);
  //将指定的Multimap和当前的Multimap合并
  boolean putAll(Multimap<? extends K, ? extends V> multimap);
  //替换指定key的value
  Collection<V> replaceValues(@Nullable K key, Iterable<? extends V> values);
  //删除Imultimap中的指定key数据
  Collection<V> removeAll(@Nullable Object key);
  //清空Imultimap中的数据
  void clear();
  //获取指定key的值
  Collection<V> get(@Nullable K key);
  //获取所有的key集合
  Set<K> keySet();

  Multiset<K> keys();

  Collection<V> values();

  Collection<Map.Entry<K, V>> entries();

  Map<K, Collection<V>> asMap();

  @Override
  boolean equals(@Nullable Object obj);

  @Override
  int hashCode();
}
```

2. ImmutableMap
- [ImmutableMap](https://blog.csdn.net/yaomingyang/article/details/80894841 )
- ImmutableMap是不可变的，线程安全的；它可以创建一些常量的映射键值对；他提供了很多的组合键值对的方法，源码中使用了一个静态内部类来组装构建ImmutableMap，静态内部类作为外部类的辅助构建ImmutableMap，内部使用final声明了一个ArrayList变量。
```java
public static class Builder<K, V>{
     final ArrayList<Map.Entry<K, V>> entries = Lists.newArrayList();

    public Builder() {}

    public Builder<K, V> put(K key, V value)
    {
      this.entries.add(ImmutableMap.entryOf(key, value));
      return this;
    }

    public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry)
    {
      K key = entry.getKey();
      V value = entry.getValue();
      if ((entry instanceof ImmutableEntry)) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);

        Map.Entry<K, V> immutableEntry = entry;
        this.entries.add(immutableEntry);
      }
      else
      {
        this.entries.add(ImmutableMap.entryOf(key, value));
      }
      return this;
    }

    public Builder<K, V> putAll(Map<? extends K, ? extends V> map)
    {
      this.entries.ensureCapacity(this.entries.size() + map.size());
      for (Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
        put(entry.getKey(), entry.getValue());
      }
      return this;
    }

    public ImmutableMap<K, V> build()
    {
      return fromEntryList(this.entries);
    }

    private static <K, V> ImmutableMap<K, V> fromEntryList(List<Map.Entry<K, V>> entries)
    {
      int size = entries.size();
      switch (size) {
      case 0: 
        return ImmutableMap.of();
      case 1: 
        return new SingletonImmutableBiMap((Map.Entry)Iterables.getOnlyElement(entries));
      }
      Map.Entry<?, ?>[] entryArray = (Map.Entry[])entries.toArray(new Map.Entry[entries.size()]);

      return new RegularImmutableMap(entryArray);
    }
  }
```
使用
```java
ImmutableMap<String, Object> map = ImmutableMap.builder().put("1", "1")
                                                         .put("2", "2")
                                                         .put("3", "3")
                                                         .build();

        ImmutableMap<String, String> map1 = ImmutableMap.of("1", "2");
```

3. ImmutableTable
- ImmutableTable是一个不可变的、线程安全的、两个元素作为key且key不可以重复的二维矩阵类型集合，它跟其它的元素一样会复制加入元素的一个副本而不会改变原来的对象；
```java
ImmutableTable<Integer, Integer, Integer> table = ImmutableTable.<Integer, Integer, Integer>builder()
        .put(1, 2, 2)
        .put(1,2,3)
        .build();
```

4. guava中Table、HashBasedTable、TreeBasedTable
- Table  当我们需要多个索引的数据结构时，大多数时候我们会选择Map<String,Map<String,Object>>Map<String,Map<String,Object>>这种Map套Map这种很繁琐的数据结构；最近在学习Guava的时候发现已经提供的有Table集合类型，来支持这种使用场景，Table支持了row和cloumn这种二维的结构，并且提供了多种的视图：
- [Table](https://blog.csdn.net/yaomingyang/article/details/80936359)
- Table有以下实现： 
    - HashBasedTable：基于 `HashMap<R,HashMap<C,V>>`的实现。 
    - TreeBasedTable：基于`TreeMap<R,TreeMap<C,V>>`的实现。 
    - ImmutableTable：基于`ImmutableMap<R,ImmutableMap<C,V>>`的实现。

