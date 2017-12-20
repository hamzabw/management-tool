package com.acc.bt.managementtool.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

  	if (request.getRequestURI().startsWith("/logout")
  			|| request.getRequestURI().startsWith("/login") 
  			|| !request.getMethod().equals("OPTIONS")) {
  		response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
  		response.setHeader("Access-Control-Allow-Credentials", "true");
  		response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
  		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
  	}
  		
  	filterChain.doFilter(request, response);
  }
	
}
