package wang.study.jvm.lesson9;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    public static final int NO_LOCK = 0;
    public static final int LOCK = 1;
    public static final int ATOMIC_INCR = 2;
    private String typeName;

    public Task(int type){
        this.type= type;
    }
    private int type;
    private int count = 0;
    private AtomicInteger count2 = new AtomicInteger();

    public int run(){
        if(type == NO_LOCK){
            return nolock();
        }
        else if(type == LOCK){
            return lock();
        }
        else{
            return atomicIncr();
        }
    }

    public int getResult(){
        if(type == ATOMIC_INCR){
            return count2.get();
        }
        return count;
    }

    public int nolock(){
        count++;
        return count;
    }

    public synchronized  int lock(){
        count++;
        return count;
    }

    public int atomicIncr(){
        return count2.incrementAndGet();
    }

    public String getTypeName() {
        if(type == NO_LOCK){
            return "无锁";
        }
        else if(type == LOCK){
            return "加锁";
        }
        else{
            return "原子增加";
        }
    }
}
