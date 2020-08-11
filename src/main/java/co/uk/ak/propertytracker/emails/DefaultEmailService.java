package co.uk.ak.propertytracker.emails;

import co.uk.ak.propertytracker.dto.LettingPropertiesTrackingResult;
import co.uk.ak.propertytracker.emails.dto.Mail;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultEmailService implements EmailService
{
   final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

   @Value("${right.move.email.notification.recipients}")
   private String emailNotificationRecipients;

   @Value("${current.environment}")
   private String currentEnvironment;

   @Autowired
   private EmailSender emailSender;

   @Override
   public void sendLettingReportsEmail(final LettingPropertiesTrackingResult trackingResult)
   {
      final Map<String, Object> model = new HashMap<>();
      model.put("numberOfPropertiesLet", trackingResult.getNumberOfPropertiesLet());
      model.put("newProperties", trackingResult.getNewProperties());
      model.put("letProperties", trackingResult.getLetProperties());

      final Mail mail = Mail.builder().
               to(emailNotificationRecipients)
               .from("right.move.property.alerts@gmail.com")
               .subject(buildSubject())
               .model(model)
               .build();

      emailSender.sendEmail(mail, "right-move-lettings-report.ftl");
   }

   private String buildSubject()
   {
      final StringBuilder subject = new StringBuilder();
      if (currentEnvironment == null || !currentEnvironment.equalsIgnoreCase("PRODUCTION"))
      {
         subject.append("[TEST] ");
      }
      subject.append("RightMove property alert for Buckingham") // todo - change the hard-coding of buckingham
               .append(" - ")
               .append("[")
               .append(simpleDateFormat.format(DateTime.now().toDate()))
               .append("]");
      return subject.toString();
   }
}
