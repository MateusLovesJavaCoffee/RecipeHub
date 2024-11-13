package br.com.mateusulrich.recipeservice.api.controller.handler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProblemDetail {

    @Schema(example = "2024-11-09T12:36:00Z", description = "Timestamp of when the error occurred")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant occurredAt;

    @Schema(example = "Invalid data", description = "A short title or summary of the error")
    private String title;

    @Schema(example = "422", description = "HTTP status code indicating the type of error")
    private int status;

    @Schema(example = "One or more fields are invalid. Please correct them and try again.", description = "Detailed description of the error")
    private String detail;

    @Schema(example = "/api/ingredients", description = "The path of the API endpoint where the error occurred")
    private String path;

    @Schema(description = "List of objects or fields that caused the error")
    private List<ValidationError> validationErrors = new ArrayList<>();

    public ProblemDetail(String title, HttpStatus status, String detail, String path) {
        this.occurredAt = Instant.now();
        this.title = title;
        this.status = status.value();
        this.detail = detail;
        this.path = path;
    }
    public void addError(final String fieldName, final String message) {
        validationErrors.removeIf(x -> x.field().equals(fieldName));
        validationErrors.add(new ValidationError(fieldName, message));
    }
}
