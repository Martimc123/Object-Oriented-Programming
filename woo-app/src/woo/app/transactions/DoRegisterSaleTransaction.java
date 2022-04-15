package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.app.exceptions.UnavailableProductException;
import woo.exceptions.BadEntryException;
import woo.exceptions.NonUniqueClientKey;

/**
 * Register sale.
 */
public class DoRegisterSaleTransaction extends Command<Storefront> {

  private Input<String> _client_key;
  private Input<Integer> _deadline;
  private Input<String> _prod_key;
  private Input<Integer> _amount;

  public DoRegisterSaleTransaction(Storefront receiver) {
    super(Label.REGISTER_SALE_TRANSACTION, receiver);
    _client_key = _form.addStringInput(Message.requestClientKey());
    _deadline = _form.addIntegerInput(Message.requestPaymentDeadline());
    _prod_key = _form.addStringInput(Message.requestProductKey());
    _amount = _form.addIntegerInput(Message.requestAmount());
  }

  @Override
  public final void execute() throws DialogException,UnknownClientKeyException,UnknownProductKeyException,UnavailableProductException {
    _form.parse();
    int option = 0;
    try {
       if (!_receiver.In_clients(_client_key.value()))
       {
         _receiver.throwexceptionclient(_client_key.value());
       }
       else if (!_receiver.In_products(_prod_key.value()))
       {
         option = 2;
         _receiver.throwentryexception(_prod_key.value(),new UnknownProductKeyException(_prod_key.value()));
       }
       else if (_amount.value() > _receiver.getProduct(_prod_key.value()).getStock())
       {
         option = 3;
         _receiver.throwentryexception("",
         new UnavailableProductException(_prod_key.value(),_amount.value(),_receiver.getProduct(_prod_key.value()).getStock()));
       }
       else
       {
         int base_cost = _amount.value() * _receiver.getProduct(_prod_key.value()).getPrice();
         _receiver.getProduct(_prod_key.value()).setStock(-1*_amount.value());
         _receiver.create_Sale(_client_key.value(),_prod_key.value(),_amount.value(),base_cost,base_cost,_deadline.value(),_deadline.value());
         _receiver.getClient(_client_key.value()).setPurchasesM(base_cost);
         _receiver.setSales(base_cost);
        _receiver.setContBalance();
        }
    } 
    catch (NonUniqueClientKey e)
    {
      throw new UnknownClientKeyException(_client_key.value());
    }
    catch (BadEntryException e)
    {
      if (option == 2)
      throw new UnknownProductKeyException(_prod_key.value());
      else if (option == 3)
      throw new UnavailableProductException(_prod_key.value(),_amount.value(),_receiver.getProduct(_prod_key.value()).getStock());
    }
  }

}
