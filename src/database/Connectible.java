package database;

/**
 * Interfaz que gestiona la conexión y la desconexión de la base de datos
 * @author Team 12
 */

public interface Connectible {
    
    /**
     * Conecta a la base de datos
     * @throws Exception En caso de no conseguir conectarse
     */

	void conectar()  throws Exception;
    
    /**
     * Desconecta de la base de datos
     */
	void desconectar() throws Exception;

}
