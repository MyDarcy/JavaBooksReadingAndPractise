import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnMethod;

import static com.sun.btrace.BTraceUtils.Threads.jstack;
import static com.sun.btrace.BTraceUtils.println;


@BTrace
public class OracleBtracerInflater {
    @OnMethod(
            clazz="java.util.zip.Inflater",
            method="/.*/"
    )
    public static void traceCacheBlock(){
        println("Who call java.util.zip.Inflater's methods :");
        jstack();
    }
}
