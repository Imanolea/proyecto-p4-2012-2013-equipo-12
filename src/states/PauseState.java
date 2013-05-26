package states;

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
import database.LocalStatsHandler;
import database.Player;
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
import game.MainApp;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Estado del menú relativo a la pantalla de pause
 * @author Team 12
 */

public class PauseState extends AbstractAppState implements ScreenController {

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
    private String titulos[][] = {{"Reanudar", "Continue"}, {"Estadísticas", "Statistics"},
        {"Registrarse", "Sign Up"}, {"Cambiar de Jugador", "Change Player"}, {"Salir", "Quit"}};
    private int i = 1;
    private boolean primeraVez1 = true;
    private boolean primeraVez2 = true;
    private boolean primeraVez3 = true;
    private String nameJugador;

    public PauseState(MainApp game) {
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

    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.game = (MainApp) game;
        this.assetManager = this.game.getAssetManager();
        this.stateManager = this.game.getStateManager();
        this.inputManager = this.game.getInputManager();
        this.viewPort = this.game.getViewPort();
        this.audioRenderer = this.game.getAudioRenderer();
        this.guiViewPort = this.game.getGuiViewPort();
        this.flyCam = new FlyByCamera(game.getCamera());
        nameJugador = "";
        app.getRenderer().applyRenderState(RenderState.DEFAULT);


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
        nifty.addScreen("MenuScreenGame", new ScreenBuilder("Menu") {
            {
                controller(new PauseState(game));
                layer(new LayerBuilder("Layer_ID") {
                    {
                        
                        backgroundColor(Color.BLACK);                    
                    }
                });
            // <layer>

            layer( 
                
                    new LayerBuilder("Layer_ID") {
                    {
                        childLayoutVertical();

                    // <panel_1>
                    panel(new PanelBuilder("Panel_TITLE") {
                        {
                            childLayoutCenter();
                            alignRight();
                            valignBottom();
                            height("10%");
                            width("95%");

                        }
                    }); // </panel_1>

                    // <panel_1>
                    panel(new PanelBuilder("Panel_TITLE") {
                        {
                            childLayoutCenter();
                            alignCenter();
                            height("5%");
                            width("90%");

                            
                            text(new TextBuilder() {
                                {
                                    text("PAUSE");
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
                            childLayoutVertical();            
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

                                    control(new ButtonBuilder("Button_continue", titulos[0][i]) {
                                        {
                                            alignCenter();
                                            valignCenter();
                                            backgroundColor("#f108");
                                            height("50%");
                                            width("80%");
                                            visibleToMouse(true);
                                            interactOnClick("unpauseGame()");
                                        }
                                    });
                                }
                            });// </panel_2.2>


                            panel(new PanelBuilder("Panel_EXIT") {
                                {
                                    childLayoutCenter();
                                    height("30%");
                                    width("55%");
                                    alignCenter();
                                    valignCenter();

                                    control(new ButtonBuilder("Button_QUIT", titulos[4][i]) {
                                        {
                                            alignCenter();
                                            valignCenter();
                                            backgroundColor("#f108");
                                            height("28%");
                                            width("80%");
                                            visibleToMouse(true);
                                            interactOnClick("exit()");

                                        }
                                    });
                                }
                            });


                        }
                    }); // </panel_2>

                }
            }
        );
        // </layer>
    }

    }.build(nifty)

    );
        // </screen>

        game.getGUIViewPort ()

    .addProcessor(niftyDisplay);
        nifty.gotoScreen (

"MenuScreenGame");
    }
    
    @Override
        public void update(float tpf) {
        super.update(tpf);

        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();
        
    }
    
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        game.getViewPort().attachScene(rootNode);
        game.getGUIViewPort().attachScene(guiNode);
        
        
    }
    

        public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        game.getViewPort().detachScene(rootNode);
        game.getGUIViewPort().detachScene(guiNode);
        
        }
    
    public void render(RenderManager rm) {
        
    }
    
    
    public void exit() {
        System.exit(0);
    }
    
    public void unpauseGame() {
        nifty.removeScreen("MenuScreenGame");
        
        game.loadGameFromMenuGame(); 
    }
}
