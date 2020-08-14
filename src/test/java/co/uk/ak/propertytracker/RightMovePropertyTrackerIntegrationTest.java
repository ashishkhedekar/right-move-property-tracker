package co.uk.ak.propertytracker;

import co.uk.ak.builder.PropertyModelBuilder;
import co.uk.ak.propertytracker.facade.PropertiesTrackerFacade;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.rule.SmtpServerRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class RightMovePropertyTrackerIntegrationTest
{
   @Rule
   public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

   @Rule
   public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

   @Autowired
   private PropertiesTrackerFacade facade;

   @Autowired
   private PropertyRepository propertyRepository;

   @Test
   public void testSingleProperty_newProperty()
   {
//      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
//               .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
//                        .withBodyFile("single-property-not-let.json")));
//
//      final LettingPropertiesTrackingResult trackingResult = facade.trackProperties("london");
//
//      assertThat(trackingResult.getNumberOfPropertiesLet()).isOne();
//      assertThat(trackingResult.getNewProperties()).isOne();
   }

   @Test
   public void testSingleProperty_noChange()
   {
//      //given property exists
//      givenPropertyExists(91967702L);
//
//      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
//               .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
//                        .withBodyFile("single-property-not-let.json")));
//
//      final LettingPropertiesTrackingResult trackingResult = facade.trackProperties("london");
//
//      assertThat(trackingResult.getNumberOfPropertiesLet()).isZero();
   }

   @Test
   public void testSingleProperty_let()
   {
//      //given property exists
//      givenPropertyExists(91967702L);
//
//      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
//               .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
//                        .withBodyFile("single-property-let.json")));
//
//      final LettingPropertiesTrackingResult trackingResult = facade.trackProperties("london");
//
//      assertThat(trackingResult.getNumberOfPropertiesLet()).isOne();
//      assertThat(trackingResult.getLetProperties()).hasSize(1);
   }

   @Test
   public void testTwoProperties_noChange()
   {
//      //given property exists
//      givenPropertyExists(91967702L);
//      givenPropertyExists(72367257L);
//
//      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
//               .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
//                        .withBodyFile("two-properties-not-let.json")));
//
//      final LettingPropertiesTrackingResult trackingResult = facade.trackProperties("london");
//
//      assertThat(trackingResult.getNumberOfPropertiesLet()).isZero();
   }

   @Test
   public void testTwoProperties_singleLet()
   {
//      //given property exists
//      givenPropertyExists(91967702L);
//      givenPropertyExists(72367257L);
//
//      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
//               .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
//                        .withBodyFile("two-properties-one-let.json")));
//
//      final LettingPropertiesTrackingResult trackingResult = facade.trackProperties("london");
//
//      assertThat(trackingResult.getNumberOfPropertiesLet()).isOne();
//      assertThat(trackingResult.getLetProperties()).hasSize(1);
   }

   private void givenPropertyExists(final long id)
   {
      final PropertyModel propertyModel = PropertyModelBuilder.builder()
               .id(id)
               .bedrooms(5)
               .propertySubType("Terraced")
               .displayAddress("Buckingham")
               .displayStatus("")
               .build();
      propertyRepository.save(propertyModel);
   }


}
