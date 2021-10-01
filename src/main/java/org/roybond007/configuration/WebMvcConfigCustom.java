package org.roybond007.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfigCustom implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		File resourceDirectory = new File("/cover");
		
		Path resourceDirectoryPath = resourceDirectory.toPath();
		
		if(Files.notExists(resourceDirectoryPath)) {
			try {
				Files.createDirectories(resourceDirectoryPath);
			} catch (IOException e) {
				
				System.err.println("something went wrong while initializing" + e.getLocalizedMessage());
			}
		}

		
		registry
			.addResourceHandler("/**")
			.addResourceLocations(new FileSystemResource("C:" + File.separator + "cover" + File.separator));
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry
			.addMapping("/**")
			.allowCredentials(true)
			.allowedHeaders("*")
			.allowedMethods("*")
			.allowedOrigins("http://localhost:3000")
			.exposedHeaders("*");
	}
	
}
