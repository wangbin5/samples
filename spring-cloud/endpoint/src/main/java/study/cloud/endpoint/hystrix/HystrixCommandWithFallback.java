package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by Administrator on 2017/9/4.
 */
public class HystrixCommandWithFallback extends HystrixCommand<String> {
    protected HystrixCommandWithFallback() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() throws Exception {
         throw new RuntimeException("fail");
    }

    @Override
    protected String getFallback() {
        return "fallback";
    }
}
