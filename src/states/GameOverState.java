package states;

import com.jme3.app.Application;
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
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.PopupBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.controls.window.builder.WindowBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import game.MainApp;

public class GameOverState extends AbstractAppState implements ScreenController {

    protected Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");
    protected BitmapText menuText;
    protected BitmapFont menuFont;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private MainApp game = null; // variable del tipo MainApp, que gestiona el manejo de los estados
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private NiftyJmeDisplay niftyDisplay;
    private FlyByCamera flyCam;
    private Nifty nifty;
    public static boolean b = false;

    public GameOverState(MainApp game) {
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
            if (name.equals("Exit")) {
                System.exit(0);
            }
        }
    }
    /*
     * 
     */

    public void loadFPSText() {
        menuFont = game.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        menuText = new BitmapText(menuFont, false);
        menuText.setSize(menuFont.getCharSet().getRenderedSize());
        menuText.setLocalTranslation(0, (game.getContext().getSettings().getHeight() / 2f) - (menuText.getLineHeight() / 2f), 0);
        menuText.setText("Frames per second");
        guiNode.attachChild(menuText);
    }
    
    /**
     * Método inicial que se le llama inicialmente, y en el que se cargan el estado y sus elementos
     * @param stateManager variable AppStateManager
     * @param app variable de tipo Applicacion
     */
    @Override
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
        game.setInput(true);

        // enable depth test and back-face culling for performance
        game.getRenderer().applyRenderState(RenderState.DEFAULT);


        // Init input
        if (game.getInputManager() != null) {
            game.getInputManager().addMapping("Exit", new KeyTrigger(KeyInput.KEY_ESCAPE));
        }


        niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);


        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        inputManager.setCursorVisible(true);


        // a partir de aqui se crean todos los elementos que contendrá este estado
        nifty.addScreen("GameOverScreen", new ScreenBuilder("GameOverScreen") { // comenzamos la creacion de la pantalla y le damos un nombre: GameOverScreen
            {
                controller(new MenuState(game)); // Esto conecta este estado con el ScreenController correcpondiente, que es donde se encntraran los metodos a los que los botones de esta clase llama

                layer(new LayerBuilder("LayerGameOver") { // layer donde se cargarán los elementos
                    {
                        childLayoutVertical(); // organizacion vertical de los elementos

                        panel(new PanelBuilder() { // panel
                            {
                                childLayoutVertical(); // Organización de los submétodos en forma vertical
                                alignCenter(); // alineacion central
                                valignBottom(); // preferencia por colocar los elementos al fondo
                                height("15%"); // porcentaje de altura del panel
                                width("90%"); // porcentaje de anchura del panel

                                // add text

                                panel(new PanelBuilder() { // subpanel
                                    {
                                        childLayoutCenter();
                                        alignCenter();
                                        valignBottom();
                                        height("50%");
                                        width("90%");

                                    }
                                });

                                text(new TextBuilder() { // texto inicial
                                    {
                                        text("GAME OVER");
                                        font("Interface/Fonts/Jokerman31.fnt");
                                        height("50%");
                                        width("100%");
                                    }
                                });

                            }
                        }); // </panel_1>

                        panel(new PanelBuilder() {
                            {
                                childLayoutCenter(); // Organización de los submétodos en forma central
                                alignCenter();
                                valignBottom();
                                height("10%");// porcentaje de altura del panel
                                width("90%"); // porcentaje de anchura del panel

                                text(new TextBuilder() {
                                    {
                                        text("SCORE: " + game.getScore()); // añade el texto
                                        font("Interface/Fonts/Jokerman.fnt"); // define la fuente a usar
                                        height("100%");
                                        width("10%");
                                    }
                                });

                            }
                        }); // </panel_2>


                        panel(new PanelBuilder() { // panel_3
                            {
                                childLayoutVertical(); // Organización de los submétodos en forma central
                                alignCenter();
                                valignCenter();
                                height("10%");
                                width("100%");
                            }
                        }); // </panel_3>

                        panel(new PanelBuilder("Panel_Buttons") { // panel donde se insertarán los botones
                            {
                                childLayoutVertical(); // Organización de los submétodos en forma central       
                                alignCenter();
                                valignCenter();
                                height("60%");
                                width("100%");

                                control(new ButtonBuilder("PLAY AGAIN", "PLAY AGAIN") { // boton PLAY
                                    {
                                        alignCenter();
                                        valignTop();
                                        backgroundColor("#f108");
                                        height("15%");
                                        width("30%");
                                        visibleToMouse(true);
                                        interactOnRelease("loadGameFromGameOver()");

                                    }
                                });
                                panel(new PanelBuilder("Panel_Buttons") { // panel vacio a modo de separacion
                                    {
                                        childLayoutVertical();             
                                        alignCenter();
                                        valignCenter();
                                        height("20%");
                                        width("100%");
                                    }
                                }); // </panel_4.1>


                                control(new ButtonBuilder("BACK TO MENU", "BACK TO MENU") { // boton BACK TO MENU
                                    {
                                        alignCenter();
                                        valignCenter();
                                        backgroundColor("#f108");
                                        height("15%");
                                        width("30%");
                                        visibleToMouse(true);
                                        interactOnRelease("loadMenuFromGaveOver()");

                                    }
                                });

                                panel(new PanelBuilder("Panel_Buttons") { // panel vacio a modo de separacion
                                    {
                                        childLayoutVertical(); // panel properties, add more...               
                                        alignCenter();
                                        valignCenter();
                                        height("20%");
                                        width("100%");
                                    }
                                }); // </panel_4.2>

                                control(new ButtonBuilder("EXIT", "EXIT") { //botón EXIT
                                    {
                                        alignCenter();
                                        valignBottom();
                                        backgroundColor("#f108");
                                        height("15%");
                                        width("30%");
                                        visibleToMouse(true);
                                        interactOnRelease("exit()");
                                    }
                                });

                            }
                        }); // </panel_4>

                    }
                }); // </layer>
            }
        }.build(nifty)); //construye todo lo que se encuentra dentro de estos corchetes

        game.getGUIViewPort().addProcessor(niftyDisplay);
        nifty.gotoScreen("GameOverScreen"); // se usa para ir a la pantalla correspondiente que ponemos como parametro
    }

    public void update(float tpf) {
        super.update(tpf);

        // simple update and root node

        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();

    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        game.getInputManager().addListener(new AppActionListener(), "Exit");
        game.getViewPort().attachScene(rootNode);
        game.getGUIViewPort().attachScene(guiNode);
        if (b == true) {
            game.getGUIViewPort().addProcessor(niftyDisplay);
        }
//      
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        game.getViewPort().detachScene(rootNode);
        game.getGUIViewPort().detachScene(guiNode);
        game.getGUIViewPort().removeProcessor(niftyDisplay);
    }

    public void render(RenderManager rm) {
    }
}
