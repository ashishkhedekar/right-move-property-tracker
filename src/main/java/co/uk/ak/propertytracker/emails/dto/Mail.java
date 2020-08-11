package co.uk.ak.propertytracker.emails.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class Mail {
   private String from;
   private String to;
   private String subject;
   private String content;
   private Map<String, Object> model;
}
