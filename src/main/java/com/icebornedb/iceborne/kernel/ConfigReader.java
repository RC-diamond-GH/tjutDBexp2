package com.icebornedb.iceborne.kernel;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    public static DBConfig readConfig() {
        Properties properties = new Properties();
        try {
            InputStream stream = new FileInputStream("C:\\Users\\10129\\Documents\\ProxyD\\iceborne\\iceborne\\config\\dbconnection.properties");
            properties.load(stream);
        }catch (Exception e) {
            e.printStackTrace();
        }
        DBConfig config = new DBConfig();
        config.setDriver(properties.getProperty("driver"));
        config.setUrl(properties.getProperty("url"));
        config.setUser(properties.getProperty("user"));
        config.setPassword(properties.getProperty("pwd"));
        config.setDatabase(properties.getProperty("database"));
        return config;
    }
}
