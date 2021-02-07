package com.darcy.cls.c21.anonymous;

import com.darcy.cls.c14.Point;

public class Outer {
    public void test(final int x, final int y){
        Point p = new Point(2,3){                
                                               
            @Override                              
            public double distance() {             
                return distance(new Point(x,y));     
            }                                      
        };                                       
                                                 
        System.out.println(p.distance());        
    }

}
