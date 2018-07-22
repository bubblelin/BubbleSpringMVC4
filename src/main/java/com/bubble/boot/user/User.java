package com.bubble.boot.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class User {

	private String twitterHandler;
	private String email;
	private LocalDate birthDate;
	private List<String> tastes = new ArrayList<>();
}
