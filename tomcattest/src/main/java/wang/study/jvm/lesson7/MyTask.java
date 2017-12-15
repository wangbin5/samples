package wang.study.jvm.lesson7;

import java.util.Random;
import java.util.concurrent.locks.Lock;

/**
 * Created by Administrator on 2017/12/15.
 */
public class MyTask implements  Runnable{
    private Lock lock1;
    private Lock lock2;
    public MyTask(Lock lock1,Lock lock2){
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        while(true){
            int sleepTime = new Random().nextInt(10);
            lock1.lock();
            sleep(sleepTime);
            System.out.println("lock 1 ");
            lock2.lock();
            System.out.println("lock 2 ");
            sleep(sleepTime);

        }
    }

    private void sleep(int sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
