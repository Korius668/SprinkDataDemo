package prez.prez2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Prez2Application {
    public static void main(String[] args) {
        SpringApplication.run(Prez2Application.class, args);
    }
}