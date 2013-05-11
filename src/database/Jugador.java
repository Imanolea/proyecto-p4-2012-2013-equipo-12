package database;


public class Jugador {
	
	private String password;
	private String nick;
	private String nombre;
	
	public Jugador(String ni, String c, String no){
		password = c;
		nick = ni;
		nombre = no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String cod_u) {
		this.password = cod_u;
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
        
        public void printJugador(Jugador j){
            System.out.println("PASSWORD: "+j.getPassword());
            System.out.println("NAME: "+j.getNombre());
            System.out.println("NICK: "+j.getNick());
        }
	
}
