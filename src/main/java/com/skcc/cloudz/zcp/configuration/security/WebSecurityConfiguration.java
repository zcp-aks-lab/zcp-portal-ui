package com.skcc.cloudz.zcp.configuration.security;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.DelegatingAuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import com.skcc.cloudz.zcp.common.security.filter.OpenIdConnectFilter;
import com.skcc.cloudz.zcp.common.security.handler.LogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Resource(name = "keycloakOpenIdTemplate")
    private OAuth2RestTemplate restTemplate;

    @Autowired
    private LogoutHandler logoutHandler;

    @Autowired
    private SecurityProperties security;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .addFilterAfter(openIdConnectFilter(), OAuth2ClientContextFilter.class);

        http.httpBasic().authenticationEntryPoint(entryPoint()).and().logout().logoutUrl("/logout")
                .logoutSuccessHandler(logoutHandler).and().csrf().disable();

        http.authorizeRequests().antMatchers("/error/accessDenied").permitAll().anyRequest().authenticated().and()
                .headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy", "script-src 'self'"));

        http.exceptionHandling().accessDeniedPage("/error/accessDenied");
    }

    @Bean
    public OpenIdConnectFilter openIdConnectFilter() {
        final OpenIdConnectFilter filter = new OpenIdConnectFilter("/login");
        filter.setRestTemplate(restTemplate);

        return filter;
    }

    @Bean
    public AuthenticationEntryPoint entryPoint() {
        /**
         * @see HttpBasicConfigurer
         */
        RequestMatcher X_REQUESTED_WITH = new RequestHeaderRequestMatcher("X-Requested-With", "XMLHttpRequest");
        RequestMatcher ACCEPT_APPLICATION_JSON = new RequestMatcher() {
            public boolean matches(HttpServletRequest request) {
                List<MediaType> types = MediaType.parseMediaTypes(request.getHeader("Accept"));
                return types.contains(MediaType.APPLICATION_JSON);
            }
        };
        AuthenticationEntryPoint unauthorized = new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);

        // for ajax
        LinkedHashMap<RequestMatcher, AuthenticationEntryPoint> entryPoints = new LinkedHashMap<RequestMatcher, AuthenticationEntryPoint>();
		entryPoints.put(X_REQUESTED_WITH, unauthorized);
		entryPoints.put(ACCEPT_APPLICATION_JSON, unauthorized);

        // for form
        DelegatingAuthenticationEntryPoint entryPoint = new DelegatingAuthenticationEntryPoint(entryPoints);
        entryPoint.setDefaultEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));

        return entryPoint;
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        /*
         * "security.ignored" properties do not work.
         * Because SpringBootWebSecurityConfiguration class is inactivated - by @ConditionalOnMissingBean(WebSecurityConfiguration.class).
         * So apply security.ignored properties manually.
         */
        web.ignoring().antMatchers("/static/**");

        if(security.getIgnored().isEmpty())
            return;

        for(String path : security.getIgnored()){
            path = StringUtils.cleanPath(path);
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            web.ignoring().antMatchers(path);
        }
    }
    
    /*@Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
    
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }*/

}
