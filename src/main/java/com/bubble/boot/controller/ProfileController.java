package com.bubble.boot.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bubble.boot.config.UserProfileSession;
import com.bubble.boot.date.USLocalDateFormatter;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yanlin
 */
@Slf4j
@Controller
public class ProfileController {

	private UserProfileSession userProfileSession;
	
	// 如果不适用spring-test框架，使用构造函数注入比域注入更好
	@Autowired
	public ProfileController(UserProfileSession userProfileSession) {
		this.userProfileSession = userProfileSession;
	}
	
	/**
	 * 暴露一个属性给Web页面
	 */
	@ModelAttribute("dateFormat")
	public String localeFormat(Locale locale) {
		return USLocalDateFormatter.getPattern(locale);
	}
	
	/**
	 * 暴露ProfileForm为模型属性，可以不用在displayProfile中注入
	 */
	@ModelAttribute
	public ProfileForm getProfileForm() {
		return userProfileSession.toForm();
	}
	
//	@RequestMapping("/profile")
//	public String displayProfile(ProfileForm profileForm) {
//		return "/profile/profilePage";
//	}
	
	@RequestMapping(value = "/profile", params = {"save"}, method = RequestMethod.POST)
	public String saveProfile(@Valid ProfileForm profileForm, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			// 如果出现错误不进行重定向，以便在同一个web页面展示错误
			return "profile/profilePage";
		}
		userProfileSession.saveForm(profileForm); // 放在session中
		log.debug("save ok: {}", profileForm);
		return "redirect:/profile";
	}
	

	@RequestMapping(value = "/profile", params = {"addTaste"})
	public String addRow(ProfileForm profileForm) {
		profileForm.getTastes().add(null);
		return "profile/profilePage";
	}
	
	
	@RequestMapping(value = "/profile", params = {"removeTaste"})
	public String removeRow(ProfileForm profileForm, HttpServletRequest req) {
		Integer rowId = Integer.valueOf(req.getParameter("removeTaste"));		
		profileForm.getTastes().remove(rowId.intValue());
		return "profile/profilePage";
	}
}
