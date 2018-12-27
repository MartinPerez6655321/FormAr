package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.AdministradorDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AdministradorDAOMYSQL
{
	private static final String insert = "INSERT INTO administrador_table (persona, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM administrador_table WHERE id=?";
	private static final String readall = "SELECT * FROM administrador_table";
	private static final String update = "UPDATE administrador_table SET persona=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM administrador_table WHERE id=?";
	private static AdministradorDAOMYSQL instance;
	
	private AdministradorDAOMYSQL(){}
	
	public static AdministradorDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new AdministradorDAOMYSQL();
		return instance;
	}
	
	private List<AdministradorDTO> query(PreparedStatement statement) throws SQLException
	{
		List<AdministradorDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			AdministradorDTO elem = new AdministradorDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setPersona(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("persona")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(AdministradorDTO administradordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(administradordto.getPersona()!=null)
				statement.setInt(1, administradordto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, administradordto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				administradordto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(AdministradorDTO administradordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, administradordto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(AdministradorDTO administradordto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(administradordto.getPersona()!=null)
				statement.setInt(1, administradordto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, administradordto.getDisponibleEnSistema());
			statement.setInt(3, administradordto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<AdministradorDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AdministradorDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<AdministradorDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AdministradorDTO> readByList(List<Integer> ids)
	{
		List<AdministradorDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
