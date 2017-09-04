package study.rxjava.sample;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/13.
 */
public class SimpleUsage {
    /**
     * 在独立线程中运行任务
     * @throws InterruptedException
     */
    public void runOnOtherThread() throws InterruptedException {
        Flowable.fromCallable(() -> {
                Thread.sleep(1000); //  imitate expensive computation
                return "Done";
            })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.single())
                .subscribe(System.out::println, Throwable::printStackTrace);

        Thread.sleep(2000); // <--- wait for the flow to finish
    }

    /**
     * 多线程运行，不保证顺序
     */
    public void runNoSequence(){
        Flowable.range(1, 10)
                .flatMap(v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }
    /**
     * 多线程运行，保证顺序
     */
    public void runWithSequence(){
        Flowable.range(1, 10)
                .parallel()
                .runOn(Schedulers.computation())
                .map(w -> w * w)
                .sequential()
                .blockingSubscribe(System.out::println);
    }
    /**
     * 多线程运行，保证顺序
     */
    public void runWithSequence2(){
        Flowable.range(1, 10)
                .concatMap (v ->
                        Flowable.just(v)
                                .subscribeOn(Schedulers.computation())
                                .map(w -> w * w)
                )
                .blockingSubscribe(System.out::println);
    }

    public static void main(String[] args) throws InterruptedException {
        new SimpleUsage().runWithSequence2();
        System.out.println("start task ");
    }
}
