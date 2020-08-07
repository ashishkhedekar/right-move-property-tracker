package co.uk.ak.rightmove.propertytracker.client;

import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class RightMoveWebClient
{
   private static final Logger LOG = LoggerFactory.getLogger(RightMoveWebClient.class);

   private final ObjectMapper MAPPER = new ObjectMapper();

   @Value("${right.move.base.url}")
   private String rightMoveBaseUrl;

   @Value("${right.move.search.api.url}")
   private String rightMoveSearchApiUrl;

   @Autowired
   private RestTemplate restTemplate;

   @SneakyThrows
   public RightMoveResult callRightMove(String locationIdentifier)
   {
      return restTemplate.getForObject(URI.create(buildSearchUrl(locationIdentifier)), RightMoveResult.class);
   }

   private String buildSearchUrl(final String locationIdentifier)
   {
      final String url = rightMoveBaseUrl + rightMoveSearchApiUrl +
               "locationIdentifier=%s&minBedrooms=3&maxPrice=25000&numberOfPropertiesPerPage=24&radius=0.0&sortType=6&index=0&includeLetAgreed=true&viewType=LIST&channel=RENT&areaSizeUnit=sqft&currencyCode=GBP&isFetching=false";

      return String.format(url, locationIdentifier);
   }

   public String go() {
      return this.restTemplate.getForEntity(this.rightMoveBaseUrl + "/resource", String.class)
               .getBody();
   }
}
