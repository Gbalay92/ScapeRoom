package com.example.randomnumber;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;


public class ViewController implements Initializable {
    @FXML
    private Label nIntentos;
    @FXML
    private Label minMax;
    @FXML
    private Label feedback;
    @FXML
    private Label intentosR;
    @FXML
    private TextField inputNumber;
    @FXML
    private Button reset;
    @FXML
    private ImageView derrota;
    @FXML
    private ImageView victoria;
    private int nMin;
    private int nMax;

    private int intentos = 6;
    private int aleatorio ;
    private boolean cont = true;
    private int diff;


    public void setnIntentos(int tries) {
        nIntentos.setText("Tienes un total de " + tries + " intentos");
    }




    public void onButtonResetClick(ActionEvent actionEvent) {
        chargeProperties();
        newGame();

    }

    public void newGame() {
        reset.setVisible(false);
        victoria.setVisible(false);
        derrota.setVisible(false);
        feedback.setText("¿Serás capaz de salir esta vez?");
        feedback.setTextFill(Color.WHITESMOKE);
        aleatorio = (int) (Math.random()*(nMax-nMin) + nMin);
        diff = 100;
        intentosR.setText("Te quedan " + intentos + " intentos");
        inputNumber.setText("");
        cont = true;
        writeLogger(new Date(), "New game");
        System.out.println(aleatorio);
    }


    public void onButtonProbarCLick(ActionEvent actionEvent) {
        if (cont) {
            try {
                checkInput();
            } catch (NumberFormatException e) {
                feedback.setText("A la proxima que no introduzcas numeros enteros te quito un intento");
            }
        }
    }

    private void checkInput() {
        int numIntr = Integer.parseInt(inputNumber.getText());
        if (numIntr > nMax || numIntr < nMin) {
            feedback.setText("El numero tiene que ser entre "+nMin+" y "+ nMax+" .. asi vas mal.");
        } else if (intentos != 0 && numIntr != aleatorio) {
            //no haber acertado
            writeLogger(new Date(), String.valueOf(numIntr) + " WRONG");
            intentos--;
            intentosR.setText("Te quedan " + intentos + " intentos");
            diff = aleatorio - numIntr;
            if (diff < 10 && diff > -10) {
                if (aleatorio < numIntr) {
                    feedback.setText("restale un poco, casi lo tienes!");
                } else {
                    feedback.setText("sumale un poco, casi lo tienes!");
                }
                feedback.setTextFill(Color.RED);
            } else if (diff < 20 && diff > -20) {
                if (aleatorio < numIntr) {
                    feedback.setText("Te acercas, pero tampoco mucho, sigue bajando!");
                } else {
                    feedback.setText("Te acercas, pero tampoco mucho, sigue subiendo!");
                }
                feedback.setTextFill(Color.ORANGE);
            } else {
                if (aleatorio < numIntr) {
                    feedback.setText("Asi no vas a salir nunca.. Baja que aun te queda!");
                } else {
                    feedback.setText("Asi no vas a salir nunca.. O empiezas a subir o te quedas aqui!!");
                }
                feedback.setTextFill(Color.AQUA);
            }
            //if(aleatorio)
        } else if (numIntr == aleatorio && intentos != 0) {
            //acertar
            writeLogger(new Date(), String.valueOf(numIntr) + " WIN");
            feedback.setText("CORRE!! ERES LIBRE!");
            victoria.setVisible(true);
            feedback.setTextFill(Color.MAGENTA);
            cont = false;
            reset.setVisible(true);
        }
        if (intentos == 0) {
            derrota.setVisible(true);
            reset.setVisible(true);
            feedback.setTextFill(Color.WHITESMOKE);
            feedback.setText("Tienes que quedarte aqui.. PARA SIEMPRE");
            cont = false;
        }
    }

    public static void writeLogger(Date date, String registro) {
        //System.out.println(System.getProperty("user.dir"));

        try {
            File log = new File("src\\main\\resources\\com\\example\\randomnumber\\log.csv");

            if (!log.exists()) {
                PrintWriter out = new PrintWriter(new FileWriter(log), true);
                //System.out.println("creando nuevo archivo.");
                log.createNewFile();
                out.write("Date;Number\n");
                out.append(date.toString() + "; " + registro + "\n");
                out.close();
            } else {
                FileWriter out = new FileWriter(log, true);
                out.append(date.toString() + "; " + registro + "\n");
                out.close();
            }
        } catch (IOException e) {
            System.out.println("COULD NOT LOG!!");
        }
    }

    public void chargeProperties() {
        FileInputStream in;
        try {
            in = new FileInputStream("src\\main\\resources\\configuration\\config.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Properties prop = new Properties();
        try {
            prop.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        nMin= Integer.parseInt(prop.getProperty("nMin"));
        nMax= Integer.parseInt(prop.getProperty("nMax"));
        intentos= Integer.parseInt(prop.getProperty("tries"));
        intentosR.setText("Te quedan " + intentos + " intentos");
        minMax.setText("Numero entre "+nMin+" y "+ nMax);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chargeProperties();
        aleatorio=(int) (Math.random()*(nMax-nMin) + nMin);
        setnIntentos(intentos);
        System.out.println(aleatorio);

    }
}
