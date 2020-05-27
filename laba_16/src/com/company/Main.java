package com.company;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

class Port
{
    public ReentrantLock FirstDok = new ReentrantLock();
    public ReentrantLock SecondDok = new ReentrantLock();
}

public class Main {

    public static void main(String[] args) {

        Port port = new Port();

        Ship One = new Ship("One",port);
        Ship Two = new Ship("Two",port);
        Ship Three = new Ship("Three",port);

        One.start();
        Two.start();
        Three.start();
    }
}

class Ship extends Thread
{
    private static  final int  SHIP_UNLOAD_DELAY_MILLIS = 500;
    int Container = 10;
    String Name;
    Port port;

    public Ship(String name, Port port) {
        Name = name;
        this.port = port;
    }


    public void FindDock() throws InterruptedException {
        System.out.println("Ship "+ Name +" goes to the port");
        System.out.println("Ship "+ Name +" are checking port 1");
        if(port.FirstDok.tryLock())
        {
            System.out.println("Ship "+ Name +" enter the first dock");
            Unloading();
            port.FirstDok.unlock();
        }
        else
        {
            System.out.println("Ship "+ Name +" is checking dock 2 for 0.5 second");
            if(port.SecondDok.tryLock(SHIP_UNLOAD_DELAY_MILLIS, TimeUnit.MICROSECONDS))
            {
                System.out.println("Ship "+ Name +" enter the second dock");
                Unloading();
                port.SecondDok.unlock();
            }
            else
            {
                System.out.println("Ship "+ Name + " did not find a free dock and went to another port");
            }
        }
        System.out.println("Ship "+ Name + " leaves port");
    }

    public void Unloading() throws InterruptedException {
        System.out.println("Ship " + Name + " start Unloading containers");
        for (int i = 0; Container > i; i++){
            sleep(SHIP_UNLOAD_DELAY_MILLIS);
                Container--;
         }
            System.out.println("Ship "+ Name +" has unloaded");
    }

    @Override
    public void run() {
        try {
            FindDock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
