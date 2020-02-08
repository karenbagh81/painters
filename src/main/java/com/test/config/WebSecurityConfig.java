package com.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }


   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/user", "/user/by-json",
                        "/user/reset-password-request", "/user/reset-password")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }*/

    public void configure(WebSecurity web) throws Exception {
        web
                .httpFirewall(defaultHttpFirewall())
                //  .httpFirewall(allowUrlEncodedSlashHttpFirewall())
                .ignoring()
                .antMatchers(HttpMethod.GET, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.PATCH, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.POST, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.PUT, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.DELETE, "/**")
                .and()
                .ignoring()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/api-docs");

    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        /*StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;*/
        return new DefaultHttpFirewall();
    }
}
