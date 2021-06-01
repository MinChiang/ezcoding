package com.ezcoding.common.foundation.core.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author MinChiang
 * @version 1.0.0
 * @date 2021-06-01 15:40
 */
@Documented
@Constraint(validatedBy = {SpecialValueValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface SpecialValue {

    String message() default "{com.ezcoding.common.foundation.core.validation.SpecialValue.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?> classOf() default String.class;

    String[] optionValues() default {};

}
