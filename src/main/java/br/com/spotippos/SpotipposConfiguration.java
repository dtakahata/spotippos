package br.com.spotippos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@ComponentScan("br.com.spotippos")
@SpringBootApplication
@EnableJpaRepositories(basePackages = "br.com.spotippos.repository")
public class SpotipposConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(SpotipposConfiguration.class, args);
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}
