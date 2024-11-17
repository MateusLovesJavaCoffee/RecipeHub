package br.com.mateusulrich.recipeservice.storage.service;

import br.com.mateusulrich.recipeservice.common.exception.AmazonS3Exception;
import br.com.mateusulrich.recipeservice.common.utils.MD5ChecksumUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class S3Service implements StorageService {

    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.s3.region}")
    private String region;

    public S3Service(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    @Async
    public CompletableFuture<String> store(MultipartFile file, String folder) {
        try {
            uploadFile(file, folder);
            String uploadedUrl = generateFileUrl(folder);
            return CompletableFuture.completedFuture(uploadedUrl);
        } catch (IOException e) {
            return CompletableFuture.failedFuture(new AmazonS3Exception("Erro ao realizar upload para o S3", e.getCause()));
        }
    }

    private void uploadFile(MultipartFile file, String folder) throws IOException {
        try {
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(file.getContentType());
            meta.setContentLength(file.getSize());
            meta.setContentMD5(MD5ChecksumUtils.calculateMD5Checksum(file.getBytes()));

            log.info("Starting file upload to S3");

            s3client.putObject(bucketName, folder, file.getInputStream(), meta);

            log.info("Upload completed successfully!");
        } catch (AmazonServiceException e) {
            log.error("Amazon service error: {}", e.getErrorMessage());
            log.error("Error Code: {}", e.getErrorCode());
            throw new AmazonS3Exception("Amazon S3 Service error:", e.getCause());
        } catch (AmazonClientException e) {
            log.error("Client error amazon: {}", e.getMessage());
            throw new AmazonS3Exception("Amazon S3 Client error", e.getCause());
        }
    }

    public String generateFileUrl(String folder) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, folder);
    }

    @Override
    public void delete(String folder) {
        try {
            log.info("Starting file deletion: {}", folder);
            s3client.deleteObject(bucketName, folder);
            log.info("File deleted Successfully: {}", folder);
        } catch (AmazonServiceException e) {
            log.error("Error deleting file in S3. Error: {}", e.getMessage());
        } catch (AmazonClientException e) {
            log.error("Client error deleting file in S3. Error: {}", e.getMessage());
        }
    }
}
