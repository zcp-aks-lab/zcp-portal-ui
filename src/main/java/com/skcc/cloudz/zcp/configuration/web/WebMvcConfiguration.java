package com.skcc.cloudz.zcp.configuration.web;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.skcc.cloudz.zcp.configuration.web.interceptor.AddOnServiceMetaDataInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
    
    @Bean(name = "localeResolver")
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
    }

    @Bean
    public AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor() {
        AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor = new AddOnServiceMetaDataInterceptor();
        return addOnServiceMetaDataInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(addOnServiceMetaDataInterceptor())
            .addPathPatterns(new String[] {"/*", "/**/*"})
            .excludePathPatterns(new String[] {"/static/**", "/i18n/properties/**", "/error/**"});
    }
    
}
