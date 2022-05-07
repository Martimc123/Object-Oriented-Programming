package woo;

import java.io.Serializable;

public interface P_Observer extends Serializable {
    public void update(Notification n);
}
