package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.Transaction;
import woo.Order;
import woo.Sale;


/**
 * Show specific transaction.
 */
public class DoShowTransaction extends Command<Storefront> {

  Input<Integer> _id;

  public DoShowTransaction(Storefront receiver) {
    super(Label.SHOW_TRANSACTION, receiver);
    _id = _form.addIntegerInput(Message.requestClientKey());
  }

  @Override
  public final void execute() throws DialogException,UnknownTransactionKeyException {
    _form.parse();
    try{
      if (!_receiver.In_Orders(_id.value()) && !_receiver.In_Sales(_id.value()))
      {
        throw new UnknownTransactionKeyException(_id.value());
      }
      else{
        if (_receiver.In_Orders(_id.value()))
        {
          Order enc = _receiver.getOrder(_id.value());
          _display.popup(_receiver.showOrderSpec(enc,enc.getProds()));
        }
        else if (_receiver.In_Sales(_id.value()))
        {
          Sale venda = _receiver.getSale(_id.value());
          _display.popup(_receiver.showSaleSpec(venda));
        }
      }
    }
    catch (UnknownTransactionKeyException e)
    {
      _display.popup(e.getMessage());
    }
  }
}
