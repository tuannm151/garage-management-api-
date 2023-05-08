package com.example.GARA_API.configuration;

import com.example.GARA_API.exception.RestAccessDeniedHandler;
import com.example.GARA_API.filter.CustomAuthenticationFilter;
import com.example.GARA_API.filter.CustomAuthorizationFilter;
import com.example.GARA_API.services.appUser.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final Argon2PasswordEncoder argon2PasswordEncoder;
    private final AppUserDetailsService AppUserDetailsService;


    @Bean
    RestAccessDeniedHandler accessDeniedHandler() {
        return new RestAccessDeniedHandler();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(AppUserDetailsService).passwordEncoder(argon2PasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean());

        customAuthenticationFilter.setFilterProcessesUrl("/api/auth/login");
        http
                .cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers( "/api/auth/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .antMatchers("/api/auth/users/customer").hasAnyAuthority("ROLE_USER")
                .antMatchers("/api/auth/users/**", "/api/auth/login").permitAll();
        http
                .authorizeRequests()
                        .antMatchers("/api/product/admin/**").hasAnyAuthority(
                                "ROLE_ADMIN")
//
                .antMatchers("/api/product/**").permitAll();

        http
                .authorizeRequests()
                        .antMatchers("/api/cart/**").hasAnyAuthority("ROLE_USER");
        http
                .authorizeRequests()
                        .antMatchers("/api/order/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .antMatchers("/api/order/**").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());

    }

}
