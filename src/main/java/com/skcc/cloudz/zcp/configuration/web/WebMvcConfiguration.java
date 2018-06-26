package com.skcc.cloudz.zcp.configuration.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.skcc.cloudz.zcp.configuration.web.interceptor.AddOnServiceMetaDataInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Bean
    public AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor() {
        AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor = new AddOnServiceMetaDataInterceptor();
        return addOnServiceMetaDataInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(addOnServiceMetaDataInterceptor())
            .addPathPatterns(new String[] {"/*", "/**/*"})
            .excludePathPatterns(new String[] {"/static/**", "/i18n/**", "/error/**"});
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry
          .addMapping("/*")
          .allowedOrigins("*")
          .allowedMethods("GET, POST, PUT, DELETE")
          .allowedHeaders("Content-Type")
          .allowCredentials(false)
          .maxAge(3600);
    }
    
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/management/namespace/pop/popNamespaceDel.html").setViewName("content/management/namespace/pop/popNamespaceDel");
	    registry.addViewController("/management/namespace/create").setViewName("content/management/namespace/namespace-add");
	      
	}
}
