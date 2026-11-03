package br.edu.utfpr.td.tsi.sensores.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class Sensor {

    private String sensorId;
    private long timestamp;
    private double temperatura;
    private double umidade;
    private String localizacao;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("SensorID")
    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    @DynamoDbSortKey
    @DynamoDbSecondarySortKey(indexNames = {"LocalizacaoIndex"})
    @DynamoDbAttribute("Timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @DynamoDbSecondarySortKey(indexNames = {"TemperaturaIndex"})
    @DynamoDbAttribute("Temperatura")
    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }

    @DynamoDbAttribute("Umidade")
    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"LocalizacaoIndex"})
    @DynamoDbAttribute("Localizacao")
    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "sensorId='" + sensorId + '\'' +
                ", timestamp=" + timestamp +
                ", temperatura=" + temperatura +
                ", umidade=" + umidade +
                ", localizacao='" + localizacao + '\'' +
                '}';
    }
}
