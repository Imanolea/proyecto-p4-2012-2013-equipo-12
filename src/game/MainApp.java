package game;

import com.jme3.app.Application;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioNode.Status;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;
import com.jme3.system.Timer;
import database.LocalStatsHandler;
import database.OnlineStatsHandler;
import database.Player;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.opengl.Display;
import states.*;

/**
 * Clase que gestiona los cambios de los estados y principal de la aplicación
 *
 * @author Team 12
 */
public class MainApp extends Application {

    protected Node rootNode = new Node("Root Node");
    private GameState te = null;
    private MenuState ms = null;
    private PauseState ms2 = null;
    private AppSettings s;
    private InputState is = null;
    private InputState2 is2 = null;
    private StatisticsState ss = null;
    private LogInState ls = null;
    private LogInState2 ls2 = null;
    private GameOverState gos = null;
    private Player player;
    private boolean online = false;
    private boolean conectado = false;
    private String namePlayer;
    private String score;
    private AudioNode backgroundAudio;

    public MainApp() {
    }

    public void start() {

        if (settings == null) {
            s = new AppSettings(true);
            s.setSettingsDialogImage("/Pictures/Portada.jpg");
            setSettings(s);
        }
        if (!JmeSystem.showSettingsDialog(settings, true)) {
            return;
        }

        super.start();
        LocalStatsHandler.getInstance().conectar();
        try {
            OnlineStatsHandler.getInstance().conectar();

        } catch (ClassNotFoundException ex) {
            System.out.println("CLASS NOT FOUND, CONNECTION TO THE SERVER DID NOT SUCCEED");
            conectado = false;

        } catch (SQLException ex) {
            System.out.println("CONNECTION TO THE SERVER DID NOT SUCCEED");
            conectado = false;
        }
    }

    public void initialize() {
        super.initialize();

        ms = new MenuState(this);
        te = new GameState(this);
        is = new InputState(this);
        ss = new StatisticsState(this);
        ls = new LogInState(this);
        ls2 = new LogInState2(this);
        ms2 = new PauseState(this);
        gos = new GameOverState(this);
        is2 = new InputState2(this);

        backgroundAudio = new AudioNode(assetManager, "/Audio/Cavern of Mystery long version.wav", false);
        backgroundAudio.setLooping(true);
        backgroundAudio.setVolume(2);

        getStateManager().attach(ls);
    }

    public void update() {
        super.update();
        float tpf = timer.getTimePerFrame() * speed;

        stateManager.update(tpf);

        stateManager.render(renderManager);

        renderManager.render(tpf, true);
    }

    public void reLoadMenu() {
        getStateManager().detach(ms);
        getStateManager().attach(ms);
    }

    public void loadMenu() {
        getStateManager().detach(te);
        getStateManager().attach(ms);
    }

    public void loadInput() {
        getStateManager().detach(ms);
        getStateManager().attach(is);
    }

    public void loadInput2() {
        getStateManager().detach(ls);
        getStateManager().attach(is2);
    }

    public void loadMenuFromInput() {
        getStateManager().detach(is);
        getStateManager().attach(ms);
    }

    public void loadMenuFromStatistics() {
        getStateManager().detach(ss);
        getStateManager().attach(ms);
    }

    public void loadMenuFromLogIn() {
        getStateManager().detach(ls);
        getStateManager().attach(ms);
    }

    public void loadLogInFromMenu() {
        getStateManager().detach(ms);
        getStateManager().attach(ls);
    }

    public void loadLogInFromInput() {
        getStateManager().detach(is);

        ls = new LogInState(this);


        getStateManager().attach(ls);

    }

    public void loadMenuFromLogIn2() {
        getStateManager().detach(ls2);
        getStateManager().attach(ms);
    }

    public void loadLogIn2FromMenu() {
        getStateManager().detach(ms);
        getStateManager().attach(ls2);
    }

    public void loadLogInFromInput2() {
        getStateManager().detach(is2);
        getStateManager().attach(ls);
    }

    public AppSettings getSettings() {
        return settings;
    }

    public void loadGame() {
        getStateManager().detach(ms);
        te = new GameState(this);
        getStateManager().attach(te);
    }

    public void loadGameFromMenuGame() {
        getStateManager().detach(ms2);
        te.setEnabled(true);
        inputManager.setCursorVisible(false);

    }

    public void loadMenuGameFromGame() {
        getStateManager().attach(ms2);
    }

    public ViewPort getViewPort() {
        return viewPort;
    }

    public ViewPort getGUIViewPort() {
        return guiViewPort;
    }

    public Timer getTimer() {
        return timer;
    }

    public static void main(String... args) {
        new MainApp().start();
    }

    public void loadStatistics() {

        getStateManager().detach(ms);
        getStateManager().attach(ss);
    }

    public void loadMenuFromGaveOver() {

        getStateManager().detach(gos);
        getStateManager().attach(ms);
    }

    public void loadGameFromGameOver() {

        getStateManager().detach(gos);
        te = new GameState(this);
        getStateManager().attach(te);
    }

    public void loadGameOverFromGame() {
        inputEnabled = false;
        getStateManager().detach(te);
        getStateManager().attach(gos);

    }

    public void setPlayer(Player p) {
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public void setOnline(boolean b) {
        this.online = b;
    }

    public boolean getOnline() {
        return online;
    }

    public void setConectado(boolean b) {
        this.conectado = b;
    }

    public boolean getConectado() {
        return conectado;
    }

    public void setNombre(String n) {
        this.namePlayer = n;
    }

    public String getNombre() {
        return namePlayer;
    }

    public void setInput(boolean input) {

        this.inputEnabled = input;
    }

    public boolean getInput() {

        return inputEnabled;
    }

    public void setScore(String s) {

        this.score = s;
    }

    public String getScore() {

        return score;
    }

    public void playAudio() {

        if (backgroundAudio.getStatus() == Status.Stopped) {
            this.backgroundAudio.play();
        }
    }

    public void stopAudio() {

        if (backgroundAudio.getStatus() == Status.Playing) {
            this.backgroundAudio.stop();
        }
    }
}
