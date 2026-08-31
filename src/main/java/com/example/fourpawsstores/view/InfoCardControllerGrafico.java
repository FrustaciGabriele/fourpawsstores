package com.example.fourpawsstores.view;

import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.InputStream;
import java.sql.SQLException;

public abstract class InfoCardControllerGrafico {
    HBox cardInfoOrder(OrderBean order) {
        Label id =new Label("Numero ordine:"+ order.getOrderIdB());
        Label state= new Label("Stato ordine: "+ order.getStateB());
        Label payment= new Label("Pagamento: "+ order.getTypeOfPayB());
        Label total=new Label("Totale:"+order.getTotalB()+"€");
        Label date=new Label("Data: "+ order.getDateB());
        Button viewOrder =new Button("Apri");
        viewOrder.setOnAction(e->{
            try {
                showOrder(order);
            } catch (DAOException | SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox infoorder =new VBox(10);
        VBox otherinfo=new VBox(10);
        infoorder.getChildren().addAll(id,state,payment);
        otherinfo.getChildren().addAll(date,total);
        HBox orderline = new HBox(10);
        orderline.getStyleClass().add("order-row");
        orderline.getChildren().addAll(infoorder,otherinfo,viewOrder);
        return orderline;
    }

    HBox cardInfoProduct(ProductBean p, OrderBean orderB) {
        HBox product =new HBox(10);
        product.setStyle("-fx-border-color: #cccccc;\n" +
                "    -fx-border-width: 1;\n" +
                "    -fx-border-radius: 5;\n" +
                "    -fx-background-radius: 5;\n" +
                "    -fx-padding: 5;");
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

        Label name = new Label("Prodotto: " + p.getNameB());
        Label price= new Label("Prezzo: "+ p.getPriceB()+"€");
        Label numProd= new Label("Qt: "+ String.valueOf(orderB.getQuantityB().get(orderB.getListProductB().indexOf(p))));

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
                img = new HBox(new Text("IMG \n NON PRESENTE"));
            }
        }else{
            img = new HBox(new Text("IMG \n NON PRESENTE"));
        }
        img.setAlignment(Pos.CENTER);
        img.setMinWidth(90);
        img.setPrefWidth(90);
        img.setMaxWidth(90);
        img.setMinHeight(90);
        img.setPrefHeight(90);
        img.setMaxHeight(90);
        img.setStyle(" -fx-border-color: #cccccc;\n" +
                "    -fx-border-width: 1;\n" +
                "    -fx-background-color: #ffffff;\n" +
                "    -fx-border-radius: 6;\n" +
                "    -fx-background-radius: 6;");

        info.getChildren().addAll(name,price);
        product.getChildren().addAll(img,info,numProd);
        return product;
    }

    protected abstract void showOrder(OrderBean order) throws DAOException, SQLException;
}
