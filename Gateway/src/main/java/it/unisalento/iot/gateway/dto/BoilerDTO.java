package it.unisalento.iot.gateway.dto;

import it.unisalento.iot.gateway.domains.AlarmType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class BoilerDTO {

  @Id private String id;
  @CreatedDate private Date date;
}
