package woo.app.clients;

import pt.tecnico.po.ui.Command;                                     
import pt.tecnico.po.ui.DialogException;                                                                                                
import woo.Storefront;
import woo.Client;                                        

/**
 * Show all clients.
 */
public class DoShowAllClients extends Command<Storefront> {

  public DoShowAllClients(Storefront storefront) {
    super(Label.SHOW_ALL_CLIENTS, storefront);
  }

  @Override
  public void execute() throws DialogException {
   _display.popup(_receiver.showClients());
  }
}
