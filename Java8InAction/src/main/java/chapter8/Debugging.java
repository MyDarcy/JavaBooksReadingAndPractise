package chapter8;


import java.util.*;

import static java.util.Comparator.*;

public class Debugging{
    public static void main(String[] args) {
        // null in list.
        List<Point> points = Arrays.asList(new Point(12, 2), null);
//        points.stream().map(p -> p.getX()).forEach(System.out::println);
        points.stream().map(Point::getX).forEach(System.out::println);

        points.forEach(System.out::println);
    }


    private static class Point{
        private int x;
        private int y;

        public static Comparator<Point> compareByXThenByY = comparing(Point::getX).thenComparing(Point::getY);



        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        private Point(int x, int y) {
            this.x = x;
            this.y = y;

        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }


    }
}
