package projeto.api.utils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UtilsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UtilsApplication.class, args);
	}

}
