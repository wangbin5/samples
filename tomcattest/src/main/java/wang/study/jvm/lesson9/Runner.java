package wang.study.jvm.lesson9;

import java.util.ArrayList;
import java.util.List;

public class Runner implements Runnable{
    public Runner(Task task,int maxCount){
        this.task = task;
        this.maxCount = maxCount;
    }
    private int maxCount;
    private Task task;


    public static void main(String[] args){
        int threadCount = 1000;
        int maxCount = 1000000;
        int type = Task.ATOMIC_INCR;
        Task task = new Task(type);
        long start = System.currentTimeMillis();
        List<Thread> threads = new ArrayList<>();
        for(int i=0;i<threadCount;i++){
            int count = maxCount/threadCount;
            if(i<maxCount%threadCount){
                count++;
            }
            Thread thread = new Thread(new Runner(task,count));
            threads.add(thread);
            thread.start();;
        }
        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        printResult(task,maxCount,threadCount,System.currentTimeMillis()-start);
    }

    private static void printResult(Task task, int maxCount, int threadCount, long time) {
        String name = task.getTypeName();
        System.out.println("加锁方式: "+name+" , 线程数： "+threadCount+",最大数："+maxCount+",用时："+time+",结果："+task.getResult());
    }

    @Override
    public void run() {
        int result = 0;
        while(result< maxCount){
            task.run();
            result++;
        }
    }
}
