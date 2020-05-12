package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Thread thread1 = new Thread(new NewThread(), Integer.toString(i));
            thread1.start();
            thread1.join();
            threadList.add(thread1);
        }
    }
}
