package study.cloud.endpoint.hystrix.spring;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import rx.Observable;
import rx.Subscriber;
import study.cloud.endpoint.hystrix.MyHystrixCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

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
public class SpringHystrixService {

    @HystrixCommand(fallbackMethod = "fallback")
    public String getValueSync(String param){
        return param;
    }


    @HystrixCommand
    public Future<String> getValueAsyc(final String param){
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return param;
            }
        };
    }

    @HystrixCommand
    public Observable<String> getObservable(final String param){
        return Observable.create(new Observable.OnSubscribe<String>(){
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(param);
                subscriber.onCompleted();
            }
        });
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public String getValueFromFallback(String param){
        throw new RuntimeException("error");
    }
    public String fallback(String param){
        return "fallback "+param;
    }

    @HystrixCommand
    @CacheResult(cacheKeyMethod = "cacheKeyMethod")
    public String getValueFromCache(int code){
        return "value "+new Random().nextInt(code);
    }

    public String cacheKeyMethod(int code){
        return code+"";
    }

    @HystrixCommand(commandKey = "commandKey",groupKey = "groupKey",threadPoolKey = "threadPoolKey")
    public String configSample(){
        return "123";
    }

    @HystrixCollapser(batchMethod = "findByIds")
    public String findById(String id){
        return null;
    }

    @HystrixCommand
    public List<String> findByIds(List<String> ids){
        List<String> results = new ArrayList<>();
        for(String id : ids){
            results.add("result "+id);
        }
        return results;
    }

}
