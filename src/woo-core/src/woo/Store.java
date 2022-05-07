package woo;

import java.io.*;
import java.lang.reflect.Array;

import woo.exceptions.*;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Class Store implements a store.
 */
public class Store implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202009192006L;
  
  private Map<String, Product> _products = new TreeMap<String, Product>(String.CASE_INSENSITIVE_ORDER);
  private Map<String, Client> _clients = new TreeMap<String, Client>(String.CASE_INSENSITIVE_ORDER);
  private Map<String, Supplier> _suppliers = new TreeMap<String, Supplier>(String.CASE_INSENSITIVE_ORDER);
  private Map<Integer, Order> _orders = new TreeMap<Integer, Order>();
  private Map<Integer, Sale> _sales = new TreeMap<Integer, Sale>();

  private int _trans_id = 0;

  private int _date = 0;
  
  //Contabilistic Balance
  private int _cont_balance = 0;
  
  //Available Balance
  private int _av_balance = 0;

  private int _sales_paid = 0;

  private int _acc_sales = 0;

  private int _acc_orders = 0;


/************************************** STORE BALANCE ***************************************************** */
  public void setBalance()
  {
      _av_balance+=(_sales_paid - _acc_orders);
  }

  public void setContBalance()
  {
      _cont_balance+=(_acc_sales - _acc_orders);
  }

  public void setOrders(int cost)
  {
      _acc_orders+= cost;
  }

  public void setSales(int cost)
  {
      _acc_sales+= cost;
  }

  public void setSalesPaid(int cost)
  {
      _sales_paid+= cost;
  }

  public int getAvBalance()
  {
      return _av_balance;
  }

  public int getContBalance()
  {
      return _cont_balance;
  }

  public void check_sales()
  {
    for(Map.Entry<Integer, Sale> s : _sales.entrySet())
    {
        if (s.getValue().getStatus() == 0)
        {
            s.getValue().setPaymentDate(getDate());
            s.getValue().setPeriod(s.getValue().CalcPeriod(s.getValue().getDeadline()
            ,s.getValue().getPaymentDate(),getProduct(s.getValue().getProductKey()).getNvalue()));
            
            getClient(s.getValue().getClientKey()).applyFine(s.getValue());
            setSales(s.getValue().getFinalCost());
            setContBalance();
        }   
      }
  }

/**************************************************************************************************************** */

/*********************************************** OTHER  ********************************************************* */
  public Store(){}
  
  public int getDate()
  {
      return _date;
  }

  void advanceDate(int days) throws BadDateException{
      if (days > 0)  
        _date += days;
        else throw new BadDateException(days);
  }

  /**
   * @param txtfile filename to be loaded.
   * @throws IOException
   * @throws BadEntryException
   */
  void importFile(String txtfile) throws IOException, BadEntryException{
  	BufferedReader reader = new BufferedReader(new FileReader(txtfile));
        String line;
            // Reads all lines from file "filename"
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                Pattern patBox = Pattern.compile("^(BOX)");
                Pattern patBook = Pattern.compile("^(BOOK)");
                Pattern patContainer = Pattern.compile("^(CONTAINER)");
                Pattern patSupplier = Pattern.compile("^(SUPPLIER)");
                Pattern patClient = Pattern.compile("^(CLIENT)");
                if (patBox.matcher(fields[0]).matches()){
                    // Creates and registers Boxes
                    String key = fields[1];
                    String service_type = fields[2];
                    String supplier_key = fields[3];
                    int price = Integer.parseInt(fields[4]);
                    int critical_value = Integer.parseInt(fields[5]);
                    int stock = Integer.parseInt(fields[6]);
                    _products.put(key, new Box(key, supplier_key, price, critical_value, stock, service_type));
                }
                else if (patContainer.matcher(fields[0]).matches()){
                    // Creates and registers Containers
                    String key = fields[1];
                    String service_type = fields[2];
                    String service_level = fields[3];
                    String supplier_key = fields[4];
                    int price = Integer.parseInt(fields[5]);
                    int critical_value = Integer.parseInt(fields[6]);
                    int stock = Integer.parseInt(fields[7]);
                    _products.put(key, new Container(key, supplier_key, price, critical_value, stock, service_level, service_type));
                }
                else if (patBook.matcher(fields[0]).matches()){
                    // Creates and registers Containers
                    String key = fields[1];
                    String title = fields[2];
                    String author = fields[3];
                    String isbn = fields[4];
                    String supplier_key = fields[5];
                    int price = Integer.parseInt(fields[6]);
                    int critical_value = Integer.parseInt(fields[7]);
                    int stock = Integer.parseInt(fields[8]);
                    _products.put(key, new Book(key, supplier_key, price, critical_value, stock, title, author, isbn));
                }
                else if (patClient.matcher(fields[0]).matches()){
                    // Creates and registers Containers
                    String key = fields[1];
                    String name = fields[2];
                    String address = fields[3];
                    _clients.put(key, new Client(key, name, address));
                }
                else if (patSupplier.matcher(fields[0]).matches()){
                    // Creates and registers Containers
                    String key = fields[1];
                    String name = fields[2];
                    String address = fields[3];
                    _suppliers.put(key, new Supplier(key, name, address));
                }
            }
    }

    public int getTransId()
    {
        return _trans_id;
    }

    public void incTransId()
    {
        _trans_id++;
    }

    public Map<Integer, Order> getOrders()
    {
        return _orders;
    }

    public Order getOrder(int key)
    {
        return _orders.get(key);
    }

    public Map<Integer, Sale> getSales()
    {
        return _sales;
    }

    public Sale getSale(int key)
    {
        return _sales.get(key);
    }

    public void create_Sale(String client_key,String product_key,int quantity,int base_cost,int final_cost,int deadline,int payment_date)
    {
        Sale venda = new Sale(getTransId(),client_key,product_key,quantity,base_cost,final_cost,deadline,payment_date);
        _sales.put(_trans_id,venda);
        getClient(client_key).getTransactions().put(_trans_id,venda);
        incTransId();
    }

