package woo;

import java.io.Serializable;
import java.util.*;

public class Sale extends Transaction implements Serializable{
    
    private String _client_key;
    private String _product_key;
    private int _quantity;
    private int _base_cost;
    private int _final_cost;
    private int _deadline;
    private int _payment_date;
    private int _status;
    private int _period = 0;

    public Sale(int key,String client_key,String product_key,int quantity,int base_cost,int final_cost,int deadline,int payment_date) {
        super(key);
        _client_key = client_key;
        _product_key = product_key;
        _quantity = quantity;
        _base_cost = base_cost;
        _final_cost = final_cost;
        _deadline = deadline;
        _payment_date = payment_date;
        //if not paid status is 0 else is 1
        _status = 0;
    }

    public int getDeadline()
    {
        return _deadline;
    }

    public int getPaymentDate()
    {
        return _payment_date;
    }

    public int getFinalCost()
    {
        return _final_cost;
    }

    public int CalcPeriod(int limit,int pay,int N)
    {
        int no_delay = limit-pay;
        int delay = pay-limit;
        int res = 0;
        if (no_delay >= N)
            res = 1;
        else if (no_delay >= 0 && no_delay < N)
            res = 2;
        else if (delay > 0 && delay <= N)
            res = 3;
        else if (delay > N)
            res = 4;
        return res;
    }

    public int getPeriod()
    {
        return _period;
    }

    public void setPeriod(int period)
    {
        _period = period;
    }

    //Change can either be a discount or a fine
    public void applyChangeCost(double change)
    {
        _final_cost+= change;
    }

    public int getStatus()
    {
        return _status;
    }

    public String getClientKey()
    {
        return _client_key;
    }

    public String getProductKey()
    {
        return _product_key;
    }

    public int getBaseCost()
    {
        return _base_cost;
    }

    public void setStatusPayed()
    {
        _status = 1;
    }

    public void setPaymentDate(int date)
    {
        _payment_date = date;
    }

    public String showSaleAfterPay()
    {
        String str = super.toString() + String.format("|%s|%s|%d|%d|%d|%d|%d",_client_key,_product_key,_quantity,_base_cost,_final_cost,_deadline,_payment_date);
        return str;   
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = new String();
        if (_status == 0)
        {
            str = super.toString() + String.format("|%s|%s|%d|%d|%d|%d",_client_key,_product_key,_quantity,_base_cost,_final_cost,_deadline);
        }
        else if (_status == 1)
        {
            str = super.toString() + String.format("|%s|%s|%d|%d|%d|%d|%d",_client_key,_product_key,_quantity,_base_cost,_final_cost,_deadline,_payment_date);
            //return str;
        }
        return str;
    }
}
