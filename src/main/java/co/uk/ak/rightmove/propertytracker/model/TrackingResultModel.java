package co.uk.ak.rightmove.propertytracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingResultModel
{
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private int numberOfPropertiesLet;
   private int numberOfPropertiesReduced;
   private Date createdDate;
}
