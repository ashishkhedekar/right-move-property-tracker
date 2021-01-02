package co.uk.ak.propertytracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApplicationConfiguration
{
   @Value("${right.move.base.url}")
   private String rightMoveBaseUrl;

   public String getRightMoveBaseUrl()
   {
      return rightMoveBaseUrl;
   }

   @Bean(name = "jsonMapper")
   @Primary
   public ObjectMapper jsonMapper() {
      return new CustomJsonObjectMapper();
   }

   @Bean
   @Primary
   public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
      final FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
      bean.setTemplateLoaderPath("classpath:/templates/");
      return bean;
   }

   @Bean
   public WebMvcConfigurer corsConfigurer() {
      return new WebMvcConfigurer() {
         @Override
         public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/market-details").allowedOrigins("http://localhost:4200", "https://aqueous-retreat-09818.herokuapp.com/");
            registry.addMapping("/market-summary").allowedOrigins("http://localhost:4200", "https://aqueous-retreat-09818.herokuapp.com/");
         }
      };
   }

}
