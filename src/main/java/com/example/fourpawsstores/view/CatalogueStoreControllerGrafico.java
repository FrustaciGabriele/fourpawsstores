package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.controller.NavigationController;
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
    private CatalogueController controllerCatalog;
    private CatalogueBean catalogueBean;
    public void inizializza(StoreBeans storeB) throws DAOException, SQLException {
        Add.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/Add.png"))));
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/catalogo2.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/package.png"))));
        titleC.setText("Il tuo catalogo: ");
        controllerCatalog = new CatalogueController(storeB);
        catalogueBean= controllerCatalog.getCatalogue(storeB);
        if(controllerCatalog.checkLenght()){
        for(ProductBean p :catalogueBean.getListProdB()){

            HBox productInfo = productInfoCatStore(p);

            productList.getChildren().add(productInfo);

        }
        }else{
            Label warning= new Label("Non sono presenti prodotti nel catalogo");
            productList.getChildren().add(warning);
        }
    }
    private HBox productInfoCatStore(ProductBean p){
        HBox prodCatStore =new HBox(10);
        prodCatStore.getStyleClass().add("product-row");
        prodCatStore.setPrefHeight(100);
        prodCatStore.setMinHeight(100);
        prodCatStore.setMaxHeight(100);
        prodCatStore.setAlignment(Pos.CENTER_LEFT);
        prodCatStore.setFillHeight(true);
        HBox.setHgrow(prodCatStore, Priority.ALWAYS);

        VBox info =new VBox(10);
        prodCatStore.setAlignment(Pos.CENTER);
        HBox.setHgrow(info, Priority.ALWAYS);
        info.setMaxWidth(Double.MAX_VALUE);

        VBox buttons= new VBox(10);
        buttons.setMinWidth(130);
        buttons.setPrefWidth(130);
        prodCatStore.setAlignment(Pos.CENTER_RIGHT);
        Label name = new Label("Prodotto: \n" + p.getNameB());
        Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
        HBox iamgeProd;
        if(p.getImage() != null) {
            try {
                InputStream input = p.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(90);
                imageView.setFitWidth(90);
                imageView.setPreserveRatio(true);

                iamgeProd = new HBox(imageView);
            } catch (SQLException e) {
                iamgeProd = new HBox(new Text("IMG \nNON PRESENTE"));
            }
        }else{
            iamgeProd = new HBox(new Text("IMG \nNON PRESENTE"));
        }
        iamgeProd.setAlignment(Pos.CENTER);
        iamgeProd.setMinWidth(90);
        iamgeProd.setPrefWidth(90);
        iamgeProd.setMaxWidth(90);
        iamgeProd.setMinHeight(90);
        iamgeProd.setPrefHeight(90);
        iamgeProd.setMaxHeight(90);
        iamgeProd.getStyleClass().add("scroll-image");
        Button removeProd= new Button("Rimuovi il prodotto");
        removeProd.setOnAction(e ->{
            try {
                deleteProd(p);
                utils.openAdvisepopup("Prodotto rimosso correttamente");
                refreshUI();
            } catch (DAOException | SQLException ex) {
                utils.showErrorPopup("Errore");
                throw new RuntimeException(ex);
            }

        });
        String buttonText;
        if(p.getState().equals("disponibile")){
            buttonText="Segna indisponibile";
        }else{buttonText="Segna disponibile  ";}
        Button notAvailableProd= new Button(buttonText);
        notAvailableProd.setOnAction(e->{
            try {
                changeState(p);
                utils.openAdvisepopup("Prodotto modificato correttamente");
                refreshUI();
            } catch (DAOException | SQLException ex) {
            utils.showErrorPopup("Errore");
            throw new RuntimeException(ex);
            }

        });
        Button description= new Button("i");
        description.getStyleClass().add("roundbutton");
        description.setOnAction(e ->{
            utils.showPopUpDes(p.getDescriptionB());
        });


        buttons.getChildren().addAll(removeProd,notAvailableProd);
        info.getChildren().addAll(name,price);
        prodCatStore.getChildren().addAll(iamgeProd,info,description,buttons);
        return prodCatStore;
    }

    private void refreshUI() throws DAOException, SQLException {
        catalogueBean = controllerCatalog.refreshCatalogue();
        productList.getChildren().clear();
        for(ProductBean p :catalogueBean.getListProdB()){

            HBox productInfo = productInfoCatStore(p);

            productList.getChildren().add(productInfo);

        }
    }

    private void changeState(ProductBean p) throws DAOException, SQLException {
        controllerCatalog.changeProductState(p);
    }

    private  void deleteProd(ProductBean p) throws DAOException, SQLException {
        controllerCatalog.deleteProduct(p);

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


    public void goToOrders() throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goToOrderSceneStore();
    }

    public void goToProfile(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goToProfileStore();
    }
}
