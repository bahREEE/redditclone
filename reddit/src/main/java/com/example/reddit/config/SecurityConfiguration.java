package com.example.reddit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/*import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;*/
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;

import org.springframework.security.crypto.bcrypt.*;


@EnableWebSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final UserDetailsService userDetailsService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable()
                    .authorizeRequests()
                            .antMatchers("/api/auth/**")
                                .permitAll()
                                    .anyRequest()
                                        .authenticated();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {

        authenticationManagerBuilder.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());

    }
 /*
        auth.inMemoryAuthentication()
        .withUser("root")
        .password("toor")
        .roles("ADMIN")
        .credentialsExpired(true)
        .accountExpired(true)
        .accountLocked(true)
        .roles("USER");
    }
*/
    
}
