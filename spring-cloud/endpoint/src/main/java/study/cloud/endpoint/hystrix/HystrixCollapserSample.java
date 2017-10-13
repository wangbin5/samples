package study.cloud.endpoint.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCommand;

import java.util.Collection;
import java.util.List;

/**
 * Created by Administrator on 2017/9/5.
 */
public class HystrixCollapserSample extends HystrixCollapser<List<String>,String, Integer> {
    public HystrixCollapserSample(int key){
        this.key = key;
    }
    private int key;
    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        int index = 0;
        for(CollapsedRequest<String, Integer> request : collapsedRequests){
            request.setResponse(batchResponse.get(index));
            index++;
        }
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        return new BatchCommand(collapsedRequests);
    }
}
