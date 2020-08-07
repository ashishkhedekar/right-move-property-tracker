package co.uk.ak.rightmove.propertytracker;

import co.uk.ak.rightmove.propertytracker.client.RightMoveWebClient;
import co.uk.ak.rightmove.propertytracker.dto.RightMoveResult;
import co.uk.ak.rightmove.propertytracker.facade.RightMovePropertiesTrackerFacade;
import co.uk.ak.rightmove.propertytracker.rule.SmtpServerRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class SimpleWiremockTest
{
   @Rule
   public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

   @Rule
   public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

   @Autowired
   private RightMovePropertiesTrackerFacade facade;

   @Autowired
   private RestTemplate restTemplate;

   @Autowired
   private RightMoveWebClient webClient;

   @Test
   public void givenWireMockAdminEndpoint_whenGetWithoutParams_thenVerifyRequest() {

      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8089/__admin", String.class);
      assertThat(response.getBody()).contains("mappings");
   }

   @Test
   public void givenWireMockEndpoint_whenGetWithoutParams_thenVerifyRequest() {
      stubFor(get(urlEqualTo("/api/resource/"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBody("test")));

      ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8089/api/resource/", String.class);

      assertThat(response.getBody()).contains("test");
      verify(getRequestedFor(urlMatching("/api/resource/.*")));
   }

   @Test
   public void test()
   {

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("response.json")));

      final RightMoveResult something = webClient.callRightMove("london");
      assertThat(something.getResultCount()).isEqualTo("1");
   }

   @Test
   public void test_1()
   {

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("response.json")));

      facade.trackProperties("london");
   }
}
