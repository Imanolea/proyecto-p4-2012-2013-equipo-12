package GUI;

import GUI.MainScreen;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * @author iamcreasy
 */
public class PowdersScreenController implements ScreenController {

    public void onStartScreen() {
    }

    public void onEndScreen() {
    }

    public void bind(Nifty nifty, Screen screen) {
        
    }

    public void startPowders() {
    }
    
    public void showStatistics(){
        System.out.println("CLICKoo");
    }
    
    public void exit(){
        System.exit(0);
    }
    
    public static void changeLanguage(MainScreen m, int nLanguage){
        System.out.println("CLICK");
        m.setI(nLanguage);
    }
    
    public int getInt1()
    {
        return 1;
    }
    
    public void init(){
    }
}
