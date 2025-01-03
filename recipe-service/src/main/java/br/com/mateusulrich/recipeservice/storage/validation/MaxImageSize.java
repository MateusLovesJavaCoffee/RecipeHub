package br.com.mateusulrich.recipeservice.storage.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { MaxImageSizeValidator.class })
public @interface MaxImageSize {

    String message() default "Invalid max image size";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String max();
}
