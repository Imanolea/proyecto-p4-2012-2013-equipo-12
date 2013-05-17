package database;

import database.Connectible;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LocalStatsHandler extends JPanel implements Connectible {

    /**
     *
     */
    private static final long serialVersionUID = -6855550844603162276L;
    private Connection conn;
    private Statement statement;
    private ResultSet rs;
    private PreparedStatement ps;
    private static LocalStatsHandler instance = null;

    @Override
    public void conectar() {

        try {
            // Cargar por refletividad el driver de JDBC SQLite
            Class.forName("org.sqlite.JDBC");
            // Ahora indicamos la URL para conectarse a la BD de SQLite
            conn = DriverManager.getConnection("jdbc:sqlite:EstadisticasLocal.sqlite");
        } catch (ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Clase no encontrada.", "La conexi��n no pudo ser establecida", 2);
        } catch (SQLException e2) {
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Error de sentencia SQL.", "La conexi��n no pudo ser establecida", 2);
        }
    }

    public static Connection conectar(Connection con) {
        Connection connection = null;
        try {
            // Cargar por refletividad el driver de JDBC SQLite
            Class.forName("org.sqlite.JDBC");
            // Ahora indicamos la URL para conectarse a la BD de SQLite
            con = DriverManager.getConnection("jdbc:sqlite:EstadisticasLocal.sqlite");
        } catch (Exception e1) {
            System.out.print("EXCEPTION");
        }
        return connection;
    }

    public static Connection conectarStatic() {
        Connection connection = null;
        try {
            // Cargar por refletividad el driver de JDBC SQLite
            Class.forName("org.sqlite.JDBC");
            // Ahora indicamos la URL para conectarse a la BD de SQLite
            connection = DriverManager.getConnection("jdbc:sqlite:EstadisticasLocal.sqlite");
        } catch (Exception e1) {
            System.out.print("EXCEPTION");
        }
        return connection;
    }

    @Override
    public void desconectar() {
        try {
            // cerramos todo
            conn.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. La BD no se ha podido cerrar correctamente.", "La conexi��n no pudo ser establecida", 2);
        }
    }

    public static void desconectar(Connection connection) {
        try {
            // cerramos todo
            connection.close();
        } catch (Exception e) {
            System.out.print("EXCEPTION");
        }
    }

    public static LocalStatsHandler getInstance() {
        if (instance == null) {
            instance = new LocalStatsHandler();
        }
        return instance;
    }

    public void listarEstadisticasJugares() {
        conectar();
        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM JUGADOR;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next()) {
                String name = "" + rs.getString("NAME");
                System.out.println("NOMBRE: " + name);
                String nick = "" + rs.getString("NICK");
                System.out.println("NICK: " + nick);
                String pass = "" + rs.getString("PASSWORD");
                System.out.println("PASS: " + pass);
                System.out.println("--");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Error de ejecuci��n de sentencias SQLite.", "La conexi��n no pudo ser establecida", 2);
        }
        desconectar();
    }

    public String comprobarJugador(String insertedNick, String insertedPassword) {
        String name = "";
        conectar();
        boolean encontrado = false;
        try {
            String query = "SELECT * FROM JUGADOR;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next() && !encontrado) {
                String nick = "" + rs.getString("NICK");
                String pass = "" + rs.getString("PASSWORD");
                name = "" + rs.getString("NAME");
                if (insertedNick.equals(nick) && insertedPassword.equals(pass)) {
                    encontrado = true;
                    return name;
                }
            }
            rs.close();
            statement.close();
            desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    
    public static String comprobarJugadorStatic(String insertedNick, String insertedPassword) {
        Connection connection = null;
        Statement statement;
        ResultSet rs;
        String name = "";
        boolean encontrado = false;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:EstadisticasLocal.sqlite");
            String query = "SELECT * FROM JUGADOR;";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next() && !encontrado) {
                String nick = "" + rs.getString("NICK");
                String pass = "" + rs.getString("PASSWORD");
                name = "" + rs.getString("NAME");
                if (insertedNick.equals(nick) && insertedPassword.equals(pass)) {
                    encontrado = true;
                    return name;
                }
            }
            rs.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String listarTop10(int pos) {
        String string = "";
        try {
            String query = "SELECT * FROM TOP;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            int i = 0;
            // y recorremos lo obtenido
            while (rs.next() && i < 10) {
                if (pos == i) {
                    return string;
                } else {
                    String nick = "" + rs.getString("NICK");
                    String punt = "" + rs.getString("PUNTUACION");
                    String disparos_ac = "" + rs.getString("DISPAROS_AC");
                    String disparos_tot = "" + rs.getString("DISPAROS_TOT");
                    String tiempo = "" + rs.getString("TIEMPO");
                    int prec = (Integer.parseInt(disparos_ac) * 100) / Integer.parseInt(disparos_tot);
                    string = "NICK: " + nick + "   PUNTUACION: " + punt + "   PRECISION: " + prec + "%   TIEMPO: " + tiempo + "seg.";
                    i++;
                }
            }
            rs.close();
            statement.close();
            desconectar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }
    
    public static String listarTop10Static(int pos) {
        String[] array = new String[10];
        Connection connection = null;
        Statement statement;
        ResultSet rs;
        String string = "";

        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:EstadisticasLocal.sqlite");
            String query = "SELECT * FROM TOP;";
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            int i = 0;
            // y recorremos lo obtenido
            while (rs.next() && i < 10) {
                if (pos == i) {
                    return string;
                } else {
                    String nick = "" + rs.getString("NICK");
                    //System.out.println("NICK: " + nick);
                    String punt = "" + rs.getString("PUNTUACION");
                    //System.out.println("PUNTUACION: " + name);
                    String disparos_ac = "" + rs.getString("DISPAROS_AC");
                    //System.out.println("DISPAROS_AC: " + disparos_ac);
                    String disparos_tot = "" + rs.getString("DISPAROS_TOT");
                    //System.out.println("DISPAROS_TOT: " + disparos_tot);
                    String tiempo = "" + rs.getString("TIEMPO");
                    //System.out.println("TIEMPO: " + tiempo);
                    //System.out.println("TIEMPO: " + (int) Float.parseFloat(tiempo) / 3600 + "horas, " + (int) (Float.parseFloat(tiempo) % 3600) / 1 + "minutos, " + (int) (Float.parseFloat(tiempo) % 360) % 1 + "segundos.");
                    //System.out.println("--");
                    int prec = (Integer.parseInt(disparos_ac) * 100) / Integer.parseInt(disparos_tot);
                    string = "NICK: " + nick + "   PUNTUACION: " + punt + "   PRECISION: " + prec + "%   TIEMPO: " + tiempo + "seg.";
                    array[i] = string;
                    i++;
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        desconectar(connection);
        return string;
    }
    
    public void mostrarDisparosTopPorciento() {
        conectar();
        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT NICK, DISPAROS_AC, DISPAROS_TOT FROM TOP;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next()) {
                String nick = "" + rs.getString("NICK");
                String disparos_ac = "" + rs.getString("DISPAROS_AC");
                String disparos_tot = "" + rs.getString("DISPAROS_TOT");
                System.out.println("PORCENTAJE ACIERTOS por " + nick + ": " + (Integer.parseInt(disparos_ac) * 100) / Integer.parseInt(disparos_tot));

                System.out.println("--");
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Error de ejecucion de sentencias SQLite.", "La conexion no pudo ser establecida", 2);
        }
        desconectar();
    }

    public void agregarPerfil(Player jugador) {
        conectar();
        String password = jugador.getPassword();
        String nick = jugador.getNick();
        String name = jugador.getNombre();
        try {
            ps = conn.prepareStatement("INSERT INTO JUGADOR VALUES (?, ?, ?)");
            ps.setString(1, nick);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.executeUpdate();
            ps.close();
            desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Error a la hora de arreglar un PERFIL.", "La conexi��n no pudo ser establecida", 2);
        }
    }

    public static void agregarPerfilStatic(Player jugador) {
        Connection connection;
        PreparedStatement ps;
        String password = jugador.getPassword();
        String nick = jugador.getNick();
        String name = jugador.getNombre();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:EstadisticasLocal.sqlite");
            ps = connection.prepareStatement("INSERT INTO JUGADOR VALUES (?, ?, ?)");
            ps.setString(1, nick);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.executeUpdate();
            ps.close();
            desconectar(connection);
        } catch (Exception e) {
            System.out.print("Exception");
            e.printStackTrace();
        }
    }  

    public void agregarPartida(Game partida) {
        conectar();
        String nick = partida.getNick();
        Timestamp fecha_hora = partida.getFecha_hora();
        String fecha_horaS = fecha_hora.toString();
        String puntuacion = partida.getPuntuacion();
        String nivel = partida.getNivel();
        String disparos_ac = partida.getDisparos_ac();
        String disparos_tot = partida.getDisparos_tot();
        String muertes = partida.getMuertes();
        double tiempo = partida.getTiempo();

        try {
            ps = conn.prepareStatement("INSERT INTO PARTIDA VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, nick);
            ps.setString(2, fecha_horaS);
            ps.setString(3, puntuacion);
            ps.setString(4, nivel);
            ps.setString(5, disparos_ac);
            ps.setString(6, disparos_tot);
            ps.setString(7, muertes);
            ps.setDouble(8, tiempo);
            ps.executeUpdate();
            ps.close();
            desconectar();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Error ejecutando una PARTIDAS", "La conexi��n no pudo ser establecida", 2);
        }
    }
    

    public static ArrayList listarEstadisticasPartidas() {

        ArrayList<Game> aP = new ArrayList<Game>();
        Connection connection = conectarStatic();
        conectar(connection);
        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM PARTIDA;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next()) {
                String nick = "" + rs.getString("NICK");
                System.out.println("NICK: " + nick);
                String time = "" + rs.getString("FECHA_HORA");
                System.out.println("FECHA_HORA: " + time);
                String punt = "" + rs.getString("PUNTUACION");
                System.out.println("PUNTUACION: " + punt);
                String nivel = "" + rs.getString("NIVEL");
                System.out.println("NIVEL: " + nivel);
                String disparos_ac = "" + rs.getString("DISPAROS_AC");
                System.out.println("DISPAROS_AC: " + disparos_ac);
                String disparos_tot = "" + rs.getString("DISPAROS_TOT");
                System.out.println("DISPAROS_TOT: " + disparos_tot);
                String muertos = "" + rs.getString("MUERTES");
                System.out.println("MUERTOS: " + muertos);
                String tiempo = "" + rs.getString("TIEMPO");
                System.out.println("TIEMPO: " + tiempo);

                Game p = new Game(nick, punt, nivel, disparos_ac, disparos_tot, muertos, tiempo);
                aP.add(p);
            }
            statement.close();
            desconectar(connection);
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return aP;
    }

    public static String listarEstadisticasPartidas(int i) {

        Connection connection = conectarStatic();
        conectar(connection);
        i++;
        int j = 0;
        String r = "";

        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM PARTIDA;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next() && j != i) {
                String nick = "" + rs.getString("NICK");
                System.out.println("NICK: " + nick);
                String time = "" + rs.getString("FECHA_HORA");
                System.out.println("FECHA_HORA: " + time);
                String punt = "" + rs.getString("PUNTUACION");
                System.out.println("PUNTUACION: " + punt);
                String nivel = "" + rs.getString("NIVEL");
                System.out.println("NIVEL: " + nivel);
                String disparos_ac = "" + rs.getString("DISPAROS_AC");
                System.out.println("DISPAROS_AC: " + disparos_ac);
                String disparos_tot = "" + rs.getString("DISPAROS_TOT");
                System.out.println("DISPAROS_TOT: " + disparos_tot);
                String muertos = "" + rs.getString("MUERTES");
                System.out.println("MUERTOS: " + muertos);
                String tiempo = "" + rs.getString("TIEMPO");
                System.out.println("TIEMPO: " + tiempo);

                Game p = new Game(nick, punt, nivel, disparos_ac, disparos_tot, muertos, tiempo);
                r = p.print();
                j++;
            }
            statement.close();
            desconectar(connection);
        } catch (SQLException e) {
            e.printStackTrace();

        }

        return r;


    }

    public static void main(String[] args) {
        getInstance().listarEstadisticasJugares();
        //getInstance().comprobarJugador("e", "Jesus");

        /*Player Imanol = new Player("3", "Jesus", "jesuspereira");
         getInstance().agregarPerfil(Imanol);

         Game partidita = new Game("3", "1230", "3", "900", "50", "9", "12800");
         getInstance().agregarPartida(partidita);
         //getInstance().listarEstadisticasPartidas();
         getInstance().listarTop10();
         //getInstance().mostrarDisparosTopporciento();
         //getInstance().listarEstadisticasJugares();*/
    }
}