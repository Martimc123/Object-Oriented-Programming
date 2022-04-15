package woo;

import java.io.Serializable;

public class Transaction implements Serializable{
    private int _key;

    public Transaction(int key) {
        _key = key;
    }

    public int getId()
    {
        return _key;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("%s", _key);
        return str;
    }
}