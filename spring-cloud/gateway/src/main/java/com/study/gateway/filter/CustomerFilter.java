package com.study.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2017/7/31.
 */
@Component
public class CustomerFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        Map<String, List<String>> params = context.getRequestQueryParams();
        if(params == null){
            context.setRequestQueryParams(new HashMap<String,List<String>>());
            params = context.getRequestQueryParams();
        }
        params.put("id", Arrays.asList("123"));
        System.out.println("run filter");
        return new Date();
    }
}
