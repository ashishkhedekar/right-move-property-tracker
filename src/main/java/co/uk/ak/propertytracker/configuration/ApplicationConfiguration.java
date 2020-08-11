package co.uk.ak.propertytracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class ApplicationConfiguration
{
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
}
