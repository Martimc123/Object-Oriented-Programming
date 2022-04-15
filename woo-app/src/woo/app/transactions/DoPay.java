package woo.app.transactions;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import woo.app.exceptions.UnknownTransactionKeyException;
import woo.Client;
import woo.Sale;
import woo.exceptions.BadEntryException;

/**
 * Pay transaction (sale).
 */
public class DoPay extends Command<Storefront> {

  Input<Integer> _key;
  
  public DoPay(Storefront storefront) {
    super(Label.PAY, storefront);
    _key = _form.addIntegerInput(Message.requestTransactionKey());
  }

  @Override
  public final void execute() throws DialogException,UnknownTransactionKeyException {
    _form.parse();
    try {
      if (!_receiver.In_Orders(_key.value()) && !_receiver.In_Sales(_key.value()))
      {
        _receiver.throwentryexception(String.valueOf(_key.value()),new UnknownTransactionKeyException(_key.value()));
      }
      else if (_receiver.In_Orders(_key.value()) && !_receiver.In_Sales(_key.value()))
      {}
      else if (_receiver.In_Sales(_key.value()) && (_receiver.getSale(_key.value()).getStatus() == 1))
      {}
      else
      {
        Sale venda = _receiver.getSale(_key.value());
        //int base_cost = venda.getBaseCost();
        int payment_date = _receiver.getDate();
        venda.setPaymentDate(payment_date);
        Client cliente = _receiver.getClient(venda.getClientKey());

        venda.setPeriod(venda.CalcPeriod(venda.getDeadline(),payment_date,
        _receiver.getProduct(venda.getProductKey()).getNvalue()));
        cliente.applyFine(venda);
        cliente.applyDiscount(venda);
        cliente.setPoints(venda.getFinalCost(),venda.getPeriod());
        
        if (venda.getPeriod() == 1 || venda.getPeriod() == 2)
          cliente.verifyPoints();
        if (venda.getPeriod() == 3 || venda.getPeriod() == 4)
          cliente.verifyDelay(venda.getDeadline(),venda.getPaymentDate());

        venda.setStatusPayed();
        cliente.setPurchasesP(venda.getFinalCost());
        _receiver.setSalesPaid(venda.getFinalCost());
        _receiver.setBalance();
      }
   }
   catch (BadEntryException e)
    {
      throw new UnknownTransactionKeyException(_key.value());
    } 
  }

}
