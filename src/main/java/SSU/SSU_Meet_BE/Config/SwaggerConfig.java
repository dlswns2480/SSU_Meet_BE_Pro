package SSU.SSU_Meet_BE.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI OpenApi() {

        return new OpenAPI()
                .info(new Info().title("SSU_MEET")
                        .description("SSU_MEET BE api 입니다.")
                        .version("v0.0.1"));
    }
}