package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.EstadoDeRecadoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EstadoDeRecadoDAOMYSQL
{
	private static final String insert = "INSERT INTO estadoderecado_table (nombre, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM estadoderecado_table WHERE id=?";
	private static final String readall = "SELECT * FROM estadoderecado_table";
	private static final String update = "UPDATE estadoderecado_table SET nombre=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM estadoderecado_table WHERE id=?";
	private static EstadoDeRecadoDAOMYSQL instance;
	
	private EstadoDeRecadoDAOMYSQL(){}
	
	public static EstadoDeRecadoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EstadoDeRecadoDAOMYSQL();
		return instance;
	}
	
	private List<EstadoDeRecadoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EstadoDeRecadoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EstadoDeRecadoDTO elem = new EstadoDeRecadoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EstadoDeRecadoDTO estadoderecadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, estadoderecadodto.getNombre());
			statement.setBoolean(2, estadoderecadodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				estadoderecadodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EstadoDeRecadoDTO estadoderecadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, estadoderecadodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EstadoDeRecadoDTO estadoderecadodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, estadoderecadodto.getNombre());
			statement.setBoolean(2, estadoderecadodto.getDisponibleEnSistema());
			statement.setInt(3, estadoderecadodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EstadoDeRecadoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EstadoDeRecadoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EstadoDeRecadoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EstadoDeRecadoDTO> readByList(List<Integer> ids)
	{
		List<EstadoDeRecadoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
