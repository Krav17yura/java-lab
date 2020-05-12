package com.company;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int sum = 0;
        int numberOfThreads = 10;

          System.out.print("Enter Arr length: ");
          Scanner sc = new Scanner(System.in);
          int arrLength = sc.nextInt();

          int[] arr = new int[arrLength];
          Random rn = new Random();
          for(int  i = 0; i < arr.length; i++){
              arr[i] = rn.nextInt(100);
          }
        long time = System.nanoTime();

          for(int i = 0; i < arr.length; i++){
           sum += arr[i];
          }
          System.out.println(sum);

        time = System.nanoTime() - time;
        System.out.printf("Elapsed %,9.3f ms\n", time/1_000_000.0);


        long time2 = System.nanoTime();
        sum = 0;
        int beginIndex = 0;
        int midleIndex = arrLength/numberOfThreads;
        for(int i = 0; i < numberOfThreads; i++){
            int[] threadArr = Arrays.copyOfRange(arr, beginIndex, midleIndex);
            beginIndex = midleIndex;
            midleIndex += arrLength/numberOfThreads;
            MultyThreadSum thr = new MultyThreadSum(threadArr, arrLength/numberOfThreads, "Thread #" + i);
            thr.start();
            thr.join();
            sum += thr.Res();
            System.out.println("Result thread - " + i + ": " + thr.Res());
        }
        System.out.println("Result: " + sum);
        time2 = System.nanoTime() - time2;
        System.out.printf("Elapsed %,9.3f ms\n", time2/1_000_000.0);



    }

    public static class MultyThreadSum extends Thread
    {
        int[] arr;
        int numberOfTherad;
        int result = 0;

        public MultyThreadSum(int[] arr, int numberOfTherad, String name)
        {
            super(name);
            this.numberOfTherad = numberOfTherad;
            this.arr = new int[this.numberOfTherad];
            this.arr = arr;
        }

        public void run() {
            for (int i = 0; i < arr.length; i++)
                result += arr[i];
        }

        public int Res()
        {return result;}

    }
}
