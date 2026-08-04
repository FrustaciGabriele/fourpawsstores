package com.example.fourpawsstores;

import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.fourpawsstores.utils.utils;

import java.io.IOException;
import java.net.URL;
//prova commit

public class FourPawsApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicazioneStage.setStage(stage);


        FXMLLoader fxmlLoader = new FXMLLoader(FourPawsApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),utils.getSceneW(), utils.getSceneH());
        scene.getRoot().requestFocus();
        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}