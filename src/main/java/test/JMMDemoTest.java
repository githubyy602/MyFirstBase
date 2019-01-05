package test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("test")
public class JMMDemoTest {

    //静态共享对象
    public static Object obj1 = new Object(),obj2 = new Object();

    @RequestMapping(value = "/lock.do",method = RequestMethod.GET)
    public String lock(){

        DeadLock deadLock1 = new DeadLock();
        deadLock1.flag = true;
        DeadLock deadLock2 = new DeadLock();
        deadLock2.flag = false;

        //启动线程
        new Thread(deadLock1).start();
        new Thread(deadLock2).start();

        return "";
    }

    class DeadLock implements Runnable {

        private boolean flag;

        @Override
        public void run(){
            if(flag){
                synchronized (obj1){

                    //捕获异常 ctrl+alt+t
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //开启另一个同步锁
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

}
