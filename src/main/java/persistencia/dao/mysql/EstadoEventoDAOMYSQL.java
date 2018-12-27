package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;

import connections.ConnectionManager;
import dto.EstadoEventoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EstadoEventoDAOMYSQL
{
	private static final String insert = "INSERT INTO estadoevento_table (nombre, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM estadoevento_table WHERE id=?";
	private static final String readall = "SELECT * FROM estadoevento_table";
	private static final String update = "UPDATE estadoevento_table SET nombre=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM estadoevento_table WHERE id=?";
	private static EstadoEventoDAOMYSQL instance;
	
	private EstadoEventoDAOMYSQL(){}
	
	public static EstadoEventoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EstadoEventoDAOMYSQL();
		return instance;
	}
	
	private List<EstadoEventoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EstadoEventoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EstadoEventoDTO elem = new EstadoEventoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EstadoEventoDTO estadoeventodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, estadoeventodto.getNombre());
			statement.setBoolean(2, estadoeventodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				estadoeventodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EstadoEventoDTO estadoeventodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, estadoeventodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EstadoEventoDTO estadoeventodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, estadoeventodto.getNombre());
			statement.setBoolean(2, estadoeventodto.getDisponibleEnSistema());
			statement.setInt(3, estadoeventodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EstadoEventoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EstadoEventoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EstadoEventoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EstadoEventoDTO> readByList(List<Integer> ids)
	{
		List<EstadoEventoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
