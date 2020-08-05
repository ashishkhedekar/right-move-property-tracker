package co.uk.ak.rightmove.propertytracker.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:general.properties")
public class ApplicationConfiguration
{
   @Value( "${current.environment}" )
   private String currentEnvironment;
}
