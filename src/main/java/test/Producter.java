package test;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage: test.bak
 * @Author: Yangy
 * @CreateTime: 2019-01-04 14:58
 * @Description:生产者
 **/
public class Producter implements Runnable{

    private volatile boolean isRuning = true;
    private static AtomicInteger count = new AtomicInteger();
    private static final int SLEEPING_TIME = 1000;
    //消息队列
    private BlockingQueue<PCData> queue;

    public Producter(BlockingQueue<PCData> queue){
        this.queue = queue;
    }

    @Override
    public void run() {

        Random random = new Random();
        PCData temp;

        while (isRuning){

            try {

                int sleep = random.nextInt(SLEEPING_TIME);
                System.out.println("生产者"+Thread.currentThread().getId()+"即将睡眠"+sleep+"毫秒！\n");

                Thread.sleep(sleep);
                //模拟程序执行
                temp = new PCData(count.incrementAndGet());
                System.out.println("消息缓冲区存入:"+temp.getIntData());
                //将结果放进消息缓冲区
                if(!queue.offer(temp,2,TimeUnit.SECONDS)){
                    System.out.println("消息缓冲区存入数据失败！");
                }


            } catch (InterruptedException e) {
                e.printStackTrace();
                //发生异常则该终止该线程
                Thread.currentThread().interrupt();
            }

        }

    }

    public void stop(){
        isRuning = false;
    }

}
