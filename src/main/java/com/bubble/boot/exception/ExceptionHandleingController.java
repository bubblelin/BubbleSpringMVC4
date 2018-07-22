package com.bubble.boot.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ExceptionHandleingController {

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation") // 409
	// @ExceptionHandler(DataIntegrityViolationException.class)
	public void  conflict(){
		
	}
	
	
	// @ExceptionHandler({SQLException.class, DataAccessException.class})
	public String databaseError(){
		return "databaseError";
	}
	
	
	@ExceptionHandler(ImageNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex){
		
		log.error("Request:" + req.getRequestURL() + " raised " + ex);
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("Exception", ex);
		mv.addObject("url", req.getRequestURL());
		mv.setViewName("error");
		return mv;
	}
}
