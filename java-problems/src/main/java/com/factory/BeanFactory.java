package com.factory;

/**
 * Author by darcy
 * Date on 17-9-20 下午8:28.
 * Description:
 */
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ioc.bean.Book;
import com.ioc.bean.User;

public class BeanFactory {
  private static Map<String, Object> beanMap = new HashMap<String, Object>();

  /**
   * bean工厂的初始化,并进行属性注入
   *
   * @param xml
   *
   */
  @SuppressWarnings("unchecked")
  public void init(String xml) {
    try {

      //从class目录下获取指定的xml文件
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      InputStream ins = classLoader.getResourceAsStream(xml);

      //解析xml
      SAXReader reader = new SAXReader();
      Document doc = reader.read(ins);
      Element root = doc.getRootElement();
      Element beanNode;
      //遍历bean
      for (Iterator i = root.elementIterator("bean"); i.hasNext();) {
        beanNode = (Element) i.next();

        // 获取bean的属性id和class
        Attribute id = beanNode.attribute("id");
        Attribute className = beanNode.attribute("class");

        //利用Java反射机制，通过class的名称获取Class对象
        Class beanClass = Class.forName(className.getText());

        //实例化对象
        Object obj = beanClass.newInstance();

        //设置对象属性值
        setBeanProperty(beanNode,beanClass,obj);

        // 将对象放入beanMap中，其中key为id值，value为对象
        beanMap.put(id.getText(), obj);
      }
    } catch (Exception e) {
      System.out.println(e.toString());
      e.printStackTrace();
    }
  }

  //设置对象属性值
  @SuppressWarnings("unchecked")
  private static void setBeanProperty(Element beanNode,Class beanClass,Object obj) throws Exception{
    // 设置值的方法
    Method method = null;
    // 获取对应class的信息
    java.beans.BeanInfo beanInfo = java.beans.Introspector.getBeanInfo(beanClass);
    // 获取其属性描述
    java.beans.PropertyDescriptor proDescriptor[] = beanInfo.getPropertyDescriptors();
    // 遍历该bean的property属性
    for (Iterator ite = beanNode.elementIterator("property"); ite.hasNext();) {
      Element beanPro = (Element) ite.next();
      // 获取该property的name属性
      Attribute name = beanPro.attribute("name");
      Attribute ref = beanPro.attribute("ref");
      Object proVal = null;
      if(ref!=null){
        proVal=beanMap.get(ref.getValue());
      }else{
        // 获取该property的子元素value的值
        for (Iterator ite1 = beanPro.elementIterator("value"); ite1.hasNext();) {
          Element node = (Element) ite1.next();
          proVal = node.getText();
          break;
        }
      }

      // map里面存的是Object, 调用属性的set方法设置属性.
      for (int k = 0; k < proDescriptor.length; k++) {
        if (proDescriptor[k].getName().equalsIgnoreCase(name.getText())) {

          // 我: 还以为没有这种方法.
          method = proDescriptor[k].getWriteMethod();
          // 利用Java的反射极致调用对象的某个set方法，并将值设置进去
          method.invoke(obj, proVal);
        }
      }
    }
  }


  /**
   * * 通过bean的id获取bean的对象. *
   *
   * @param beanName
   *            bean的id *
   * @return 返回对应对象
   */
  public static Object getBean(String beanName) {
    Object obj = beanMap.get(beanName);
    return obj;
  }

  static{
    BeanFactory factory = new BeanFactory();
    factory.init("config.xml");
  }
  /**
   * * 测试方法. *
   */
  public static void main(String[] args) {

    User userBean = (User) BeanFactory.getBean("userBean");
    System.out.println("userName=" + userBean.getUserName());
    System.out.println("password=" + userBean.getPassword());
    System.out.println("password=" + userBean.getBook().getBookName());

    Book bookBean=(Book)BeanFactory.getBean("bookBean");
    System.out.println("bookName=" + bookBean.getBookName());
  }
}
