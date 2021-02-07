package basic;

/**
 * Author by darcy
 * Date on 17-9-8 上午10:18.
 * Description:
 * 内存泄漏的场景：
 * 1. 静态集合类: 和程序的生命周期一致
 * 2. 各种数据库连接, IO连接, 网络连接等以及监听器.
 * 3. 第三方jar包
 * 4. 单例: 同样静态作用域.
 */
public class MemoryLeakTest {
}
