import org.openjdk.btrace.core.annotations.BTrace;
import org.openjdk.btrace.core.annotations.OnMethod;

import static org.openjdk.btrace.core.BTraceUtils.Threads.jstack;
import static org.openjdk.btrace.core.BTraceUtils.println;

@BTrace
public class BtracerInflater{
    @OnMethod(
            clazz="java.util.zip.Inflater",
            method="/.*/"
    )
    public static void traceCacheBlock(){
        println("Who call java.util.zip.Inflater's methods :");
        jstack();
    }
}
