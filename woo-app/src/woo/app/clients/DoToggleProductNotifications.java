package woo.app.clients;

import pt.tecnico.po.ui.Command;  
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownClientKeyException;
import woo.app.exceptions.UnknownProductKeyException;
import woo.exceptions.BadEntryException;
import woo.exceptions.NonUniqueClientKey;
/**
 * Toggle product-related notifications.
 */
public class DoToggleProductNotifications extends Command<Storefront> {

  Input<String> _client_id;
  Input<String> _product_id;

  public DoToggleProductNotifications(Storefront storefront) {
    super(Label.TOGGLE_PRODUCT_NOTIFICATIONS, storefront);
    _client_id = _form.addStringInput(Message.requestClientKey());
    _product_id = _form.addStringInput(Message.requestProductKey());
  }

  @Override
  public void execute() throws DialogException,UnknownClientKeyException,UnknownProductKeyException {
   _form.parse();
    try {
       if (!_receiver.In_clients(_client_id.value()))
       {
        _receiver.throwexceptionclient(_client_id.value());
       }
       else if (!_receiver.In_products(_product_id.value()))
       {
         _receiver.throwentryexception(_product_id.value(),new UnknownProductKeyException(_product_id.value()));
       }
       else
       {
         if (_receiver.getProduct(_product_id.value()).getObservers().contains(_receiver.getClient(_client_id.value())))
         {
          _receiver.getProduct(_product_id.value()).removeObserver( _receiver.getClient(_client_id.value()) );
          _display.popup(Message.notificationsOff(_client_id.value(),_product_id.value()));
         }
         else if (!_receiver.getProduct(_product_id.value()).getObservers().contains(_receiver.getClient(_client_id.value())))
         {
          _receiver.getProduct(_product_id.value()).registerObserver( _receiver.getClient(_client_id.value()) );
          _display.popup(Message.notificationsOn(_client_id.value(),_product_id.value()));
         }
       }
    } catch (NonUniqueClientKey e){
      throw new UnknownClientKeyException(_client_id.value());
    }
    catch (BadEntryException e)
    {
      throw new UnknownProductKeyException(_product_id.value());
    }
  }

}
