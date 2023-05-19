package com.txy.blog;

import com.txy.blog.service.RoleService;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.CommandLineRunner;
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
public class BlogApplication implements CommandLineRunner {

    private final RoleService roleService;

    public BlogApplication(RoleService roleService) {
        this.roleService = roleService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        roleService.createRole("ROLE_ADMIN");
        roleService.createRole("ROLE_USER");
    }
}
