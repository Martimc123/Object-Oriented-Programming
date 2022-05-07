package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.Supplier;

/**
 * Enable/disable supplier transactions.
 */
public class DoToggleTransactions extends Command<Storefront> {

  Input<String> _id;

  public DoToggleTransactions(Storefront receiver) {
    super(Label.TOGGLE_TRANSACTIONS, receiver);
     _id = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException,UnknownSupplierKeyException {
      _form.parse();
      String key = _id.value().toLowerCase();
      Supplier sup = _receiver.getSupplier(key);
      try{
          if (!_receiver.In_supp(key))
          {
            throw new UnknownSupplierKeyException(_id.value());
          }
          else if (sup.getStatus().equals(Message.yes()))
          {
            sup.SetStatus(Message.no());
            _display.popup(Message.transactionsOff(_id.value()));
          }
          else if (sup.getStatus().equals(Message.no()))
          {
            sup.SetStatus(Message.yes());
            _display.popup(Message.transactionsOn(_id.value()));
          }
      }
      catch (UnknownSupplierKeyException e)
      {
        _display.popup(e.getMessage());
      }
  }
}
