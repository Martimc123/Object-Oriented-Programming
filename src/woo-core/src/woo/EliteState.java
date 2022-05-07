package woo;

import woo.Sale;
import java.io.Serializable;

public class EliteState implements State,Serializable {
    Client cliente;

    public EliteState(Client cliente)
    {
        this.cliente = cliente;
    }

    public void verifyPoints(int points)
    {
        //Doesn't need to verify points since its in the maximum state possible
    }

    public void verifyDelay(int limit,int pay)
    {
        if ( (pay-limit) > 15)
        {
            cliente.removePoints(4,0.75);
            cliente.setState(cliente.getSelectionState());
        }
    }

    public void applyFine(Sale sale){}

    public void applyDiscount(Sale sale)
    {
        int period = sale.getPeriod();
        if (period == 1 || period == 2)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.1*-1);
        }
        else if (period == 3)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.05*-1);
        }
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "ELITE";
    }
}
