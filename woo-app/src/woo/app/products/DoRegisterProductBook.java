package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownSupplierKeyException;
import java.io.*;
import java.util.*;
import woo.Supplier;
import woo.exceptions.BadEntryException;

/**
 * Register book.
 */
public class DoRegisterProductBook extends Command<Storefront> {

  Input<String> _id;
  Input<String> _title;
  Input<String> _author;
  Input<String> _isbn;
  Input<Integer> _price;
  Input<Integer> _critic_value;
  Input<String> _sup_id;

  public DoRegisterProductBook(Storefront receiver) {
    super(Label.REGISTER_BOOK, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _title = _form.addStringInput(Message.requestBookTitle());
    _author = _form.addStringInput(Message.requestBookAuthor());
    _isbn = _form.addStringInput(Message.requestISBN());
    _price = _form.addIntegerInput(Message.requestPrice());
    _critic_value = _form.addIntegerInput(Message.requestStockCriticalValue());
    _sup_id = _form.addStringInput(Message.requestSupplierKey());
  }

  @Override
  public final void execute() throws DialogException,UnknownSupplierKeyException {
    _form.parse();
    try {
      if (!_receiver.In_supp(_sup_id.value()))
      _receiver.throwentryexception(_sup_id.value(),new UnknownSupplierKeyException(_sup_id.value()));
      else
      {
        _receiver.create_Book(_id.value(),_sup_id.value(),_price.value(),_critic_value.value(),0,_title.value(),_author.value(),
        _isbn.value());
        _receiver.create_BookSpec(_id.value(),_sup_id.value(),_price.value(),_critic_value.value(),0,_title.value(),_author.value(),
        _isbn.value(),_receiver.getSupplier(_sup_id.value()).getProducts());
        _receiver.registersInteresse( _receiver.getProduct(_id.value()) );
      }
    }
    catch (BadEntryException e)
    {
      throw new UnknownSupplierKeyException(_sup_id.value());
    }
  }
}
