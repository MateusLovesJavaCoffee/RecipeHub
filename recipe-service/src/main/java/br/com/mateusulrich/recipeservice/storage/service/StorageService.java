package br.com.mateusulrich.recipeservice.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface StorageService {
    CompletableFuture<String> store(MultipartFile multipartFile, String folder) throws IOException;
    String generateFileUrl(String folder);
    void delete(String folder);
}
