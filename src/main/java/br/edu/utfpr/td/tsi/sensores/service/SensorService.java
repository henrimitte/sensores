package br.edu.utfpr.td.tsi.sensores.service;

import br.edu.utfpr.td.tsi.sensores.exception.SensorAlreadyRegisteredException;
import br.edu.utfpr.td.tsi.sensores.exception.SensorNotRegisteredException;
import br.edu.utfpr.td.tsi.sensores.model.Sensor;

import java.util.List;

public interface SensorService {
    Sensor insert(Sensor sensor) throws SensorAlreadyRegisteredException;

    Sensor update(Sensor sensor) throws SensorNotRegisteredException;

    Sensor delete(String id, long timestamp);

    List<Sensor> getAll();

    List<Sensor> getAllBySensorId(String id);

    List<Sensor> getAllByLocalizacao(String localizacao);

    Sensor getBySensorIdAndTimestamp(String id, long timestamp);

    List<Sensor> getAllBySensorIdAndLocalizacao(String id, String localizacao);

}
