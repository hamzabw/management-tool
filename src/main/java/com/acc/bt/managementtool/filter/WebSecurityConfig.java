package com.acc.bt.managementtool.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.acc.bt.managementtool.auth.CustomAuthenticationManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationManager authManager;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
    	
    	http.csrf().disable();
    	
    	http.httpBasic().and().authorizeRequests()
          .anyRequest().permitAll().and()
          .formLogin()
          .defaultSuccessUrl("/success", true).and()
          .logout().permitAll();
    }
   
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
    	return authManager;
    }
}	
