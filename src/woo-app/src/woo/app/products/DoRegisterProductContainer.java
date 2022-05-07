package woo.app.products;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;                    
import woo.Storefront; 
import woo.app.exceptions.UnknownSupplierKeyException;
import woo.app.exceptions.UnknownServiceTypeException;
import woo.app.exceptions.UnknownServiceLevelException;
import java.util.Arrays;
import java.util.List;
import java.io.*;

/**
 * Register container.
 */
public class DoRegisterProductContainer extends Command<Storefront> {

  Input<String> _id;
  Input<String> _sup_id;
  Input<Integer> _price;
  Input<Integer> _critic_value;
  Input<String> _service;
  Input<String> _quality;


  public DoRegisterProductContainer(Storefront receiver) {
    super(Label.REGISTER_CONTAINER, receiver);
    _id = _form.addStringInput(Message.requestProductKey());
    _price = _form.addIntegerInput(Message.requestPrice());
    _critic_value = _form.addIntegerInput(Message.requestStockCriticalValue());
    _sup_id = _form.addStringInput(Message.requestSupplierKey());
    _service = _form.addStringInput(Message.requestServiceType());
    _quality = _form.addStringInput(Message.requestServiceLevel());
  }

  @Override
  public final void execute() throws DialogException,UnknownSupplierKeyException,UnknownServiceTypeException,UnknownServiceLevelException {
    _form.parse();
    String[] type_service = new String[]{"NORMAL","AIR","EXPRESS","PERSONAL"};
    String[] type_quality = new String[]{"B4","C4","C5","DL"};
    List<String> lst_s = Arrays.asList(type_service);
    List<String> lst_q = Arrays.asList(type_quality);
    try {
      if (!_receiver.In_supp(_sup_id.value().toLowerCase()))
        throw new UnknownSupplierKeyException(_sup_id.value());
      else if (!lst_s.contains(_service.value()))
        throw new UnknownServiceTypeException(_service.value());
      else if (!lst_q.contains(_quality.value()))
        throw new UnknownServiceLevelException(_quality.value());
      else
      {
        _receiver.create_Container(_id.value().toLowerCase(),_sup_id.value().toLowerCase(),_price.value(),_critic_value.value(),0,_service.value(),_quality.value());
        _receiver.create_ContainerSpec(_id.value().toLowerCase(),_sup_id.value().toLowerCase(),_price.value(),_critic_value.value(),0,_service.value(),_quality.value(),
        _receiver.getSupplier(_sup_id.value()).getProducts());
        _receiver.registersInteresse(_receiver.getProduct(_id.value()));
      }
    }
    catch (UnknownSupplierKeyException e)
    {
      _display.popup(e.getMessage());
    }
    catch (UnknownServiceTypeException e)
    {
      _display.popup(e.getMessage());
    }
    catch (UnknownServiceLevelException e)
    {
      _display.popup(e.getMessage());
    }
  }
}
