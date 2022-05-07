package woo.app.main;

import pt.tecnico.po.ui.Command;                                                                                                              import pt.tecnico.po.ui.DialogException;                                                                                                      import pt.tecnico.po.ui.Input;                                                                                                                import woo.Storefront;
import java.io.IOException;
import pt.tecnico.po.ui.DialogException;                                                                                               
import woo.Storefront;
import pt.tecnico.po.ui.Input;

/**
 * Save current state to file under current name (if unnamed, query for name).
 */
public class DoSave extends Command<Storefront> {

  private Input<String> fileName;

  /** @param receiver */
  public DoSave(Storefront receiver){
    super(Label.SAVE, receiver);
    if (_receiver.getFileName() == null)
    	fileName = _form.addStringInput(Message.newSaveAs());
  }

  /** @see pt.tecnico.po.ui.Command#execute() */
  @Override
  public final void execute (){
	if (_receiver.getSave()){
        try {
            if(_receiver.getFileName() == null){
                _form.parse();
                _receiver.setFileName(fileName.value());
                _receiver.save(fileName.value());
            }
            _receiver.save(_receiver.getFileName());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }	
  }
}
