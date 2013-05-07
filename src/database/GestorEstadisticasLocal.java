package database;

import database.Conectable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GestorEstadisticasLocal extends JPanel implements Conectable {

    /**
     *
     */
    private static final long serialVersionUID = -6855550844603162276L;
    private Connection conn;
    private Statement statement;
    private ResultSet rs;
    private PreparedStatement ps;
    private static GestorEstadisticasLocal instance = null;

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
        }catch(Exception e){
            System.out.print("EXCEPTION");
        }
    }
    
    public static GestorEstadisticasLocal getInstance() {
        if (instance == null) {
            instance = new GestorEstadisticasLocal();
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
                String cod_u = "" + rs.getString("COD_U");
                System.out.println("COD_U: " + cod_u);
                String nick = "" + rs.getString("NICK");
                System.out.println("NICK: " + nick);
                String name = "" + rs.getString("NOMBRE");
                System.out.println("NOMBRE: " + name);
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

    public void listarTop10() {
        conectar();
        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM TOP;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            int i = 1;
            // y recorremos lo obtenido
            while (rs.next() && i <= 10) {
                String nick = "" + rs.getString("NICK");
                System.out.println("NICK: " + nick);
                String name = "" + rs.getString("PUNTUACION");
                System.out.println("PUNTUACION: " + name);
                String nivel = "" + rs.getString("NIVEL");
                System.out.println("NIVEL: " + nivel);
                String disparos_ac = "" + rs.getString("DISPAROS_AC");
                System.out.println("DISPAROS_AC: " + disparos_ac);
                String disparos_tot = "" + rs.getString("DISPAROS_TOT");
                System.out.println("DISPAROS_TOT: " + disparos_tot);
                String tiempo = "" + rs.getString("TIEMPO");
                System.out.println("TIEMPO: " + tiempo);
                System.out.println("TIEMPO: " + (int) Float.parseFloat(tiempo) / 3600 + "horas, " + (int) (Float.parseFloat(tiempo) % 3600) / 1 + "minutos, " + (int) (Float.parseFloat(tiempo) % 360) % 1 + "segundos.");
                System.out.println("--");
                i++;
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Error de ejecucion de sentencias SQLite.", "La conexion no pudo ser establecida", 2);
        }
        desconectar();
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

    public void agregarPerfil(Jugador jugador) {
        conectar();
        String cod_u = jugador.getCod_u();
        String nick = jugador.getNick();
        String nombre = jugador.getNombre();
        try {
            ps = conn.prepareStatement("INSERT INTO JUGADOR VALUES (?, ?, ?)");
            ps.setString(1, cod_u);
            ps.setString(2, nick);
            ps.setString(3, nombre);
            ps.executeUpdate();
            ps.close();
            desconectar();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectarse a la base de datos EstadisticasLocal. Error a la hora de arreglar un PERFIL.", "La conexi��n no pudo ser establecida", 2);
        }
    }
    
    public static void agregarPerfilStatic(Jugador jugador) {
        Connection connection;
        PreparedStatement ps;
        String cod_u = jugador.getCod_u();
        String nick = jugador.getNick();
        String nombre = jugador.getNombre();
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:EstadisticasLocal.sqlite");
            ps = connection.prepareStatement("INSERT INTO JUGADOR VALUES (?, ?, ?)");
            ps.setString(1, cod_u);
            ps.setString(2, nick);
            ps.setString(3, nombre);
            ps.executeUpdate();
            ps.close();
            desconectar(connection);
        } catch (Exception e) {
            System.out.print("Exceptioc");
            e.printStackTrace();
        }
    }
    
    public static String ultimoCod_uAsignado() {
        Connection connection = conectarStatic();
        conectar(connection);
        String cod_u = "";
        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT COD_U FROM JUGADOR ORDER BY COD_U DESC;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            
            
            cod_u = "" + rs.getString("COD_U");
            
            rs.close();
            statement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        desconectar(connection);
        return cod_u;
    }

    public void agregarPartida(Partida partida) {
        conectar();
        String cod_u = partida.getCod_u();
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
            ps.setString(1, cod_u);
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

    public void listarEstadisticasPartidas() {
        conectar();
        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM PARTIDA;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next()) {
                String cod_u = "" + rs.getString("COD_U");
                System.out.println("COD_U: " + cod_u);
                String nick = "" + rs.getString("FECHA_HORA");
                System.out.println("FECHA_HORA: " + nick);
                String name = "" + rs.getString("PUNTUACION");
                System.out.println("PUNTUACION: " + name);
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

    public static void main(String[] args) {
        //Jugador Imanol = new Jugador("12", "Jon Ander2", "Jonan2");
        //getInstance().agregarPerfil(Imanol);

        //Partida partidita = new Partida("13", "900", "3", "900", "1200", "9", 12800);
        //getInstance().agregarPartida(partidita);
        //getInstance().listarEstadisticasPartidas();
        //getInstance().listarTop10();
        //getInstance().mostrarDisparosTopporciento();
        getInstance().listarEstadisticasJugares();
    }
}
