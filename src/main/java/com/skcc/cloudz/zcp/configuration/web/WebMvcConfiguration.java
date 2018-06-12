package com.skcc.cloudz.zcp.configuration.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.skcc.cloudz.zcp.configuration.web.interceptor.AddOnServiceMetaDataInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    
    /*@Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);
        return sessionLocaleResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }*/

    @Bean
    public AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor() {
        AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor = new AddOnServiceMetaDataInterceptor();
        return addOnServiceMetaDataInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(addOnServiceMetaDataInterceptor())
            .addPathPatterns(new String[] {"/*", "/**/*"})
            .excludePathPatterns(new String[] {"/static/**", "/i18n/properties/**", "/error/**"});
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
    
}
