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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private void openStorePopup(StoreBeans store) {
        Popup popup = new Popup();

        Stage owner = ApplicazioneStage.getStage();
        Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 80, Color.WHITE);
        Button buttonClose = new Button("X");
        buttonClose.setOnAction(e -> popup.hide());
        buttonClose.setStyle("-fx-alignment: center-right;");
        Text title =new Text ("Scheda Negozio");
        HBox titleboard=new HBox(buttonClose,title);
        Text Name=new Text("Nome: "+ store.getName());
        Text Address=new Text("Indirizzo: "+ store.getAddress());
        Text Tel= new Text("Tel. : "+ store.getTel());
        VBox information= new VBox(Name,Address,Tel);
        HBox img;
        if(store.getImage() != null) {
            try {
                InputStream input = store.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);

                img = new HBox(imageView);
            } catch (SQLException e) {
                img = new HBox(new Text("IMG NON PRESENTE"));
            }
        }else{
            img = new HBox(new Text("IMG NON PRESENTE"));
        }
        HBox imgInfo=new HBox(img, information);
        Text description= new Text("descrizione: "+store.getDescription());
        Button catalogue =new Button("Vai al Catalogo");
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
        VBox All= new VBox(titleboard,imgInfo, description,catalogue);

        popup.getContent().addAll(overlay, All);
        popup.show(owner);
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


}
