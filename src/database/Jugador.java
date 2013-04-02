package database;


public class Jugador {
	
	private String cod_u;
	private String nick;
	private String nombre;
	
	public Jugador(String c, String ni, String no){
		cod_u = c;
		nick = ni;
		nombre = no;
	}

	public String getCod_u() {
		return cod_u;
	}

	public void setCod_u(String cod_u) {
		this.cod_u = cod_u;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
