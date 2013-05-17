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
import database.LocalStatsHandler;
import database.Game;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import de.lessvoid.nifty.tools.Color;
import game.MainApp;
import java.util.ArrayList;

public class StatisticsState extends AbstractAppState implements ScreenController {

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

    public StatisticsState(MainApp game) {
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

        final String s = LocalStatsHandler.listarTop10Static(0);

        nifty.addScreen("StatisticsScreen", new ScreenBuilder("SScreen") {
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
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder("ef") {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        String s = LocalStatsHandler.listarTop10Static(1);
                                        text(s);
                                    }
                                });
                            }
                        }); // </panel_1>
                        // <panel_2>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(2)) {
                                            String s = LocalStatsHandler.listarTop10Static(2);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_2>
                        // <panel_3>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(3)) {
                                            String s = LocalStatsHandler.listarTop10Static(3);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_3>
                        // </panel_4>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder("ef") {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(4)) {
                                            String s = LocalStatsHandler.listarTop10Static(4);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_4>
                        // <panel_5>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(5)) {
                                            String s = LocalStatsHandler.listarTop10Static(5);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_5>
                        // <panel_5>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(6)) {
                                            String s = LocalStatsHandler.listarTop10Static(6);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_6>
                        // <panel_7>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder("ef") {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(7)) {
                                            String s = LocalStatsHandler.listarTop10Static(7);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_7>
                        // <panel_8>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(8)) {
                                            String s = LocalStatsHandler.listarTop10Static(8);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_8>
                        // <panel_9>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(9)) {
                                            String s = LocalStatsHandler.listarTop10Static(9);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_9>
                        // <panel_10>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                // add text

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Default.fnt");
                                        if (noHayMasPartidas(10)) {
                                            String s = LocalStatsHandler.listarTop10Static(10);
                                            text(s);
                                        }
                                    }
                                });
                            }
                        }); // </panel_10>

                        control(new ButtonBuilder("Button_QUIT2", "Back") {
                            {
                                alignCenter();
                                valignCenter();
                                backgroundColor("#f108");
                                height("8%");
                                width("40%");
                                visibleToMouse(true);
                                interactOnClick("loadMenuFromStatistics()");

                            }
                        });

                    }
                });
                // </layer>
            }
        }.build(nifty));
        // </screen>


        game.getGUIViewPort().addProcessor(niftyDisplay);
        nifty.gotoScreen("StatisticsScreen"); // it is used to start the screen
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

    public static boolean noHayMasPartidas(int i) {
        if (i != 0) {
            String nueva = LocalStatsHandler.listarTop10Static(i);
            String deAntes = LocalStatsHandler.listarTop10Static(i - 1);
            if (nueva.equals(deAntes)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }


    }
}
