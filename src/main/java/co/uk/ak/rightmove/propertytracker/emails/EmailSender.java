package co.uk.ak.rightmove.propertytracker.emails;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class EmailSender
{
   final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

   @Value("${right.move.email.notification.recipients}")
   private String emailNotificationRecipients;

   @Value("${current.environment}")
   private String currentEnvironment;

   @Autowired
   private JavaMailSender sender;

   public void sendEmail(final LettingPropertiesTrackingResult trackingResult)
   {
      sender.send(mimeMessage -> {
         MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
         message.setFrom("no-reply-rightmove-tracker@gmail.com");
         message.setTo(emailNotificationRecipients);
         message.setSubject(buildSubject());
         message.setText(String.format("Number of properties let [%s]%n Number of new properties [%s] ", trackingResult.getNumberOfPropertiesLet(), trackingResult.getNewProperties()), true);
      });
   }

   private String buildSubject()
   {

      final StringBuilder subject = new StringBuilder();
      if (currentEnvironment == null || !currentEnvironment.equalsIgnoreCase("PRODUCTION"))
      {
         subject.append("[TEST] ");
      }
      subject.append("RightMove property alert for Buckingham")
               .append(" - ")
               .append("[")
               .append(simpleDateFormat.format(DateTime.now().toDate()))
               .append("]");
      return subject.toString();
   }
}
