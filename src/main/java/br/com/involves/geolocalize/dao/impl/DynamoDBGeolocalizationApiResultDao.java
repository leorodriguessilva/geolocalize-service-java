package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;

import java.util.Date;

public class DynamoDBGeolocalizationApiResultDao implements PersistentDao {

    private final AmazonDynamoDB client;

    private String tableName;

    private String regionName;

    public DynamoDBGeolocalizationApiResultDao(String tableName, String regionName) {
        this.tableName = tableName;
        this.regionName = regionName;
        client = createConnection(regionName);
    }

    private AmazonDynamoDB createConnection(String regionName) {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(regionName)
                .build();
    }

    @Override
    public boolean save(GeolocalizationApiResult geolocalizationApiResult) {
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(tableName);

        Item item = new Item();
        item.withPrimaryKey("query", geolocalizationApiResult.getQuery())
        .with("longitude", geolocalizationApiResult.getLongitude())
        .with("latitude", geolocalizationApiResult.getLatitude())
        .with("expireAt", geolocalizationApiResult.getExpireAt());

        PutItemOutcome outcome = table.putItem(item);
        return outcome.getPutItemResult() != null;
    }

    @Override
    public boolean deleteByQuery(String query) {
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(tableName);

        PrimaryKey key = new PrimaryKey();
        key.addComponent("query", query);

        DeleteItemOutcome outcome = table.deleteItem(key);
        return outcome.getDeleteItemResult() != null;
    }

    @Override
    public GeolocalizationApiResult findByQuery(String query) {
        DynamoDB dynamoDB = new DynamoDB(client);

        Table table = dynamoDB.getTable(tableName);

        PrimaryKey key = new PrimaryKey();
        key.addComponent("query", query);

        Item item = table.getItem(key);

        GeolocalizationApiResult result = new GeolocalizationApiResult();
        result.setQuery(item.get("query").toString());
        result.setLongitude(Float.parseFloat(item.get("longitude").toString()));
        result.setLatitude(Float.parseFloat(item.get("latitude").toString()));
        result.setExpireAt(new Date(Long.parseLong(item.get("expireAt").toString())));

        return result;
    }

    @Override
    public void close() throws Exception {
        client.shutdown();
    }
}
