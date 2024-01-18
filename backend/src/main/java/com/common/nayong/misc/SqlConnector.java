package com.common.nayong.misc;

import com.common.core.base.log.Log;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class SqlConnector {
    private static final String K_URL = "jdbc:sqlserver://192.168.85.138;databaseName=RanUser;encrypt=true;trustServerCertificate=true;CharacterSet=UTF-8";
    private static final String K_USERNAME = "ranonline";
    private static final String K_PASSWORD = "123456";
    private static final String K_QUERY = "SELECT * FROM GSUserInfo";

    public SqlConnector() {}

    public void connect() {
        try (Connection connection = DriverManager.getConnection(K_URL, K_USERNAME, K_PASSWORD);
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(K_QUERY);) {
            while(resultSet.next()) {
                System.out.println("UserID : " + resultSet.getString("UserID"));
            }
        } catch (SQLException e) {
            Log.e(e.getMessage());
        }
    }
}
