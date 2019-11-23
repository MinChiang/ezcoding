package com.ezcoding.common.foundation.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2019-08-07 11:13
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Enum> {

    private Enum[] enums;

    @Override
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (Enum e : this.enums) {
            if (value.equals(e)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enums = constraintAnnotation.enumClass().getEnumConstants();
    }

}
