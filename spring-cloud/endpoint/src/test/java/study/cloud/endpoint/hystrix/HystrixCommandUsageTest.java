package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;

/**
 * Created by Administrator on 2017/9/4.
 */
public class HystrixCommandUsageTest {

    @Test
    public void testSyncCall(){
        assertEquals("test", new MyHystrixCommand().execute());
    }

    @Test
    public void testAsyncCall() throws ExecutionException, InterruptedException {
        Future<String> future = new MyHystrixCommand().queue();
        assertEquals("test",future.get());
    }

    @Test
    public void testObservableByAction(){
        new MyHystrixCommand().observe().subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println("call by actions");
                assertEquals("test",s);
            }
        });
    }
    //不需要等待注册，直接调用run方法，输出次序： run -》wait to subscribe -》 on next
    //如果没有调用sleep，切换线程，则和testCoolObservableByObserver表现一样。
    @Test
    public void testHotObservableByObserver() throws InterruptedException {
        Observable<String> observable = new MyHystrixCommand().observe();
        Thread.sleep(3);
        System.out.println("wait to subscribe ");
        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                System.out.println("on next");
                assertEquals("test",s);
            }
        });
    }
    //调用subscribe注册后，才会调用run方法，输出次序： wait to subscribe -》run -》 on next
    @Test
    public void testCoolObservableByObserver() throws InterruptedException {
        Observable<String> observable = new MyHystrixCommand().toObservable();
        Thread.sleep(3);
        System.out.println("wait to subscribe ");
        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                System.out.println("on next");
                assertEquals("test",s);
            }
        });
    }

    @Test
    public void testGetValueByFallback(){
        assertEquals("fallback",new HystrixCommandWithFallback().execute());
    }

    @Test
    public void testRequestCache(){
        //使用请求缓存，必须初始化上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try{
            String expected = new HystrixCommandWithCache(100).execute();
            String result = new HystrixCommandWithCache(100).execute();
            assertEquals(expected,result);
        }
        finally {
           context.shutdown();
        }
    }
    //TODO https://github.com/Netflix/Hystrix/wiki/How-To-Use Request Collapsing
}
