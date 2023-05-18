package com.txy.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Blog Backend APIs",
                description = "Spring Boot Blog Backend APIs Documentation",
                version = "v1.0",
                contact = @Contact(
                        name = "Xiyuan Tu",
                        email = "xiyuan.tyler@gmail.com",
                        url = "https://www.linkedin.com/in/xiyuan/"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "Github Repo",
                url = "https://github.com/XiyuanTu/blog-backend"
        )
)
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
