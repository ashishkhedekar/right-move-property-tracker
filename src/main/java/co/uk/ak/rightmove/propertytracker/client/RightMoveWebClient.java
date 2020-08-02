package co.uk.ak.rightmove.propertytracker.client;

import co.uk.ak.rightmove.propertytracker.model.Property;
import co.uk.ak.rightmove.propertytracker.model.RightMoveResult;
import co.uk.ak.rightmove.propertytracker.model.RightmoveJsonSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class RightMoveWebClient
{
   private static final Logger LOG = LoggerFactory.getLogger(RightMoveWebClient.class);

   @SneakyThrows
   public void callRightMove()
   {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create("https://www.rightmove.co.uk/api/_search?locationIdentifier=REGION%5E239&minBedrooms=3&maxPrice=25000&numberOfPropertiesPerPage=24&radius=0.0&sortType=6&index=0&includeLetAgreed=true&viewType=LIST&channel=RENT&areaSizeUnit=sqft&currencyCode=GBP&isFetching=false"))
               .build();

      HttpResponse<String> response =
               client.send(request, HttpResponse.BodyHandlers.ofString());

      ObjectMapper objectMapper = new ObjectMapper();


      RightMoveResult rightMoveResult = objectMapper.readValue(response.body(), RightMoveResult.class);

      LOG.info("Found [{}] properties on rightmove ", rightMoveResult.getResultCount());

      for (Property property : rightMoveResult.getProperties())
      {
         LOG.info("Property [{}] is let agreed [{}] ", property.getId(), property.getDisplayStatus());
      }


   }

}
