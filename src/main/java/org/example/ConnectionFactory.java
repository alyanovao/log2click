package org.example;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static String driver = "com.clickhouse.jdbc.ClickHouseDriver";
    private static String url = "jdbc:clickhouse://localhost:8123?socket_timeout=300000";
    private static String Name = "default";
    private static String Password = "";

    private static interface Singleton {
        final ConnectionFactory INSTANCE = new ConnectionFactory();
    }

    private final DataSource dataSource;

    private ConnectionFactory() {

        Properties properties = new Properties();
        try {
            properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties"));
            driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            Name = properties.getProperty("username");
            Password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        GenericObjectPool pool = new GenericObjectPool();
        DriverManagerConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
                url, properties
        );
        new PoolableConnectionFactory(
                connectionFactory, pool, null, "SELECT 1", 3, false, false, Connection.TRANSACTION_READ_COMMITTED
        );

        this.dataSource = new PoolingDataSource(pool);
    }

    public static Connection getDatabaseConnection() throws SQLException {
        return Singleton.INSTANCE.dataSource.getConnection();
    }
}
