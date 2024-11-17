package br.com.mateusulrich.recipeservice.api.controller.handler;

import br.com.mateusulrich.recipeservice.common.exception.DomainException;
import br.com.mateusulrich.recipeservice.common.exception.EntityInUseException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandle {

    public static final String VALIDATION_ERRORS_DETAIL = "One or more fields are invalid. Please correct them and try again.";

    @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ProblemDetail> NotFoundException(NotFoundException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            ProblemDetail problem = new ProblemDetail(
                    "RESOURCE NOT FOUND",
                    status,
                    ex.getMessage(),
                    request.getRequestURI());
            return ResponseEntity.status(status).body(problem);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ProblemDetail> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {

            return handleBindException(ex, request);
        }
        @ExceptionHandler(BindException.class)
        public ResponseEntity<ProblemDetail> handleBindException(BindException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
            ProblemDetail problem = new ProblemDetail(
                    "Validation Errors",
                    status,
                    VALIDATION_ERRORS_DETAIL,
                    request.getRequestURI()

            );
            for (FieldError f : ex.getBindingResult().getFieldErrors()) {
                problem.addError(f.getField(), f.getDefaultMessage());
            }
            return ResponseEntity.status(status).body(problem);
        }


        @ExceptionHandler(EntityInUseException.class)
        public ResponseEntity<ProblemDetail> entityInUseException(EntityInUseException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.CONFLICT;
            String title = "ENTITY IN USE";
            String detail = ex.getMessage();
            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());
            return ResponseEntity.status(status).body(problem);
        }


        @ExceptionHandler(DomainException.class)
        public ResponseEntity<ProblemDetail> domainException(DomainException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.BAD_REQUEST;;
            String detail = ex.getMessage();
            String title = "DOMAIN EXCEPTION VIOLATION";
            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());
            return ResponseEntity.status(status).body(problem);
        }

        @ExceptionHandler(InvalidFormatException.class)
        private ResponseEntity<ProblemDetail> invalidFormatException(InvalidFormatException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String path = joinPath(ex.getPath());
            String title = "INVALID PROPERTY FORMAT";

            String detail = String.format("The property '%s' received the value '%s', "
                            + "which is of an invalid type. Please correct it and provide a value compatible with the type %s.",
                    path, ex.getValue(), ex.getTargetType().getSimpleName());

            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());

            return ResponseEntity.status(status).body(problem);
        }
        @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
        private ResponseEntity<ProblemDetail> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            String title = "Not Acceptable Media Type";
            String detail = "The requested media type is not supported by the server. Please verify the 'Accept' header in your request and try again.";
            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());
            return ResponseEntity.status(status).body(problem);
        }

//        @ExceptionHandler(Exception.class)
//        public ResponseEntity<ProblemDetail> handleUncaught(Exception ex, HttpServletRequest request) {
//            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//            String title = "Internal Server Error";
//            String detail = "An unexpected error occurred on the server. Please try again later. If the problem persists, contact the system administrator.";
//            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());
//
//            return ResponseEntity.status(status).body(problem);
//        }

        @ExceptionHandler(MissingServletRequestParameterException.class)
        public ResponseEntity<ProblemDetail> missingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String title = "Missing Request Parameter";
        String detail = ex.getMessage();

        ProblemDetail problemDetail = new ProblemDetail(title, status, detail, request.getRequestURI());
        return ResponseEntity.status(status).body(problemDetail);
        }

        @ExceptionHandler(NoHandlerFoundException.class)
        public ResponseEntity<ProblemDetail> noHandlerFoundException (NoHandlerFoundException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.NOT_FOUND;
            String title = "Resource Not Found";
            String detail = "The resource '%s' you are trying to access does not exist.".formatted(ex.getRequestURL());
            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());

            return ResponseEntity.status(status).body(problem);
        }

        @ExceptionHandler(TypeMismatchException.class)
        public ResponseEntity<ProblemDetail> typeMismatchException (TypeMismatchException ex, HttpServletRequest request) {
            if (ex instanceof MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
                return methodArgumentTypeMismatchException(
                        methodArgumentTypeMismatchException, request);
            }
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String title = "Invalid Parameter Type";
            String detail = String.format("The parameter '%s' has received the value '%s', which is of an invalid type. Please provide a value of type %s.",
                    ex.getPropertyName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());

            return ResponseEntity.status(status).body(problem);
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ProblemDetail> methodArgumentTypeMismatchException (MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String title = "Invalid Argument Type";
            String detail = String.format("The argument '%s' has received the value '%s', which is of an invalid type. Please provide a value of type %s.",
                    ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());
            return ResponseEntity.status(status).body(problem);
        }

        @ExceptionHandler(PropertyBindingException.class)
        public ResponseEntity<ProblemDetail> propertyBindingException (PropertyBindingException ex, HttpServletRequest request) {
            String path = joinPath(ex.getPath());
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String title = "Invalid Property Binding";
            String detail = "The property '%s' does not exist or cannot be mapped. Please check the field name and try again.".formatted(path);
            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());

            return ResponseEntity.status(status).body(problem);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<ProblemDetail> accessDeniedException (AccessDeniedException ex, HttpServletRequest request) {
            HttpStatus status = HttpStatus.FORBIDDEN;
            String title = "Access Denied";
            String detail = "You do not have permission to access the resource '%s'. Please check your permissions and try again.";
            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());

            return ResponseEntity.status(status).body(problem);
        }
        @ExceptionHandler(HttpMessageNotReadableException.class)
        private ResponseEntity<ProblemDetail> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
            Throwable rootCause = ExceptionUtils.getRootCause(ex);
            if (rootCause instanceof InvalidFormatException ife) {
                return invalidFormatException(ife, request);
            } else if (rootCause instanceof PropertyBindingException pbe) {
                return propertyBindingException(pbe,  request);
            }
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String title = "Malformed JSON request";
            String detail = "The request body is not readable or is incorrectly formatted. Please ensure that the request body is properly structured in JSON format";
            ProblemDetail problem = new ProblemDetail(title, status, detail, request.getRequestURI());

            return ResponseEntity.status(status).body(problem);
        }

        private String joinPath(List<JsonMappingException.Reference> references) {
            return references.stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .collect(Collectors.joining("."));
        }

}
