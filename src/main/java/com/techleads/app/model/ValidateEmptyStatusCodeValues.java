package com.techleads.app.model;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateEmptyStatusCodeValues implements ConstraintValidator<ValidateEmptyStatusCode, CharSequence> {

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		List<String> list = Arrays.asList(new String[] { "MA", "DL", "TJ" });
		return list.contains(value);
	}

}
