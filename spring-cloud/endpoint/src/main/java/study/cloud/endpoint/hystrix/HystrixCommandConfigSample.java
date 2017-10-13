package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;

/**
 * Created by Administrator on 2017/9/4.
 */
public class HystrixCommandConfigSample extends HystrixCommand {
    protected HystrixCommandConfigSample(HystrixCommandGroupKey group) {
        super(createHystrixCommandSetter());
    }

    private static Setter createHystrixCommandSetter() {
        //组名
        HystrixCommandGroupKey groupKey = HystrixCommandGroupKey.Factory.asKey("ExampleGroup");
        Setter setter = Setter.withGroupKey(groupKey);
        //命令名
        setter.andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"));
        //线程池名
        setter.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool"));
        return setter;
    }

    @Override
    protected Object run() throws Exception {
        return "";
    }
}
