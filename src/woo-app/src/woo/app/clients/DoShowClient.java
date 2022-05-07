package woo.app.clients;

import woo.app.exceptions.DuplicateClientKeyException;
import woo.app.exceptions.UnknownClientKeyException;
import woo.exceptions.NonUniqueClientKey;
import woo.Storefront;
import pt.tecnico.po.ui.DialogException;

import java.util.ArrayList;

import pt.tecnico.po.ui.Command;     
import pt.tecnico.po.ui.Input;                                                                                                    import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;                                                                                                                        //FIXME import other classes
import woo.Client;
import woo.Notification;

/**
 * Show client.
 */
public class DoShowClient extends Command<Storefront> {
  /** Input field. */
  private Input<String> key;

  public DoShowClient(Storefront storefront) {
    super(Label.SHOW_CLIENT, storefront);
    key = _form.addStringInput(Message.requestClientKey());
  }

  @Override
  public void execute() throws DialogException, UnknownClientKeyException{
    _form.parse();
    try {
       if (_receiver.In_clients(key.value()))
       {
         _display.popup(_receiver.showClientNotifications(key.value()));
        _receiver.getClient(key.value()).setNotifications(new ArrayList<Notification>());
       }
       else
       {
         _receiver.throwexceptionclient(key.value());
       }
    } catch (NonUniqueClientKey e){
      throw new UnknownClientKeyException(key.value());
    }
  }
}