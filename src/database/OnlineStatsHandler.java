package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Team 12
 * @Description Esta clase permite realizar la conexion a una base de datos
 * albergada en un servidor externo
 */
public class OnlineStatsHandler extends JFrame implements Connectible {

    private static final long serialVersionUID = -9029903859955635542L;
    private Connection conn;
    private Statement statement;
    private ResultSet rs;
    private PreparedStatement ps;
    private static OnlineStatsHandler instance = null;

    /**
     * Método dinámico empleado para hacer la conexión con la base de datos
     * externa
     */
    public void conectar() {

        try {
            // Cargar por refletividad el driver de JDBC MySQL
            Class.forName("com.mysql.jdbc.Driver");
            // Ahora indicamos la URL, USUARIO Y CONTRASEÑA para conectarse a la BD de MySQL albergada en un servidor externo
            String url = "jdbc:mysql://lamaisondeleiaylocomj.homelinux.com/powders";
            String userid = "powders";
            String password = "p0wd3rs";

            /*
             * String url = "jdbc:mysql://lamaisondeleiaylocomj.homelinux.com/powders";
                        String userid = "powders";
                        String password = "p0wd3rs";
             */
            conn = DriverManager.getConnection(url, userid, password);

        } catch (ClassNotFoundException e1) {
           System.out.println("Required class not found");
        } catch (SQLException e2) {
            System.out.println("Connection to the server did not succeed"); 
        }
    }

    /**
     * Método dinámico empleado para hacer la desconexión con la base de datos
     * externa
     */
    public void desconectar() {
        try {
            // cerramos todo
            conn.close();
        } catch (SQLException e) {
             System.out.println("Connection to the server did not succeed");

        }
    }

    public static OnlineStatsHandler getInstance() {
        if (instance == null) {
            instance = new OnlineStatsHandler();
        }
        return instance;
    }


