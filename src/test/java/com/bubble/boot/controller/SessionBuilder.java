package com.bubble.boot.controller;

import java.util.Arrays;

import org.springframework.mock.web.MockHttpSession;

import com.bubble.boot.profile.UserProfileSession;

/**
 * @author yanlin
 */
public class SessionBuilder {

	private final MockHttpSession session;
	
	UserProfileSession sessionBean;
	
	public SessionBuilder() {
		this.session = new MockHttpSession();
		this.sessionBean = new UserProfileSession();
		session.setAttribute("scopedTarget.userProfileSession", sessionBean);// ?sessionBean是否有值？
	}
	
	public SessionBuilder userTastes(String... tastes) {
		sessionBean.setTastes(Arrays.asList(tastes));
		return this;
	}
	
	public MockHttpSession build() {
		return session;
	}
}
