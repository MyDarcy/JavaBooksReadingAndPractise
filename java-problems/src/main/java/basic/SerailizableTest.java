package basic;

/**
 * Author by darcy
 * Date on 17-9-8 上午9:56.
 * Description:
 *
 *
 * 序列化:
 * 1. 如果一个类能够被序列化,那么它的子类也能被序列化
 * 2. static代表类的静态成员, transient代表对象的瞬时数据, 这两种类型的数据成员不能被序列化.
 *
 * 使用场景:
 * 1. 通过网络发送对象, 譬如RPC调用, 对象的状态持久化到数据库或者文件.
 * 2. 序列化能实现深复制.
 *
 * SerailVersionUID用来判定类的兼容性.如果待序列化的对象和目标对象的SerailVersionUID不同，那么反序列化会抛出
 * InvalidClassException异常.所以在被序列化的类中最好显式的定义SerialVersionUID, 该字段必须是static final的.
 *
 * 自定义SerialVersionUID的优点:
 * 1. 提高程序的运行效率。类中没有显示的声明SerialVersionUID，　那么序列化时候会通过计算得到一个SerialVersionUID，　
 * 显式的声明避免显式的计算.
 * 2. 不同平台的兼容性,
 * 3. 各个版本的兼容性。
 */
public class SerailizableTest {
}
