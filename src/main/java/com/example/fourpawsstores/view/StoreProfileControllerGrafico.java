package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.AddProductController;
import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.controller.SearchController;
import com.example.fourpawsstores.controller.profileController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ProfileBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.utils.utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Objects;

public class StoreProfileControllerGrafico {
    @FXML
    private HBox StoreImg;
    @FXML
    private  Label storeName;
    @FXML
    private Label storeTel;
    @FXML
    private  Label storeAddr;
    @FXML
    private Label storeDes;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Icatalogo;
    @FXML
    private ImageView Iordini;

    private profileController controller;
    private StoreBeans storeB;
    private ProfileBean profileB;
    public void inizializza() throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona2.png"))));
        Icatalogo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/catalogo.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/package.png"))));
        controller=new profileController();
        profileB=controller.getProfileStore();
        storeB= controller.obtainStorebyId(profileB.getStoreId());
        storeName.setText(storeB.getName());
        storeAddr.setText(storeB.getAddress());
        storeTel.setText(storeB.getTel());
        storeDes.setText(storeB.getDescription());
        if(storeB.getImage() != null) {
            try {
                InputStream input = storeB.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(200);
                imageView.setPreserveRatio(true);

                StoreImg.getChildren().add(imageView);
            } catch (SQLException e) {
                StoreImg.getChildren().add(new Text("IMG NON PRESENTE"));
            }
        }else{
            StoreImg.getChildren().add(new Text("IMG NON PRESENTE"));
        }
    }
    public void changeProfile(MouseEvent mouseEvent) {
        utils.openAdvisepopup("funzione non ancora implementata");
    }

    public void goToCatalog(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goBackSceneCatStore();
    }

    public void goToOrder(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.goToOrderSceneStore();
    }
}
