package br.com.involves.geolocalize.dao.impl;

import br.com.involves.geolocalize.dao.api.PersistentDao;
import br.com.involves.geolocalize.domain.GeolocalizationApiResult;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Date;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CassandraGeolocalizationApiResultDao implements PersistentDao {

    private final Logger logger = LogManager.getLogger(CassandraGeolocalizationApiResultDao.class);

    private final CqlSession session;

    private String tableName;

    private String host;

    private int port;

    private String user;

    private String pass;

    public CassandraGeolocalizationApiResultDao(String host, int port, String user, String pass, String tableName) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.tableName = tableName;
        session = createSession();
    }

    public boolean save(GeolocalizationApiResult geolocalizationApiResult) {
        try {
            PreparedStatement statement = session.prepare(String.format("insert into %s (query, longitude, latitude, expireAt) values (?, ?, ?, ?)", tableName));
            BoundStatement binded = statement.bind();
            binded.setString(1, geolocalizationApiResult.getQuery());
            binded.setFloat(2, geolocalizationApiResult.getLongitude());
            binded.setFloat(3, geolocalizationApiResult.getLatitude());
            binded.setLong(4, geolocalizationApiResult.getExpireAt().getTime());

            session.execute(binded);
            return Boolean.TRUE;
        } catch(Exception ex) {
            logger.error(ex);
            return Boolean.FALSE;
        }
    }

    public boolean deleteByQuery(String query) {
        try {
            PreparedStatement statement = session.prepare(String.format("delete from %s where query = ?", tableName));
            BoundStatement binded = statement.bind(query);

            session.execute(binded);
            return Boolean.TRUE;
        } catch(Exception ex) {
            logger.error(ex);
            return Boolean.FALSE;
        }
    }

    public GeolocalizationApiResult findByQuery(String query) {
        try {
            PreparedStatement statement = session.prepare(String.format("select * from %s where query = ?", tableName));
            BoundStatement binded = statement.bind(query);

            ResultSet rs = session.execute(binded);
            Row row = rs.one();
            if(row != null) {
                GeolocalizationApiResult result = new GeolocalizationApiResult();
                result.setQuery(row.getString("query"));
                result.setLongitude(row.getFloat("longitude"));
                result.setLatitude(row.getFloat("latitude"));
                result.setExpireAt(new Date(row.getLong("expireAt")));
                return result;
            }
        } catch(Exception ex) {
            logger.error(ex);
        }
        return null;
    }

    public void close() throws IOException {
        session.close();
    }

    private CqlSession createSession() {
        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(host, port))
                .withAuthCredentials(user, pass)
                .build();
    }
}
