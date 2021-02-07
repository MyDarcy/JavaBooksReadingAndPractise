package chapter2;

import com.darcy.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Author by darcy
 * Date on 17-7-25 上午10:35.
 * Description:
 */
public class Realities {

    public static class Apple {
        private int weight = 0;
        private String color = "";

        public Apple(int weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }

    public static void main(String[] args) {
        /**
         * Java8中List自带了sort方法, sort的行为可以使用java.util.Comparator对象来参数化.
         * 进而支持更简洁的语法: lambda.
         */
        List<Apple> inventory = Arrays.asList(new Apple(80,"green"),
                new Apple(155, "green"),
                new Apple(120, "red"),
                new Apple(210, "green"),
                new Apple(90, "red"));

        // demo1 -- List中的sort方法.
        // 升序.
        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        System.out.println(inventory);

        // 降序.
        inventory.sort((Apple a1, Apple a2) -> {
            return a2.getWeight().compareTo(a1.getWeight());
        });
        System.out.println(inventory);


        // demo2 -- Runnable执行代码块.
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello, world.");
            }
        });

        Thread thread1 = new Thread(() -> {
            System.out.println("hello, world.");
        });


        // demo3 -- GUI事件处理.
        JButton bt = new JButton("click.");
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });


        bt.addActionListener((ActionEvent event) -> {
            System.out.println(event);
        });


    }

}
