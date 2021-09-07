package com.example.demo;

import com.example.demo.custom.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    CustomAuthFailed authFailed;
//    @Autowired
//    CustomDeniedHandler customDeny;

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("user").password("{noop}123")
//                .authorities("ROLE_USER");
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(new CustomAuthenticationProvider());
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{noop}123")
                .roles("ADMIN")
                .and()
                .withUser("user")
                .password("{noop}123")
                .roles("USER")
                .and()
                .withUser("student")
                .password("{noop}123")
                .roles("USER")
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .anonymous().principal("guest").authorities("ROLE_GUEST") // give name to user without login
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/course").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/student").hasAnyRole( "ADMIN", "USER")
                .antMatchers("/hello").permitAll()  // login or not
                //.antMatchers("/hello").anonymous()  // without login
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(new CustomAuthFailed())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthFailed() )
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomDeniedHandler())
        ;

        //http.addFilterBefore()

    }
}
