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
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.radiobutton.builder.RadioButtonBuilder;
import de.lessvoid.nifty.controls.radiobutton.builder.RadioGroupBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import game.MainApp;

/**
 * Estado del menú relativo al registro del jugado desde el menú del juego
 *
 * @author Team 12
 */
public class InputState2 extends AbstractAppState implements ScreenController {

    protected Node rootNode = new Node("Root Node");
    protected Node guiNode = new Node("Gui Node");
    protected BitmapText menuText;
    protected BitmapFont menuFont;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private MainApp game = null;
    private AudioRenderer audioRenderer;
    private ViewPort guiViewPort;
    private NiftyJmeDisplay niftyDisplay;
    private FlyByCamera flyCam;
    private Nifty nifty;
    private String titulos[][] = {{"Comienzo", "Start"},
        {"Estadísticas", "Statistics"}, {"¿?", "?¿"}, {"Salir", "Quit"}};
    private int i = 0;
    public static boolean b = false;

    public InputState2(MainApp game) {
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

    /**
     * Método que inicializa las variables de la aplicación
     *
     * @param stateManager Gestiona los estados del juego
     * @param app Aplicación del juego
     */
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

        // enable depth test and back-face culling for performance
        game.getRenderer().applyRenderState(RenderState.DEFAULT);


        // Init input
        if (game.getInputManager() != null) {
            game.getInputManager().addMapping("SIMPLEAPP_Exit1", new KeyTrigger(KeyInput.KEY_0));
        }



        niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);


        nifty = niftyDisplay.getNifty();
        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        inputManager.setCursorVisible(true);



        nifty.addScreen("InputScreen2", new ScreenBuilder("IScreen2") {
            {
                //controller(new GUI.PowdersScreenController()); // This connects the Java class StartingScreen and the GUI screen.     
                controller(new MenuState(game));


                layer(new LayerBuilder("Layer_ID2") {
                    {
                        childLayoutVertical(); // layer properties, add more...

                        // <panel_1>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("35%");
                                width("50%");
                            }
                        }); // </panel_1>

                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("10%");
                                width("50%");

                                text(new TextBuilder() {
                                    {
                                        text("SIGN UP");
                                        font("Interface/Fonts/Jokerman23.fnt");
                                        height("100%");
                                        width("100%");
                                    }
                                });

                            }
                        }); // </panel_1>

