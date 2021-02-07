package chapter9;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Intro{

    public static void main(String...args){

        List<Integer> numbers = Arrays.asList(3, 5, 1, 2, 6);
        // sort is a default method
        // naturalOrder is a static method

        /**
         default void sort(Comparator<? super E> c) {
             Object[] a = this.toArray();
             Arrays.sort(a, (Comparator) c);
             ListIterator<E> i = this.listIterator();
             for (Object e : a) {
                 i.next();
                 i.set((E) e);
             }
         }
         */
        numbers.sort(Comparator.naturalOrder());
        System.out.println(numbers);
   }
}
