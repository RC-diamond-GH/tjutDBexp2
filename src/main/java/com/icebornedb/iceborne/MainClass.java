package com.icebornedb.iceborne;

import com.icebornedb.iceborne.kernel.KernelMain;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainClass extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 450);
        stage.setTitle("课程管理");
        stage.setScene(scene);
        stage.show();
    }
    public static KernelMain kernel;

    public static void main(String[] args) {
        try {
            kernel = KernelMain.getInstance();
        } catch (ClassNotFoundException | SQLException ignored) {}
        launch();
    }
}