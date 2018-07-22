package com.bubble.boot.profile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yanlin
 */
@Setter@Getter@ToString
public class ProfileForm {

	@NotEmpty(message = "不能空")
	@Size(min = 2, max = 16, message = "长度须在{2}~{1}")
	private String twitterHandle;
	
	@Email(message = "请填写你的有效邮箱地址")
	@NotEmpty(message = "邮箱地址不能空")
	private String email;
	
	@NotNull(message = "出生日期不能空")
//	@PastLocalDate
	private LocalDate birthDate;
	
	@NotEmpty
	private List<String> tastes = new ArrayList<>();
}
