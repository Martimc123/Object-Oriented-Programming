package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnauthorizedSupplierException;
import woo.app.exceptions.WrongSupplierException;
import woo.Supplier;
import woo.Product;
import woo.Notification;
import woo.exceptions.BadEntryException;

/**
 * Register order.
 */
public class DoRegisterOrderTransaction extends Command<Storefront> {

  Input<String> _sup_id;
  Input<String> _prod_id;
  Input<Integer> _quantity;
  Input<String> _more;


  public DoRegisterOrderTransaction(Storefront receiver) {
    super(Label.REGISTER_ORDER_TRANSACTION, receiver);
     _sup_id = _form.addStringInput(Message.requestSupplierKey());
     _prod_id = _form.addStringInput(Message.requestProductKey());
      _quantity = _form.addIntegerInput(Message.requestAmount());
      _more = _form.addStringInput(Message.requestMore());
  }

  @Override
  public final void execute() throws DialogException,UnauthorizedSupplierException,WrongSupplierException {
    _form.parse();
    Supplier sup = _receiver.getSupplier(_sup_id.value());
    int cost = 0;
    int option = 0;
        try
        {
          //while (_more.value().equals("s")){
          if (sup.getStatus().equals("NÃO")){
            option = 1;
            _receiver.throwentryexception(_sup_id.value(),new UnauthorizedSupplierException(_sup_id.value()));
            }
          else if (!_receiver.In_productsSpec(_prod_id.value(),sup.getProducts())){
            option = 2;
            _receiver.throwentryexception(_prod_id.value(),new WrongSupplierException(_sup_id.value(),_prod_id.value()));
          }
          else
          {
            Product produto = _receiver.getProduct(_prod_id.value());
            int old_stock = produto.getStock();
            if (old_stock == 0)
            {
              Notification NEW = new Notification("NEW",_prod_id.value(), produto.getPrice() ,old_stock+_quantity.value());
              _receiver.getProduct(_prod_id.value()).notifyObservers(NEW);
            }
            cost+= (sup.getProducts().get(_prod_id.value()).getPrice() * _quantity.value());
            _receiver.getProduct(_prod_id.value()).setStock(_quantity.value());
            _receiver.setOrders(cost);
            _receiver.setContBalance();
            _receiver.setBalance();
          }
          //_display.popup(String.format("%s|%s|%d\n",_more.value(),_prod_id.value(),_quantity.value()));
          //_form.parse();
          //_display.popup(String.format("|%d|",cost));
    
          //_prod_id = _form.addStringInput(Message.requestProductKey());
          //_quantity = _form.addIntegerInput(Message.requestAmount());
      }
      catch (BadEntryException e)
      {
        if (option == 1)
        throw new UnauthorizedSupplierException(_sup_id.value());
        else if (option == 2)
        throw new WrongSupplierException(_sup_id.value(),_prod_id.value());
      }
    }
}
