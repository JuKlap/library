package management.system.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan({"management.system.library.mappers"})
@SpringBootApplication
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);
	}

}
