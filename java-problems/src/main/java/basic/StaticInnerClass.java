package basic;

/**
 * Author by darcy
 * Date on 17-9-9 下午9:25.
 * Description:
 *
 * 1. 静态内部类
 *   声明为static, 可以不依赖于外部类而实例化,静态内部类能与外部类有相同的名字,不同访问外部类的普通成员变量.
 *   只能访问外部类中的静态成员和静态方法.
 * 2. 非静态内部类
 *   可以自由的引用外部类的静态成员和非静态成员, 但是它和外部类的实例绑定到了一起。非静态内部类不能定义静态变量和成员.
 * 3. 局部内部类
 *   定义在代码块的内部,　局部类就像局部变量一样, 不能被public, protected, private, static修饰, 只能访问
 *   方法中定义的final变量.
 * 4. 匿名内部类
 *    不能有构造函数
 *    不能定义静态成员, 方法,类
 *    不能是public protected, private, static,
 *    new后面
 *    局部类的限制一样对其有效.
 *
 */
public class StaticInnerClass {
}
