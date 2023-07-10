package it.unisalento.iot.gateway.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

@Document("carbonMonoxideRawData")
@Getter
@Setter
@NoArgsConstructor
public class CarbonMonoxideRawData implements Serializable {

  @Id private String id;
  @CreatedDate private Date date;
  private float CarbonMonoxideRawData;
}
