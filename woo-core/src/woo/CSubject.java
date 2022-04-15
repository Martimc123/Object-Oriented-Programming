package woo;

import java.io.Serializable;

public interface CSubject extends Serializable {
    public void registerObserver(P_Observer o);
    public void removeObserver(P_Observer o);
    public void notifyObservers(Notification n);

}
