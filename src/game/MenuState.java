/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioRenderer;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.RenderState;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import database.GestorEstadisticasLocal;
import database.Jugador;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.tools.Color;
import java.io.FileWriter;
import java.io.PrintWriter;

public class MenuState extends AbstractAppState implements ScreenController {
    
    protected Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");
    protected BitmapText menuText;
    protected BitmapFont menuFont;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private SimpleApplication app;
    public MainApp game = null;
    private AppActionListener actionListener = new AppActionListener();
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private NiftyJmeDisplay niftyDisplay;
    private FlyByCamera flyCam;
    private Nifty nifty;
    private String titulos[][] = {{"Comienzo", "Start"}, {"Estadísticas", "Statistics"},
        {"Registrarse", "Sign Up"}, {"Cambiar de Jugador", "Change Player"}, {"Salir", "Quit"}};
    private int i = 1;
    private boolean primeraVez1 = true;
    private boolean primeraVez2 = true;
    private boolean primeraVez3 = true;
    private String nameJugador;
    
    public MenuState(MainApp game) {
        this.game = game;
    }
    
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
    }
    
    public void onStartScreen() {
    }
    
    public void onEndScreen() {
    }
    
    private class AppActionListener implements ActionListener {
        
        public void onAction(String name, boolean value, float tpf) {
            if (!value) {
                return;
            }
            
        }
    }
    
    public void loadFPSText() {
        menuFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        menuText = new BitmapText(menuFont, false);
        menuText.setSize(menuFont.getCharSet().getRenderedSize());
        menuText.setLocalTranslation(0, (game.getContext().getSettings().getHeight() / 2f) - (menuText.getLineHeight() / 2f), 0);
        menuText.setText("Frames per second");
        guiNode.attachChild(menuText);
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.game = (MainApp) game; // can cast Application to something more specific

        this.assetManager = this.game.getAssetManager();
        this.stateManager = this.game.getStateManager();
        this.inputManager = this.game.getInputManager();
        this.viewPort = this.game.getViewPort();
        this.audioRenderer = this.game.getAudioRenderer();
        this.guiViewPort = this.game.getGuiViewPort();
        this.flyCam = new FlyByCamera(game.getCamera());
        nameJugador = "";
        // enable depth test and back-face culling for performance
        app.getRenderer().applyRenderState(RenderState.DEFAULT);


        // Init input
        if (game.getInputManager() != null) {
            game.getInputManager().addMapping("SIMPLEAPP_Exit1", new KeyTrigger(KeyInput.KEY_0));
        }
        
        
        if (niftyDisplay == null) {
            niftyDisplay = new NiftyJmeDisplay(
                    assetManager, inputManager, audioRenderer, guiViewPort);
            
        }
        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        
        inputManager.setCursorVisible(true);



        // <screen>
        nifty.addScreen("MenuScreen", new ScreenBuilder("Menu") {
            {
                controller(new MenuState(game)); // This connects the Java class StartingScreen and the GUI screen.     

                // <layer>
                layer(new LayerBuilder("Layer_ID") {
                    {
                        childLayoutVertical(); // layer properties, add more...

                        // <panel_1>
                        panel(new PanelBuilder("Panel_TITLE") {
                            {
                                childLayoutCenter();
                                alignRight();
                                valignBottom();
                                height("10%");
                                width("95%");

                                // "Hi, user" text.

                                /*text(new TextBuilder() {
                                    {
                                        alignRight();
                                        text("Hi, " + nameJugador);
                                        font("Interface/Fonts/Default.fnt");
                                        height("100%");
                                        width("27%");
                                    }
                                });*/
                            }
                        }); // </panel_1>

                        // <panel_1>
                        panel(new PanelBuilder("Panel_TITLE") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("5%");
                                width("90%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        text("POWDERS");
                                        font("Interface/Fonts/Default.fnt");
                                        height("100%");
                                        width("100%");
                                    }
                                });
                            }
                        }); // </panel_1>

                        // <panel_2>
                        panel(new PanelBuilder("Panel_BUTTONS") {
                            {
                                childLayoutVertical(); // panel properties, add more...               
                                alignCenter();
                                height("70%");
                                width("50%");
                                
                                panel(new PanelBuilder("Panel_EMPTY") {
                                    {
                                        childLayoutCenter();
                                        height("16%");
                                        width("55%");
                                        
                                    }
                                });
                                
                                panel(new PanelBuilder("Panel_START") {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        childLayoutCenter();
                                        height("16%");
                                        width("55%");

                                        // GUI element
                                        control(new ButtonBuilder("Button_START", titulos[0][i]) {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#f108");
                                                height("50%");
                                                width("80%");
                                                visibleToMouse(true);
                                                interactOnClick("startGame()");
                                            }
                                        });
                                    }
                                });// </panel_2.1>

                                // <panel_2.2>
                                panel(new PanelBuilder("Panel_STATISTICS") {
                                    {
                                        childLayoutCenter();
                                        alignCenter();
                                        valignCenter();
                                        height("16%");
                                        width("55%");

                                        // GUI element
                                        control(new ButtonBuilder("Button_STATISTICS", titulos[1][i]) {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#f108");
                                                height("50%");
                                                width("80%");
                                                visibleToMouse(true);
                                                interactOnClick("startStatistics()");
                                            }
                                        });
                                    }
                                });// </panel_2.2>

                                // <panel_2.3>
                                panel(new PanelBuilder("Panel_SIGN_UP") {
                                    {
                                        childLayoutCenter();
                                        alignCenter();
                                        valignCenter();
                                        height("16%");
                                        width("55%");

                                        // GUI element
                                        control(new ButtonBuilder("Button_SIGN_UP", titulos[2][i]) {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#f108");
                                                height("50%");
                                                width("80%");
                                                visibleToMouse(true);
                                                interactOnClick("loadInputFromMenu()");
                                            }
                                        });
                                    }
                                });// </panel_2.3>

                                // <panel_2.4>
                                panel(new PanelBuilder("Panel_CHANGEPLAYER") {
                                    {
                                        childLayoutCenter();
                                        alignCenter();
                                        valignCenter();
                                        height("16%");
                                        width("55%");

                                        // GUI element
                                        control(new ButtonBuilder("Button_CHANGEPLAYER", titulos[3][i]) {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#f108");
                                                height("50%");
                                                width("80%");
                                                visibleToMouse(true);
                                                interactOnClick("loadLogIn2FromMenu()");
                                                
                                            }
                                        });
                                    }
                                });// </panel_2.4>

                                panel(new PanelBuilder("Panel_EXIT") {
                                    {
                                        childLayoutCenter();
                                        height("16%");
                                        width("55%");
                                        alignCenter();
                                        valignCenter();

                                        // GUI element
                                        control(new ButtonBuilder("Button_QUIT", titulos[4][i]) {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#f108");
                                                height("50%");
                                                width("80%");
                                                visibleToMouse(true);
                                                interactOnClick("exit()");
                                                
                                            }
                                        });
                                    }
                                });
                                
                                
                            }
                        }); // </panel_2>

                        // <panel_3>
                        panel(new PanelBuilder("Panel_LANGUAGES") {
                            {
                                childLayoutHorizontal();
                                alignRight();
                                valignTop();
                                height("12%");
                                width("100%");
                                
                                panel(new PanelBuilder("Panel_LANGUAGES") {
                                    {
                                        childLayoutOverlay();
                                        alignRight();
                                        valignCenter();
                                        height("12%");
                                        width("80%");
                                    }
                                });
                                
                                
                                image(new ImageBuilder() {
                                    {
                                        this.filename("/Pictures/Titulo.png");
                                        valignCenter();
                                        alignRight();
                                        height("75%");
                                        width("16%");
                                        
                                    }
                                });
                                
                                
                            }
                        });// </panel_3>
                    }
                });
                // </layer>
            }
        }.build(nifty));
        // </screen>

        game.getGUIViewPort().addProcessor(niftyDisplay);
        nifty.gotoScreen("MenuScreen"); // it is used to start the screen
