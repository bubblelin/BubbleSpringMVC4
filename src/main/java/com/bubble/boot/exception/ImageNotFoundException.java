package com.bubble.boot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such Image")
public class ImageNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

}
