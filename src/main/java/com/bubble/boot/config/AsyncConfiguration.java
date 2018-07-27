package com.bubble.boot.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author yanlin
 */
@Configuration
@EnableAsync// 启用spring的异步功能
public class AsyncConfiguration implements AsyncConfigurer{

	protected final Log log = LogFactory.getLog(getClass());
	
	@Override
	public Executor getAsyncExecutor() {
		return Executors.newFixedThreadPool(10);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> log.error("无法捕捉assync 异常", ex);
	}

}
