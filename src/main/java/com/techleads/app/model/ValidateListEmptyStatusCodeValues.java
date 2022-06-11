package com.techleads.app.model;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateListEmptyStatusCodeValues implements ConstraintValidator<ValidateListEmptyStatusCodes, List<String>> {

	@Override
	public boolean isValid(List<String> value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub

		List<String> list = Arrays.asList(new String[] { "MA", "DL", "TJ" });

		return value == null ? false : list.containsAll(value);
	}

//	@Override
//	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
//		List<String> list = Arrays.asList(new String[] { "MA", "DL", "TJ" });
//		return list.contains(value);
//	}

}