                        // <panel_2>
                        panel(new PanelBuilder("Panel_BUTTONS2") {
                            {
                                childLayoutVertical(); // panel properties, add more...               
                                alignCenter();
                                height("60%");
                                width("50%");

                                panel(new PanelBuilder("Panel_EMPTY2") {
                                    {
                                        childLayoutHorizontal();
                                        height("10%");
                                        width("40%");
                                        alignCenter();
                                        valignCenter();

                                        text(new TextBuilder() {
                                            {
                                                text("Name: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("50%");
                                                width("50%");
                                            }
                                        });

                                        panel(new PanelBuilder("Panel_TITLE2") {
                                            {
                                                childLayoutCenter();
                                                alignCenter();
                                                height("1%");
                                                width("10%");
                                            }
                                        }); // </panel_1>

                                        control(new TextFieldBuilder("NameInput", "") {
                                            {
                                                //interactOnMouseOver("borrarTextoName()");
                                                alignLeft();
                                                width("65%");
                                            }
                                        });

                                    }
                                });// </panel_2.1>

                                // <panel_2.2>
                                panel(new PanelBuilder("Panel_STATISTICS2") {
                                    {
                                        childLayoutHorizontal();
                                        height("10%");
                                        width("40%");
                                        alignCenter();
                                        valignCenter();

                                        text(new TextBuilder() {
                                            {
                                                text("Nick: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("50%");
                                                width("50%");
                                            }
                                        });

                                        panel(new PanelBuilder("Panel_TITLE2") {
                                            {
                                                childLayoutCenter();
                                                alignCenter();
                                                height("1%");
                                                width("10%");
                                            }
                                        }); // </panel_1>

                                        control(new TextFieldBuilder("NickInput", "") {
                                            {
                                                //interactOnMouseOver("borrarTextoNick()");
                                                width("65%");
                                            }
                                        });

                                    }
                                });// </panel_2.2>

                                // <panel_2.3>
                                panel(new PanelBuilder("Panel_STATISTICS3") {
                                    {
                                        childLayoutHorizontal();
                                        height("10%");
                                        width("40%");
                                        alignCenter();
                                        valignCenter();

                                        text(new TextBuilder() {
                                            {
                                                text("Password:");
                                                font("Interface/Fonts/Default.fnt");
                                                height("50%");
                                                width("50%");
                                            }
                                        });

                                        panel(new PanelBuilder("Panel_TITLE2") {
                                            {
                                                childLayoutCenter();
                                                alignCenter();
                                                height("1%");
                                                width("10%");
                                            }
                                        }); // </panel_1>

                                        control(new TextFieldBuilder("PassInput", "") {
                                            {
                                                //interactOnMouseOver("borrarTextoPass()");
                                                width("65%");
                                            }
                                        });

                                    }
                                });// </panel_2.3>

                                // <panel_2.4>
                                panel(new PanelBuilder("Panel_Buttons") {
                                    {
                                        childLayoutHorizontal();
                                        alignCenter();
                                        valignCenter();
                                        height("16%");
                                        width("55%");

                                        // GUI element
                                        control(new ButtonBuilder("Button_OK2", "OK") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#191970");
                                                height("50%");
                                                width("40%");
                                                visibleToMouse(true);
                                                interactOnClick("insertarUsuario2()");
                                                //interactOnRelease("loadMenuFromInput()");
                                            }
                                        });

                                        panel(new PanelBuilder("Panel_PANELEMPTY2") {
                                            {
                                                childLayoutHorizontal();
                                                alignCenter();
                                                valignCenter();
                                                height("4%");
                                                width("20%");
                                            }
                                        });

                                        control(new ButtonBuilder("Button_QUIT2", "Back") {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#191970");
                                                height("50%");
                                                width("40%");
                                                visibleToMouse(true);
                                                interactOnClick("loadLogInFromInput2()");

                                            }
                                        });
                                    }
                                });// </panel_2.4>

                                control(new RadioGroupBuilder("RadioGroup"));
                                panel(new PanelBuilder("Panel_Space") {
                                    {
                                        childLayoutHorizontal();
                                        alignCenter();
                                        valignCenter();
                                        height("18%");
                                        width("18%");


                                        control(new LabelBuilder("LocalL", "Local"));

                                        control(new RadioButtonBuilder("LocalR") {
                                            {
                                                group("RadioGroup");
                                                interactOnRelease("setOffline()");
                                            }
                                        });
                                        panel(new PanelBuilder("Panel_Space") {
                                            {
                                                childLayoutCenter();
                                                alignCenter();
                                                valignCenter();
                                                height("7%");
                                                width("30%");
                                            }
                                        });
                                        control(new LabelBuilder("OnlineL", "Online"));
                                        control(new RadioButtonBuilder("OnlineR") {
                                            {

                                                group("RadioGroup");
                                                interactOnRelease("setOnline()");
                                            }
                                        });

                                        panel(new PanelBuilder("Panel_Space") {
                                            {

                                                childLayoutHorizontal();
                                                alignCenter();
                                                valignCenter();
                                                height("2%");
                                                width("30%");

                                            }
                                        });


                                    }
                                });

                            }
                        }); // </panel_2>

                    }
                });
                // </layer>
            }
        }.build(nifty));
        // </screen>

        new PopupBuilder("popupConnectionError") {
            {
                childLayoutCenter();
                backgroundColor("#000a");


                panel(new PanelBuilder("PanelPopup") {
                    {
                        childLayoutVertical();
                        alignCenter();
                        backgroundImage("/Pictures/FondoDialog.png");
                        valignCenter();
                        height("20%");
                        width("40%");

                        text(new TextBuilder() {
                            {
                                alignCenter();
                                color("f043");
                                text("Impossible to access the database.");
                                font("Interface/Fonts/Default.fnt");
                                height("50%");
                                width("27%");
                            }
                        });

                        control(new ButtonBuilder("Btn1", "OK") {
                            {
                                alignCenter();
                                valignCenter();
                                height("30%");
                                width("40%");
                                interactOnClick("closePopupConnectionError()");
                            }
                        });

                    }
                });

            }
        }.registerPopup(nifty);

        new PopupBuilder("popupUserError") {
            {
                childLayoutCenter();
                backgroundColor("#000a");


                panel(new PanelBuilder("PanelPopup") {
                    {
                        childLayoutVertical();
                        alignCenter();
                        backgroundImage("/Pictures/FondoDialog.png");
                        valignCenter();
                        height("20%");
                        width("40%");

                        text(new TextBuilder() {
                            {
                                alignCenter();
                                color("f043");
                                text("Nick already in use.");
                                font("Interface/Fonts/Default.fnt");
                                height("50%");
                                width("27%");
                            }
                        });

                        control(new ButtonBuilder("Btn1", "OK") {
                            {
                                alignCenter();
                                valignCenter();
                                height("30%");
                                width("40%");
                                interactOnClick("closePopupUserError()");
                            }
                        });

                    }
                });

            }
        }.registerPopup(nifty);


        game.getGUIViewPort().addProcessor(niftyDisplay);
        nifty.gotoScreen("InputScreen2"); // it is used to start the screen
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
        //  game.getInputManager().addListener(new AppActionListener(), "SIMPLEAPP_Exit1");
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

    public void startInput() {

        //nifty.exit();
        nifty.removeScreen("MenuScreen");
        game.loadInput();


    }

    public void quitInput() {
        game.stop();
    }

    public void loadInput() {
        game.loadInput();
    }
}
