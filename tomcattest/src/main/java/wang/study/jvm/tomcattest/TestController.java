package wang.study.jvm.tomcattest;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wang.study.jvm.tomcattest.model.DateValue;

import java.io.IOException;
import java.util.Date;

@RestController
public class TestController {
    @RequestMapping(value="/api/get_time" ,method = RequestMethod.GET)
    public DateValue getDate(){
        return new DateValue(new Date());
    }


    @RequestMapping(value="/api/get_value",method = RequestMethod.GET)
    public String getValue() throws IOException {
        return IOUtils.toString(new ClassPathResource("templates/response.json").getInputStream(),"utf-8");
    }

}
