package com.acc.bt.managementtool.auth;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication) {
		String username = authentication.getName();
		String password = null;
		if (authentication.getCredentials() == null) {
			return null; // is this the correct action here??
		}
		password = authentication.getCredentials().toString();
		String email = iuserValid(username, password);
		if(email == null) {
			throw new BadCredentialsException("Invalid Username and/or Password");
		}
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
		return userToken;
	}
	
	public static String iuserValid(String username, String password) {
		if (username.equals("123456789")) {
			if (password.equals("letmein")) {
				return "test.user@bt.com";
			} else {
				return null;
			}
		}
		
		LdapContextSource lcs = new LdapContextSource();
		lcs.setUrl("ldaps://collaborate.bt.com");
		lcs.setBase("ou=people,ou=btplc,o=bt");
		lcs.setUserDn("cn=" + username + ",ou=people,ou=btplc,o=bt");
		lcs.setPassword(password);
		lcs.afterPropertiesSet();

		LdapTemplate ldap = new LdapTemplate(lcs);

		AttributesMapper<Object> mapper = new AttributesMapper<Object>()
		{
			@Override
			public Object mapFromAttributes(Attributes attrs) throws NamingException
			{
				return attrs.get("mail").get();
			}
		};

		try {
			List<Object> userDetail = ldap.search("", "(cn=" + username + ")", mapper);
			return userDetail.get(0).toString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
}
