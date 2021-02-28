package com.chisom.authorizationserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * For configuring the end users recognized by this Authorization Server.
 *
 * @author Chisom.Iwowo
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Gets the {@link AuthenticationManager} to use. The default strategy is if
     * {@link #configure(AuthenticationManagerBuilder)} method is overridden to use the
     * {@link AuthenticationManagerBuilder} that was passed in. Otherwise, autowire the
     * {@link AuthenticationManager} by type.
     *
     * @return the {@link AuthenticationManager} to use
     * @throws Exception exception
     */
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();
    }
}
