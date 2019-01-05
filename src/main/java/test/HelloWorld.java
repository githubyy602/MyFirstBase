package test;

import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HelloWorld {


    private int num = 1;

    private String str = "aaa";

    private List<Object> temp = new ArrayList<>();

    public  int methodOne(int i){

        int sum = num + i;

        return  sum;
    }

    public String methodTwo(String s){
        return s+",hello";
    }

    public void methodThree(){
        methodThree();
    }

//    public static void main(String [] arg){
////        HelloWorld helloWorld = new HelloWorld();
////        String result = helloWorld.methodTwo("jack");
////        long currentTime = System.currentTimeMillis();
////        while (true){
////
////            if(System.currentTimeMillis()-currentTime > 60000){
////                break;
////            }
////
// //           System.out.println(result);
////        }
        List<Object> list = new ArrayList<>();

//        while (true){
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            HelloWorld helloWorld = new HelloWorld();
//
//            helloWorld.temp.add(UUID.randomUUID().toString());
//
//            list.add(helloWorld);
//        }
//
//    }


    public static Object obj1 = new Object(),obj2 = new Object();

    class DeadLock implements Runnable {

        private boolean flag;

        @Override
        public void run(){
            if(flag){
                synchronized (obj1){

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (obj2){
                        System.out.println("flag is "+flag);
                    }

                }
            }

            if (!flag){
                synchronized (obj2){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (obj1){
                        System.out.println("flag is "+flag);
                    }
                }
            }
        }
    }

//    public static void main(String [] args){
//        DeadLock deadLock1 = new HelloWorld().new DeadLock();
//        deadLock1.flag = true;
//        DeadLock deadLock2 = new HelloWorld().new DeadLock();
//        new Thread(deadLock1,"DemoThread-1").start();
//        new Thread(deadLock2,"DemoThread-2").start();
//    }

    public static void main(String[] args) {
        System.out.println();
        Assert.assertEquals(1,1);
    }

}
