package Integracion.ConfiguracionBD;

public final class ConfgBD {
	// Librería de MySQL
	@SuppressWarnings("unused")
	private String driver = "com.mysql.jdbc.Driver";
	// Nombre de la base de datos
	@SuppressWarnings("unused")
	private static String database = "bdvideojuegos";
	// Host
	@SuppressWarnings("unused")
	private static String hostname = "127.0.0.1";
	// Puerto
	@SuppressWarnings("unused")
	private static String port = "3306";
//	// Ruta de nuestra base de datos con los parámetros necesarios
//	 public static final String URL = "jdbc:mysql://" + hostname + ":" + port +
//	 "/" + database + "?useSSL=false&allowPublicKeyRetrieval=true";
//	// Nombre de usuario
//	 public static final String USER = "root";
//	// Clave de usuario
//	 public static final String PASSWORD = "";

	// Conexión remota a base de datos alojada en servidor
	public static final String URL = "jdbc:mysql://95.216.10.148:3306/bdvideojuegos?useSSL=false&allowPublicKeyRetrieval=true";
	public static final String USER = "videojuegos";
	public static final String PASSWORD = "videojuegos123";
}