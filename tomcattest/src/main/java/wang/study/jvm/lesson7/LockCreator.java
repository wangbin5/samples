package wang.study.jvm.lesson7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2017/12/15.
 */
public class LockCreator {

    public Lock createLock(String key){
        return new ReentrantLock();
    }
}
