package woo;

import woo.Sale;
import java.io.Serializable;

public interface State extends Serializable{
    public void verifyPoints(int points);
    public void verifyDelay(int limit,int pay);
    public void applyDiscount(Sale sale);
    public void applyFine(Sale sale);
}
