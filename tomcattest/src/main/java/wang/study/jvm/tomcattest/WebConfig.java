package wang.study.jvm.tomcattest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan({"wang.util.entity","com.wang.school"})
public class WebConfig extends WebMvcConfigurerAdapter{

	private String[] patterns = new String[]{
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			"yyyy-MM-dd"				
	};
	@InitBinder
	public void initBinder(WebDataBinder binder) {

		
	}
	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

	}
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {

	}
	
	public void addInterceptors(InterceptorRegistry registry) {

	}
	
	
	

	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {	 
	    configurer.defaultContentType(MediaType.APPLICATION_JSON).
        	mediaType("xml", MediaType.APPLICATION_XML).
        	mediaType("json", MediaType.APPLICATION_JSON);
	}

	





   

   
   
  
}
