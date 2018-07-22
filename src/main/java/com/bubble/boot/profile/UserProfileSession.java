package com.bubble.boot.profile;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanlin
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) // 使用CGLib代理
@Setter@Getter
public class UserProfileSession implements Serializable{

	/**
	 * HTTP会话能够将任意的对象存储在内存之中，不过让存储在会话中的对象支持序列化是一种好的实践
	 */
	private static final long serialVersionUID = 1L;
	
	private Resource picturePath;
	private String twitterHandle;
	private String email;
	private LocalDate birthDate;
	private List<String> tastes = new ArrayList<>();
	
	public void saveForm(ProfileForm profileForm) {
		this.twitterHandle = profileForm.getTwitterHandle();
		this.email = profileForm.getEmail();
		this.birthDate = profileForm.getBirthDate();
		this.tastes = profileForm.getTastes();
	}
	
	public ProfileForm toForm() {
		ProfileForm profileForm = new ProfileForm();
		profileForm.setTwitterHandle(twitterHandle);
		profileForm.setEmail(email);
		profileForm.setBirthDate(birthDate);
		profileForm.setTastes(tastes);
		return profileForm;
	}
}
