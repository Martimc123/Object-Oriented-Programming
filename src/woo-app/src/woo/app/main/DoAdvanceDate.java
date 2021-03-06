package woo.app.main;

import pt.tecnico.po.ui.Command;
import pt.tecnico.po.ui.DialogException;
import pt.tecnico.po.ui.Input;
import woo.Storefront;
import pt.tecnico.po.ui.Menu;
import woo.app.exceptions.*;
import woo.exceptions.BadDateException;
import woo.app.exceptions.InvalidDateException;

/**
 * Advance current date.
 */
public class DoAdvanceDate extends Command<Storefront> {
  Input<Integer> _days;

  public DoAdvanceDate(Storefront receiver) {
    super(Label.ADVANCE_DATE, receiver);
    _days = _form.addIntegerInput(Message.requestDaysToAdvance());
  }

  @Override
  public final void execute() throws DialogException, InvalidDateException{
    _form.parse();
    int dias = _days.value();
    try{
      if(dias>0){
        //_receiver.check_sales();
        _receiver.advanceDate_front(dias);
      }
      else
        throw new InvalidDateException(dias);
   }
   catch (InvalidDateException e) {
     _display.popup(e.getMessage());
   }
  }
}