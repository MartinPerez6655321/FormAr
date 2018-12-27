package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;

import connections.ConnectionManager;
import dto.EstadoAsistenciaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EstadoAsistenciaDAOMYSQL
{
	private static final String insert = "INSERT INTO estadoasistencia_table (nombre, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM estadoasistencia_table WHERE id=?";
	private static final String readall = "SELECT * FROM estadoasistencia_table";
	private static final String update = "UPDATE estadoasistencia_table SET nombre=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM estadoasistencia_table WHERE id=?";
	private static EstadoAsistenciaDAOMYSQL instance;
	
	private EstadoAsistenciaDAOMYSQL(){}
	
	public static EstadoAsistenciaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EstadoAsistenciaDAOMYSQL();
		return instance;
	}
	
	private List<EstadoAsistenciaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EstadoAsistenciaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EstadoAsistenciaDTO elem = new EstadoAsistenciaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EstadoAsistenciaDTO estadoasistenciadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, estadoasistenciadto.getNombre());
			statement.setBoolean(2, estadoasistenciadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				estadoasistenciadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EstadoAsistenciaDTO estadoasistenciadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, estadoasistenciadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EstadoAsistenciaDTO estadoasistenciadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, estadoasistenciadto.getNombre());
			statement.setBoolean(2, estadoasistenciadto.getDisponibleEnSistema());
			statement.setInt(3, estadoasistenciadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EstadoAsistenciaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EstadoAsistenciaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EstadoAsistenciaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EstadoAsistenciaDTO> readByList(List<Integer> ids)
	{
		List<EstadoAsistenciaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
