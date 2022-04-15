package woo;

import java.io.Serializable;

public class Notification implements Serializable{
    
    private String _type;
    private String _product_key;
    private int _product_price;
    private int _product_stock;
    

    public Notification(String type,String key,int price,int stock) {
        _type = type;
        _product_key = key;
        _product_price = price;
        _product_stock = stock;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("%s|%s|%d", _type,_product_key.toUpperCase(),_product_price);
        return str;
    }
}
