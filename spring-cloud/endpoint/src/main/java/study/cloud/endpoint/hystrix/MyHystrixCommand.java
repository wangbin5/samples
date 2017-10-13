package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

/**
 * Created by Administrator on 2017/9/4.
 */

public class MyHystrixCommand extends HystrixCommand<String> {
    //提供组名
    public MyHystrixCommand() {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
    }

    @Override
    protected String run() throws Exception {
        System.out.println("run");
        return "test";
    }
}
