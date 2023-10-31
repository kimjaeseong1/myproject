package com.example.withfuture.config;

import com.example.withfuture.dto.Response;
import com.example.withfuture.handler.CustomAccessDeniedHandler;
import com.example.withfuture.handler.CustomLogoutSuccessHandler;
import com.example.withfuture.service.MemberService;
import com.example.withfuture.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.server.authorization.ServerWebExchangeDelegatingServerAccessDeniedHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MessageService messageService;

    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService);
    }


    @Override
    protected  void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
         http
                 .exceptionHandling()
                 .accessDeniedHandler(customAccessDeniedHandler)
                 .authenticationEntryPoint(((request, response, authException) -> {
                     response.sendRedirect("/view/login");
                 }))
                 .and()
                 .authorizeRequests()
                .antMatchers("/view/board/write","/board").authenticated()
                .regexMatchers( HttpMethod.PATCH,"/board/\\d+").authenticated()
                .regexMatchers(HttpMethod.DELETE,"/board/\\d+").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll()
                .and()
            .logout()
                .logoutUrl("/userlogout")
                .logoutSuccessHandler(new CustomLogoutSuccessHandler(messageService))
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/view/board/list")
                .permitAll();







//        http
//            .sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false)
//                .expiredUrl("/view/login?expired=true")
//                .sessionRegistry(sessionRegistry());

    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
