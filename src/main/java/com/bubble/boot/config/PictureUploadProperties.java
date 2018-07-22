package com.bubble.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yanlin
 */
@Setter@Getter
@Component
@ConfigurationProperties(prefix = "upload.pictures")
public class PictureUploadProperties {

	private Resource uploadPath;
	private Resource anonymousPictures;
	
}
