package com.bubble.boot.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author yanlin
 */
@Controller
public class LoginController {

	@RequestMapping("/login")
	public String authenticate() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String name = auth.getName(); //主体名，即登录用户名
	    System.out.println(name + ", " + auth.getAuthorities());//saysky 或 anonymousUser
		return "login";
	}
}
