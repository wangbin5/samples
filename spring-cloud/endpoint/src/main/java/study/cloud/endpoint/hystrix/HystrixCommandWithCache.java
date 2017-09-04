package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.Random;

/**
 * Created by Administrator on 2017/9/4.
 */

public class HystrixCommandWithCache extends HystrixCommand<String> {
    private int key;
    //提供组名
    public HystrixCommandWithCache(int key) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.key = key;
    }

    @Override
    protected String run() throws Exception {
        return "test"+new Random().nextInt(key);
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(key);
    }
}
