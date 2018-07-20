package com.bubble.boot.date;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yanlin
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=PastLocalDate.PastValidator.class)
@Documented
public @interface PastLocalDate {

	String message() default "{javax.validation.constraints.Past.message}";
	
	Class<?>[] groups() default {};
	
	
	class PastValidator implements ConstraintValidator<PastLocalDate, LocalDate>{

		@Override
		public void initialize(PastLocalDate constraintAnnotation) {
			
		}

		@Override
		public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
			//保证我们的日期的确是一个过去的值
			return value == null || value.isBefore(LocalDate.now());
		}
		
	}
}
