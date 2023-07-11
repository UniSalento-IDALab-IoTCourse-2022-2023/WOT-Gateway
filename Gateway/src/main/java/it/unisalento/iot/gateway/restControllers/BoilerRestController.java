package it.unisalento.iot.gateway.restControllers;

import it.unisalento.iot.gateway.domains.RawData;
import it.unisalento.iot.gateway.dto.RawDataDTO;
import it.unisalento.iot.gateway.repositories.IRawDataRepository;
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
