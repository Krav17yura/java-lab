package com.company;

import java.util.concurrent.Semaphore;

public class Main {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 5; i++) {
            new Thread(new Philosopher(semaphore, "Философ " + (i + 1), 3)).start();
        }
    }
}

class Philosopher implements Runnable {
    public int delay = 500;
    private int approach;
    private int approachCount = 0;
    private Semaphore semaphore;
    private String philosopherName;


    Philosopher(Semaphore semaphore, String philosopherName, int approach) {
        this.semaphore = semaphore;
        this.approach = approach;
        this.philosopherName = philosopherName;
    }

    public void run() {
        while (approachCount < approach) {
            try {
                semaphore.acquire();
                sitAtTable();
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                leaveTable();
                semaphore.release();
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void sitAtTable() {
        System.out.println(this.philosopherName + " садится за стол");
    }

    private void leaveTable() {
        System.out.println(this.philosopherName + " выходит из-за стола");
        approachCount++;
    }
    
}
