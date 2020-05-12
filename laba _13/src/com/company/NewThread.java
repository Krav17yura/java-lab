package com.company;

import java.math.BigInteger;

public class NewThread implements Runnable {

    @Override
    public void run() {
            System.out.print(Thread.currentThread().getName() + " i=" );
            System.out.print(calculateFactorial(new BigInteger(Thread.currentThread().getName())));
            System.out.println();
    }


    private BigInteger calculateFactorial(BigInteger num) {
        BigInteger one = new BigInteger("1");
        if (num.intValue() <= 2) {
            return num;
        } else {
            return num.multiply(calculateFactorial(num.subtract(one)));
        }

    }
}
