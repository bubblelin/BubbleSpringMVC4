package com.bubble.boot.search;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.TwitterProfile;

import lombok.Getter;

@Getter
public class LightTweet implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String profileImageUrl;
	private String user;
	private String text;
	private LocalDateTime date;
	private String lang;
	private Integer retweetCount;
	
	public LightTweet(String text){
		this.text = text;
	}
	
	public static LightTweet ofTweet(Tweet tweet){
		LightTweet lightTweet = new LightTweet(tweet.getText());
		Date createdAt = tweet.getCreatedAt();
		if(createdAt != null){
			lightTweet.date = LocalDateTime.ofInstant(createdAt.toInstant(), ZoneId.systemDefault());
		}
		TwitterProfile tweetUser = tweet.getUser();
		if(tweetUser != null){
			lightTweet.user = tweetUser.getName();
			lightTweet.profileImageUrl = tweetUser.getProfileImageUrl();
		}
		lightTweet.lang = tweet.getLanguageCode();
		lightTweet.retweetCount = tweet.getRetweetCount();
		return lightTweet;
	}
}
