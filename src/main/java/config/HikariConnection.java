package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariConnection {

    private static final HikariDataSource ds;

    static {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(
                "jdbc:mysql://localhost:3306/foodStore" +
                        "?useSSL=false" +
                        "&serverTimezone=UTC" +
                        "&allowPublicKeyRetrieval=true"
        );


        config.setUsername("root");
        config.setPassword("");

        config.setMaximumPoolSize(10);

        ds = new HikariDataSource(config);
    }

    public static Connection getConnection()
            throws SQLException {

        return ds.getConnection();
    }
}