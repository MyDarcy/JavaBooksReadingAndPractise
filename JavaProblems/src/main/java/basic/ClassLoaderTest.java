package basic;

/**
 * Author by darcy
 * Date on 17-9-8 上午10:10.
 * Description:
 *
 * 加载: class文件　-> 流到jvm中。
 * 链接
 *    验证: class文件的正确性校验.
 *    准备: 类中的静态变量分配存储空间
 *    解析: 符号引用转为直接引用
 * 初始化; 静态变量或者静态代码块的初始化.
 */
public class ClassLoaderTest {

  public static void main(String[] args) {
    ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
    System.out.println(classLoader);

    // 负责加载　java.ext.dirs指定的目录以及　jar/lib/ext目录下的jar文件.
    ClassLoader ext = classLoader.getParent();
    System.out.println(ext);

    // boot类加载器负责加载 jre/lib/rt.jar
    ClassLoader boot = ext.getParent();
    System.out.println(boot);
  }
}
