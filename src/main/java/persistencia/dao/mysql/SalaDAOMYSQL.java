package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.SalaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SalaDAOMYSQL
{
	private static final String insert = "INSERT INTO sala_table (alias, codigo, capacidad, disponibleEnSistema) VALUES(?, ?, ?, ?)";
	private static final String delete = "DELETE FROM sala_table WHERE id=?";
	private static final String readall = "SELECT * FROM sala_table";
	private static final String update = "UPDATE sala_table SET alias=?, codigo=?, capacidad=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM sala_table WHERE id=?";
	private static SalaDAOMYSQL instance;
	
	private SalaDAOMYSQL(){}
	
	public static SalaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new SalaDAOMYSQL();
		return instance;
	}
	
	private List<SalaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<SalaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			SalaDTO elem = new SalaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setAlias(resultSet.getString("alias"));
			elem.setCodigo(resultSet.getString("codigo"));
			elem.setCapacidad(resultSet.getInt("capacidad"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(SalaDTO saladto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, saladto.getAlias());
			statement.setString(2, saladto.getCodigo());
			statement.setInt(3, saladto.getCapacidad());
			statement.setBoolean(4, saladto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				saladto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(SalaDTO saladto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, saladto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(SalaDTO saladto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, saladto.getAlias());
			statement.setString(2, saladto.getCodigo());
			statement.setInt(3, saladto.getCapacidad());
			statement.setBoolean(4, saladto.getDisponibleEnSistema());
			statement.setInt(5, saladto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<SalaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public SalaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<SalaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<SalaDTO> readByList(List<Integer> ids)
	{
		List<SalaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
