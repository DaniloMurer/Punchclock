package com.danilojakob.m223.punchclock.config;

import com.danilojakob.m223.punchclock.security.JwtAuthenticationFilter;
import com.danilojakob.m223.punchclock.security.JwtAuthorizationFilter;
import com.danilojakob.m223.punchclock.security.SecurityConstants;
import com.danilojakob.m223.punchclock.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.ws.rs.HttpMethod;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder encryption;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder encryption) {
        this.userDetailsService = userDetailsService;
        this.encryption = encryption;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SecurityConstants.SING_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                // Disable Spring Session Creation
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encryption);
    }

}
