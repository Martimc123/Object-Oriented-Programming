package woo.app.suppliers;

import pt.tecnico.po.ui.Command;    
import pt.tecnico.po.ui.DialogException;             
import pt.tecnico.po.ui.Input;                    
import woo.Storefront;            
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.exceptions.BadEntryException;

/**
 * Show all transactions for specific supplier.
 */
public class DoShowSupplierTransactions extends Command<Storefront> {

  Input<String> _id;

  public DoShowSupplierTransactions(Storefront receiver) {
    super(Label.SHOW_SUPPLIER_TRANSACTIONS, receiver);
    _id = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public void execute() throws DialogException,UnknownSupplierKeyException {
    _form.parse();
    try{
      if (!_receiver.In_supp(_id.value()))
      {
        _receiver.throwentryexception(_id.value(),new UnknownSupplierKeyException(_id.value()));
      }
      else
      {
        _display.popup(_receiver.showOrdersSpec(_receiver.getSupplier(_id.value()).getTransactions()));
      }
    }
    catch (BadEntryException e)
    {
      throw new UnknownSupplierKeyException(_id.value());
    }
  }

}
