package br.edu.utfpr.td.tsi.sensores.repository;

import br.edu.utfpr.td.tsi.sensores.exception.SensorAlreadyRegisteredException;
import br.edu.utfpr.td.tsi.sensores.exception.SensorNotRegisteredException;
import br.edu.utfpr.td.tsi.sensores.model.Sensor;

import java.util.List;

public interface SensorRepository {

    Sensor insert(Sensor sensor) throws SensorAlreadyRegisteredException;

    Sensor update(Sensor sensor) throws SensorNotRegisteredException;

    Sensor delete(String id, long timestamp);

    List<Sensor> getAll();

    List<Sensor> getAllBySensorId(String id);

    Sensor getBySensorIdAndTimestamp(String id, long timestamp);

    List<Sensor> getAllByLocalizacao(String localizacao);

    List<Sensor> getAllBySensorIdAndLocalizacao(String id, String localizacao);
}
