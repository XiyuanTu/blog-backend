# Blog Backend APIs

A regular blog system backend project with the following functionalities:

+ Authentication and authorization
+ CRUD operations for blog posts, comments and categories

## Technology Stack

| Component     | Technology        |
|---------------|-------------------|
| Backend       | Spring Boot       |
| Auth          | Spring Security   |
| Database      | Mysql             |
| ORM           | Hibernate         |
| Documentation | Swagger/Springdoc |
| CI/CD         | Docker            |
| Deployment    | AWS               |

## How to deploy on AWS

It took me a lot of time to figure out how to deploy its docker image on AWS, so I'd like to document the deployment
process.

+ Create a db instance on Amazon RDS
+ Update the database url with the instance's url before packaging
+ Package
+ Create an Amazon ECS repository and upload the docker image
  + When building the docker image, the platform needs to be the same. For
    example: ```docker build -t blog --platform=linux/arm64 .```
+ Create a cluster and a task, and then run the task in the cluster
