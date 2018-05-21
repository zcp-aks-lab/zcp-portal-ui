package com.skcc.cloudz.zcp.configuration.security;

import javax.annotation.Resource;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.skcc.cloudz.zcp.common.security.event.SessionDestroyedEventHandler;
import com.skcc.cloudz.zcp.common.security.filter.OpenIdConnectFilter;
import com.skcc.cloudz.zcp.common.security.filter.SessionTimeoutFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Resource(name="keycloakOpenIdTemplate")
    private OAuth2RestTemplate restTemplate;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
        .addFilterBefore(new SessionTimeoutFilter(), BasicAuthenticationFilter.class)
        .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
        .addFilterAfter(openIdConnectFilter(), OAuth2ClientContextFilter.class);
        
        http
        .httpBasic().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/k8s-login"));
        
        http
        .authorizeRequests()
        //.antMatchers("/main").permitAll()
        .anyRequest()
        .authenticated();
    }
    
    @Bean
    public OpenIdConnectFilter openIdConnectFilter() {
        final OpenIdConnectFilter filter = new OpenIdConnectFilter("/k8s-login");
        filter.setRestTemplate(restTemplate);
        
        return filter;
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }
    
    @Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
    
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }
    
    @Bean
    public SessionDestroyedEventHandler sessionDestroyedEventHandler() {
        return new SessionDestroyedEventHandler();
    }

}
