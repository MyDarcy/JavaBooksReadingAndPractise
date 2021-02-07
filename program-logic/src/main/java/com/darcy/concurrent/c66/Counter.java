package com.darcy.concurrent.c66;

public class Counter {

    private int count;

    public synchronized void incr(){
        count ++;
    }
    
    public synchronized int getCount() {
        return count;
    }

}
