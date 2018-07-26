package com.bubble.boot.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * 监听器：为了启用登录、注册场景。
 * 当已知用户再次登录时，将会被调用
 * @author yanlin
 */
@Component
public class AuthenticatingSignInAdapter implements SignInAdapter{

	public static void authenticate(Connection<?> connection) {
		UserProfile userProfile = connection.fetchUserProfile();
		String username = userProfile.getUsername();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				username, null, null);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		System.out.println(String.format("User %s %s connected.", 
				userProfile.getFirstName(), userProfile.getLastName()));
	}
	
	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		authenticate(connection);
		return null;
	}

}
