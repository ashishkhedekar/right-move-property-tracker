package co.uk.ak.propertytracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RightMovePropertyModel
{
   @Id
   private Long id;

   private Integer bedrooms;
   @Lob
   private String summary;
   private String displayAddress;
   private String propertySubType;
   private String displayStatus;
   private Date firstVisibleDate;
   @Column(nullable = true)
   private Long daysOnMarket;
   @Column(nullable = true)
   private Boolean premiumListing;

////   @OneToOne
//   private RightMovePriceModel price;
//   private String propertyUrl;
//   private String firstVisibleDate;

//   private String addedOrReduced;
//   private Boolean isRecent;
//   private String formattedDistance;
}
