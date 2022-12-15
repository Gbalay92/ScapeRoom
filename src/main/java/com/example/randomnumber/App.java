package com.example.randomnumber;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("tabs.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 480);
        stage.setTitle("Scape Room");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        ViewController.writeLogger(new Date(), "New start");
        launch();
    }
}