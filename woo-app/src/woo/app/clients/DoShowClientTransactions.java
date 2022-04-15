package woo.app.clients;

import pt.tecnico.po.ui.Command; 
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.NonUniqueClientKey;
                                                                                                             
/**
 * Show all transactions for a specific client.
 */
public class DoShowClientTransactions extends Command<Storefront> {

  Input<String> client_key;

  public DoShowClientTransactions(Storefront storefront) {
    super(Label.SHOW_CLIENT_TRANSACTIONS, storefront);
    client_key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException,UnknownClientKeyException {
    _form.parse();
    try {
      if (_receiver.In_clients(client_key.value()))
      {
        _display.popup(_receiver.showClientSales(client_key.value()));
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
