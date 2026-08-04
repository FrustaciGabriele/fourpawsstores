package com.example.fourpawsstores.utils;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
public class utils {
    private static double sceneW = 400;
    private static double sceneH = 700;
    private static int grafica=0;
    public static void switchGrafica(){
        if(grafica==0){
            grafica=1;
            sceneW = 1200;
            sceneH = 700;
        }else{
            grafica=0;
            sceneW = 414;
            sceneH = 695;
        }
    }

    public static double getSceneW(){
        return sceneW;
    }

    public static double getSceneH(){
        return sceneH;
    }
}
