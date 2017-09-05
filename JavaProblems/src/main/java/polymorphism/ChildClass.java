package polymorphism;

/**
 * Author by darcy
 * Date on 17-9-5 下午3:15.
 * Description:
 *
 * Java多态的实现原理
 http://blog.csdn.net/thinkGhoster/article/details/2307001
 http://blog.csdn.net/seu_calvin/article/details/52191321
 http://blog.csdn.net/qc_liu/article/details/42584099
 *
 */
public class ChildClass extends BaseClass {

  public static void main(String[] args) {
    /*
    print_ChildClass
    print_ChildClass
    BaseClass scret
     */
    BaseClass bc = new ChildClass();
    bc.print();
    bc.baseMethod();
  }

  void print() {
    System.out.println("print_ChildClass");
  }

  void childMethod() {
    System.out.println("childMethod");
  }

}

/*
  polymorphism.png
  这个是类被虚拟机加载进方法区之后，在方法区里面的布局。

  如果我们像上面那样，用一个基类引用指向子类对象，那么，这个引用所绑定的类还是子类。
  只不过它所能访问的虚方法表与一般子类对象不同。上面黄色的部分才是base这个引用所能
  访问的vtable的范围(关于vtable的生成有时间在总结)，而普通的子类引用可以访问整个
  子类的vtable。所以能很明显的看到，红色的两个方法才是它真正可以调用的。

  有一点需要注意的是，上述代码中的base就是一个指向子类的引用！因此，这段程序测试的结果是
  print_ChildClass
  print_ChildClass
  BaseClass private method

  可以看到，this在基类中已经不再代表BaseClass的对象，因为调用时进入虚拟机栈的是一个子类的引用。
  所以this.print()会调用子类的print()。强调一下this的含义：代表着调用本方法的类实例的引用。

  但是！！！问题来了！！！为神马this.priMethod()可以调用基类的私有方法呢？
  有没有一种被骗了的感觉呢？这里需要再说明一下，这个this就是一个子类对象的引用
  ，一点没错。但在这里，子类对象的引用就可以调用其基类的私有方法，原因是，这里的
  调用不再是invokevirtual，而变成了invokespecial！

  invokespecial它的作用是调用实例初始化方法，私有方法，父类的方法（详情可以参考JVM规范）。
  这里我们调用的是父类的私有方法，这里这个父类当然是针对上下文环境里这个this而言的。所以，
  编译器产生了invokespecial指令，使得在这里用子类的对象的引用调用了父类的方法。
 */

/*
darcy@darcy-ubuntu:~/IdeaProjects/JavaForGit/JavaBooksReading/JavaProblems/src/main/java$ javac  polymorphism/ChildClass.java
darcy@darcy-ubuntu:~/IdeaProjects/JavaForGit/JavaBooksReading/JavaProblems/src/main/java$ javap -v polymorphism.ChildClass
Classfile /home/darcy/IdeaProjects/JavaForGit/JavaBooksReading/JavaProblems/src/main/java/polymorphism/ChildClass.class
  Last modified 2017-9-5; size 630 bytes
  MD5 checksum ce289d0787439064b7f7abef6089f425
  Compiled from "ChildClass.java"
public class polymorphism.ChildClass extends polymorphism.BaseClass
  minor version: 0
  major version: 52
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #10.#21        // polymorphism/BaseClass."<init>":()V
   #2 = Class              #22            // polymorphism/ChildClass
   #3 = Methodref          #2.#21         // polymorphism/ChildClass."<init>":()V
   #4 = Methodref          #10.#23        // polymorphism/BaseClass.print:()V
   #5 = Methodref          #10.#24        // polymorphism/BaseClass.baseMethod:()V
   #6 = Fieldref           #25.#26        // java/lang/System.out:Ljava/io/PrintStream;
   #7 = String             #27            // print_ChildClass
   #8 = Methodref          #28.#29        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #9 = String             #18            // childMethod
  #10 = Class              #30            // polymorphism/BaseClass
  #11 = Utf8               <init>
  #12 = Utf8               ()V
  #13 = Utf8               Code
  #14 = Utf8               LineNumberTable
  #15 = Utf8               main
  #16 = Utf8               ([Ljava/lang/String;)V
  #17 = Utf8               print
  #18 = Utf8               childMethod
  #19 = Utf8               SourceFile
  #20 = Utf8               ChildClass.java
  #21 = NameAndType        #11:#12        // "<init>":()V
  #22 = Utf8               polymorphism/ChildClass
  #23 = NameAndType        #17:#12        // print:()V
  #24 = NameAndType        #31:#12        // baseMethod:()V
  #25 = Class              #32            // java/lang/System
  #26 = NameAndType        #33:#34        // out:Ljava/io/PrintStream;
  #27 = Utf8               print_ChildClass
  #28 = Class              #35            // java/io/PrintStream
  #29 = NameAndType        #36:#37        // println:(Ljava/lang/String;)V
  #30 = Utf8               polymorphism/BaseClass
  #31 = Utf8               baseMethod
  #32 = Utf8               java/lang/System
  #33 = Utf8               out
  #34 = Utf8               Ljava/io/PrintStream;
  #35 = Utf8               java/io/PrintStream
  #36 = Utf8               println
  #37 = Utf8               (Ljava/lang/String;)V
{
  public polymorphism.ChildClass();
    descriptor: ()V
    flags: ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method polymorphism/BaseClass."<init>":()V
         4: return
      LineNumberTable:
        line 8: 0

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=2, args_size=1
         0: new           #2                  // class polymorphism/ChildClass
         3: dup
         4: invokespecial #3                  // Method "<init>":()V
         7: astore_1
         8: aload_1
         9: invokevirtual #4                  // Method polymorphism/BaseClass.print:()V
        12: aload_1
        13: invokevirtual #5                  // Method polymorphism/BaseClass.baseMethod:()V
        16: return
      LineNumberTable:
        line 16: 0
        line 17: 8
        line 18: 12
        line 19: 16

  void print();
    descriptor: ()V
    flags:
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #7                  // String print_ChildClass
         5: invokevirtual #8                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 22: 0
        line 23: 8

  void childMethod();
    descriptor: ()V
    flags:
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #6                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #9                  // String childMethod
         5: invokevirtual #8                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 26: 0
        line 27: 8
}
SourceFile: "ChildClass.java"

 */