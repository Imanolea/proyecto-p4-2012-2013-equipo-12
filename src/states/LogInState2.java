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
import database.Player;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.PopupBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import game.MainApp;
import game.MainApp;

/**
 * Estado del menú relativo a la pantalla de "log in" desde el menú del juego
 * @author Team 12
 */
public class LogInState2 extends AbstractAppState implements ScreenController {

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
    public static boolean b = false;
    private Player player;

    public LogInState2(MainApp game) {
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
        this.game = (MainApp) game;

        this.assetManager = this.game.getAssetManager();
        this.stateManager = this.game.getStateManager();
        this.inputManager = this.game.getInputManager();
        this.viewPort = this.game.getViewPort();
        this.audioRenderer = this.game.getAudioRenderer();
        this.guiViewPort = this.game.getGuiViewPort();
        this.flyCam = new FlyByCamera(game.getCamera());
        this.player=this.game.getPlayer();

        game.getRenderer().applyRenderState(RenderState.DEFAULT);

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

        nifty.addScreen("LogInScreen2", new ScreenBuilder("LogInScreen2") {
            {
                controller(new MenuState(game));


                layer(new LayerBuilder("LayerLogIn") {
                    {
                        childLayoutVertical();
                        
                        // <panel_1>
                        panel(new PanelBuilder("Panel_Title_Powders") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("15%");
                                width("100%");
                                
                            }
                        }); // </panel_1>

                        panel(new PanelBuilder("Panel_Title_LogIn") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("10%");
                                width("50%");

                                text(new TextBuilder() {
                                    {
                                        text("LOG IN");
                                        font("Interface/Fonts/Jokerman23.fnt"); // define la fuente a usar
                                        height("100%");
                                        width("100%");
                                    }
                                });
                            }
                        }); // </panel_1>

                        // <panel_2>
                        panel(new PanelBuilder("Panel_RegisterProcess") {
                            {
                                childLayoutVertical();       
                                alignCenter();
                                valignCenter();
                                height("50%");
                                width("60%");

                                panel(new PanelBuilder("Panel_Text") {
                                    {
                                        childLayoutVertical();
                                        height("30%");
                                        width("50%");
                                        alignCenter();
                                        valignCenter();

                                        text(new TextBuilder() {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                font("Interface/Fonts/Default.fnt");
                                                 text("Insert your nick and password if you are already registered");
                                                height("20%");
                                                width("80%");
                                            }
                                        });

                                        text(new TextBuilder() {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                text("Otherwise, please \"SIGN UP\"");
                                                font("Interface/Fonts/Default.fnt");
                                                height("20%");
                                                width("80%");
                                            }
                                        });
                                    }
                                }); // </panel_2>


                                panel(new PanelBuilder("Panel_InsertData1") {
                                    {
                                        childLayoutHorizontal();              
                                        height("7%");
                                        width("50%");
                                        alignCenter();
                                        valignCenter();

                                        text(new TextBuilder() {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                text("Nick: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("70%");
                                                width("30%");
                                            }
                                        });

                                        control(new TextFieldBuilder("NickLogIn") {
                                            {
                                                width("50%");
                                                height("100%");
                                            }
                                        });
                                    }
                                });

                                panel(new PanelBuilder("Panel_Empty") {
                                    {
                                        childLayoutHorizontal();            
                                        alignCenter();
                                        valignCenter();
                                        height("3%");
                                        width("50%");

                                    }
                                });


                                panel(new PanelBuilder("Panel_InsertData12") {
                                    {
                                        childLayoutHorizontal();       
                                        alignCenter();
                                        valignCenter();
                                        height("7%");
                                        width("50%");

                                        text(new TextBuilder() {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                text("Password: ");
                                                font("Interface/Fonts/Default.fnt");
                                                height("70%");
                                                width("30%");
                                            }
                                        });

                                        control(new TextFieldBuilder("PassLogIn") {
                                            {
                                                width("50%");
                                                height("100%");
                                            }
                                        });
                                    }
                                }); // </panel_2>

                                panel(new PanelBuilder("Panel_Buttons") {
                                    {
                                        childLayoutHorizontal();       
                                        alignCenter();
                                        valignCenter();
                                        height("30%");
                                        width("50%");

                                        control(new ButtonBuilder("Button_LOGIN", "LOG IN") {
                                            {
                                                alignCenter();
                                                valignBottom();
                                                backgroundColor("#f108");
                                                height("35%");
                                                width("45%");
                                                visibleToMouse(true);
                                                interactOnClick("cargarUsuario2()");
                                            }
                                        });

                                        panel(new PanelBuilder("Panel_Space") {
                                            {
                                                childLayoutHorizontal();
                                                alignCenter();
                                                valignCenter();
                                                height("4%");
                                                width("10%");

                                            }
                                        });

                                        control(new ButtonBuilder("Button_QUIT", "BACK") {
                                            {
                                                alignCenter();
                                                valignBottom();
                                                backgroundColor("#f108");
                                                height("35%");
                                                width("45%");
                                                visibleToMouse(true);
                                                interactOnClick("loadMenuFromLogIn2()");
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
                                text("Wrong nick or password.");
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
        nifty.gotoScreen("LogInScreen2");
    }

    public void update(float tpf) {
        super.update(tpf);

        rootNode.updateLogicalState(tpf);
        guiNode.updateLogicalState(tpf);
        rootNode.updateGeometricState();
        guiNode.updateGeometricState();

    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        super.stateAttached(stateManager);
        game.getViewPort().attachScene(rootNode);
        game.getGUIViewPort().attachScene(guiNode);
        if (b == true) {
            game.getGUIViewPort().addProcessor(niftyDisplay);
        }   
    }

    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
        game.getViewPort().detachScene(rootNode);
        game.getGUIViewPort().detachScene(guiNode);
        game.getGUIViewPort().removeProcessor(niftyDisplay);
    }

    public void render(RenderManager rm) {
    }

    public void startInput() {

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
