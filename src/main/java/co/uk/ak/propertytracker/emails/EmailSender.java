package co.uk.ak.propertytracker.emails;

import co.uk.ak.propertytracker.emails.dto.Mail;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;

@Service
public class EmailSender
{
   final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");

   @Value("${right.move.email.notification.recipients}")
   private String emailNotificationRecipients;

   @Value("${current.environment}")
   private String currentEnvironment;

   @Value("${disable.emails}")
   private boolean disableEmails;

   @Autowired
   private JavaMailSender sender;

   @Autowired
   private Configuration configuration;

   public void sendEmail(final Mail mail, final String emailTemplateName)
   {
      if (disableEmails) {
         return;
      }
      sender.send(mimeMessage -> {
         MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());
         message.setFrom(mail.getFrom());
         message.setTo(mail.getTo());
         message.setSubject(mail.getSubject());

         final Template t = configuration.getTemplate(emailTemplateName);
         message.setText(FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel()), true);
      });
   }
}
