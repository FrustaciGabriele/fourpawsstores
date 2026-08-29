package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class OrderControllerGrafico extends InfoCardControllerGrafico {
    @FXML
    Label Title;
    @FXML
    private ImageView Iordini;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private VBox orderList;
    private ListOrderBean orders;
    private OrderController controller;
    public void inizializza()  throws DAOException, SQLException {
        Title.setText("I tuoi ordini:");
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Imappa.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/map.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/packageclicked.png"))));
        controller= new OrderController();
        orders= controller.getOrders();
        for (OrderBean order : orders.getListOrderB()){
            HBox orderline = cardInfoOrder(order);
            orderList.getChildren().add(orderline);
        }
    }
    @Override
    protected void showOrder(OrderBean order) throws DAOException, SQLException {
        StoreBeans store= controller.getInfoStore(order.getStoreIdB());
        OrderBean orderB= controller.getCompleteOrder(order);
        popupOrder(store,orderB);
    }

    private void popupOrder(StoreBeans store, OrderBean orderB) {
        Popup popup = new Popup();

        Stage owner = ApplicazioneStage.getStage();
        Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 80, Color.WHITE);

        Label title=new Label("Dettagli ordine:");
        Button buttonClose = new Button("X");
        buttonClose.setOnAction(e -> popup.hide());
        buttonClose.setStyle("-fx-alignment: center-right;");
        HBox top=new HBox(title,buttonClose);

        HBox storeInfo= new HBox(10);
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
        Text Name=new Text("Nome: "+ store.getName());
        Text Address=new Text("Indirizzo: "+ store.getAddress());
        Text Tel= new Text("Tel. : "+ store.getTel());
        VBox information= new VBox(Name,Address,Tel);
        storeInfo.getChildren().addAll(img,information);

        VBox productOrderList=new VBox(10);
        List<ProductBean> listB=orderB.getListProductB();
        for(ProductBean p :listB){
            HBox product = cardInfoProduct(p,orderB);
            productOrderList.getChildren().add(product);
        }
        ScrollPane scrollProduct = new ScrollPane(productOrderList);
        VBox all=new VBox(top,storeInfo,scrollProduct);
        popup.getContent().addAll(overlay,all);
        popup.show(owner);
    }

    public void goToMap() throws IOException, IOException {
        controller.goBackToMap();

    }

    public void goToProfile(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        controller.goToProfileScene();
    }
}
