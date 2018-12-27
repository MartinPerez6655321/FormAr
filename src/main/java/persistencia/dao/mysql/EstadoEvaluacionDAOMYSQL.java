package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.EstadoEvaluacionDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EstadoEvaluacionDAOMYSQL
{
	private static final String insert = "INSERT INTO estadoevaluacion_table (nombre, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM estadoevaluacion_table WHERE id=?";
	private static final String readall = "SELECT * FROM estadoevaluacion_table";
	private static final String update = "UPDATE estadoevaluacion_table SET nombre=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM estadoevaluacion_table WHERE id=?";
	private static EstadoEvaluacionDAOMYSQL instance;
	
	private EstadoEvaluacionDAOMYSQL(){}
	
	public static EstadoEvaluacionDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EstadoEvaluacionDAOMYSQL();
		return instance;
	}
	
	private List<EstadoEvaluacionDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EstadoEvaluacionDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EstadoEvaluacionDTO elem = new EstadoEvaluacionDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EstadoEvaluacionDTO estadoevaluaciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, estadoevaluaciondto.getNombre());
			statement.setBoolean(2, estadoevaluaciondto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				estadoevaluaciondto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EstadoEvaluacionDTO estadoevaluaciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, estadoevaluaciondto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EstadoEvaluacionDTO estadoevaluaciondto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, estadoevaluaciondto.getNombre());
			statement.setBoolean(2, estadoevaluaciondto.getDisponibleEnSistema());
			statement.setInt(3, estadoevaluaciondto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EstadoEvaluacionDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EstadoEvaluacionDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EstadoEvaluacionDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EstadoEvaluacionDTO> readByList(List<Integer> ids)
	{
		List<EstadoEvaluacionDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
