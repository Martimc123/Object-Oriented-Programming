package woo;

import java.io.*;
import woo.exceptions.*;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set; 
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Storefront: façade for the core classes.
 */
public class Storefront {

  /** Current filename. */
  private String _filename;
  
  /** The actual store. */
  private Store _store = new Store();
  private boolean save = false;

  
  /**
   * @throws IOException
   * @throws FileNotFoundException
   * @throws MissingFileAssociationException
   */
  public void save(String file) throws IOException{
    ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
    oos.writeObject(_store);
    oos.close();
    save = false;
} 

  /**
   * @param filename
   * @throws MissingFileAssociationException
   * @throws IOException
   * @throws FileNotFoundException
   */
  public void saveAs(String filename) throws IOException{
    _filename = filename;
    save(filename);
  }

  /**
   * @param filename
   * @throws UnavailableFileException
   */
  public void load(String file) throws IOException, ClassNotFoundException {
    ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
    _store = (Store) ois.readObject();
    ois.close(); 
    _filename = file;
  }
  
  /**
   * @param textfile
   * @throws ImportFileException
   */
  public void importFile(String textfile) throws ImportFileException {
    try {
      _store.importFile(textfile);
      save = true;
    } catch (IOException | BadEntryException e) {
      throw new ImportFileException(textfile);
    }
  }

/**************************************************** STORE BALANCE **************************************************** */

  public void setBalance()
  {
      _store.setBalance();
      save = true;
  }

  public void setContBalance()
  {
      _store.setContBalance();
      save = true;
  }

  public void setOrders(int cost)
  {
      _store.setOrders(cost);
      save = true;
  }

  public void setSales(int cost)
  {
      _store.setSales(cost);
      save = true;
  }

  public void setSalesPaid(int cost)
  {
      _store.setSalesPaid(cost);
      save = true;
  }

  public int getAvBalance()
  {
      return _store.getAvBalance();
  }

  public int getContBalance()
  {
      return _store.getContBalance();
  }

  public int getTransId()
  {
    return _store.getTransId();
  }

  public void incTransId()
  {
    _store.incTransId();
    save = true;
  }

/******************************************************************************************************************************************* */

/*************************************************** SUPPLIERS **************************************************************************** */
  public Boolean In_supp(String key)
  {
    return _store.In_supp(key);
  }

  public void create_supplier(String Id,String name,String address) throws BadEntryException
  {
    _store.create_supplier(Id,name,address);
    save = true;
  }

  public void throwentryexception(String specify,Exception cause) throws BadEntryException
  {
    throw new BadEntryException(specify,cause);
  }

  public Supplier getSupplier(String key)
  {
    return _store.getSupplier(key);
  }

  public Map<String, Supplier> getSuppliers()
  {
    return _store.getSuppliers();
  }

  public String showSuppliers()
  {
    return _store.showSuppliers();
  }

/**************************************************************************************************************************************** */

/*************************************************** CLIENTS **************************************************************************** */
  public Client getClient(String key)
  {
    return _store.getClient(key);
  }
  
  public String showClients()
  {
    return _store.showClients();
  }

  public Boolean In_clients(String key)
  {
    return _store.In_clients(key);
  }

  public void create_client(String Id,String name,String address)
  {
    _store.create_client(Id,name,address);
    save = true;
  }

/******************************************************************************************************************************************* */

/*************************************************** PRODUCTS **************************************************************************** */
  public Map<String, Product> getProducts()
  {
    return _store.getProducts();
  }
  
  public Boolean In_products(String key)
  {
    return _store.In_products(key);
  }

  public Boolean In_productsSpec(String key,Map<String, Product> products)
  {
    return _store.In_productsSpec(key,products);
  }

  public String showClientNotifications(String key)
  {
    return _store.showClientNotifications(key);
  }

  public void ChangePrice(String key,int price,Map<String, Product> products)
  {
    _store.ChangePrice(key,price,products);
    save = true;
  }

