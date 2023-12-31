package com.locar.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

      @Bean
      public ClassLoaderTemplateResolver pdfTemplateResolver() {
         ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
         resolver.setSuffix(".html");
         resolver.setTemplateMode("HTML5");
         resolver.setCharacterEncoding("UTF-8");
         resolver.setOrder(1);
         return resolver;
      }
}
