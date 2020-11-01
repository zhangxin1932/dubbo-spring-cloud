package com.zy.producer.fileoperation.config;

import com.zy.producer.fileoperation.service.StorageService;
import io.minio.MinioClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBean {
    @Bean
    CommandLineRunner init(final StorageService storageService) {
        return args -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

    @Bean
    public MinioClient minioClient() {
        MinioClient minioClient = new MinioClient("http://192.168.0.156:9999", "minioadmin", "minioadmin");
        return minioClient;
    }

}
