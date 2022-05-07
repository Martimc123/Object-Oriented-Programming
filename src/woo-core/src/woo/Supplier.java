package woo;

import java.io.Serializable;
import java.util.*;

public class Supplier implements Serializable{
    /* The Supplier's id */
    private String _key;

    /* The Supplier's name */
    private String _name;

    /* The Supplier's address */
    private String _address;

    /* The Supplier's status */
    private String _active;

    private Map<String, Product> _products;
    private Map<Integer, Order> _transactions;

    /**
   * Constructor (initializes id,name,address and active).
   * 
   * @param key
   *          the supplier's key.
   * @param name
   *          the supplier's name.
   * @param address
   *          the supplier's address.
   * @param active
   *          the supplier's status.
   */
    public Supplier(String key,String name,String address)
    {
        _key = key;
        _name = name;
        _address = address;
        _active = "SIM";
        _products = new HashMap<String, Product>();
        _transactions = new HashMap<Integer, Order>();
    }

    /**
    * @return the supplier's key.
    */
    public String getId()
    {
        return _key;
    }

    /**
    * @return the supplier's transactions.
    */
    public Map<Integer, Order> getTransactions()
    {
        return _transactions;
    }

    public Map<String, Product> getProducts()
    {
        return _products;
    }

    /**
    * @return the supplier's name.
    */
    public String getName()
    {
        return _name;
    }

    /**
    * @return the supplier's address.
    */
    public String getAddress()
    {
        return _address;
    }

    /**
    * @return the supplier's status.
    */
    public String getStatus()
    {
        return _active;
    }

    public void SetStatus(String nstatus)
    {
        _active = nstatus;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("%s|%s|%s|%s", _key.toUpperCase(), _name, _address,_active);
        return str;
    }
}