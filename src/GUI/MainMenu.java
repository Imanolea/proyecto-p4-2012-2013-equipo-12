/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import game.Main;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MainMenu extends JFrame {

    private static final long serialVersionUID = 1L;
    // Declaracion de componentes
    private JPanel mainPanel;
    private JButton settingsButton;
    public JButton playButton;
    private JButton leaderboardsButton;
    private JMenuBar menubar;
    private JMenu fileMenu;
    private JMenuItem exitMenuItem;
    public boolean iniciar = false;
    

    /**
     * Constructor de la ventana encargada de mostrar el menú del programa
     */
    public MainMenu() {


        // Aspecto general de la ventana y formato de componentes y contenedores



        setSize(350, 400);
        setLocationRelativeTo(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(mainPanel);
        setResizable(false);
        setTitle("Powders - Main Menu (ALPHA)");
        setDefaultCloseOperation(EXIT_ON_CLOSE);



        // Creacion de componentes relativos a atributos

        menubar = new JMenuBar();
        setJMenuBar(menubar);
        fileMenu = new JMenu("File");
        menubar.add(fileMenu);
        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
        settingsButton = new JButton("SETTINGS");
        leaderboardsButton = new JButton("STATS");
        playButton = new JButton("PLAY");


        // Creacion de componentes relativos a atributos

        settingsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
        playButton.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
        playButton.setSelected(false);

    //private JButton settingsButton;
    //private JButton playButton;
    //private JButton leaderboardsButton;
        
        /*playButton.addActionListener(new ActionListener() {

            /*public void actionPerformed(ActionEvent ae) {
                game.Main.thread.start();
            }
        });*/
        


        leaderboardsButton.setFont(new Font("Lucida Grande", Font.PLAIN, 28));




        placeLine(mainPanel, "Start playing Powders)", playButton);
        placeLine(mainPanel, "Check Powders leaderboards", leaderboardsButton);
        placeLine(mainPanel, "Customise your settings", settingsButton);





    }

    private void placeLine(Container p, String etiqueta, Component campo) {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        tempPanel.add(campo);
        tempPanel.add(new JLabel(etiqueta));
        p.add(tempPanel);
    }

    public static void main(String[] args) {
        MainMenu frame = new MainMenu();
        frame.setVisible(true);


    }
}
