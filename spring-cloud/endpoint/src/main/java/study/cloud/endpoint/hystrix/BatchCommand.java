package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public class BatchCommand extends HystrixCommand<List<String>> {
    private Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collapsedRequests;
    public BatchCommand(Collection<HystrixCollapser.CollapsedRequest<String, Integer>> collapsedRequests) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.collapsedRequests = collapsedRequests;
    }

    @Override
    protected List<String> run() throws Exception {
        List<String> result = new ArrayList<>();
        for(HystrixCollapser.CollapsedRequest<String, Integer> request : this.collapsedRequests){
            Integer key = request.getArgument();
            result.add("result by "+key);
        }
        return result;
    }
}
