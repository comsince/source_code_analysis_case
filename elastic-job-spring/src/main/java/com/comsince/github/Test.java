package com.comsince.github;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author comsicne
 *         Copyright (c) [2019] [Meizu.inc]
 * @Time 19-4-10 下午3:10
 **/
public class Test {
    static int printnum = 0;

    public static void main(String[] args){

        Object lock0 = new Object();
        Object lock1 = new Object();
        Thread thread0 = new Thread(new Runnable() {
            @Override
            public void run() {

               while (printnum <= 1000){
                   System.out.println(Thread.currentThread().getId()+" thread0 print "+(printnum++));
                   synchronized (lock1){
                       lock1.notify();
                   }
                   try {
                       synchronized (lock0){
                           lock0.wait();
                       }
                   } catch (Exception e){

                   }
               }
            }
        });

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (printnum <= 1000){
                    //System.out.println("thread1 "+printnum);

                    try {
                        synchronized (lock1){
                            lock1.wait();
                        }
                    } catch (Exception e){

                    }
                  if(printnum < 1000){
                  }
                    System.out.println(Thread.currentThread().getId()+" thread1 print "+(printnum++));

                    synchronized (lock0){
                        lock0.notify();
                    }


                }
            }
        });
//        thread1.start();
//        thread0.start();

//        Num num = new Num();
//        new PrintThread(num).start();
//        new PrintThread0(num).start();

        System.out.println(Math.pow(10,3));
        int[] test = new int[]{1,2,3,4,4,8,9,9,3,8,3,3,9};
        int[] result = plusOne0(test);
        for(int i= 0;i<result.length;i++){
            System.out.println(result[i]);
        }
    }

    private static ReentrantLock lock = new ReentrantLock();

    public static class PrintThread0 extends Thread{

        private Num num;

        public PrintThread0(Num num){
            this.num = num;
        }

        @Override
        public void run() {
            while (num.num <=100){
                if(num.flag){
                    try {
                        lock.lock();
                        System.out.println(" print0 "+num.num);
                        num.num++;
                        num.flag = false;
                    } catch (Exception e){

                    } finally {
                        lock.unlock();
                    }
                }

            }
        }
    }

    public static class PrintThread extends Thread{

        private Num num;

        public PrintThread(Num num){
            this.num = num;
        }

        @Override
        public void run() {
            while (num.num <=100){
                if(!num.flag){
                    try {
                        lock.lock();
                        System.out.println(" print "+num.num);
                        num.num++;
                        num.flag = true;
                    } catch (Exception e){

                    } finally {
                        lock.unlock();
                    }
                }

            }
        }
    }

    static class Num{
        public Num() {
        }

        int num = 1;
        volatile boolean flag = true;
    }



    public static int[] plusOne(int[] digits) {
        int result =0;
        for(int i=0;i<digits.length;i++){
            result = (int) (result + digits[i] * Math.pow(10,digits.length-1-i));
        }
        result++;
        int[] resultArr;
        int currentlength = digits.length;
        System.out.println(result/Math.pow(10,digits.length));
        if(result/(int)(Math.pow(10,digits.length))>0){
            currentlength++;
        }
        resultArr = new int[currentlength];
        for(int j=currentlength-1;j>=0;j--){
            resultArr[j]=result%10;
            result = result/10;
        }
        return resultArr;

    }
    public static int[] plusOne0(int[] digits) {
        int[] resultarr = digits;
        for(int i=resultarr.length-1;i>=0;i--){
            if(resultarr[i] + 1  < 10){
                resultarr[i] = resultarr[i]+1;
                break;
            } else if(i==0){
                digits[i] =0;
                resultarr = new int[resultarr.length+1];
                resultarr[0] =1;
            }else {
                resultarr[i] =0;
            }
        }
        return resultarr;

    }
}
