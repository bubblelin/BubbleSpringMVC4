package com.bubble.boot.config;

import java.time.LocalDate;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.UrlPathHelper;

import com.bubble.boot.date.USLocalDateFormatter;

/**
 * @author yanlin
 */
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter{

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatterForFieldType(LocalDate.class, new USLocalDateFormatter());
	}
	
	@Bean
	public LocaleResolver localeResolver() {
		// 在 HTTP 会话中查找和存储地域信息
		return new SessionLocaleResolver();
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");

		System.out.println("lang---->" + localeChangeInterceptor.getParamName());
		return localeChangeInterceptor;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	/**
	 * 处理 MultipartException，它需要在 Servlet 容器级别（也就是 Tomcat 级别）
	 * 来进行，因为这个异常不会由我们的控制器直接抛出
	 * @return
	 */
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		
		return container -> container.addErrorPages(new ErrorPage(
				MultipartException.class, "/uploadError"));
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setRemoveSemicolonContent(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}
}
