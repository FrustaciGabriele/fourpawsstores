package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.utils.utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;

public class CatalogueStoreControllerGrafico {
    @FXML
    private ImageView Add;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Icatalogo;
    @FXML
    private ImageView Iordini;
    @FXML
    private Label titleC;
    @FXML
    private VBox productList;
    private CatalogueController controller;
    private CatalogueBean catalogueBean;
    public void inizializza(StoreBeans storeB) throws DAOException, SQLException {
        Add.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Add.png"))));
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/package.png"))));
        titleC.setText("Il tuo catalogo: ");
        controller= new CatalogueController(storeB);
        catalogueBean= controller.getCatalogue(storeB);
        if(controller.checkLenght()){
        for(ProductBean p :catalogueBean.getListProdB()){

            HBox productInfo =productInfo(p);

            productList.getChildren().add(productInfo);

        }
        }else{
            Label warning= new Label("Non sono presenti prodotti nel catalogo");
            productList.getChildren().add(warning);
        }
    }
    private HBox productInfo(ProductBean p){
        HBox product =new HBox(10);
        product.getStyleClass().add("product-row");
        product.setPrefHeight(100);
        product.setMinHeight(100);
        product.setMaxHeight(100);
        product.setAlignment(Pos.CENTER_LEFT);
        product.setFillHeight(true);
        HBox.setHgrow(product, Priority.ALWAYS);

        VBox info =new VBox(10);
        product.setAlignment(Pos.CENTER);
        HBox.setHgrow(info, Priority.ALWAYS);
        info.setMaxWidth(Double.MAX_VALUE);

        VBox buttons= new VBox(10);
        buttons.setMinWidth(130);
        buttons.setPrefWidth(130);
        product.setAlignment(Pos.CENTER_RIGHT);
        Label name = new Label("Prodotto: \n" + p.getNameB());
        Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
        HBox img;
        if(p.getImage() != null) {
            try {
                InputStream input = p.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(90);
                imageView.setFitWidth(90);
                imageView.setPreserveRatio(true);

                img = new HBox(imageView);
            } catch (SQLException e) {
                img = new HBox(new Text("IMG \nNON PRESENTE"));
            }
        }else{
            img = new HBox(new Text("IMG \nNON PRESENTE"));
        }
        img.setAlignment(Pos.CENTER);
        img.setMinWidth(90);
        img.setPrefWidth(90);
        img.setMaxWidth(90);
        img.setMinHeight(90);
        img.setPrefHeight(90);
        img.setMaxHeight(90);
        img.getStyleClass().add("scroll-image");
        Button removeProd= new Button("Rimuovi il prodotto");
        removeProd.setOnAction(e ->{

        });
        Button notAvailableProd= new Button("Segna non disponibile");
        notAvailableProd.setOnAction(e->{

        });
        Button description= new Button("i");
        description.getStyleClass().add("roundbutton");
        description.setOnAction(e ->{
            utils.showPopUpDes(p.getDescriptionB());
        });


        buttons.getChildren().addAll(removeProd,notAvailableProd);
        info.getChildren().addAll(name,price);
        product.getChildren().addAll(img,info,description,buttons);
        return product;
    }
    public void addProduct(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader;
        Stage stage = ApplicazioneStage.getStage();
        Scene scene;
        String fxmlFile;
        fxmlFile="/com/example/fourpawsstores/aggiungiprodotto.fxml";
        fxmlLoader = new FXMLLoader();
        Parent rootNode = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));
        final AddProductControllerGrafico controller=fxmlLoader.getController();
        controller.inizializza();
        scene = new Scene(rootNode, utils.getSceneW(), utils.getSceneH());


        stage.setTitle("4Paws Stores");
        stage.setScene(scene);
        stage.show();
    }


}
