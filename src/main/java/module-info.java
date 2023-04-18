module com.icebornedb.iceborne {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.mariadb.jdbc;


    exports com.icebornedb.iceborne;
    opens com.icebornedb.iceborne to javafx.base, javafx.fxml;
}