package database;

/**
 * Tipo de entidad que almacena los datos relacionados con el jugador
 * @author Team 12
 */
public class Player {
	
	private String password;
	private String nick;
	private String nombre;
	
	public Player(String password,String nick,String nombre)
        {
            this.password=password;
            this.nick=nick;
            this.nombre=nombre;
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
        
        public void printJugador(Player j){
            System.out.println("PASSWORD: "+j.getPassword());
            System.out.println("NAME: "+j.getNombre());
            System.out.println("NICK: "+j.getNick());
        }
	
}
