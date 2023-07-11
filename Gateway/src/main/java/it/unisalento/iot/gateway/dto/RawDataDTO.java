package it.unisalento.iot.gateway.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class RawDataDTO {

  @Id private String id;
  @CreatedDate private Date date;
  private float temperatureRawData;
  private float pressureRawData;
  private float carbonMonoxideRawData;
  private float performanceRawData;
  private int stateRawData;
}
