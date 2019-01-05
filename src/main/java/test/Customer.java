package test;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 * @BelongsProject: IDEAWorkPlace
 * @BelongsPackage:
 * @Author: Yangy
 * @CreateTime: 2019-01-04 15:00
 * @Description:消费者
 **/
public class Customer implements Runnable{

    private static final int SLEEPING_TIME = 1000;
    //消息队列
    private BlockingQueue<PCData> queue;

    public Customer(BlockingQueue<PCData> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        Random random = new Random();
        PCData temp;

        System.out.println("当前运行消费者ID："+Thread.currentThread().getId());

        try {

            while (true){

                temp = queue.take();
                if(temp != null){
                    long result = temp.getIntData()*temp.getIntData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",temp.getIntData(),temp.getIntData(),result));

                    int sleep = random.nextInt(SLEEPING_TIME);
                    System.out.println("消费者"+Thread.currentThread().getId()+"即将睡眠"+sleep+"毫秒！\n");

                    Thread.sleep(sleep);
                }
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("消费异常-"+Thread.currentThread().getId());
            Thread.currentThread().interrupt();
        }

    }

}
