package chapter8;

import java.util.ArrayList;
import java.util.List;

/**
 * 观察者模式是一种比较常见的方案，某些事件发生时（比如状态转变），如果一个对象（通
 * 常我们称之为主题）需要自动地通知其他多个对象（称为观察者），就会采用该方案。
 *
 * example:
 * 好几家报纸机构，比如《纽约时报》《卫报》以及《世界报》都订阅了新闻，
 * 他们希望当接收的新闻中包含他们感兴趣的关键字时，能得到特别通知。
 */
public class ObserverMain {

    public static void main(String[] args) {
        Feed f = new Feed();
        f.registerObserver(new NYTimes());
        f.registerObserver(new Guardian());
        f.registerObserver(new LeMonde());
        f.notifyObservers("The queen said her favourite book is Java 8 in Action!");


        Feed feedLambda = new Feed();

        // Observer接口的所有实现类都提供了一个方法： notify。
        // 无需显式地实例化三个观察者对象，直接传递Lambda表达式表示需要执行的行为即可
        feedLambda.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking news in NY! " + tweet); }
        });
        feedLambda.registerObserver((String tweet) -> {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Yet another news in London... " + tweet); }
        });

        feedLambda.notifyObservers("Money money money, give me money!");

    }

    // 观察者接口
    interface Observer{
        // 一旦接收到一条新的新闻，该方法就会被调用
        void inform(String tweet);
    }

    interface Subject{
        // 注册一个新的观察者
        void registerObserver(Observer o);
        // 方法通知它的观察者一个新闻的到来
        void notifyObservers(String tweet);
    }

    /**
     * 声明不同的观察者（比如，这里是三家不同的报纸机构），依据新闻中不同的关键字分别定义不同的行为
     */
    static private class NYTimes implements Observer{
        @Override
        public void inform(String tweet) {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking news in NY!" + tweet);
            }
        }
    }

    static private class Guardian implements Observer{
        @Override
        public void inform(String tweet) {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Yet another news in London... " + tweet);
            }
        }
    }

    static private class LeMonde implements Observer{
        @Override
        public void inform(String tweet) {
            if(tweet != null && tweet.contains("wine")){
                System.out.println("Today cheese, wine and news! " + tweet);
            }
        }
    }

    static private class Feed implements Subject{
        private final List<Observer> observers = new ArrayList<>();
        public void registerObserver(Observer o) {
            this.observers.add(o);
        }
        // 通知.
        public void notifyObservers(String tweet) {
            observers.forEach(o -> o.inform(tweet));
        }
    }

}
