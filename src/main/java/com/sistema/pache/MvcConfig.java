package com.sistema.pache;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
          .addResourceHandler("/pdfs/**")
          .addResourceLocations("file:/sistema/media/10pache/temp");
        registry
        .addResourceHandler("/imagens/**")
        .addResourceLocations("file:/sistema/media/10pache/galeria/");
        
        if (!registry.hasMappingForPattern("/webjars/**")) {
    		registry.addResourceHandler("/webjars/**").addResourceLocations(
    				"classpath:/META-INF/resources/webjars/");
    	}
    	if (!registry.hasMappingForPattern("/**")) {
    		registry.addResourceHandler("/**").addResourceLocations(
    				"classpath:/META-INF/resources/", "classpath:/resources/",
    				"classpath:/static/", "classpath:/public/", "classpath:/relatorios/");
    	}
    }
}