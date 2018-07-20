package com.bubble.boot.date;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * @author yanlin
 */
public class USLocalDateFormatter implements Formatter<LocalDate>{

	public static final String CHINA_PATTERN = "MM/dd/yyyy";
	public static final String NORMAL_PATTERN = "yyyy/MM/dd";
	
	@Override
	public String print(LocalDate object, Locale locale) {
		
		return DateTimeFormatter.ofPattern(getPattern(locale)).format(object);
	}

	@Override
	public LocalDate parse(String text, Locale locale) throws ParseException {
		
		return LocalDate.parse(text, DateTimeFormatter.ofPattern(getPattern(locale)));
	}

	
	public static String getPattern(Locale locale) {
		
		return isChina(locale) ? CHINA_PATTERN : NORMAL_PATTERN;
	}

	
	private static boolean isChina(Locale locale) {
		
		return Locale.CHINA.getCountry().equals(locale.getCountry());
	}

}
