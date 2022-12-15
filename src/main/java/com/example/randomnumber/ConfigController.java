package com.example.randomnumber;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ConfigController implements Initializable {
    @FXML
    TextField nMin;
    @FXML
    TextField nMax;
    @FXML
    TextField tries;
    @FXML
    Button save;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void onButtonOk(ActionEvent actionEvent) throws IOException {
        if(nMin.getText().isEmpty() || nMax.getText().isEmpty() || tries.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, "Form Error!",
                    "Please enter all fields");
            return;
        }
        writeProperties();
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("tabs.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setTitle("Users");
        stage.setScene(new Scene(root));
        stage.show();


    }



    private void writeProperties() {
        Path destino = Paths.get("C:\\Users\\zalo_\\OneDrive\\Escritorio\\entregas\\randomnumber\\src\\main\\resources\\configuration\\config.properties");
        try {
            BufferedWriter bw = Files.newBufferedWriter(destino);
            String linea= "#config file\n" +
                    "#first test properties file\n\n";
            bw.write(linea);
            linea = "nMin= " + nMin.getText();
            bw.write(linea+"\n");
            linea = "nMax= " + nMax.getText();
            bw.write(linea+"\n");
            linea = "tries= " + tries.getText();
            bw.write(linea);
            bw.flush();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
