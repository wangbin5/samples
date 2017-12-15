package wang.study.jvm.lesson7;

import java.util.concurrent.locks.Lock;

/**
 * Created by Administrator on 2017/12/15.
 */
public class Runner {

    public static void main(String[] args){
        LockCreator lc = new LockCreator();
        Lock lock1 = lc.createLock("lock1");
        Lock lock2 = lc.createLock("lock2");
        Lock lock3 = lc.createLock("lock3");
        Lock lock4 = lc.createLock("lock4");
        new Thread(new MyTask(lock1,lock2)).start();
        new Thread(new MyTask(lock2,lock3)).start();
        new Thread(new MyTask(lock3,lock4)).start();
        new Thread(new MyTask(lock4,lock1)).start();
    }
}
