package woo;

import woo.Sale;
import java.io.Serializable;

public class NormalState implements State,Serializable{
    Client cliente;

    public NormalState(Client cliente)
    {
        this.cliente = cliente;
    }
    
    public void verifyPoints(int points)
    {
        if (points > 2000)
            cliente.setState(cliente.getSelectionState());
    }

    public void verifyDelay(int limit,int pay)
    {
        //Doesn't matter the delay since it's a normal client
    }

    public void applyFine(Sale sale)
    {
        int delay = sale.getPaymentDate() - sale.getDeadline();
        int period = sale.getPeriod();
        if (period == 3)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.05*delay);
        }
        else if (period == 4)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.1*delay);
        }
    }

    public void applyDiscount(Sale sale)
    {
        if (sale.getPeriod() == 1)
            sale.applyChangeCost(sale.getFinalCost()*0.1*-1);
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "NORMAL";
    }
}
