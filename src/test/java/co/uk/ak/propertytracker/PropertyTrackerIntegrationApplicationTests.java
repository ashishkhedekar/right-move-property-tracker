package co.uk.ak.propertytracker;

import co.uk.ak.propertytracker.dto.Channel;
import co.uk.ak.propertytracker.dto.LocationDto;
import co.uk.ak.propertytracker.endpoints.searchcriteriadto.SearchCriteriaDto;
import co.uk.ak.propertytracker.model.PropertyModel;
import co.uk.ak.propertytracker.model.PropertyUpdateRecordModel;
import co.uk.ak.propertytracker.repository.PropertyRepository;
import co.uk.ak.propertytracker.repository.PropertyUpdateRecordRepository;
import co.uk.ak.propertytracker.rule.SmtpServerRule;
import co.uk.ak.propertytracker.scheduler.Scheduler;
import co.uk.ak.propertytracker.service.LocationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
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

   @Autowired
   private PropertyUpdateRecordRepository propertyUpdateRecordRepository;

   @Autowired
   private Scheduler scheduler;

   @Autowired
   private LocationService locationService;

   private static final long SINGLE_NON_LET_PROPERTY = 91967702L;
   private static final long SINGLE_LET_PROPERTY = 89887883L;
   private static final long SINGLE_NO_CHANGE_PROPERTY = 72533088L;
   private static final long TWO_NON_LET_PROPERTIES_1 = 88932767L;
   private static final long TWO_NON_LET_PROPERTIES_2 = 72367257L;
   private static final long SINGLE_NO_CHANGE_PROPERTY_1 = 94524230L;
   private static final long SINGLE_NO_CHANGE_PROPERTY_2 = 95677817L;
   private static final long TWO_LET_PROPERTIES_1 = 90674429L;
   private static final long TWO_LET_PROPERTIES_2 = 71205657L;

   @Before
   public void setup() throws Exception
   {
      final SearchCriteriaDto searchCriteria = SearchCriteriaDto
               .builder()
               .channel(Channel.RENT)
               .locationIdentifier("london")
               .build();

      //Create location
      mvc.perform(
               MockMvcRequestBuilders.post("/search-criteria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(searchCriteria)))
               .andExpect(status().isOk());

   }

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

      scheduler.fetchRightMovePropertyUpdates();

      assertThatPropertySavedWithDisplayStatus(SINGLE_NON_LET_PROPERTY, "");
      assertThatPropertyAssociatedWithLocation(SINGLE_NON_LET_PROPERTY, "london");
      assertThatUpdateRecordAdded(SINGLE_NON_LET_PROPERTY, "registered", null, "true");
   }

   @Test
   public void testSingleExistingProperty_noChange() throws Exception
   {
      final PropertyModel existingProperty = new PropertyModel();
      existingProperty.setPropertyId(SINGLE_NO_CHANGE_PROPERTY);
      existingProperty.setDisplayStatus("");
      existingProperty.setBedrooms(4);
      existingProperty.setFirstVisibleDate(DateTime.now().minusDays(10).toDate());
      propertyRepository.save(existingProperty);

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("single-property-no-change.json")));

      scheduler.fetchRightMovePropertyUpdates();

      //Verify property saved and has correct status
      assertThatPropertySavedWithDisplayStatus(SINGLE_NO_CHANGE_PROPERTY, "");
      assertThatPropertyAssociatedWithLocation(SINGLE_NO_CHANGE_PROPERTY, "london");
   }

   @Test
   public void testSingleExistingProperty_beingLet() throws Exception
   {
      final PropertyModel existingProperty = new PropertyModel();
      existingProperty.setPropertyId(SINGLE_LET_PROPERTY);
      existingProperty.setDisplayStatus("");
      existingProperty.setBedrooms(4);
      existingProperty.setFirstVisibleDate(DateTime.now().minusDays(10).toDate());
      propertyRepository.save(existingProperty);

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("single-property-let.json")));

      mvc.perform(MockMvcRequestBuilders.get("/properties-to-let?locationId=london"))
               .andExpect(status().isOk());

      assertThatPropertySavedWithDisplayStatus(SINGLE_LET_PROPERTY, "Let Agreed");
      assertThatPropertyAssociatedWithLocation(SINGLE_LET_PROPERTY, "london");
      assertThatUpdateRecordAdded(SINGLE_LET_PROPERTY, "displayStatus", "", "Let Agreed");
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

      scheduler.fetchRightMovePropertyUpdates();

      assertThatPropertySavedWithDisplayStatus(TWO_NON_LET_PROPERTIES_1, "");
      assertThatUpdateRecordAdded(TWO_NON_LET_PROPERTIES_1, "registered", null, "true");
      assertThatPropertySavedWithDisplayStatus(TWO_NON_LET_PROPERTIES_2, "");
      assertThatUpdateRecordAdded(TWO_NON_LET_PROPERTIES_2, "registered", null, "true");
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


      scheduler.fetchRightMovePropertyUpdates();

      assertThatPropertySavedWithDisplayStatus(SINGLE_NO_CHANGE_PROPERTY_1, "");
      assertThatPropertySavedWithDisplayStatus(SINGLE_NO_CHANGE_PROPERTY_2, "");
   }

   @Test
   public void testTwoNewProperties_oneLetOut() throws Exception
   {
      //given property already exists
      final PropertyModel existingProperty1 = new PropertyModel();
      existingProperty1.setPropertyId(TWO_LET_PROPERTIES_1);
      existingProperty1.setDisplayStatus("");
      existingProperty1.setBedrooms(4);
      existingProperty1.setFirstVisibleDate(DateTime.now().minusDays(10).toDate());
      propertyRepository.save(existingProperty1);

      final PropertyModel existingProperty2 = new PropertyModel();
      existingProperty2.setPropertyId(TWO_LET_PROPERTIES_2);
      existingProperty2.setDisplayStatus("");
      existingProperty2.setBedrooms(4);
      existingProperty2.setFirstVisibleDate(DateTime.now().minusDays(10).toDate());
      propertyRepository.save(existingProperty2);

      stubFor(get(urlMatching("/api/_search\\?locationIdentifier=london(.)*"))
               .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", APPLICATION_JSON_VALUE)
                        .withBodyFile("two-properties-one-let.json")));

      scheduler.fetchRightMovePropertyUpdates();

      assertThatPropertySavedWithDisplayStatus(TWO_LET_PROPERTIES_1, "Let Agreed");
      assertThatPropertySavedWithDisplayStatus(TWO_LET_PROPERTIES_2, "");
      assertThatUpdateRecordAdded(TWO_LET_PROPERTIES_1, "displayStatus", "", "Let Agreed");
   }

   private void assertThatPropertySavedWithDisplayStatus(final long propertyId, final String displayStatus)
   {
      final Optional<PropertyModel> property = propertyRepository.findByPropertyId(propertyId);
      if (property.isPresent())
      {
         assertThat(property.get().getDisplayStatus()).isEqualTo(displayStatus);
      }
      else
      {
         fail("Property with id " + propertyId + " was not saved in the DB");
      }
   }

   private void assertThatPropertyAssociatedWithLocation(final long propertyId, final String locationIdentifier)
   {
      final LocationDto location = locationService.findLocationByLocationIdentifier(locationIdentifier);
      assertThat(location.getProperties()).isNotEmpty();

      final boolean propertyAssociatedWithLocation = location.getProperties().stream()
               .anyMatch(p -> p.getId() == propertyId);
      assertThat(propertyAssociatedWithLocation).isTrue();
   }

   private void assertThatUpdateRecordAdded(long propertyId, String field, String oldValue, String newValue)
   {
      final List<PropertyUpdateRecordModel> changes = propertyUpdateRecordRepository.findByPropertyPropertyId(propertyId);
      assertThat(changes).hasSize(1);
      assertThat(changes.get(0).getField()).isEqualTo(field);
      assertThat(changes.get(0).getOldValue()).isEqualTo(oldValue);
      assertThat(changes.get(0).getNewValue()).isEqualTo(newValue);
   }

   public static String asJsonString(final Object obj) {
      try {
         return new ObjectMapper().writeValueAsString(obj);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
}
