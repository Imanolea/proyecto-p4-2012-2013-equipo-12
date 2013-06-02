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
import database.OnlineStatsHandler;
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

/**
 * Estado relativo al menú que recoge las puntuaciones de los distintos jugadores, tanto online como local
 * @author Team 12
 */

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

        final String s = LocalStatsHandler.getInstance().listarTop10(0);

        nifty.addScreen("StatisticsScreen", new ScreenBuilder("SScreen") {
            {
                controller(new MenuState(game));


                layer(new LayerBuilder("Layer_ID2") {
                    {
                        childLayoutVertical();

                        // <panel_1>
                        panel(new PanelBuilder("Panel_TITLE2") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("9%");
                                width("100%");

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        if (game.getOnline()) {
                                             text(OnlineStatsHandler.getInstance().listarTop10(1));
                                       
                                        } else {
                                                text(LocalStatsHandler.getInstance().listarTop10(1));
                                        }
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

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(2)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(2);
                                            }
                                        } else {
                                            if (noHayMasPartidas(2)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(2);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(3)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(3);
                                            }
                                        } else {
                                            if (noHayMasPartidas(3)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(3);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder("ef") {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(4)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(4);
                                            }
                                        } else {
                                            if (noHayMasPartidas(4)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(4);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(5)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(5);
                                            }
                                        } else {
                                            if (noHayMasPartidas(5)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(5);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(6)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(6);
                                            }
                                        } else {
                                            if (noHayMasPartidas(6)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(6);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder("ef") {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(7)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(7);
                                            }
                                        } else {
                                            if (noHayMasPartidas(7)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(7);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(8)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(8);
                                            }
                                        } else {
                                            if (noHayMasPartidas(8)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(8);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");
                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(9)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(9);
                                            }
                                        } else {
                                            if (noHayMasPartidas(9)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(9);
                                            }
                                        }
                                        text(s);
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

                                text(new TextBuilder() {
                                    {
                                        alignCenter();
                                        valignCenter();
                                        height("100%");
                                        width("100%");
                                        font("Interface/Fonts/Jokerman18.fnt");

                                        String s = "";
                                        if (game.getOnline()) {
                                            if (noHayMasPartidasOnline(10)) {
                                                s = OnlineStatsHandler.getInstance().listarTop10(10);
                                            }
                                        } else {
                                            if (noHayMasPartidas(10)) {
                                                s = LocalStatsHandler.getInstance().listarTop10(10);
                                            }
                                        }
                                        text(s);
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
        nifty.gotoScreen("StatisticsScreen");
    }

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

    public static boolean noHayMasPartidas(int i) {
        if (i != 0) {
            String nueva = LocalStatsHandler.getInstance().listarTop10(i);
            String deAntes = LocalStatsHandler.getInstance().listarTop10(i - 1);
            if (nueva.equals(deAntes)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public static boolean noHayMasPartidasOnline(int i) {
        if (i != 0) {
            String nueva = OnlineStatsHandler.getInstance().listarTop10(i);
            String deAntes = OnlineStatsHandler.getInstance().listarTop10(i - 1);
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
