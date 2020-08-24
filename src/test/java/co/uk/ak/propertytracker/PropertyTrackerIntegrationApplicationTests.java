package co.uk.ak.propertytracker;

import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.rule.SmtpServerRule;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PropertyTrackerIntegrationApplicationTests
{
   @Rule
   public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8089));

   @Rule
   public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

   @Autowired
   private MockMvc mvc;

   @Autowired
   private PropertyRepository propertyRepository;

   private static final long SINGLE_NON_LET_PROPERTY = 91967702L;
   private static final long SINGLE_LET_PROPERTY = 89887883L;
   private static final long SINGLE_NO_CHANGE_PROPERTY = 72533088L;
   private static final long TWO_NON_LET_PROPERTIES_1 = 88932767L;
   private static final long TWO_NON_LET_PROPERTIES_2 = 72367257L;
   private static final long SINGLE_NO_CHANGE_PROPERTY_1 = 94524230L;
   private static final long SINGLE_NO_CHANGE_PROPERTY_2 = 95677817L;
   private static final long TWO_LET_PROPERTIES_1 = 90674429L;
   private static final long TWO_LET_PROPERTIES_2 = 71205657L;

   @Test
   public void testSingleNewProperty() throws Exception
   {
      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("single-property-not-let.json")));

      //Verify property does not exist
      final Optional<PropertyModel> byPropertyId = propertyRepository.findByPropertyId(SINGLE_NON_LET_PROPERTY);
      assertThat(byPropertyId.isPresent()).isFalse();

      mvc.perform(MockMvcRequestBuilders.get("/properties-to-let?locationId=london"))
               .andExpect(status().isOk());

      final Optional<PropertyModel> savedProperty = propertyRepository.findByPropertyId(SINGLE_NON_LET_PROPERTY);
      assertThat(savedProperty.isPresent()).isTrue();
      assertThat(savedProperty.get().getDisplayStatus()).isEqualTo("");
   }

   @Test
   public void testSingleExistingProperty_noChange() throws Exception
   {
      final PropertyModel existingProperty = new PropertyModel();
      existingProperty.setPropertyId(SINGLE_NO_CHANGE_PROPERTY);
      existingProperty.setDisplayStatus("");
      existingProperty.setBedrooms(4);
      propertyRepository.save(existingProperty);

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("single-property-no-change.json")));

      mvc.perform(MockMvcRequestBuilders.get("/properties-to-let?locationId=london"))
               .andExpect(status().isOk());

      //Verify property saved and has correct status
      final Optional<PropertyModel> byPropertyId = propertyRepository.findByPropertyId(SINGLE_NO_CHANGE_PROPERTY);
      assertThat(byPropertyId.isPresent()).isTrue();
      assertThat(byPropertyId.get().getDisplayStatus()).isEqualTo("");
   }

   @Test
   public void testSingleExistingProperty_beingLet() throws Exception
   {
      final PropertyModel existingProperty = new PropertyModel();
      existingProperty.setPropertyId(SINGLE_LET_PROPERTY);
      existingProperty.setDisplayStatus("");
      existingProperty.setBedrooms(4);
      propertyRepository.save(existingProperty);

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("single-property-let.json")));

      mvc.perform(MockMvcRequestBuilders.get("/properties-to-let?locationId=london"))
               .andExpect(status().isOk());

      //Verify property saved and has correct status
      final Optional<PropertyModel> byPropertyId = propertyRepository.findByPropertyId(SINGLE_LET_PROPERTY);
      assertThat(byPropertyId.isPresent()).isTrue();
      assertThat(byPropertyId.get().getDisplayStatus()).isEqualTo("Let Agreed");
   }

   @Test
   public void testTwoNewProperties() throws Exception
   {
      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("two-properties-not-let.json")));

      //Verify property does not exist
      final Optional<PropertyModel> firstProperty = propertyRepository.findByPropertyId(TWO_NON_LET_PROPERTIES_1);
      assertThat(firstProperty.isPresent()).isFalse();

      final Optional<PropertyModel> secondProperty = propertyRepository.findByPropertyId(TWO_NON_LET_PROPERTIES_2);
      assertThat(secondProperty.isPresent()).isFalse();


      mvc.perform(MockMvcRequestBuilders.get("/properties-to-let?locationId=london"))
               .andExpect(status().isOk());

      final Optional<PropertyModel> savedProperty1 = propertyRepository.findByPropertyId(TWO_NON_LET_PROPERTIES_1);
      assertThat(savedProperty1.isPresent()).isTrue();
      assertThat(savedProperty1.get().getDisplayStatus()).isEqualTo("");

      final Optional<PropertyModel> savedProperty2 = propertyRepository.findByPropertyId(TWO_NON_LET_PROPERTIES_2);
      assertThat(savedProperty2.isPresent()).isTrue();
      assertThat(savedProperty2.get().getDisplayStatus()).isEqualTo("");
   }

   @Test
   public void testTwoNewProperties_noChange() throws Exception
   {
      //given property already exists
      final PropertyModel existingProperty1 = new PropertyModel();
      existingProperty1.setPropertyId(SINGLE_NO_CHANGE_PROPERTY_1);
      existingProperty1.setDisplayStatus("");
      existingProperty1.setBedrooms(4);
      propertyRepository.save(existingProperty1);

      final PropertyModel existingProperty2 = new PropertyModel();
      existingProperty2.setPropertyId(SINGLE_NO_CHANGE_PROPERTY_2);
      existingProperty2.setDisplayStatus("");
      existingProperty2.setBedrooms(4);
      propertyRepository.save(existingProperty2);

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("two-properties-not-let.json")));


      mvc.perform(MockMvcRequestBuilders.get("/properties-to-let?locationId=london"))
               .andExpect(status().isOk());

      final Optional<PropertyModel> savedProperty1 = propertyRepository.findByPropertyId(SINGLE_NO_CHANGE_PROPERTY_1);
      assertThat(savedProperty1.isPresent()).isTrue();
      assertThat(savedProperty1.get().getDisplayStatus()).isEqualTo("");

      final Optional<PropertyModel> savedProperty2 = propertyRepository.findByPropertyId(SINGLE_NO_CHANGE_PROPERTY_2);
      assertThat(savedProperty2.isPresent()).isTrue();
      assertThat(savedProperty2.get().getDisplayStatus()).isEqualTo("");
   }

   @Test
   public void testTwoNewProperties_oneLetOut() throws Exception
   {
      //given property already exists
      final PropertyModel existingProperty1 = new PropertyModel();
      existingProperty1.setPropertyId(TWO_LET_PROPERTIES_1);
      existingProperty1.setDisplayStatus("");
      existingProperty1.setBedrooms(4);
      propertyRepository.save(existingProperty1);

      final PropertyModel existingProperty2 = new PropertyModel();
      existingProperty2.setPropertyId(TWO_LET_PROPERTIES_2);
      existingProperty2.setDisplayStatus("");
      existingProperty2.setBedrooms(4);
      propertyRepository.save(existingProperty2);

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("two-properties-one-let.json")));


      mvc.perform(MockMvcRequestBuilders.get("/properties-to-let?locationId=london"))
               .andExpect(status().isOk());

      final Optional<PropertyModel> savedProperty1 = propertyRepository.findByPropertyId(TWO_LET_PROPERTIES_1);
      assertThat(savedProperty1.isPresent()).isTrue();
      assertThat(savedProperty1.get().getDisplayStatus()).isEqualTo("Let Agreed");

      final Optional<PropertyModel> savedProperty2 = propertyRepository.findByPropertyId(TWO_LET_PROPERTIES_2);
      assertThat(savedProperty2.isPresent()).isTrue();
      assertThat(savedProperty2.get().getDisplayStatus()).isEqualTo("");
   }
}
