package br.edu.utfpr.td.tsi.sensores.repository;

import br.edu.utfpr.td.tsi.sensores.model.Sensor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;

import java.net.URI;
import java.util.List;

@Repository
public class DynamoDbSensorRepositoryImpl implements SensorRepository {

    private final String tableName = "SensoresToledo";
    private final String localizacaoIndexName = "LocalizacaoIndex";

    private final DynamoDbClient dynamoDbClient = DynamoDbClient.builder().endpointOverride(URI.create("http://localhost:4566")).build();
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient).build();
    private final DynamoDbTable<Sensor> sensorTable = enhancedClient.table(tableName, TableSchema.fromBean(Sensor.class));

    DynamoDbSensorRepositoryImpl() {
        try {
            dynamoDbClient.describeTable(b -> b.tableName(tableName));
        } catch (ResourceNotFoundException e) {
            sensorTable.createTable();
            dynamoDbClient.waiter().waitUntilTableExists(b -> b.tableName(tableName));
        }
    }

    public boolean exists(Sensor sensor) {
        return sensorTable.getItem(sensor) != null;
    }

    @Override
    public Sensor insert(Sensor sensor) {
        return sensorTable.updateItem(sensor);
    }

    @Override
    public Sensor update(Sensor sensor) {
        return sensorTable.updateItem(sensor);
    }

    @Override
    public Sensor delete(String id, long timestamp) {
        Key key = Key.builder().partitionValue(id).sortValue(timestamp).build();
        return sensorTable.deleteItem(key);
    }

    @Override
    public List<Sensor> getAll() {
        return sensorTable.scan().items().stream().toList();
    }

    @Override
    public List<Sensor> getAllBySensorId(String id) {
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(id).build());

        return sensorTable.query(queryConditional).items().stream().toList();
    }

    @Override
    public Sensor getBySensorIdAndTimestamp(String id, long timestamp) {
        Key key = Key.builder().partitionValue(id).sortValue(timestamp).build();

        return sensorTable.getItem(key);
    }

    @Override
    public List<Sensor> getAllByLocalizacao(String localizacao) {
        DynamoDbIndex<Sensor> tableIndex = sensorTable.index(localizacaoIndexName);

        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(localizacao).build());

        return tableIndex.query(queryConditional).stream().flatMap(sensorPage -> sensorPage.items().stream()).toList();
    }

    @Override
    public List<Sensor> getAllBySensorIdAndLocalizacao(String id, String localizacao) {
        DynamoDbIndex<Sensor> tableIndex = sensorTable.index(localizacaoIndexName);

        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder().partitionValue(localizacao).build());

        return tableIndex.query(q -> q.queryConditional(queryConditional).filterExpression(Expression.builder().expression("SensorID = :sid").putExpressionValue(":sid", AttributeValue.fromS(id)).build())).stream().flatMap(sensorPage -> sensorPage.items().stream()).toList();
    }
}
