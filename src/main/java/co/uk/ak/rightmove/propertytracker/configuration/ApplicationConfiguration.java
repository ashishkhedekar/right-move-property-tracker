package co.uk.ak.rightmove.propertytracker.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ApplicationConfiguration
{
   @Bean(name = "jsonMapper")
   @Primary
   public ObjectMapper jsonMapper() {
      return new CustomJsonObjectMapper();
   }
}
