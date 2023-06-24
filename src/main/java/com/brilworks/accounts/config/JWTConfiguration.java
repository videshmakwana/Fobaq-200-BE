package com.brilworks.accounts.config;

import com.brilworks.accounts.config.tokenstore.TokenStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class JWTConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().
                disable().
                cors().
                disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll().and().
                authorizeRequests().
                antMatchers("/rest/**", "/swagger-ui*/**", "/api-docs").
                permitAll().
                anyRequest().authenticated().
                and().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.csrf().disable().addFilterBefore(authenticationTokenProcessingFilter(), FilterSecurityInterceptor.class);
        http.headers().frameOptions().disable();
    }

    @Bean(name = "passwordEncoder")
    public PasswordEncoder passwordencoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private TokenStoreService tokenStoreService;

    @Bean
    public AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter() {
        return new AuthenticationTokenProcessingFilter(tokenStoreService, "ANONYMOUS", "anonymousUser");
    }

}
