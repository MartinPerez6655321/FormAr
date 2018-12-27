package connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager 
{
	private static ConnectionManager instancia;
	private DBProperties dbprops = DBProperties.getInstance();
	private Connection connection;
	
	private ConnectionManager()
	{
		while(connection == null)
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
				String connectionString =  
						"jdbc:mysql:"
						+ "//" + 
						dbprops.get("db_ip") + 
						":" + 
						dbprops.get("db_puerto") + 
						"/" + 
						dbprops.get("db_nombre");
				connection = DriverManager.getConnection(connectionString, dbprops.get("db_usuario"), dbprops.get("db_password"));
			} catch(Exception e) {
				e.printStackTrace();
				dbprops.intentarDeNuevo();
			}
	}
	
	public void manageConnectionParameters()
	{
		dbprops.intentarDeNuevo();
	}
	
	public static ConnectionManager getConnectionManager()
	{								
		if(instancia == null)
			instancia = new ConnectionManager();
		return instancia;
	}

	public Connection getSQLConnection() 
	{
		return this.connection;
	}
	
	public static void closeConnectionManager()
	{
		try 
		{
			instancia.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		instancia = null;
	}

	public String getBD()
	{
		return dbprops.get("db_nombre");
	}

	public String getUsuario()
	{
		return dbprops.get("db_usuario");
	}

	public String getPassword()
	{
		return dbprops.get("db_password");
	}
}
