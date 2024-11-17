package br.com.mateusulrich.recipeservice.storage.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

public class ImageContentTypeValidator implements ConstraintValidator<ImageContentType, MultipartFile> {

    private Set<String> allowedContentTypes;

    @Override
    public void initialize(ImageContentType constraint) {
        this.allowedContentTypes = Set.of(constraint.allowed());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || this.allowedContentTypes.contains(multipartFile.getContentType());
    }
}
