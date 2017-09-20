import org.junit.Test;

import com.factory.BeanFactory;
import com.ioc.bean.User;

public class IocTest {

	@Test
	public void testIoc(){
		 
		User userBean = (User) BeanFactory.getBean("userBean");
		System.out.println("userName=" + userBean.getUserName());
		System.out.println("password=" + userBean.getPassword());
		System.out.println("password=" + userBean.getBook().getBookName());
		
	}
}
