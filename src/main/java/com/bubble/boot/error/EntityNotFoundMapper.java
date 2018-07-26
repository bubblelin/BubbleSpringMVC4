package com.bubble.boot.error;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yanlin
 */
@Slf4j
@ControllerAdvice
public class EntityNotFoundMapper {

	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity cound not be found")
	public void handleNotFound(HttpServletRequest req, Exception ex) {
	
		log.error("request:" + req.getRequestURL() + " raised " + ex);
		
		// api接口异常，可以不用再设置视图
		// ModelAndView mv = new ModelAndView();
		// mv.addObject("Exception", ex);
		// mv.addObject("url", req.getRequestURL());
		// mv.setViewName("error"); 
	}
}
