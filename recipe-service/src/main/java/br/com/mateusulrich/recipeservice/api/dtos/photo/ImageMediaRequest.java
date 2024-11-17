package br.com.mateusulrich.recipeservice.api.dtos.photo;

import br.com.mateusulrich.recipeservice.storage.validation.ImageContentType;
import br.com.mateusulrich.recipeservice.storage.validation.MaxImageSize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

public record ImageMediaRequest(

    @Schema(description = "Arquivo da foto do produto (máximo 500kb, apenas JPEG e PNG)")
    @MaxImageSize(max = "500KB")
    @ImageContentType(allowed = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    MultipartFile file

) {
}
