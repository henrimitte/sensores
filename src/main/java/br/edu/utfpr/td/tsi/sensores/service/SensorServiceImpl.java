package br.edu.utfpr.td.tsi.sensores.service;

import br.edu.utfpr.td.tsi.sensores.exception.SensorAlreadyRegisteredException;
import br.edu.utfpr.td.tsi.sensores.exception.SensorNotRegisteredException;
import br.edu.utfpr.td.tsi.sensores.model.Sensor;
import br.edu.utfpr.td.tsi.sensores.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceImpl implements SensorService {

    SensorRepository sensorRepository;

    @Autowired
    public void setSensorRepository(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public Sensor insert(Sensor sensor) throws SensorAlreadyRegisteredException {
        return sensorRepository.insert(sensor);
    }

    @Override
    public Sensor update(Sensor sensor) throws SensorNotRegisteredException {
        return sensorRepository.update(sensor);
    }

    @Override
    public Sensor delete(String id, long timestamp) {
        return sensorRepository.delete(id, timestamp);
    }

    @Override
    public List<Sensor> getAll() {
        return sensorRepository.getAll();
    }

    @Override
    public List<Sensor> getAllBySensorId(String id) {
        return sensorRepository.getAllBySensorId(id);
    }

    @Override
    public List<Sensor> getAllByLocalizacao(String localizacao) {
        return sensorRepository.getAllByLocalizacao(localizacao);
    }

    @Override
    public Sensor getBySensorIdAndTimestamp(String id, long timestamp) {
        return sensorRepository.getBySensorIdAndTimestamp(id, timestamp);
    }

    @Override
    public List<Sensor> getAllBySensorIdAndLocalizacao(String id, String localizacao) {
        return sensorRepository.getAllBySensorIdAndLocalizacao(id, localizacao);
    }
}
