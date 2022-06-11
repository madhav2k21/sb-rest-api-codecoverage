package com.techleads.app.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidateListRoutesSequenceCodeValues
		implements ConstraintValidator<ValidateListRouteSequenceCodes, List<Route>> {

	@Override
	public boolean isValid(List<Route> value, ConstraintValidatorContext context) {
		if (null == value) {
			return false;
		}
		List<String> routeCodes = value.stream().map(Route::getRoutingSequenceCode).collect(Collectors.toList());
		List<String> actualValues = Arrays.asList(new String[] { "A", "B", "C" });
		return actualValues.containsAll(routeCodes);
	}

}
