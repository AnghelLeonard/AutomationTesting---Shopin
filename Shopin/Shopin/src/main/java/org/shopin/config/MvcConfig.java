package org.shopin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/pending").setViewName("credentials/pending");
        registry.addViewController("/activate").setViewName("credentials/activate");
        registry.addViewController("/contact").setViewName("contact");
        registry.addViewController("/thanks").setViewName("thanks");

        registry.addViewController("/x10qerK0/master").setViewName("admin/master");
    }
}
