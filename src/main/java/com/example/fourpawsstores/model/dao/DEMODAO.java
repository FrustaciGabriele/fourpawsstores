package com.example.fourpawsstores.model.dao;

import com.example.fourpawsstores.model.bean.OrderBean;
import com.example.fourpawsstores.model.domain.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DEMODAO {
    private static List<Store> stores= new ArrayList<>();
    private static List<Catalogue> catalogs= new ArrayList<>();
    private static List<Order> orders=new ArrayList<>();
    private static List<Card> cards= new ArrayList<>();
    private static int idProd=1;
    private static int idOrder=1;
    private String available= "disponibile";


    public void inizializza(){
        Store negozioDemo= new Store();
        negozioDemo.setid(1);
        negozioDemo.setlat(41.9073448);
        negozioDemo.setLon(12.4778853);
        negozioDemo.setname("Negozio Demo");
        negozioDemo.setAddress("Via del corso 60");
        negozioDemo.setDescription("Questo negozio viene utilizzato per la demo");
        negozioDemo.setTel("3214567890");
        stores.add(negozioDemo);
        Catalogue cat= new Catalogue(1);
        Product p=new Product(1,"ProdottoDemo","questo prodotto viene utilizzato per la demo",null,BigDecimal.valueOf(10.20));
        p.setState(available);
        cat.addProduct(p);
        catalogs.add(cat);
        Card c= new Card("1111111111111111","utente demo",456,"03/28");
        cards.add(c);
        Order orderDemo= new Order(1,"gabbo","Paga al ritiro",BigDecimal.valueOf(20.40),"in attesa", Timestamp.valueOf(LocalDateTime.now()),1);
        orderDemo.addToQuantity(1);
        orderDemo.addToProdId(1);
        orderDemo.addToProductList(p);
        orders.add(orderDemo);
    }

    public Store getStore(int storeId) {
        for (Store s: stores){
            if(s.getid()==storeId){
                return s;
            }
        }
        return null;
    }

    public Catalogue getCatalog(int id) {
       Catalogue cat=new Catalogue(id);
       for (Catalogue c: catalogs){
           if (c.getidCat()==id){
               for (Product p: c.getList()){
                   if(!p.getState().equals("rimosso"))
                    cat.addProduct(p);
               }
           }
       }
       return cat;
    }

    public void Addproduct(Product newProduct) {
        Product p = new Product(newProduct.getName(),newProduct.getDescription(),newProduct.getImg(),newProduct.getPrice());
        idProd=idProd+1;
        p.setid(idProd);
        p.setState(available);
        for (Catalogue c: catalogs){
            if (c.getidCat()==Profile.getStoreId()){
                    c.addProduct(p);
                }
            }
        }

    public ListStores findStores(Coordinate coord) {
        ListStores list= new ListStores();
        for(Store s: stores){
            double lat1=Math.toRadians(coord.getlat());
            double lon1=Math.toRadians(coord.getlon());
            double lat2=Math.toRadians(s.getLat());
            double lon2=Math.toRadians(s.getLon());
            double dLat= lat2-lat1;
            double dLon= lon2-lon1;
            double a= Math.sin(dLat/2)*Math.sin(dLat/2)
                    +Math.cos(lat1)*Math.cos(lat2)
                    *Math.sin(dLon/2)*Math.sin(dLon/2);
            double c= 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
            double d= 6371.0 * c;
            if(d<=4){
                list.addStore(s);
            }

        }
        return list;
    }

    public boolean addOrder(Order newOrder) {
        List<Product> listcat=getCatalog(newOrder.getStoreId()).getList();
        List<Product> listorder=newOrder.getListProduct();
        for (Product p1: listorder){
            for (Product p2: listcat){
                if(p1.getId()== p2.getId() && !p2.getState().equals(available)){
                    return false;
                }
            }
        }
        idOrder=idOrder+1;
        newOrder.setOrderId(idOrder);
        orders.add(newOrder);
        return true;
    }
    public void orderModify(int id, String text){
        for(Order ord: orders){
            if (ord.getOrderId()==id){
                ord.setStateOrder(text);
            }
        }
    }

    public ListOrder getOrders(String username) {
        ListOrder listord= new ListOrder();
        for (Order ord: orders){
            if (ord.getUserId().equals(username)){
                listord.addOrder(ord);
            }
        }
        return listord;
    }

    public ListOrder getOrdersStores(int storeId) {
        ListOrder listord= new ListOrder();
        for (Order ord: orders){
            if (ord.getStoreId()==storeId){
                listord.addOrder(ord);
            }
        }
        return listord;
    }

    public Order getOrderbyId(int orderIdB) {
        Order order = new Order();
        for (Order ord: orders){
            if (ord.getOrderId()==orderIdB){
                return ord;
            }
        }
        return order;
    }

    public void changeProduct(Product p, String text) {
        int id=p.getId();
        for (Catalogue c : catalogs){
            for(Product prod: c.getList()){
                if(prod.getId()==id){
                    prod.setState(text);
                    return;
                }
            }
        }
    }

    public Card obtainCard(String cardNum) {
        Card card = new Card();
        for (Card c : cards){
            if (c.getCardNumber().equals(cardNum)){
                return c;
            }
        }
        return card;
    }
}
