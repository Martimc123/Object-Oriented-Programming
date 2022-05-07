package woo;

import woo.Sale;
import java.io.Serializable;

public class SelectionState implements State,Serializable{
    Client cliente;

    public SelectionState(Client cliente)
    {
        this.cliente = cliente;
    }

    public void verifyPoints(int points)
    {
        if (points > 25000)
            cliente.setState(cliente.getEliteState());
    }

    public void verifyDelay(int limit,int pay)
    {
        if ( (pay-limit) > 2)
            cliente.setState(cliente.getNormalState());
    }

    public void applyFine(Sale sale)
    {
        int delay = sale.getPaymentDate() - sale.getDeadline();
        int period = sale.getPeriod();
        if (period == 3 && delay > 1)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.02*delay);
        }
        else if (period == 4)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.05*delay);
        }
    }

    public void applyDiscount(Sale sale)
    {
        int period = sale.getPeriod();
        int no_delay = sale.getDeadline() - sale.getPaymentDate();
        if (period == 1)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.1*-1);
        }
        else if (period == 2 && no_delay >=2)
        {
            sale.applyChangeCost(sale.getFinalCost()*0.05*-1);
        }
        /*else if (period == 2 && no_delay < 2)
        {}*/
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        return "SELECTION";
    }
}
