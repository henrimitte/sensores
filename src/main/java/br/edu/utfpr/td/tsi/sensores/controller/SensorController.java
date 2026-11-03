package br.edu.utfpr.td.tsi.sensores.controller;

import br.edu.utfpr.td.tsi.sensores.exception.SensorAlreadyRegisteredException;
import br.edu.utfpr.td.tsi.sensores.exception.SensorNotRegisteredException;
import br.edu.utfpr.td.tsi.sensores.model.Sensor;
import br.edu.utfpr.td.tsi.sensores.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class SensorController {
    private SensorService sensorService;

    @Autowired
    public void setSensorService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/sensor/{id}/{timestamp}")
    public Sensor getBySensorIdAndTimestamp(@PathVariable("id") String id, @PathVariable("timestamp") long timestamp) {
        return sensorService.getBySensorIdAndTimestamp(id, timestamp);
    }

    @PutMapping("/sensor/{id}/{timestamp}")
    public ResponseEntity<Sensor> update(@PathVariable("id") String id, @PathVariable("timestamp") long timestamp, @RequestBody Sensor sensor) {
        sensor.setSensorId(id);
        sensor.setTimestamp(timestamp);

        try {
            Sensor sensorAtualizado = sensorService.update(sensor);
            return ResponseEntity.ok(sensorAtualizado);
        } catch (SensorNotRegisteredException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/sensor/{id}/{timestamp}")
    public ResponseEntity<Sensor> delete(@PathVariable("id") String id, @PathVariable("timestamp") long timestamp) {
        Sensor sensorAtualizado = sensorService.delete(id, timestamp);
        if (sensorAtualizado != null) {
            return ResponseEntity.ok(sensorAtualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/sensores")
    public List<Sensor> getAll() {
        return sensorService.getAll();
    }

    @PostMapping("/sensores")
    public ResponseEntity insert(@RequestBody Sensor sensor) {
        Sensor sensorNovo;

        try {
            sensorNovo = sensorService.insert(sensor);
        } catch (SensorAlreadyRegisteredException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if (sensorNovo == null) {
            return ResponseEntity.internalServerError().build();
        }

        URI localizacaoURI = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/sensor")
                .path("/{id}/{timestamp}")
                .buildAndExpand(sensorNovo.getSensorId(), sensorNovo.getTimestamp())
                .toUri();

        return ResponseEntity.created(localizacaoURI).build();
    }

    @GetMapping("/sensores/{id}")
    public List<Sensor> getAllBySensorId(@PathVariable("id") String id) {
        return sensorService.getAllBySensorId(id);
    }

    @GetMapping("/sensores/localizacao/{localizacao}")
    public List<Sensor> getAllByLocalizacao(@PathVariable("localizacao") String localizacao) {
        return sensorService.getAllByLocalizacao(localizacao);
    }

    @GetMapping("/sensores/{id}/localizacao/{localizacao}")
    public List<Sensor> getAllBySensorIdAndLocalizacao(@PathVariable("id") String id, @PathVariable("localizacao") String localizacao) {
        return sensorService.getAllBySensorIdAndLocalizacao(id, localizacao);
    }
}
