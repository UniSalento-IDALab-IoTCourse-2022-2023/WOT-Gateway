package it.unisalento.iot.gateway.restControllers;

import it.unisalento.iot.gateway.domains.RawData;
import it.unisalento.iot.gateway.dto.RawDataDTO;
import it.unisalento.iot.gateway.exceptions.CannotSendRequestException;
import it.unisalento.iot.gateway.repositories.IRawDataRepository;
import it.unisalento.iot.gateway.services.impl.BoilerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/gateway/boiler")
public class BoilerRestController {

    @Autowired
    IRawDataRepository rawDataRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BoilerService boilerService;

    /**
     * metodo per salvare i dati del boiler
     * @param rawDataDTO del boiler
     * @return exitcode + oggetto DTO del boiler
     */
    @PostMapping(path="/saveRawData", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity <RawDataDTO> saveRawData(@RequestBody RawDataDTO rawDataDTO) {

      RawData rawData = convertRawDataDTOtoRawData(rawDataDTO);
      RawData saved = rawDataRepository.save(rawData);
      rawDataDTO.setId(saved.getId());

      return new ResponseEntity<>(rawDataDTO, HttpStatus.ACCEPTED);
    }

    /**
     * effettua l'aggregazione dei dati
     * @return LISTA DTO CON HTTP_STATUS OK
     */
    @GetMapping(value = "/aggregateData")
    public void aggregateData() throws CannotSendRequestException {

      boilerService.sendBoilerData();
    }

    // MODEL MAPPER:

    /**
     * converte il dto nella domain entity
     * @param rawDataDTO oggetto DTO del boiler
     * @return trashCan
     */
    private RawData convertRawDataDTOtoRawData(RawDataDTO rawDataDTO){
        return modelMapper.map(rawDataDTO, RawData.class);
    }

    /**
     * converte la domanin entity nel DTO
     * @param rawData oggetto cassonetto
     * @return rawDataDTO
     */
    private RawDataDTO convertRawDataToRawDataDTO(Optional<RawData> rawData){
        return modelMapper.map(rawData, RawDataDTO.class);
    }
}
