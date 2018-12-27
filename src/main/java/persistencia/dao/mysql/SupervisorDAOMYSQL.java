package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.SupervisorDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SupervisorDAOMYSQL
{
	private static final String insert = "INSERT INTO supervisor_table (persona, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM supervisor_table WHERE id=?";
	private static final String readall = "SELECT * FROM supervisor_table";
	private static final String update = "UPDATE supervisor_table SET persona=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM supervisor_table WHERE id=?";
	private static SupervisorDAOMYSQL instance;
	
	private SupervisorDAOMYSQL(){}
	
	public static SupervisorDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new SupervisorDAOMYSQL();
		return instance;
	}
	
	private List<SupervisorDTO> query(PreparedStatement statement) throws SQLException
	{
		List<SupervisorDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			SupervisorDTO elem = new SupervisorDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setPersona(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("persona")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(SupervisorDTO supervisordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(supervisordto.getPersona()!=null)
				statement.setInt(1, supervisordto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, supervisordto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				supervisordto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(SupervisorDTO supervisordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, supervisordto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(SupervisorDTO supervisordto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(supervisordto.getPersona()!=null)
				statement.setInt(1, supervisordto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, supervisordto.getDisponibleEnSistema());
			statement.setInt(3, supervisordto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<SupervisorDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SupervisorDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<SupervisorDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SupervisorDTO> readByList(List<Integer> ids)
	{
		List<SupervisorDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
