package co.uk.ak.rightmove.propertytracker.configuration;

import co.uk.ak.rightmove.propertytracker.dto.TrackingResult;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSender
{
   private static final Logger LOG = LoggerFactory.getLogger(EmailSender.class);

   private final JavaMailSender sender;

   public void sendEmail(final TrackingResult trackingResult)
   {
      sender.send(mimeMessage -> {
         MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
         message.setFrom("no-reply-rightmove-tracker@gmail.com");
         message.setTo("send.me.all.emails.here@gmail.com");
         message.setSubject("RightMove property alert for Buckingham");
         message.setText(String.format("Number of properties let [%s]\n Number of new properties [%s] ", trackingResult.getNumberOfPropertiesLet(), trackingResult.getNewProperties()), true);
      });
   }

}