/**************************************************** PRODUCTS ********************************************** */
    
    public void create_Box(String key, String supplier_key, int price, int critical_value, int stock, String service_type)
    {
        Box caixa = new Box(key,supplier_key,price,critical_value,stock,service_type);
        _products.put(caixa.getId(),caixa);
    }

    public void create_BoxSpec(String key, String supplier_key, int price, int critical_value, int stock, String service_type,Map<String, Product> products)
    {
        Box caixa = new Box(key,supplier_key,price,critical_value,stock,service_type);
        products.put(caixa.getId(),caixa);
    }

    public void create_Container(String key,String supplier_key,int price,int critical_value,int stock,String type,String level)
    {
        Container contentor = new Container(key,supplier_key,price,critical_value,stock,type,level);
        _products.put(contentor.getId(),contentor);
    }

    public void create_ContainerSpec(String key,String supplier_key,int price,int critical_value,int stock,String type,String level,Map<String, Product> products)
    {
        Container contentor = new Container(key,supplier_key,price,critical_value,stock,type,level);
        products.put(contentor.getId(),contentor);
    }

    public void create_Book(String Id,String sup_id,int price,int critical_value,int stock,String title,String author,String isbn)
    {
        Book livro = new Book(Id,sup_id,price,critical_value,stock,title,author,isbn);
        _products.put(livro.getId(),livro);
    }

    public void create_BookSpec(String Id,String sup_id,int price,int critical_value,int stock,String title,String author,String isbn,Map<String, Product> products)
    {
        Book livro = new Book(Id,sup_id,price,critical_value,stock,title,author,isbn);
        products.put(livro.getId(),livro);
    }

    public Product getProduct(String key)
    {
        return _products.get(key);
    }

    public void ChangePrice(String key,int price,Map<String, Product> products)
    {
        Product c = products.get(key);
        c.setPrice(price);
    }

    public Map<String, Product> getProducts()
    {
        return _products;
    }

    public Boolean In_products(String key)
    {
        if (_products.containsKey(key))
            return true;
        else
            return false;
    }

    public Boolean In_productsSpec(String key,Map<String, Product> products)
    {
      if (products.containsKey(key))
          return true;
      else
          return false;
    }

/*************************************************************************************************************** */

/**************************************************** CLIENTS ************************************************* */
    public Client getClient(String key)
    {
        return _clients.get(key);
    }

    public void create_client(String Id,String name,String address)
    {
        Client cliente = new Client(Id,name,address);
        _clients.put(cliente.getId(),cliente);
    }

    public void throwexceptionclient(String key) throws NonUniqueClientKey
    {
        throw new NonUniqueClientKey(key);
    }

    public Boolean In_clients(String key)
    {
        if (_clients.containsKey(key))
            return true;
        else{
            return false;
        }
    }

