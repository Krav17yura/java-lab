package com.company;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        System.out.print("Enter Arr length: ");
        Scanner sc = new Scanner(System.in);
        int arrLength = sc.nextInt();

        int[] arr = new int[arrLength];
        Random rn = new Random();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rn.nextInt(100);
        }

        long time = System.nanoTime();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        System.out.println("Однопоток:" + sum);
        time = System.nanoTime() - time;
        System.out.printf("Elapsed %,9.3f ms\n", time / 1_000_000.0);


        MultyThreadSum  Sum = new MultyThreadSum ();
        sum = Sum.Sum(arr);
        long time2 = System.nanoTime();
        System.out.println("Многопоток:" + sum);
        time2 = System.nanoTime() - time2;
        System.out.printf("Elapsed %,9.3f ms\n", time2 / 1_000_000.0);
    }

    static class MultyThreadSum  {
        int numberOfThreads = 10;

        public int Sum(int arr[]) throws InterruptedException {
            int midleIndex  = arr.length / numberOfThreads;

            SumThreadnNumber[] Thread = new SumThreadnNumber[numberOfThreads];

            for (int i = 0; i < numberOfThreads; i++) {
                int beginIndex  = i * midleIndex ;
                int endIndex = (i + 1) * midleIndex ;
                if (endIndex > arr.length) {
                    endIndex = arr.length;
                }
                Thread[i] = new SumThreadnNumber(beginIndex , endIndex, arr);
                Thread[i].start();
            }

            int sum = 0;
            for (int i = 0; i < numberOfThreads; i++) {
                Thread[i].join();
                sum += Thread[i].Res();
            }
            return sum;
        }
    }

    static class SumThreadnNumber extends Thread {
        int beginIndex;
        int endIndex;
        int[] arrr;
        int result;

        public SumThreadnNumber(int first, int last, int[] arr) {
            this.beginIndex = first;
            this.endIndex = last;
            arrr = arr;
        }

        @Override
        public void run() {
            for (int i = beginIndex; i < endIndex; i++) {
                result += arrr[i];
            }
        }
        public int Res()
        {return result;}
    }
}





