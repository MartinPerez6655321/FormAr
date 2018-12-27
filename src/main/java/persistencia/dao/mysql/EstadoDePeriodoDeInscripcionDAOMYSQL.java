package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;

import connections.ConnectionManager;
import dto.EstadoDePeriodoDeInscripcionDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EstadoDePeriodoDeInscripcionDAOMYSQL
{
	private static final String insert = "INSERT INTO estadodeperiododeinscripcion_table (nombre, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM estadodeperiododeinscripcion_table WHERE id=?";
	private static final String readall = "SELECT * FROM estadodeperiododeinscripcion_table";
	private static final String update = "UPDATE estadodeperiododeinscripcion_table SET nombre=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM estadodeperiododeinscripcion_table WHERE id=?";
	private static EstadoDePeriodoDeInscripcionDAOMYSQL instance;
	
	private EstadoDePeriodoDeInscripcionDAOMYSQL(){}
	
	public static EstadoDePeriodoDeInscripcionDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EstadoDePeriodoDeInscripcionDAOMYSQL();
		return instance;
	}
	
	private List<EstadoDePeriodoDeInscripcionDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EstadoDePeriodoDeInscripcionDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EstadoDePeriodoDeInscripcionDTO elem = new EstadoDePeriodoDeInscripcionDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EstadoDePeriodoDeInscripcionDTO estadodeperiododeinscripciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, estadodeperiododeinscripciondto.getNombre());
			statement.setBoolean(2, estadodeperiododeinscripciondto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				estadodeperiododeinscripciondto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EstadoDePeriodoDeInscripcionDTO estadodeperiododeinscripciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, estadodeperiododeinscripciondto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EstadoDePeriodoDeInscripcionDTO estadodeperiododeinscripciondto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, estadodeperiododeinscripciondto.getNombre());
			statement.setBoolean(2, estadodeperiododeinscripciondto.getDisponibleEnSistema());
			statement.setInt(3, estadodeperiododeinscripciondto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EstadoDePeriodoDeInscripcionDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EstadoDePeriodoDeInscripcionDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EstadoDePeriodoDeInscripcionDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EstadoDePeriodoDeInscripcionDTO> readByList(List<Integer> ids)
	{
		List<EstadoDePeriodoDeInscripcionDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
