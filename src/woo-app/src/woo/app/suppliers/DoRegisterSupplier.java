package woo.app.suppliers;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;             
import pt.tecnico.po.ui.Input;                                                      
import woo.Storefront;
import woo.app.exceptions.DuplicateSupplierKeyException;
import woo.exceptions.BadEntryException;

/**
 * Register supplier.
 */
public class DoRegisterSupplier extends Command<Storefront> {

  /** Input field. */
  Input<String> _id;
  /** Input field. */
  Input<String> _name;
  /** Input field. */
  Input<String> _address;

  public DoRegisterSupplier(Storefront receiver) {
    super(Label.REGISTER_SUPPLIER, receiver);
    _id = _form.addStringInput(Message.requestSupplierKey());
    _name = _form.addStringInput(Message.requestSupplierName());
    _address = _form.addStringInput(Message.requestSupplierAddress());
  }

  @Override
  public void execute() throws DialogException,DuplicateSupplierKeyException {
    _form.parse();
    try{
    if (!_receiver.In_supp(_id.value())){
      _receiver.create_supplier(_id.value(),_name.value(),_address.value());
    }
    else
    _receiver.throwentryexception(_id.value(),new DuplicateSupplierKeyException(_id.value()));
  }
  catch (BadEntryException e)
  {
    throw new DuplicateSupplierKeyException(_id.value());
  }
  }
}
