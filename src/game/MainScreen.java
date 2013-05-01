/*
 * PROBLEMAS A SOLUCIONAR:
 *  - como llamar desde una clase al juego (--> ERROR) O, de lo contrario, llamar a la ventana desde el juego
 *  - mostrar ventana con estadisticas cargadas
 *  - repintar ventana en caso de cambio de lenguaje
 *  - actualizar valores desde otra clase, como el caso de la i
 */


package game;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MainScreen extends SimpleApplication implements ScreenController {

    //private String titulos[][] = new String[2][4];
    private String titulos[][] = {{"Comienzo", "Start"},
        {"Estadísticas", "Statistics"}, {"¿?", "?¿"}, {"Salir", "Quit"}};
    private int i = 0;

    public static void main(String[] args) {
        MainScreen app = new MainScreen();
        app.start();
    }
    
    public static void startMainScreen() {
        MainScreen app = new MainScreen();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        
        final MainScreen aplicacion = this;
        
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();

        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");

        inputManager.setCursorVisible(true);
        
        System.out.print("Escribo el numero"+i);

        // <screen>
        nifty.addScreen("MenuScreen", new ScreenBuilder("Menu") {
            {
                controller(new game.PowdersScreenController()); // This connects the Java class StartingScreen and the GUI screen.     

                // <layer>
                layer(new LayerBuilder("Layer_ID") {
                    {
                        childLayoutVertical(); // layer properties, add more...

                        // <panel_1>
                        panel(new PanelBuilder("Panel_TITLE") {
                            {
                                childLayoutCenter();
                                alignCenter();
                                height("15%");
                                width("50%");

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
                                                interactOnClick("startPowders()");
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
                                                interactOnClick("showStatistics()");
                                            }
                                        });
                                    }
                                });// </panel_2.2>

                                // <panel_2.3>
                                panel(new PanelBuilder("Panel_") {
                                    {
                                        childLayoutCenter();
                                        alignCenter();
                                        valignCenter();
                                        height("16%");
                                        width("55%");

                                        // GUI element
                                        control(new ButtonBuilder("Button_", titulos[2][i]) {
                                            {
                                                alignCenter();
                                                valignCenter();
                                                backgroundColor("#f108");
                                                height("50%");
                                                width("80%");
                                                visibleToMouse(true);
                                                PowdersScreenController.changeLanguage(aplicacion, i);
                                                interactOnClick("changeLanguage(this, 0)");
                                            }
                                        });
                                    }
                                });// </panel_2.3>

                                // <panel_2.4>
                                panel(new PanelBuilder("Panel_QUIT") {
                                    {
                                        childLayoutCenter();
                                        alignCenter();
                                        valignCenter();
                                        height("16%");
                                        width("55%");

                                        // GUI element
                                        control(new ButtonBuilder("Button_QUIT", titulos[3][i]) {
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
                                });// </panel_2.4>

                                panel(new PanelBuilder("Panel_QUIT") {
                                    {
                                        childLayoutCenter();
                                        height("16%");
                                        width("25%");
                                    }
                                });

                            }
                        }); // </panel_2>

                        // <panel_3>
                        panel(new PanelBuilder("Panel_LANGUAGES") {
                            {
                                childLayoutHorizontal();
                                alignCenter();
                                height("15%");
                                width("100%");

                                // <panel_3.1>
                                panel(new PanelBuilder("Panel_EMPTY3.1") {
                                    {
                                        childLayoutCenter();
                                        valignCenter();
                                        height("50%");
                                        width("50%");
                                    }
                                }); // </panel_3.1>

                                // <panel_3.2>
                                panel(new PanelBuilder("Panel_IN_LANGUAGES") {
                                    {
                                        childLayoutHorizontal();
                                        valignCenter();
                                        height("50%");
                                        width("50%"); ////////////////// NESPÂÑOL ENGLIH

                                        // <panel_3.2.1>
                                        panel(new PanelBuilder("Panel_EMPTY3.2.1") {
                                            {
                                                childLayoutHorizontal();
                                                valignCenter();
                                                height("100%");
                                                width("70%");


                                            }
                                        }); // </panel_3.2.1>

                                        // <panel_3.2.2>
                                        panel(new PanelBuilder("Panel_UK") {
                                            {
                                                childLayoutHorizontal();
                                                valignCenter();
                                                height("75%");
                                                width("10%");

                                                // add image
                                                image(new ImageBuilder() {
                                                    {
                                                        valignCenter();
                                                        alignRight();
                                                        height("100%");
                                                        width("100%");
                                                        filename("Pictures/uk.png");
                                                        visibleToMouse(true);
                                                        interactOnClick("i = getInt1()");
                                                        interactOnRelease("aplicacion.restart()");
                                                    }
                                                });


                                            }
                                        }); // </panel_3.2.2>

                                        // <panel_3.2.3>
                                        panel(new PanelBuilder("Panel_EMPTY3.2.3") {
                                            {
                                                childLayoutHorizontal();
                                                valignCenter();
                                                height("100%");
                                                width("5%");


                                            }
                                        }); // </panel_3.2.3>

                                        // <panel_3.2.4>
                                        panel(new PanelBuilder("Panel_ESP") {
                                            {
                                                childLayoutHorizontal();
                                                valignCenter();
                                                height("75%");
                                                width("10%");

                                                // add image
                                                image(new ImageBuilder() {
                                                    {
                                                        valignCenter();
                                                        alignRight();
                                                        height("100%");
                                                        width("100%");
                                                        filename("Pictures/esp.jpg");
                                                        System.out.print("DddESAPAÑA");
                                                        visibleToMouse(true);
                                                        
                                                        interactOnClick("aplicacion.getI()");
                                                        
                                                    }
                                                });


                                            }
                                        }); // </panel_3.2.4>

                                        // <panel_3.2.5>
                                        panel(new PanelBuilder("Panel_EMPTY3.2.5") {
                                            {
                                                childLayoutHorizontal();
                                                valignCenter();
                                                height("100%");
                                                width("5%");


                                            }
                                        }); // </panel_3.2.6>

                                    }
                                }); // </panel_3.2>
                            }
                        });// </panel_3>
                    }
                });
                // </layer>
            }
        }.build(nifty));
        // </screen>

        nifty.gotoScreen("MenuScreen"); // it is used to start the screen
    }

    public void startGame() {
        System.out.print("FUNCIONAAAA EL START");
        Main game = new Main();
        game.start();
    }

    public void bind(Nifty nifty, Screen screen) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onStartScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void onEndScreen() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getI() {
        System.out.print(i);
        return i;
    }

    public void setI(int entero) {
        i = entero;
    }
}
