package wb.z.Config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:/home/NumerousStudentsMomentsPicture/");
        registry.addResourceHandler("/thumbnail/**")
                .addResourceLocations("file:/home/thumbnail/");
    }
}

