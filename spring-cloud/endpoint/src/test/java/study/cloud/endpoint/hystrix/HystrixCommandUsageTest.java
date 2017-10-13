package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.junit.Test;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * hystrix 示例：
 * 1、同步调用
 * 2、异步调用
 * 3、observable（冷、热）
 * 4、fallback
 * 5、客户端缓存
 * 6、hystrix配置项
 * 7、批量处理
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
    /**
     * 不需要等待注册，直接调用run方法，输出次序： run -》wait to subscribe -》 on next
     * 如果没有调用sleep，切换线程，则和testCoolObservableByObserver表现一样。
     * 在注册后，会重播，这里的事件不会丢失
     */
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

    @Test
    public void testCollapser() throws ExecutionException, InterruptedException {
        //使用批量请求时，必须初始化上下文
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try{
            Future<String> f1 = new HystrixCollapserSample(1).queue();
            Future<String> f2 = new HystrixCollapserSample(2).queue();
            Future<String> f3 = new HystrixCollapserSample(3).queue();
            Future<String> f4 = new HystrixCollapserSample(4).queue();
            String f5 = new HystrixCollapserSample(5).execute();
            assertEquals("result by 1",f1.get());
            assertEquals("result by 2",f2.get());
            assertEquals("result by 3",f3.get());
            assertEquals("result by 4",f4.get());
            assertEquals("result by 5",f5);


            HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
            // assert the command is the one we're expecting
            assertEquals("BatchCommand", command.getCommandKey().name());
            // confirm that it was a COLLAPSED command execution
            assertTrue(command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
            // and that it was successful
            assertTrue(command.getExecutionEvents().contains(HystrixEventType.SUCCESS));


        }
        finally{
            context.shutdown();
        }
    }
    //TODO https://github.com/Netflix/Hystrix/wiki/How-To-Use Request Collapsing
}
