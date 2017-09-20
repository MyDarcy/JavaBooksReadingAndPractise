import com.factory.ApplicationContext;
import com.factory.impl.ClassPathXMLApplicationContext;
import com.ioc.service.StudentService;

/**
 * Author by darcy
 * Date on 17-9-20 下午8:54.
 * Description:
 */
public class StudentIOCTest {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXMLApplicationContext("application.xml");
    StudentService stuServ = (StudentService) context.getBean("StudentService");
    stuServ.getStudent().selfIntroDuction();
  }
}
