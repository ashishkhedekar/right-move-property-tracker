package co.uk.ak.rightmove.propertytracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RightMovePropertyModel
{
   @Id
   private Long id;

   private Integer bedrooms;
//   private String summary;
   private String displayAddress;
   private String propertySubType;
   private String displayStatus;

////   @OneToOne
//   private RightMovePriceModel price;
//   private String propertyUrl;
//   private String firstVisibleDate;

//   private String addedOrReduced;
//   private Boolean isRecent;
//   private String formattedDistance;
}
