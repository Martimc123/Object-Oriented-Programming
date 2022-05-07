package woo;

import java.io.Serializable;
import java.util.*;

public class Order extends Transaction implements Serializable{
    
    private String _supplier_key;
    private int _deadline;
    private int _total_cost;
    private Map<String, Product> _products;
    

    public Order(int key,String supp_key,int cost) {
        super(key);
        _supplier_key = supp_key;
        _total_cost = cost;
         _products = new HashMap<String, Product>();
    }

    public Map<String, Product> getProds()
    {
        return _products;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = super.toString() + String.format("|%s|%d|%d", _supplier_key,_total_cost,_deadline);
        return str;
    }
}
