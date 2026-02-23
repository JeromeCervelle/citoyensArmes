package fr.limoges.valadon.btssio.citoyensarmes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class CitoyensArmesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CitoyensArmesApplication.class, args);
    }

}
