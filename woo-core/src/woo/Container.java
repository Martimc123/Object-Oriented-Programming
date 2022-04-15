package woo;

import java.io.Serializable;

public class Container extends Product implements Serializable{
    
    private String _service_type;
    private String _service_level;
    private final int N = 8;

    public Container(String key,String supplier_key,int price,int critical_value,int stock,String service_type,String service_level) {
        super(key, supplier_key, price, critical_value, stock);
        _service_type = service_type;
        _service_level = service_level;
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

    public String getServQual()
    {
        return _service_level;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("CONTAINER|") + super.toString() + String.format("|%s|%s",_service_level,_service_type);
        return str;
    }
}
