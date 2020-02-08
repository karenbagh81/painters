package com.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class OAuth2Configuration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        @Qualifier("dataSource")
        private DataSource dataSource;


        @Autowired
        private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Autowired
        private CustomLogoutSuccessHandler customLogoutSuccessHandler;


        @Bean
        public TokenStore tokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {

            http
                    .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                    .csrf()
                    .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                    .disable()
                    .headers()
                    .frameOptions()
                    .disable()
                    //     .sessionManagement()
                    //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                    .anyRequest()
                    .authenticated()
                    .and()

                    .logout()
                    .logoutUrl("/oauth/logout")
                    .logoutSuccessHandler(customLogoutSuccessHandler);
        }
    }
}
