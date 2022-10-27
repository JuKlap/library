package management.system.library.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Import(BeanValidatorPluginsConfiguration.class)
@Configuration
public class Config {

    @Bean
    public Docket swaggerConfiguration() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("management.system.library"))
                .paths(PathSelectors.ant("/error").negate())
                .build()
                .apiInfo(apiInfo());
        docket.useDefaultResponseMessages(true);
        return appendTags(docket);
    }

    private Docket appendTags(Docket docket) {
        return docket.tags(
                new Tag(DescriptionVariables.AUTHORS,
                        "Used to get and delete authors in the library"),
                new Tag(DescriptionVariables.BOOKS,
                        "Used to get and delete books in the library")
        );
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Library management systems API")
                .description("Library management systems API")
                .version("1.0")
                .build();
    }

}
