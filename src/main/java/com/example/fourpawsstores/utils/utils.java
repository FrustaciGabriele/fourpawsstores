package com.example.fourpawsstores.utils;

import com.example.fourpawsstores.model.dao.DEMODAO;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
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
    private static int modalita=0;
    private static final String SETTING1 = "-fx-alignment: center;";
    private static final String SETTING2 = "-fx-alignment: center-right;";
    private static final String SETTING3 = "TimesNewRoman";
    private static final String SETTING4 = "-fx-background-color: white; -fx-padding: 20px; -fx-margin: 20px; ";
    public static void switchGUI(){
        if(grafica==0){
            grafica=1;
            sceneW = 1200;
            sceneH = 700;
        }else{
            grafica=0;
            sceneW = 400;
            sceneH = 700;
        }
    }
    public static void switchMode(){
        if(modalita==0){
            modalita=1;
            new DEMODAO().inizializza();
        }else{
            modalita=0;
        }
    }
    public static int getMode(){
        return modalita;
    }
    public static int getGUI(){return grafica;}

    public static double getSceneW(){
        return sceneW;
    }

    public static double getSceneH(){
        return sceneH;
    }

    public static void showErrorPopup(String msg) {Popup popup = new Popup();

        Stage owner = ApplicazioneStage.getStage();

        // Crea l'overlay nero
        Rectangle overlay = new Rectangle(owner.getWidth() - 5, owner.getHeight() - 5, Color.BLACK);
        overlay.setOpacity(0.3);

        // Crea il pulsante di chiusura
        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> popup.hide());
        closeButton.setStyle(SETTING2);

        Text title = new Text("Attenzione \t");
        title.setFont(Font.font(SETTING3, FontWeight.BOLD, 18));
        title.setStyle(SETTING1);

        HBox header = new HBox(10, title, closeButton);
        header.setAlignment(Pos.CENTER);

        Label messageLabel = new Label();
        messageLabel.setText("\n" + msg);
        messageLabel.setWrapText(true);

        VBox vBoxContentBody = new VBox(messageLabel);

        // Crea il contenuto del popup
        VBox popupContent = new VBox(header, vBoxContentBody);
        popupContent.setFillWidth(true);
        popupContent.setMaxWidth(owner.getWidth() - 200);
        popupContent.setMaxHeight(owner.getHeight() - 600);
        popupContent.setStyle(SETTING4);

        // Aggiungi l'overlay e il contenuto al popup
        StackPane popupRoot = new StackPane(overlay, popupContent);
        popupRoot.setStyle(SETTING1); // Centra il contenuto del popup
        popup.getContent().add(popupRoot);

        // Mostra il popup
        popup.show(owner);
    }

    public static void openAdvisepopup(String msg) {
        Popup popup = new Popup();
        Stage owner = ApplicazioneStage.getStage();

        // Crea l'overlay nero
        Rectangle overlay = new Rectangle(owner.getWidth() - 5, owner.getHeight() - 5, Color.BLACK);
        overlay.setOpacity(0.3);

        // Crea il pulsante di chiusura
        Button closeButton = new Button("X");

        closeButton.setOnAction(e -> popup.hide());
        closeButton.setStyle(SETTING2);

        Text title = new Text("Avviso:" +"\t");
        title.setFont(Font.font(SETTING3, FontWeight.BOLD, 18));
        title.setStyle(SETTING1);

        HBox header = new HBox(10, title, closeButton);
        header.setAlignment(Pos.CENTER);

        Label messageLabel = new Label();
        messageLabel.setText("\n" + msg);
        messageLabel.setWrapText(true);

        VBox vBoxContentBody = new VBox(messageLabel);

        // Crea il contenuto del popup
        VBox popupContent = new VBox(header, vBoxContentBody);
        popupContent.setFillWidth(true);
        popupContent.setMaxWidth(owner.getWidth() - 200);
        popupContent.setMaxHeight(owner.getHeight() - 600);
        popupContent.setStyle(SETTING4);

        // Aggiungi l'overlay e il contenuto al popup
        StackPane popupRoot = new StackPane(overlay, popupContent);
        popupRoot.setStyle(SETTING1); // Centra il contenuto del popup
        popup.getContent().add(popupRoot);

        // Mostra il popup
        popup.show(owner);
    }
    public static void showPopUpDes(String descriptionB) {

        System.out.println("" + descriptionB);
        Popup popup= new Popup();
        Stage owner = ApplicazioneStage.getStage();

        // Crea l'overlay nero
        Rectangle overlay = new Rectangle(owner.getWidth() - 5, owner.getHeight() - 5, Color.BLACK);
        overlay.setOpacity(0.3);

        // Crea il pulsante di chiusura
        Button closeButton = new Button("X");
        closeButton.setOnAction(e -> popup.hide());
        closeButton.setStyle(SETTING2);

        Text title = new Text("Descrizione: \t");
        title.setFont(Font.font(SETTING3, FontWeight.BOLD, 18));
        title.setStyle(SETTING1);

        HBox header = new HBox(10, title, closeButton);
        header.setAlignment(Pos.CENTER);

        Label messageLabel = new Label();
        messageLabel.setText("\n" + descriptionB);
        messageLabel.setWrapText(true);

        VBox vBoxContentBody = new VBox(messageLabel);

        // Crea il contenuto del popup
        VBox popupContent = new VBox(header, vBoxContentBody);
        popupContent.setFillWidth(true);
        popupContent.setMaxWidth(owner.getWidth() - 200);
        popupContent.setMaxHeight(owner.getHeight() - 600);
        popupContent.setStyle(SETTING4);

        // Aggiungi l'overlay e il contenuto al popup
        StackPane popupRoot = new StackPane(overlay, popupContent);
        popupRoot.setStyle(SETTING1); // Centra il contenuto del popup
        popup.getContent().add(popupRoot);

        // Mostra il popup
        popup.show(owner);
    }
}
