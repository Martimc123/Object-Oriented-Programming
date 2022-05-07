package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException; 
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import java.io.*;
import java.util.*;
import woo.Supplier;
import woo.Box;
import woo.Container;
import woo.Notification;

/**
 * Change product price.
 */
public class DoChangePrice extends Command<Storefront> {

  Input<String> _id;
  Input<Integer> _new_price;
  
  public DoChangePrice(Storefront receiver) {
    super(Label.CHANGE_PRICE, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _new_price = _form.addIntegerInput(Message.requestPrice());
  }

  @Override
  public final void execute() throws DialogException {
    _form.parse();
    if (_receiver.In_products(_id.value().toLowerCase()))
    {
      int old_price = _receiver.getProduct(_id.value()).getPrice();
      _receiver.ChangePrice(_id.value().toLowerCase(),_new_price.value(),_receiver.getProducts());
      for (Map.Entry<String, Supplier> entry: _receiver.getSuppliers().entrySet())
      {
        if (_receiver.In_productsSpec(_id.value().toLowerCase(),entry.getValue().getProducts()))
          _receiver.ChangePrice(_id.value().toLowerCase(),_new_price.value(),entry.getValue().getProducts());
      }
      if (_new_price.value() < old_price)
      {
        Notification bargain = new Notification("BARGAIN",_id.value(),_new_price.value(),_receiver.getProduct(_id.value()).getStock());
        _receiver.getProduct(_id.value()).notifyObservers(bargain);
      }
    }
  }
}
