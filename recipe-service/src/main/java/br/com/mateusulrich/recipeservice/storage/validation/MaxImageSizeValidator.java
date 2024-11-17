package br.com.mateusulrich.recipeservice.storage.validation;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MaxImageSizeValidator implements ConstraintValidator<MaxImageSize, MultipartFile> {

    private DataSize maxSize;

    @Override
    public void initialize(MaxImageSize constraintAnnotation) {
       this.maxSize = DataSize.parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
       if (multipartFile == null) {
           return false;
       }
        return multipartFile.getSize() <= maxSize.toBytes();
    }
}
