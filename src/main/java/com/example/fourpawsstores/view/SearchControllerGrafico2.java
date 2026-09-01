package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.controller.SearchController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.utils.utils;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Popup;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

public class SearchControllerGrafico2 {
    @FXML
    private WebView webMap;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private ImageView Iordini;
    @FXML
    private TextField street;
    @FXML
    private TextField civic;
    @FXML
    private TextField city;
    private WebEngine engine;
    private String address;
    private ListStoresBean ListStores;
    private SearchController controller;
    public void inizializza(){
        controller= new SearchController();
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Imappa.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/mapclicked.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/package.png"))));
        engine = webMap.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);
            }
        });
        engine.load(getClass().getResource("/map.html").toExternalForm());
    }
    public void findAddress() {
        if(street.getText().equals("")|| civic.getText().equals("")|| city.getText().equals("")){
            utils.showErrorPopup("riempi tutti i campi");
        }else {
            address= street.getText() +" "+ civic.getText()+ ","  + city.getText();
            addressBean addrBean = new addressBean(address);
            SearchStores(addrBean);
        }

    }
    private void SearchStores(addressBean addrBean) {
        removeMarkers();
        try {
            ListStores = controller.obtainStores(addrBean);
            engine.executeScript("centerMap(" + ListStores.getLatB() + "," + ListStores.getLonB() + ")");
            aggiungiMarker(ListStores);

        } catch (IllegalArgumentException e){
            utils.showErrorPopup(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error:"+ e);
            utils.showErrorPopup("Error");
        }


    }
    public void aggiungiMarker(ListStoresBean Stor){
        for (StoreBeans s : Stor.getList()) {
            String js =String.format(Locale.US, "addMarker(%.6f, %.6f,%d, '%s')", s.getLat(), s.getLon(), s.getid(), s.getName());
            engine.executeScript(js);
        }
    }
    @SuppressWarnings("unused")
    public void onMarkerClicked(int id) {
        StoreBeans store = ListStores.getById(id);
        if(store!=null){
            openStorePopup(store);}
        else  {utils.showErrorPopup("Error");}
    }

    private void removeMarkers() {
        engine.executeScript("removeMarkers()");
    }
    public void SeeProfile(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.profileScene();

    }

    public void openOrders(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.obtainOrders();
    }

    public void handleEnter(ActionEvent actionEvent) {
        findAddress();
    }
    private void openStorePopup(StoreBeans store) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Stage owner = ApplicazioneStage.getStage();

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1;");
        root.setPrefWidth(280);
        HBox imageStorePopup;
        if(store.getImage() != null) {
            try {
                InputStream input = store.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageViewStore = new ImageView(image);

                imageViewStore.setFitHeight(60);
                imageViewStore.setFitWidth(60);
                imageViewStore.setPreserveRatio(true);

                imageStorePopup = new HBox(imageViewStore);
            } catch (SQLException e) {
                imageStorePopup = new HBox(new Text("IMG NON PRESENTE"));
            }
        }else{
            imageStorePopup = new HBox(new Text("IMG NON PRESENTE"));
        }
        Label name = new Label("Nome: "+ store.getName());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        // Telefono
        Label tel = new Label("Tel: " + store.getTel());

        // Via
        Label address = new Label("Via: "+store.getAddress());

        // Descrizione
        Label description = new Label("Descrizione: "+ store.getDescription());
        description.setWrapText(true);
        Button catalogue =new Button("Catalogo");
        catalogue.setOnAction(e -> {
            popup.hide();
            try {
                NavigationController navController= new NavigationController();
                navController.showCatalogue(store);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        Button close=new Button("Chiudi");
        close.setOnAction(e -> popup.hide());
        HBox buttons =new HBox(10,catalogue,close);
        root.getChildren().addAll(imageStorePopup, name, address, tel, description, buttons);

        popup.getContent().add(root);

        popup.show(owner, -10000, -10000);

        double popupWidth = root.getWidth();
        double popupHeight = root.getHeight();

        double centerX = owner.getX() + (owner.getWidth() - popupWidth) / 2;
        double centerY = owner.getY() + (owner.getHeight() - popupHeight) / 2;

        popup.setX(centerX);
        popup.setY(centerY);
    }

}
