package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import com.example.fourpawsstores.model.domain.ApplicazioneStage;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private static final String SETTING1 = "TimesNewRoman";
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
        Rectangle overlay = new Rectangle(owner.getWidth() - 50, owner.getHeight() - 150, Color.WHITE);
        overlay.setStyle("-fx-fill: white; -fx-stroke: black; -fx-stroke-width: 1;");

        Label title=new Label("Dettagli ordine:");
        title.setFont(Font.font(SETTING1, FontWeight.BOLD, 18));
        title.setStyle("-fx-alignment: center");
        Button buttonClose = new Button("X");
        buttonClose.setOnAction(e -> popup.hide());
        buttonClose.setStyle("-fx-alignment: center-right;");
        HBox top=new HBox(title,buttonClose);
        top.setAlignment(Pos.CENTER);

        HBox storeInfo= new HBox(10);
        HBox img;
        if(store.getImage() != null) {
            try {
                InputStream input = store.getImage().getBinaryStream();
                Image image = new Image(input);

                ImageView imageView = new ImageView(image);

                imageView.setFitHeight(85);
                imageView.setFitWidth(85);
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
        Text storeName=new Text("Nome: "+ store.getName());
        Text storeAddress=new Text("Indirizzo: "+ store.getAddress());
        Text StoreTel= new Text("Tel. : "+ store.getTel());
        VBox storeInformation= new VBox(storeName,storeAddress,StoreTel);
        storeInfo.getChildren().addAll(img,storeInformation);

        VBox productOrderList=new VBox(10);
        List<ProductBean> listB=orderB.getListProductB();
        for(ProductBean prod :listB){
            HBox product = cardInfoProduct(prod,orderB);
            productOrderList.getChildren().add(product);
        }
        ScrollPane scrollProductOrder = new ScrollPane(productOrderList);
        scrollProductOrder.setPrefHeight(400);
        scrollProductOrder.setPrefWidth(355);
        scrollProductOrder.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollProductOrder.setFitToWidth(true);

        productOrderList.setFillWidth(true);
        productOrderList.maxWidthProperty().bind(scrollProductOrder.widthProperty());
        VBox all=new VBox(top,storeInfo,scrollProductOrder);
        all.maxWidthProperty().bind(overlay.widthProperty());
        all.maxHeightProperty().bind(overlay.heightProperty());
        all.prefWidthProperty().bind(overlay.widthProperty());
        all.prefHeightProperty().bind(overlay.heightProperty());
        popup.getContent().addAll(overlay,all);
        popup.show(owner);
    }

    public void goToMap() throws  IOException {
        NavigationController navController= new NavigationController();
        navController.goBackToMap();

    }

    public void goToProfile(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.profileScene();
    }
}
