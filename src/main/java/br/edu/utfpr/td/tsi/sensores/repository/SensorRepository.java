package br.edu.utfpr.td.tsi.sensores.repository;

import br.edu.utfpr.td.tsi.sensores.model.Sensor;

import java.util.List;

public interface SensorRepository {

    Sensor insert(Sensor sensor);

    Sensor update(Sensor sensor);

    Sensor delete(String id, long timestamp);

    List<Sensor> getAll();

    List<Sensor> getAllBySensorId(String id);

    Sensor getBySensorIdAndTimestamp(String id, long timestamp);

    List<Sensor> getAllByLocalizacao(String localizacao);

    List<Sensor> getAllBySensorIdAndLocalizacao(String id, String localizacao);

    boolean exists(Sensor sensor);
}
