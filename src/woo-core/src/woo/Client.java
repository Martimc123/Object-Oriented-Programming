package woo;

import java.io.Serializable;
import java.util.*;

public class Client implements Serializable,P_Observer{
    /* The Client's key */
    private String _key;

    /* The Client's name */
    private String _name;

    /* The Client's address */
    private String _address;

    private int _purchases_made;

    private int _purchases_paid;

    private State _normal;

    private State _selection;

    private State _elite;

    private State _state;

    private int _points;

    private Map<String, Product> _products;
    private Map<Integer, Sale> _transactions;
    private ArrayList<Notification> _notifications = new ArrayList<Notification>();

    public Client(String key, String name, String address) {
        _key = key;
        _name = name;
        _address = address;
        _purchases_made = 0;
        _purchases_paid = 0;
        _normal = new NormalState(this);
        _selection = new SelectionState(this);
        _elite = new EliteState(this);
        _state = _normal;
        _points = 0;
        _transactions = new HashMap<Integer, Sale>();
        _products = new HashMap<String, Product>();
    }

    public void update(Notification n)
    {
        _notifications.add(n);
    }

    public ArrayList<Notification> getNotifications()
    {
        return _notifications;
    }

    public void setNotifications(ArrayList<Notification> n)
    {
        _notifications = n;
    }

    /**
    * @return the client's id.
    */
    public String getId()
    {
        return _key;
    }

    public Map<Integer, Sale> getTransactions()
    {
        return _transactions;
    }

    public Map<String, Product> getProducts()
    {
        return _products;
    }

    /**
    * @return the client's name.
    */
    public String getName()
    {
        return _name;
    }

    /**
    * @return the client's address.
    */
    public String getAddress()
    {
        return _address;
    }

    public State getStatus()
    {
        return _state;
    }

    public State getNormalState()
    {
        return _normal;
    }

    public State getSelectionState()
    {
        return _selection;
    }

    public State getEliteState()
    {
        return _elite;
    }

    public void applyFine(Sale sale)
    {
        _state.applyFine(sale);
    }

    public void setState(State estado)
    {
        _state = estado;
    }

    public void applyDiscount(Sale sale)
    {
        _state.applyDiscount(sale);
    }

    public void verifyPoints()
    {
        _state.verifyPoints(_points);
    }

    public void verifyDelay(int limit,int pay)
    {
        _state.verifyDelay(limit, pay);
    }

    public void setPoints(int final_cost,int period)
    {
        if (period == 1 || period == 2)
            _points += 10*final_cost;
    }

    public void removePoints(int period,double percent)
    {
        double pontos = percent*_points;
        if (period > 2)
            _points -= pontos;
    }

    public int getPurchasesP()
    {
        return _purchases_paid;
    }

    public int getPurchasesM()
    {
        return _purchases_made;
    }

    public void setPurchasesP(int price)
    {
        _purchases_paid+=price;
    }

    public void setPurchasesM(int price)
    {
        _purchases_made+=price;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("%s|%s|%s|%s|%d|%d", _key.toUpperCase(), _name, _address,_state.toString(),_purchases_made,_purchases_paid);
        return str;
    }
}