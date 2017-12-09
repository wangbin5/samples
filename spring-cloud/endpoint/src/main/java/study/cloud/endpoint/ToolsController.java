package study.cloud.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 配合测试使用
 */
@RestController
public class ToolsController {
    @RequestMapping(value = "/timeout", method = RequestMethod.GET)
    public String timeout(){
        //响应时间在100s
        try {
            Thread.sleep(100*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "timeout result ";
    }

}
