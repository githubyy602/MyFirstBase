package test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: test
 * @Author: Yangy
 * @CreateTime: 2018-08-23 17:04
 * @Description:
 **/
public class ThreadDemo {

    public static int count = 10;
    private static ReentrantLock lock = new ReentrantLock();

    public synchronized void deleteCount(){
//        lock.lock();

        if(count > 0){
            System.out.println(Thread.currentThread().getName()+":"+count);
            count--;
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
//        lock.unlock();
    }

//    public class TestThread implements Runnable{
//
//        private ThreadDemo lockDemo;
//
//        public TestThread(ThreadDemo lockDemo){
//            this.lockDemo = lockDemo;
//        }
//
//        @Override
//        public void run() {
//            try {
//                lockDemo.addCount();
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }


//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        ThreadDemo temp = new ThreadDemo();
//        while (true){
//            executorService.execute(new Thread(){
//                @Override
//                public void run() {
//                    temp.deleteCount();
//                }
//            });
//
//            if (count <= 0) break;
//
//        }
//        executorService.shutdown();
//    }

    //wait()和notify()方法必须包含在对应的synchronized中
    public static Object obj = new Object();

    public static class ThreadOne extends Thread{
        @Override
        public void run() {
            synchronized (obj){
                System.out.println(System.currentTimeMillis()+": ThreadOne get the monitor!");

                System.out.println(System.currentTimeMillis()+": object begin wait!");
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(System.currentTimeMillis()+": ThreadOne finish!");
            }
        }
    }

    public static class ThreadTwo extends Thread{
        @Override
        public void run() {
            synchronized (obj) {
                System.out.println(System.currentTimeMillis()+": ThreadTwo get the monitor!");

                obj.notify();
                System.out.println(System.currentTimeMillis()+": ThreadTwo finish!");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main1(String[] args) {
        ThreadOne t1 = new ThreadOne();
        ThreadTwo t2 = new ThreadTwo();
        t1.start();
        t2.start();
    }

    //线程join()方法
    public static class JoinThread extends Thread{

        private static int i = 0;

        @Override
        public void run() {
            for (; i < 100000000; i++);
        }
    }

    public static void main2(String[] args) throws InterruptedException {
        JoinThread pointThread = new JoinThread();
        pointThread.start();
        pointThread.join(10);

        System.out.println("当前i="+JoinThread.i);
    }

    //线程组
    public static class AryThread implements Runnable{
        String threadName = Thread.currentThread().getThreadGroup().getName() + "-" +
                Thread.currentThread().getName();
        @Override
        public void run() {
            System.out.println("当前线程组及线程名称："+threadName);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main3(String[] args) throws InterruptedException {
        ThreadGroup tg = new ThreadGroup("ThreadGroup");
        Thread t1 = new Thread(tg,new AryThread(),"T1");
        Thread t2 = new Thread(tg,new AryThread(),"T2");

        t1.start();
        t2.start();
        t1.join(10);
        t2.join(20);
        //打印当前线程组中的活跃线程数
        System.out.println("活跃线程数:"+tg.activeCount());
        //罗列线程组中的线程信息（可以帮助调试）
        System.out.println("以下为线程组中线程信息\n");
        tg.list();
    }


    //线程-重入锁
    public static class ReentrantLockConditionThread implements Runnable{

        private static ReentrantLock lock = new ReentrantLock();
        private static Condition condition = lock.newCondition();

        @Override
        public void run() {

            System.out.println("Lock will go....");
            lock.lock();
            System.out.println("Lock is locking....");
            try {
                condition.await();
                System.out.println("Condition do await....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("T1 will release the lock....");
                lock.unlock();
            }

        }

        public static void main1(String[] args) throws InterruptedException {
            ReentrantLockConditionThread conditionThread = new ReentrantLockConditionThread();
            Thread t1 = new Thread(conditionThread);
            t1.start();
            System.out.println("Thread1 starting....");
            System.out.println("---------------------------------");
            Thread.sleep(2000);

            System.out.println("Main thread get the lock after 2 seconds....");
            lock.lock();

            System.out.println("Condition will release...");
            condition.signal();

            System.out.println("Main thread will release the lock...");
            lock.unlock();
            System.out.println("ending........");
        }
    }


    //线程池
    public static class ThreadPoolDemo implements Runnable{
        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+": 线程名-"+Thread.currentThread().getName()+" ，线程ID-"+Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
//            ExecutorService executors = Executors.newFixedThreadPool(5);
            ExecutorService executors = Executors.newCachedThreadPool();

            for (int i = 0; i < 10; i++) {
                executors.submit(new ThreadPoolDemo());
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<PCData> queue = new LinkedBlockingDeque<>();
        Producter p1 = new Producter(queue);
        Producter p2 = new Producter(queue);
        Producter p3 = new Producter(queue);
        Customer  c1 = new Customer(queue);
        Customer  c2 = new Customer(queue);
        Customer  c3 = new Customer(queue);

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(p1);
        executorService.execute(p2);
        executorService.execute(p3);
        executorService.execute(c1);
        executorService.execute(c2);
        executorService.execute(c3);

        Thread.sleep(10*1000);

        p1.stop();
        p2.stop();
        p3.stop();

        Thread.sleep(3000);
        System.out.println("================运行结束================");

        executorService.shutdown();
    }
}
