package woo.app.clients;

import pt.tecnico.po.ui.Command;                                     
import pt.tecnico.po.ui.DialogException;                                        
import pt.tecnico.po.ui.Input;                                                        
import woo.Storefront;
import woo.app.exceptions.DuplicateClientKeyException;
import woo.Product;

/**
 * Register client.
 */
public class DoRegisterClient extends Command<Storefront> {

  /** Input field. */
  private Input<String> _key;
  /** Input field. */
  private Input<String> _name;
  /** Input field. */
  private Input<String> _address;

  public DoRegisterClient(Storefront storefront) {
    super(Label.REGISTER_CLIENT, storefront);
    _key = _form.addStringInput(Message.requestClientKey());
    _name = _form.addStringInput(Message.requestClientName());
    _address = _form.addStringInput(Message.requestClientAddress());
  }

  @Override
  public void execute() throws DialogException, DuplicateClientKeyException{
    _form.parse();
    try {
      if (!_receiver.In_clients(_key.value().toLowerCase())){
        _receiver.create_client(_key.value().toLowerCase(),_name.value(),_address.value());
        //_receiver.getClient(_key.value()).registnotify(new Product());
      }
      else
        throw new DuplicateClientKeyException(_key.value());
    } catch (DuplicateClientKeyException e)
    {
      _display.popup(e.getMessage());
    }
  }
}
