package woo;

import java.io.Serializable;

public class Box extends Product implements Serializable{
    
   /* private String key;
    private String supplier_key;
    private int price;
    private int critical_value;
    private int stock;*/
    private final int N = 5;
    private String _service_type;

    public Box(String key, String supplier_key, int price, int critical_value, int stock, String service_type) {
        super(key, supplier_key, price, critical_value, stock);
        _service_type = service_type;
    }

    @Override
    public int getNvalue()
    {
        return N;
    }
    
    public String getServType()
    {
        return _service_type;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("BOX|") + super.toString() + String.format("|%s", _service_type);
        return str;
    }
}
