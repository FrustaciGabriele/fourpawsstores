package com.example.fourpawsstores.view;

import com.example.fourpawsstores.controller.NavigationController;
import com.example.fourpawsstores.controller.OrderController;
import com.example.fourpawsstores.exception.DAOException;
import com.example.fourpawsstores.model.bean.ListOrderBean;
import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.bean.ProductBean;
import com.example.fourpawsstores.model.bean.StoreBeans;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class OrderControllerGrafico2 extends InfoCardControllerGrafico{
    @FXML
    private ImageView Iordini;
    @FXML
    private ImageView Iprofilo;
    @FXML
    private ImageView Imappa;
    @FXML
    private VBox orderList;
    @FXML
    private VBox OrderBox;
    private ListOrderBean orders;
    private OrderController controller;
    public void inizializza() throws DAOException, SQLException {
        Iprofilo.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/icona.png"))));
        Imappa.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/map.png"))));
        Iordini.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/packageclicked.png"))));
        controller= new OrderController();
        orders= controller.getOrders();
        if(orders.getListOrderB().size()==0){
            orderList.getChildren().add(new Label("Non sono presenti ordini"));
        }else{
            for (OrderBean order : orders.getListOrderB()){
                HBox orderline = cardInfoOrder(order);
                orderList.getChildren().add(orderline);
            }
        }
        OrderBox.getChildren().add(new Label("Nessun ordine selezionato"));
    }

    @Override
    protected void showOrder(OrderBean order) throws DAOException, SQLException {
        StoreBeans store= controller.getInfoStore(order.getStoreIdB());
        OrderBean orderB= controller.getCompleteOrder(order);
        OrderBox.getChildren().clear();
        OrderBox.getChildren().add(fillCardBox(store, orderB));

    }

    private VBox fillCardBox(StoreBeans store, OrderBean orderB) {
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
        VBox all=new VBox(storeInfo,scrollProduct);
        return all;
    }

    public void SeeProfile(MouseEvent mouseEvent) throws DAOException, SQLException, IOException {
        NavigationController navController= new NavigationController();
        navController.profileScene();
    }

    public void gotoMap(MouseEvent mouseEvent) throws IOException {
        NavigationController navController= new NavigationController();
        navController.goBackToMap();
    }
}

