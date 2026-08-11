package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.CatalogueController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.CatalogueBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.Catalogue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.sql.SQLException;

public class CatalogueControllerGrafico {
    @FXML
    private ImageView Back;
    @FXML
    private ImageView ShoppingCart;
    @FXML
    private Label title;
    private CatalogueBean catB;
    private CatalogueController controller;

    public void inizializza(StoreBeans store) throws DAOException, SQLException {
        System.out.println("id= %d"+ store.getid());
        Back.setImage(new Image(getClass().getResourceAsStream("/images/backArrow.png")));
        ShoppingCart.setImage(new Image(getClass().getResourceAsStream("/images/icona.png")));
        title= new Label(store.getName()+ " catalogo:");
        controller= new CatalogueController();
        catB= controller.getCatalogue(store);
        for(ProductBean p :catB.getListProdB()){
            HBox product =new HBox(10);
            VBox info =new VBox(10);
            Label name = new Label("Prodotto: " + p.getNameB());
            Label description= new Label("Descrizione: "+ p.getDescriptionB());
            Label price= new Label("Prezzo: "+ p.getPriceB());
            HBox img;
            if(store.getImage() != null) {
                try {
                    InputStream input = p.getImage().getBinaryStream();
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
            Label numAdd= new Label("0");
            Button addCartButton= new Button("aggiungi al carrello");
            addCartButton.setOnAction(e ->{
                int q = Integer.parseInt(numAdd.getText()) + 1;
                numAdd.setText(String.valueOf(q));
                controller.addToCart(product);
            });
            info.getChildren().addAll(name,description,price);
            product.getChildren().addAll(img,info,addCartButton,numAdd);

        }

    }

    public void goBack(MouseEvent mouseEvent) {
    }

    public void openCart(MouseEvent mouseEvent) {
    }
}
