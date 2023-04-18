package com.icebornedb.iceborne.kernel;

public class DBConfig {
      private String driver;
      private String url;
      private String user;
      private String password;
      private String database;
      public DBConfig(String driver, String url, String user, String password, String database) {
          this.driver   = driver;
          this.url      = url;
          this.user     = user;
          this.password = password;
          this.database = database;
      }
      public DBConfig() {}
      public String getDriver() {
            return this.driver;
      }
      public String getUrl() {
            return this.url;
      }
      public String getUser() {
            return this.user;
      }
      public String getPassword() {
            return this.password;
      }
      public String getDatabase() {
            return this.database;
      }
      public void setDriver(String driver) {
            this.driver = driver;
      }
      public void setUrl(String url) {
            this.url = url;
      }
      public void setUser(String user) {
            this.user = user;
      }
      public void setPassword(String password) {
            this.password = password;
      }
      public void setDatabase(String database) {
            this.database = database;
      }
}
