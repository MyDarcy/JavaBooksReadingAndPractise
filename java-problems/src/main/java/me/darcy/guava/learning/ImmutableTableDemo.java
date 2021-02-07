package me.darcy.guava.learning;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import me.darcy.utils.PrintUtils;

import java.util.Map;
import java.util.Set;

public class ImmutableTableDemo {

  /**
   * ImmutableTable是一个不可变的、线程安全的、两个元素作为key且key不可以重复的二维矩阵类型集合; 它跟其它的元素一样会复制加入元素的一个副本而不会改变原来的对象；
   *
   */
  public static void immutableTableDemo() {

    ImmutableTable<Integer, Integer, Integer> table = ImmutableTable.<Integer, Integer, Integer>builder()
        .put(1, 2, 2)
        .put(1,3,3)
        .build();

    // ImmutableTable的双key确定的二维点是不可以重复的，如果重复就会抛出异常。
    /*
    Exception in thread "main" java.lang.IllegalArgumentException: duplicate key: (1, 2)
	   at com.google.common.base.Preconditions.checkArgument(Preconditions.java:440)
	   at com.google.common.collect.DenseImmutableTable.<init>(DenseImmutableTable.java:72)
	   at com.google.common.collect.RegularImmutableTable.forOrderedComponents(RegularImmutableTable.java:166)
	   at com.google.common.collect.RegularImmutableTable.forCellsInternal(RegularImmutableTable.java:156)
	   at com.google.common.collect.RegularImmutableTable.forCells(RegularImmutableTable.java:128)
	   at com.google.common.collect.ImmutableTable$Builder.build(ImmutableTable.java:354)
     at me.darcy.guava.learning.ImmutableTableDemo.immutableTableDemo(ImmutableTableDemo.java:12)
     at me.darcy.guava.learning.ImmutableTableDemo.main(ImmutableTableDemo.java:17)
     */
  }

  public static void tableDemo() {
    Table<Integer, Integer, Integer> table = HashBasedTable.<Integer, Integer, Integer>create();
    table.put(1, 2, 3);
    //允许row和column确定的二维点重复
    table.put(1, 6, 3);
    //判断row和column确定的二维点是否存在
    if(table.contains(1, 2)) {
      table.put(1, 4, 4);
      table.put(2, 5, 4);
    }
    System.out.println(table);
    //获取column为5的数据集
    Map<Integer, Integer> column = table.column(5);
    System.out.println(column);
    //获取rowkey为1的数据集
    Map<Integer, Integer> row = table.row(1);
    System.out.println(row);
    //获取rowKey为1，columnKey为2的的结果
    Integer value = table.get(1, 2);
    System.out.println(value);
    //判断是否包含columnKey的值
    System.out.println(table.containsColumn(3));
    //判断是否包含rowKey为1的视图
    System.out.println(table.containsRow(1));
    //判断是否包含值为2的集合
    System.out.println(table.containsValue(2));
    //将table转换为Map套Map格式
    Map<Integer, Map<Integer, Integer>> rowMap = table.rowMap();
    System.out.println(rowMap);
    //获取所有的rowKey值的集合
    Set<Integer> keySet = table.rowKeySet();
    System.out.println(keySet);
    //删除rowKey为1，columnKey为2的元素，返回删除元素的值
    Integer res = table.remove(1, 2);
    //清空集合
    table.clear();
    System.out.println(res);
    System.out.println(table);
    /**
     * {1={2=3, 6=3, 4=4}, 2={5=4}}
     * {2=4}
     * {2=3, 6=3, 4=4}
     * 3
     * false
     * true
     * false
     * {1={2=3, 6=3, 4=4}, 2={5=4}}
     * [1, 2]
     * 3
     * {}
     */
  }

  public static void main(String[] args) {
    System.out.println("immutableTableDemo");
    immutableTableDemo();

    PrintUtils.printlnDash();

    ImmutableTable<Integer, Integer, Integer> table = ImmutableTable.<Integer, Integer, Integer>of(1, 2, 2).of(2, 3, 3);
    System.out.println(table);
    System.out.println(table.size());

    PrintUtils.printlnDash();

    PrintUtils.println("tableDemo");
    tableDemo();
  }

}
