package com.bubble.boot.error;
/**
 * 在控制器中需要处理这个异常，否则Spring默认将会抛出500错误
 * @author yanlin
 */
public class EntityNotFoundException extends Exception{

	public EntityNotFoundException(String message) {
		super(message);
	}
	
	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
