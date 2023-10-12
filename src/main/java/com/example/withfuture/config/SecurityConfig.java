//package com.example.withfuture.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.security.core.session.SessionRegistryImpl;
//import org.springframework.security.web.session.HttpSessionEventPublisher;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected  void configure(HttpSecurity http) throws Exception{
//        http
//            .authorizeHttpRequests()
//                .antMatchers("/view/board/modify", "/board/{boardId}") // 수정 및 삭제는 로그인 해야 가능
//                .authenticated()
//                .anyRequest().permitAll()
//                .and()
//            .formLogin()
//                .loginPage("/view/login")
//                .loginProcessingUrl("/login")
//                .defaultSuccessUrl("/view/board/list")
//                .permitAll()
//                .and()
//            .logout()
//                .logoutSuccessUrl("/view/board/list")
//                .permitAll();
//
//        http
//            .sessionManagement()
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(false)
//                .expiredUrl("/view/login?expired=true")
//                .sessionRegistry(sessionRegistry());
//
//    }
//
//    @Bean
//    public SessionRegistry sessionRegistry() {
//        return new SessionRegistryImpl();
//    }
//
//    @Bean
//    public static HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }
//
//}
