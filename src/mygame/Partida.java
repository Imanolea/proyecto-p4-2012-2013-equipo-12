
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * 
 * @author Team 12
 *
 */
public class Partida {
	
	private String cod_u;
	private Timestamp fecha_hora;
	private String puntuacion;
	private String nivel;
	private String ranking;
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

	public Partida(String cod_u, String puntuacion, String nivel, String ranking, 
	String disparos_ac, String disparos_tot, String muertes, double tiempo){
		this.cod_u = cod_u;
		//Timestamp ts = new Timestamp(System.currentTimeMillis());
		//SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss:SS aaa");
		//String fecha_hora = formatter.format(ts);
		java.util.Date utilDate = new java.util.Date(); //fecha actual
		long lnMilisegundos = utilDate.getTime();

		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(lnMilisegundos);

		this.fecha_hora = sqlTimestamp;
		this.puntuacion = puntuacion;
		this.nivel = nivel;
		this.ranking = ranking;
		this.disparos_ac = disparos_ac;
		this.disparos_tot = disparos_tot;
		this.muertes = muertes;
		this.tiempo = tiempo;
	}
	
	public Partida(String cod_u, String puntuacion, String nivel, String ranking, 
			String disparos_ac, String disparos_tot, String muertes, double tiempo,String fecha) {
		
		this.cod_u = cod_u;
		this.fecha_h = fecha;
		this.puntuacion = puntuacion;
		this.nivel = nivel;
		this.ranking = ranking;
		this.disparos_ac = disparos_ac;
		this.disparos_tot = disparos_tot;
		this.muertes = muertes;
		this.tiempo = tiempo;
	}

	public String getCod_u() {
		return cod_u;
	}

	public void setCod_u(String cod_u) {
		this.cod_u = cod_u;
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

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
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

	public static void main(String[] args){
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		String g = formatter.format(ts);		
		System.out.println(g);
	}

}
