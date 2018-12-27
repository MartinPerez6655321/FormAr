package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;

import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.EstadoCursadaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EstadoCursadaDAOMYSQL
{
	private static final String insert = "INSERT INTO estadocursada_table (nombre, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM estadocursada_table WHERE id=?";
	private static final String readall = "SELECT * FROM estadocursada_table";
	private static final String update = "UPDATE estadocursada_table SET nombre=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM estadocursada_table WHERE id=?";
	private static EstadoCursadaDAOMYSQL instance;
	
	private EstadoCursadaDAOMYSQL(){}
	
	public static EstadoCursadaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EstadoCursadaDAOMYSQL();
		return instance;
	}
	
	private List<EstadoCursadaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EstadoCursadaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EstadoCursadaDTO elem = new EstadoCursadaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EstadoCursadaDTO estadocursadadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, estadocursadadto.getNombre());
			statement.setBoolean(2, estadocursadadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				estadocursadadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EstadoCursadaDTO estadocursadadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, estadocursadadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EstadoCursadaDTO estadocursadadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, estadocursadadto.getNombre());
			statement.setBoolean(2, estadocursadadto.getDisponibleEnSistema());
			statement.setInt(3, estadocursadadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EstadoCursadaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EstadoCursadaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EstadoCursadaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EstadoCursadaDTO> readByList(List<Integer> ids)
	{
		List<EstadoCursadaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