    /**
     * Método empleado para listar los jugadores registrados en la base de datos
     * local, en EstadisticasLocal.sqlite
     *
     * @throws Exception Esta excepción se tratará en otra clase mediante la
     * visualización del pop up correspodiente.
     */
    public void listarEstadisticasJugadores() throws Exception {

        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM JUGADOR;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // y recorremos lo obtenido
            while (rs.next()) {
                String name = "" + rs.getString("NOMBRE");
                System.out.println("NAME: " + name);
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

    }

    /**
     * Método dinámico empleado para comprobar que el nick y password insertados
     * como paramétros son los datos de un usuario que ya se encuentra
     * registrados en la base de datos local, en EstadisticasLocal.sqlite
     *
     * @param insertedNick Variable String que corresponde al nickname del
     * usuario
     * @param insertedPassword Variable String que corresponde al password del
     * usuario
     * @return Retorna un String: el nickname del usuario en caso de que los
     * parametros concuerden con los datos de un usuario ya registrado y
     * devuelve un string vacio en caso contrario
     * @throws Exception Esta excepción se tratará en otra clase mediante la
     * visualización del pop up correspodiente
     */
    public String comprobarJugador(String insertedNick, String insertedPassword) throws Exception {
        String name = "";

        boolean encontrado = false;
        String query = "SELECT * FROM JUGADOR;";
        statement = conn.createStatement();
        rs = statement.executeQuery(query);
        // y recorremos lo obtenido
        while (rs.next() && !encontrado) {
            String nick = "" + rs.getString("NICK");
            String pass = "" + rs.getString("PASSWORD");
            name = "" + rs.getString("NOMBRE");
            if (insertedNick.equals(nick) && insertedPassword.equals(pass)) {
                encontrado = true;
                return name;
            }
        }
        rs.close();
        statement.close();

        return "";
    }

    /**
     * Método dinámico empleado para listar el top 10 de jugadores con mejor
     * puntuación, indicando que posicion de los 10 tops mostrar por parametro
     *
     * @param pos Variable de tipo integer usado para indicar que partida de las
     * 10 se quiere visualizar
     * @return Retorna una variable string con el string preparado para mostrar
     * por pantalla
     */
    public String listarTop10(int pos) {
        String query = "SELECT * FROM PARTIDA ORDER BY TIEMPO DESC;";
        String string = "";

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            int i = 0;
            // y recorremos lo obtenido  nomre nick puntuacion nivel disparos_Ac disparos_tot tiempo
            while (rs.next() && i < 10) {
                String nick = "" + rs.getString("NICK");
                String punt = "" + rs.getString("PUNTUACION");
                String disparos_ac = "" + rs.getString("DISPAROS_AC");
                String disparos_tot = "" + rs.getString("DISPAROS_TOT");
                
                int prec = (int) ((Float.parseFloat(disparos_ac) * 100) / Float.parseFloat(disparos_tot));
                int prec2 = (prec * 100) / 50;
                string = string = nick + "   " + "SCORE: " + punt + " " + "   ACCURACY: " + prec2 + "%";
                i++;
                if (pos == i) {
                    return string;
                }
            }
            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return string;
    }

    /**
     * Método estatico que busca la mejor puntuación de un usuario teniendo en
     * cuenta todas las partidas realizadas por dicho usuario
     *
     * @param nickname Párametro String que indica el nickname del jugador del
     * que se quiere buscar su mejor puntuación
     * @return Retorna un string con la mejor puntuación obtenida por el usuario
     * verificable por su nick
     */
    public String searchPosition(String nickname) {

        Statement statement;
        ResultSet rs;
        String string = "";

        try {

            String query = "SELECT * FROM PARTIDA;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);

            // y recorremos lo obtenido
            while (rs.next()) {
                if ((rs.getString("NICK")).equals(nickname)) {
                    if (string == "" || Integer.parseInt(string) < Integer.parseInt(rs.getString("PUNTUACION"))) {
                        string = rs.getString("PUNTUACION");
                    }
                }
            }

            rs.close();
            statement.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return string;
    }

    /**
     * Método empleado para mostrar el tanto porciento de aciertos de un usuario
     * teniendo en cuenta el numero de disparos totales y el de aciertos
     *
     * @throws Exception
     */
    public void mostrarDisparosTopPorciento() throws Exception {

        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT NICK, DISPAROS_AC, DISPAROS_TOT FROM TOP;";
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
            // y recorremos lo obtenido.
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

    }

    /**
     * Método estático empleado para agregar el usuario p player introducido
     * como parámetro en la base de datos
     *
     * @param jugador Variable Player correspondiente al usuario a introducir en
     * la base de datos
     * @throws Exception Devuelve una excepción en caso de SQLException,
     * ClasNotFoundException o usuario ya existente en la bd
     */
    public void agregarPerfil(Player jugador) throws Exception {

        PreparedStatement ps;
        String password = jugador.getPassword();
        String nick = jugador.getNick();
        String name = jugador.getNombre();

        ps = conn.prepareStatement("INSERT INTO JUGADOR VALUES (?, ?, ?)");
        ps.setString(1, nick);
        ps.setString(2, password);
        ps.setString(3, name);
        ps.executeUpdate();
        ps.close();


    }

    /**
     * Método estático empleado para agregar una partida en la base de datos
     * introduciéndola como parámetro y asociandola con un nick
     *
     * @param partida Variable de tipo Game correspondiente a la partida a
     * introducir en la base de datos
     */
    public void addGame(Game partida) {

        String nick = partida.getNick();
        Timestamp fecha_hora = partida.getFecha_hora();
        String fecha_horaString = fecha_hora.toString();
        String puntuacion = partida.getPuntuacion();
        String nivel = partida.getNivel();
        String disparos_ac = partida.getDisparos_ac();
        String disparos_tot = partida.getDisparos_tot();
        String muertes = partida.getMuertes();
        double tiempo = partida.getTiempo();

        try {

            ps = conn.prepareStatement("INSERT INTO PARTIDA VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, nick);
            ps.setString(2, fecha_horaString);
            ps.setString(3, puntuacion);
            ps.setString(4, nivel);
            ps.setString(5, disparos_ac);
            ps.setString(6, disparos_tot);
            ps.setString(7, muertes);
            ps.setDouble(8, tiempo);
            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que escribe en concola todas las partidas realizadas por todos los
     * usuario registrados en la bd local
     */
    public void listarEstadisticasPartidas() {


        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM PARTIDA;";
            Statement statement = conn.createStatement();
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
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lista las estadísitcas de las partidas por consola
     *
     * @param i Posición de la partida
     * @return Cadena de texto con los datos de la partida
     */
    public String listarEstadisticasPartidas(int i) {


        i++;
        int j = 0;
        String r = "";

        try {
            // Ahora utilizamos las sentencias de BD
            String query = "SELECT * FROM PARTIDA;";
            Statement statement = conn.createStatement();
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

                Game p = new Game(nick, punt, nivel, disparos_ac, disparos_tot, muertos, Double.parseDouble(tiempo));
                r = p.print();
                j++;
            }
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return r;
    }

    public static void main(String[] args) throws Exception {
        
        
        //BD EN FUNCIONAMIENTO
        getInstance().conectar();
        getInstance().listarEstadisticasPartidas();
        getInstance().desconectar();
    }
}
