package database;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 *
 * @author Team 12
 *
 */
public class Game {

    private String nick;
    private Timestamp fecha_hora;
    private String puntuacion;
    private String nivel;
    private String disparos_ac;
    private String disparos_tot;
    private String muertes;
    private double tiempo;
    private String fecha_h;

    public String getFecha_h() {
        return fecha_h;
    }

    public void setFecha_h(String fecha_h) {
        this.fecha_h = fecha_h;
    }

    public Game(String nick, String puntuacion, String nivel,
            String disparos_ac, String disparos_tot, String muertes, double tiempo) {
        this.nick = nick;
        //Timestamp ts = new Timestamp(System.currentTimeMillis());
        //SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SS aaa");
        //String fecha_hora = formatter.format(ts);
        java.util.Date utilDate = new java.util.Date(); //fecha actual
        long lnMilisegundos = utilDate.getTime();

        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);
        
        this.nick = nick;
        this.fecha_hora = sqlTimestamp;
        this.puntuacion = puntuacion;
        this.nivel = nivel;
        this.disparos_ac = disparos_ac;
        this.disparos_tot = disparos_tot;
        this.muertes = muertes;
        this.tiempo = tiempo;
    }

    public Game(String nick, String fecha, String puntuacion, String nivel,
            String disparos_ac, String disparos_tot, String muertes, double tiempo) {

        this.nick = nick;
        this.fecha_h = fecha;
        this.puntuacion = puntuacion;
        this.nivel = nivel;
        this.disparos_ac = disparos_ac;
        this.disparos_tot = disparos_tot;
        this.muertes = muertes;
        this.tiempo = tiempo;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Timestamp fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public String getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(String puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getDisparos_ac() {
        return disparos_ac;
    }

    public void setDisparos_ac(String disparos_ac) {
        this.disparos_ac = disparos_ac;
    }

    public String getDisparos_tot() {
        return disparos_tot;
    }

    public void setDisparos_tot(String disparos_tot) {
        this.disparos_tot = disparos_tot;
    }

    public String getMuertes() {
        return muertes;
    }

    public void setMuertes(String muertes) {
        this.muertes = muertes;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }
    
    /*
    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String pass) {
        this.contraseña = pass;
    }
     */
    public static void main(String[] args) {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String g = formatter.format(ts);
        System.out.println(g);
    }

    public String print() {
        return ("NICK: " + this.getNick() + "; PUNTUACION: " + this.getPuntuacion());
    }
}
