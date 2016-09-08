# Spotippos Code Challenge

Code Challenge


## Requisitos

* [Git](https://git-scm.com/downloads)
* [Docker](https://docs.docker.com/)


## Componentes

* [Java 8](http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
* [Apache Maven 3.3](http://maven.apache.org/)
* [Spring Boot 1.4](http://projects.spring.io/spring-boot/)
* [Swagger 2.0](http://swagger.io/)
* [HSQLDB](http://hsqldb.org/)
* [JPA Repositories](http://docs.spring.io/spring-data/jpa/docs/1.3.0.RELEASE/reference/html/jpa.repositories.html)
* [Docker Hub](https://hub.docker.com)

## Executar com docker hub

É possível rodar com docker usando a imagem dtakahata/spotippos

```sh
$ docker run -p 8080:8080 -it dtakahata/spotippos
```

## Carga de dados
A carga das provincias é feita pelo sistema, quanto a carga das propriedades você pode usar o comando abaixo:

$ curl -H "Content-Type: application/json" -X POST -d @./data/properties.json http://localhost:8080/loadProperties

## Documentação

http://localhost:8080/swagger-ui.html