/************************************************************************************************************** */    

/*********************************************** SUPPLIERS ***************************************************** */

    public void create_supplier(String Id,String name,String address) throws BadEntryException
    {
        Supplier forne = new Supplier(Id,name,address);
        _suppliers.put(forne.getId(), forne);
    }

    public void throwentryexception(String specify,Exception cause) throws BadEntryException
    {
        throw new BadEntryException(specify,cause);
    }

    
    public Supplier getSupplier(String key)
    {
        return _suppliers.get(key);
    }


    public Map<String, Supplier> getSuppliers()
    {
        return _suppliers;
    }


    public Boolean In_supp(String key)
    {
        if (_suppliers.containsKey(key))
            return true;
        else
            return false;
    }

    
/************************************ COMANDOS PRA MOSTRAR ************************************************************ */

    public String showClients()
    {
        String str = "";
        for(Map.Entry<String, Client> entry : _clients.entrySet())
            str+=entry.getValue().toString() + '\n';
        return str;
    }

    public void registersInteresse(Product prod)
    {
        for(Map.Entry<String, Client> entry : _clients.entrySet())
            prod.registerObserver(entry.getValue());
    }

    public String showSuppliers()
    {
        String str = "";
        for(Map.Entry<String, Supplier> entry : _suppliers.entrySet())
            str+=entry.getValue().toString() + '\n';
        return str;
    }

    public String showProducts()
    {
        String str = "";
        for(Map.Entry<String, Product> entry : _products.entrySet())
            str+=entry.getValue().toString() + '\n';
        return str;
    }

    public String showProductsPrice(int price)
    {
        String str = "";
        for(Map.Entry<String, Product> entry : _products.entrySet())
        {
            if (entry.getValue().getPrice() < price)
            {
                str += entry.getValue().toString() + "\n";
            }
        }
        return str;
    }

    public String showOrdersSpec(Map<Integer, Order> transactions)
    {
        String str = "";
        String info;
        for(Map.Entry<Integer, Order> entrada : transactions.entrySet())
        {
            str+=entrada.getValue().toString() + '\n';
            Map<String,Product> prods = entrada.getValue().getProds();
            for(Map.Entry<String, Product> prod : prods.entrySet())
            {   
                info = String.format("%s|%d",prod.getValue().getId(),prod.getValue().getStock());
                str += info + "\n";
            }
        }
        return str;
    }

    public String showOrderSpec(Transaction order,Map<String,Product> products)
    {
        String str = "";
        String info;
        str+=order.toString() + '\n';
        for(Map.Entry<String, Product> prod : products.entrySet())
        {   
            info = String.format("%s|%d",prod.getValue().getId(),prod.getValue().getStock());
            str += info + "\n";
        }
        return str;
    }

    public String showSalesSpec(Map<Integer, Transaction> transactions)
    {
        String str = "";
        for(Map.Entry<Integer, Transaction> entry : transactions.entrySet())
            str+=entry.getValue().toString() + '\n';
        return str;
    }

    public String showPaidSales(String client_key)
    {
        String str = "";
        for(Map.Entry<Integer, Sale> s : getClient(client_key).getTransactions().entrySet())
        {
            if (s.getValue().getStatus() == 1)
            {
                str+=s.getValue().toString() + '\n';
            }   
        }
        return str;
    }

    public String showClientSales(String client_key)
    {
        String str = "";
        for(Map.Entry<Integer, Sale> s : getClient(client_key).getTransactions().entrySet())
        {
            str+=s.getValue().toString() + '\n';   
        }
        return str;
    }

    public String showClientNotifications(String key)
    {
        String str = "";
        str +=  getClient(key).toString() + "\n";
        ArrayList<Notification> notifications = getClient(key).getNotifications();
        for(int i=0; i< notifications.size();i++)
        {
            str+= notifications.get(i).toString() + '\n';  
        }
        return str;
    }

    public String showSaleSpec(Transaction sale)
    {
        String str = "";
        str+=sale.toString() + '\n';
        return str;
    }

    public Boolean In_Orders(int key)
    {
        if (_orders.containsKey(key))
            return true;
        else
            return false;
    }

    public Boolean In_Sales(int key)
    {
        if (_sales.containsKey(key))
            return true;
        else
            return false;
    }
}
