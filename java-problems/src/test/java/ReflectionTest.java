import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.reflection.ReflectionBean;

public class ReflectionTest {
	/**
	 * 通过反射获取类实例
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void getClassInstancceTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> classBean = Class.forName("com.reflection.ReflectionBean");
		ReflectionBean reflectionBean = (ReflectionBean) classBean.newInstance();
		System.out.println(reflectionBean.age);
	}

	/**
	 *  获取类属性
	 * 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@Test
	public void getClassPropertyTest() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		Class<?> classBean = Class.forName("com.reflection.ReflectionBean");

		Field[] fields = classBean.getFields();
		for (Field field : fields) {
			System.out.println("属性：" + field.getName() + "，类型:" + field.getType());
		}
	}

	/**
	 *  使用反射调用类方法
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * 
	 * @throws Exception
	 */
	@Test
	public void invokeClassMethodTest() throws ClassNotFoundException, SecurityException, NoSuchMethodException,
		InstantiationException,IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		Class<?> classBean = Class.forName("com.reflection.ReflectionBean");
		Method method = classBean.getMethod("setPublicTest", String.class);
		ReflectionBean obj = (ReflectionBean) classBean.newInstance();
		method.invoke(obj, "invoke method ok");
		System.out.print(obj.getPublicTest());

	}
}
