package co.uk.ak.propertytracker.emails;

import co.uk.ak.propertytracker.dto.MarketMovementReport;
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
   public void sendSomethingWentWrongEmail(final String message)
   {
      final Map<String, Object> model = new HashMap<>();
      model.put("message", message);

      final StringBuilder subject = new StringBuilder();
      if (!isProduction())
      {
         subject.append("[TEST] ");
      }
      subject.append("Something went wrong while sending email, [").append(simpleDateFormat.format(DateTime.now().toDate())).append("]");

      final Mail mail = Mail.builder().
               to(emailNotificationRecipients)
               .from("right.move.property.alerts@gmail.com")
               .subject(subject.toString())
               .model(model)
               .build();

      emailSender.sendEmail(mail, "something-went-wrong.ftl");
   }

   @Override
   public void sendLettingReportsEmail(final MarketMovementReport trackingResult)
   {
      final Map<String, Object> model = new HashMap<>();
      model.put("numberOfOffMarketProperties", trackingResult.getNumberOfOffMarketProperties());
      model.put("offMarketProperties", trackingResult.getOffMarketProperties());
      model.put("numberOfNewProperties", trackingResult.getNumberOfNewProperties());
      model.put("newProperties", trackingResult.getNewProperties());

      final Mail mail = Mail.builder().
               to(emailNotificationRecipients)
               .from("right.move.property.alerts@gmail.com")
               .subject(buildSubject(trackingResult.getChannel()))
               .model(model)
               .build();

      emailSender.sendEmail(mail, "right-move-lettings-report.ftl");
   }

   private String buildSubject(final String channel)
   {
      final StringBuilder subject = new StringBuilder();
      if (!isProduction())
      {
         subject.append("[TEST] ");
      }
      subject.append(String.format("%s report for Buckingham", getChannelDisplayText(channel))) // todo - change the hard-coding of buckingham
               .append(" - ")
               .append("[")
               .append(simpleDateFormat.format(DateTime.now().toDate()))
               .append("]");
      return subject.toString();
   }

   private boolean isProduction()
   {
      return currentEnvironment == null || !currentEnvironment.equalsIgnoreCase("PRODUCTION");
   }

   private String getChannelDisplayText(final String channel)
   {
      return channel.equalsIgnoreCase("BUY") ? "Sales" : "Lettings";
   }
}
