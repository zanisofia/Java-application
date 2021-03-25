/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import grafica.Grafica;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Matilde Rodolfi
 */
public class Algraph extends Application {
    @Override
    public void start(Stage primaryStage) {
        Grafica g = new Grafica(primaryStage);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
