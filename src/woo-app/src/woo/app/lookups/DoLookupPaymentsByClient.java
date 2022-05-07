package woo.app.lookups;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;          
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.BadEntryException;
import woo.exceptions.NonUniqueClientKey;

/**
 * Lookup payments by given client.
 */
public class DoLookupPaymentsByClient extends Command<Storefront> {

  Input<String> client_key;

  public DoLookupPaymentsByClient(Storefront storefront) {
    super(Label.PAID_BY_CLIENT, storefront);
    client_key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException,UnknownClientKeyException {
    _form.parse();
    try {
      if (_receiver.In_clients(client_key.value()))
      {
        _display.popup(_receiver.showPaidSales(client_key.value()));
      }
      else
      {
        _receiver.throwexceptionclient(client_key.value()); 
      }
   } catch (NonUniqueClientKey e){
    throw new UnknownClientKeyException(client_key.value());
   }
  }
}
