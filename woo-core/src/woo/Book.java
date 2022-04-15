package woo;

import java.io.Serializable;

public class Book extends Product implements Serializable{
    
    private String _title;
    private String _author;
    private String _isbn;
    private final int N = 3; 

    public Book(String key, String supplier_key, int price, int critical_value, int stock, String title, String author, String isbn){
        super(key, supplier_key, price, critical_value, stock);
        _title = title;
        _author = author;
        _isbn = isbn;
    }

    @Override
    public int getNvalue()
    {
        return N;
    }

    public String getTitle()
    {
        return _title;
    }

    public String getAuthor()
    {
        return _author;
    }

    public String getISBN()
    {
        return _isbn;
    }

    @Override
    @SuppressWarnings("nls")
    public String toString() {
        String str = String.format("BOOK|") + super.toString() + String.format("|%s|%s|%s", _title, _author, _isbn);
        return str;
    }
}
