package woo;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable,CSubject{
    private String _key;
    private String _supplier_key;
    private int _price;
    private int _critical_value;
    private int _stock;
    private Boolean _notify;
    private ArrayList<P_Observer> _observers;

    public Product(String key, String supplier_key, int price, int critical_value, int stock) {
        _key = key;
        _supplier_key = supplier_key;
        _price = price;
        _critical_value = critical_value;
        _stock = stock;
        _notify = true;
        _observers = new ArrayList<P_Observer>();
    }

    //used only for notification purposes
    public Product() {}

    public void registerObserver(P_Observer o)
    {
        _observers.add(o);
    }

    public ArrayList<P_Observer> getObservers()
    {
        return _observers;
    }

    public void removeObserver(P_Observer o)
    {
        int index = _observers.indexOf(o);
        if (index >= 0)
            _observers.remove(index);
    }

    public void notifyObservers(Notification n)
    {
        for (P_Observer observer : _observers)
        {
            observer.update(n);
        }
    }

    public String getId()
    {
        return _key;
    }

    public String getSupKey()
    {
        return _supplier_key;
    }

    public int getPrice()
    {
        return _price;
    }

    public void setPrice(int price)
    {
        _price = price;
    }

    public void setStock(int stock)
    {
        _stock+=stock;
    }

    public int getCriticValue()
    {
        return _critical_value;
    }

    public int getStock()
    {
        return _stock;
    }

    public Boolean getNotify()
    {
        return _notify;
    }

    public int getNvalue()
    {
        return 0;
    }

    public String showProdquant()
    {
        String str = String.format("%s|%d", _key,_stock);
        return str;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("%s|%s|%d|%d|%d", _key.toUpperCase(), _supplier_key.toUpperCase(), _price, _critical_value, _stock);
        return str;
    }
}
