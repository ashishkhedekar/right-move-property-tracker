package co.uk.ak.rightmove.propertytracker.emails;

import co.uk.ak.rightmove.propertytracker.dto.LettingPropertiesTrackingResult;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
@AllArgsConstructor
public class EmailSender
{
   private final Environment env;

   final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

   private final JavaMailSender sender;

   public void sendEmail(final LettingPropertiesTrackingResult trackingResult)
   {
      sender.send(mimeMessage -> {
         MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
         message.setFrom("no-reply-rightmove-tracker@gmail.com");
         message.setTo("send.me.all.emails.here@gmail.com");
         message.setSubject(buildSubject());
         message.setText(String.format("Number of properties let [%s]%n Number of new properties [%s] ", trackingResult.getNumberOfPropertiesLet(), trackingResult.getNewProperties()), true);
      });
   }

   private String buildSubject()
   {
      final String environment = env.getProperty("current.environment");
      final StringBuilder subject = new StringBuilder();
      if (environment == null || !environment.equalsIgnoreCase("PRODUCTION"))
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
