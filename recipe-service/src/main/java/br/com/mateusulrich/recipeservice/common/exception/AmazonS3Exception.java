package br.com.mateusulrich.recipeservice.common.exception;

public class AmazonS3Exception extends DomainException{
    public AmazonS3Exception(String message, Throwable cause) {
        super(message, cause);
    }
}
