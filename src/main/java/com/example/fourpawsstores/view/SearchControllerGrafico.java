package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.controller.SearchController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListStoresBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.bean.addressBean;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import com.example.fourpawsstores.model.domain.Store;
import com.example.fourpawsstores.utils.utils;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import javafx.stage.Popup;
import javafx.stage.Stage;
import netscape.javascript.JSObject;


import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Locale;


public class SearchControllerGrafico {
    @FXML
    private TextField address;
    @FXML
    private WebView webViewMap;
    @FXML
    private ImageView Icerca;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private ImageView Iordini;
    private SearchController search=null;
    private  WebEngine engine;
    private ListStoresBean Stores;


    public void inizializza() {
        search= new SearchController();
        Icerca.setImage(new Image(getClass().getResourceAsStream("/images/cerca.png")));
        Iprofilo.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        Imappa.setImage(new Image(getClass().getResourceAsStream("/images/mapclicked.png")));
        Iordini.setImage(new Image(getClass().getResourceAsStream("/images/package.png")));
        engine = webViewMap.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) engine.executeScript("window");
                window.setMember("javaApp", this);
            }
        });
        engine.load(getClass().getResource("/map.html").toExternalForm());

    }

    public void handleEnter(ActionEvent actionEvent) {FindAddress();}
    public void FindAddress() {
        if(
                address.getText().equals("")){
            utils.showErrorPopup("Inserisci un indirizzo");
        }else {
            addressBean addrBean = new addressBean(address.getText());
            SearchStores(addrBean);
            
        }
    }

    private void SearchStores(addressBean addrBean) {
        removeMarkers();
        try {
            Stores = search.obtainStores(addrBean);
            engine.executeScript("centerMap(" + Stores.getLatB() + "," + Stores.getLonB() + ")");
            aggiungiMarker(Stores);

        } catch (IllegalArgumentException e){
            utils.showErrorPopup(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error:"+ e);
            utils.showErrorPopup("Error");
        }


    }

    private void removeMarkers() {
        engine.executeScript("removeMarkers()");
    }

    public void SeeProfile(MouseEvent mouseEvent) throws IOException, DAOException, SQLException {
        NavigationController navController= new NavigationController();
        navController.profileScene();
        //search.profileScene();
    }

    public void openOrders(MouseEvent mouseEvent) throws IOException, DAOException, SQLException {
        NavigationController navController= new NavigationController();
        navController.obtainOrders();
        //search.obtainOrders();
    }
    public void aggiungiMarker(ListStoresBean Stor){
        for (StoreBeans s : Stor.getList()) {
            String js =String.format(Locale.US, "addMarker(%.6f, %.6f,%d, '%s')", s.getLat(), s.getLon(), s.getid(), s.getName());
            engine.executeScript(js);
        }
    }

    @SuppressWarnings("unused")
    public void onMarkerClicked(int id) {
        StoreBeans store = Stores.getById(id);
       if(store!=null){
        openStorePopup(store);}
       else  {utils.showErrorPopup("Error");}
    }

    private void openStorePopup(StoreBeans store) {
        Popup popup = new Popup();
        popup.setAutoHide(true);
        Stage owner = ApplicazioneStage.getStage();

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color: white; -fx-border-color: #cccccc; -fx-border-width: 1;");
        root.setPrefWidth(280);
        HBox img;
        if(store.getImage() != null) {
            try {
                InputStream input = store.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(60);
                imageView.setFitWidth(60);
                imageView.setPreserveRatio(true);

                img = new HBox(imageView);
            } catch (SQLException e) {
                img = new HBox(new Text("IMG NON PRESENTE"));
            }
        }else{
            img = new HBox(new Text("IMG NON PRESENTE"));
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
        root.getChildren().addAll(img, name, address, tel, description, buttons);

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