//loadMenu();
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf);



        // simple update and root node

        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();
        
    }
    
    public void stateAttached(AppStateManager stateManager) {
        //  game.getInputManager().addListener(new AppActionListener(), "SIMPLEAPP_Exit1");
        super.stateAttached(stateManager);
        game.getViewPort().attachScene(rootNode);
        game.getGUIViewPort().attachScene(guiNode);
        
        
    }
    
    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        game.getViewPort().detachScene(rootNode);
        game.getGUIViewPort().detachScene(guiNode);
        //game.getGUIViewPort().removeProcessor(niftyDisplay);
    }
    
    public void render(RenderManager rm) {
    }
    
    public void startGame() {

        //nifty.exit();
        game.loadGame();
        nifty.removeScreen("MenuScreen");
        
    }
    
    public void quitGame() {
        game.stop();
    }
    
    public void loadMenu() {
        game.loadMenu();
        
    }
    
    public void startStatistics() {
        
        nifty.removeScreen("MenuScreen");
        game.loadStatistics();
        
        
    }
    
    public void quitStatistics() {
        game.stop();
    }
    
    public void loadStatistics() {
        game.loadInput();
    }
    
    public void loadInputFromMenu() {

        // nifty.exit();
        nifty.removeScreen("MenuScreen");
        game.loadInput();
    }
    
    public void loadInputFromLogIn() {

        // nifty.exit();
        nifty.removeScreen("LogInScreen");
        game.loadInput();
    }
    
    public void exit() {
        System.exit(0);
    }
    
    public void loadMenuFromInput() {
        
        nifty.removeScreen("InputScreen");
        
        game.loadMenuFromInput();
        niftyDisplay.cleanup();
    }
    
    public void loadMenuFromStatistics() {
        nifty.removeScreen("StatisticsScreen");
        game.loadMenuFromStatistics();
    }
    
    public void loadMenuFromLogIn() {
        nifty.removeScreen("LogInScreen");
        game.loadMenuFromLogIn();
    }
    
    public void loadLogInFromMenu() {
        nifty.removeScreen("MenuScreen");
        game.loadLogInFromMenu();
    }
    
    public void loadMenuFromLogIn2() {
        nifty.removeScreen("LogInScreen2");
        game.loadMenuFromLogIn2();
    }
    
    public void loadLogIn2FromMenu() {
        nifty.removeScreen("MenuScreen");
        game.loadLogIn2FromMenu();
    }
    
    public void leerTextFields() {
        String name = nifty.getScreen("InputScreen").findNiftyControl("NameInput", TextField.class).getText();
        System.out.print("Name : " + name);
        
        String nick = nifty.getScreen("InputScreen").findNiftyControl("NickInput", TextField.class).getText();
        System.out.print("Nick : " + nick);
    }
    
    public void borrarTextoName() {
        if (primeraVez1) {
            nifty.getScreen("InputScreen").findNiftyControl("NameInput", TextField.class).setText("");
            primeraVez1 = false;
        }
    }
    
    public void borrarTextoNick() {
        if (primeraVez2) {
            nifty.getScreen("InputScreen").findNiftyControl("NickInput", TextField.class).setText("");
            primeraVez2 = false;
        }
    }
    
    public void borrarTextoPass() {
        if (primeraVez3) {
            nifty.getScreen("InputScreen").findNiftyControl("PassInput", TextField.class).setText("");
            primeraVez3 = false;
        }
    }
    
    public void insertarUsuario() {
        
        String name = nifty.getScreen("InputScreen").findNiftyControl("NameInput", TextField.class).getText();
        
        String nick = nifty.getScreen("InputScreen").findNiftyControl("NickInput", TextField.class).getText();
        
        String pass = nifty.getScreen("InputScreen").findNiftyControl("PassInput", TextField.class).getText();
        
        
        while (name.substring(0, 1).equals(" ")) {
            name = name.substring(1);
        }
        
        while (nick.substring(0, 1).equals(" ")) {
            nick = nick.substring(1);
        }
        
        Jugador j = new Jugador(nick, pass, name);
        j.printJugador(j);
        
        database.GestorEstadisticasLocal.agregarPerfilStatic(j);
        
        loadMenuFromInput();
    }
    
    public void cargarUsuario() {
        
        String nick = nifty.getScreen("LogInScreen").findNiftyControl("NickLogIn", TextField.class).getText();
        
        String password = nifty.getScreen("LogInScreen").findNiftyControl("PassLogIn", TextField.class).getText();
        
        nameJugador = database.GestorEstadisticasLocal.comprobarJugadorStatic(nick, password);
        
        if (!nameJugador.equals("")) {
            loadMenuFromLogIn();
        }
    }
    
    public void cargarUsuario2() {
        
        String nick = nifty.getScreen("LogInScreen2").findNiftyControl("NickLogIn", TextField.class).getText();
        
        String password = nifty.getScreen("LogInScreen2").findNiftyControl("PassLogIn", TextField.class).getText();
        
        nameJugador = database.GestorEstadisticasLocal.comprobarJugadorStatic(nick, password);
        
        if (!nameJugador.equals("")) {
            loadMenuFromLogIn2();
        }
    }
}
