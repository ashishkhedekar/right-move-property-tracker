package co.uk.ak.rightmove.propertytracker.client;

import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@AllArgsConstructor
public class RightMoveWebClient
{
   private final ObjectMapper MAPPER = new ObjectMapper();

   @SneakyThrows
   public RightMoveResult callRightMove(String locationIdentifier)
   {
      final HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
               .uri(URI.create(String.format("https://www.rightmove.co.uk/api/_search?locationIdentifier=%s&minBedrooms=3&maxPrice=25000&numberOfPropertiesPerPage=24&radius=0.0&sortType=6&index=0&includeLetAgreed=true&viewType=LIST&channel=RENT&areaSizeUnit=sqft&currencyCode=GBP&isFetching=false", locationIdentifier)))
               .build();

      final HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
      return MAPPER.readValue(response.body(), RightMoveResult.class);
   }
}