  public Product getProduct(String key)
  {
    return _store.getProduct(key);
  }

  public String showProducts()
  {
    return _store.showProducts();
  }

  public String showProductsPrice(int price)
  {
    return _store.showProductsPrice(price);
  }
  
  public void create_Box(String key, String supplier_key, int price, int critical_value, int stock, String service_type)
    {
        _store.create_Box(key,supplier_key,price,critical_value,stock,service_type);
        save = true;
    }

  public void create_BoxSpec(String key, String supplier_key, int price, int critical_value, int stock, String service_type,
  Map<String, Product> products)
    {
        _store.create_BoxSpec(key,supplier_key,price,critical_value,stock,service_type,products);
        save = true;
    }

    public void create_Container(String key,String supplier_key,int price,int critical_value,int stock,String type,String level)
    {
        _store.create_Container(key,supplier_key,price,critical_value,stock,type,level);
        save = true;
    }

    public void create_ContainerSpec(String key,String supplier_key,int price,int critical_value,int stock,String type,String level,Map<String, Product> products)
    {
        _store.create_ContainerSpec(key,supplier_key,price,critical_value,stock,type,level,products);
        save = true;
    }

    public void create_Book(String Id,String sup_id,int price,int critical_value,int stock,String title,String author,String isbn)
    {
        _store.create_Book(Id,sup_id,price,critical_value,stock,title,author,isbn);
        save = true;
    }

    public void create_BookSpec(String Id,String sup_id,int price,int critical_value,int stock,String title,String author,String isbn,Map<String, Product> products)
    {
        _store.create_BookSpec(Id,sup_id,price,critical_value,stock,title,author,isbn,products);
        save = true;
    }

    public void registersInteresse(Product prod)
    {
      _store.registersInteresse(prod);
    }

/******************************************************************************************************************************************************************************** */   

/*********************************************************************** OTHER ************************************************************************************************** */

  public Map<Integer, Order> getOrders()
  {
    return _store.getOrders();
  }

  public void check_sales()
  {
    _store.check_sales();
  }

  public Order getOrder(int key)
  {
    return _store.getOrder(key);
  }

  public Map<Integer, Sale> getSales()
  {
    return _store.getSales();
  }

  public Sale getSale(int key)
  {
    return _store.getSale(key);
  }

  public void create_Sale(String client_key,String product_key,int quantity,int base_cost,int final_cost,int deadline,int payment_date)
  {
    _store.create_Sale(client_key,product_key,quantity,base_cost,final_cost,deadline,payment_date);
    save = true;
  }
  
  public String showOrdersSpec(Map<Integer, Order> transactions)
  {
    return _store.showOrdersSpec(transactions);
  }

  public String showOrderSpec(Transaction order,Map<String,Product> products)
  {
    return _store.showOrderSpec(order,products);
  }

  public String showSalesSpec(Map<Integer, Transaction> transactions)
  {
    return _store.showSalesSpec(transactions);
  }

  public String showSaleSpec(Transaction sale)
  {
    return _store.showSaleSpec(sale);
  }

  public Boolean In_Orders(int key)
  {
    return _store.In_Orders(key);
  }

  public Boolean In_Sales(int key)
  {
    return _store.In_Sales(key);
  }

  public String showPaidSales(String client_key)
  {
    return _store.showPaidSales(client_key);
  }

  public String showClientSales(String client_key)
  {
    return _store.showClientSales(client_key);
  }

  public int getDate()
  {
    return _store.getDate();
  }

  public void advanceDate_front(int days) throws BadDateException
  {
    _store.advanceDate(days);
    save = true;
  }

  public void throwexceptionclient(String key) throws NonUniqueClientKey
  {
    _store.throwexceptionclient(key);
  }

  /** Getters and setters */
  public String getFileName() {
    return _filename;
  }
  
  public void setFileName(String name) {
    _filename = name;
  }
  
  public boolean getSave() {
    return save;
  }
}
