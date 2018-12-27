package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.AsistenciaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AsistenciaDAOMYSQL
{
	private static final String insert = "INSERT INTO asistencia_table (alumno, estado, disponibleEnSistema) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM asistencia_table WHERE id=?";
	private static final String readall = "SELECT * FROM asistencia_table";
	private static final String update = "UPDATE asistencia_table SET alumno=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM asistencia_table WHERE id=?";
	private static AsistenciaDAOMYSQL instance;
	
	private AsistenciaDAOMYSQL(){}
	
	public static AsistenciaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new AsistenciaDAOMYSQL();
		return instance;
	}
	
	private List<AsistenciaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<AsistenciaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			AsistenciaDTO elem = new AsistenciaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setAlumno(AlumnoDAOMYSQL.getInstance().readById(resultSet.getInt("alumno")));
			elem.setEstado(EstadoAsistenciaDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(AsistenciaDTO asistenciadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(asistenciadto.getAlumno()!=null)
				statement.setInt(1, asistenciadto.getAlumno().getId());
			else
				statement.setObject(1, null);
			if(asistenciadto.getEstado()!=null)
				statement.setInt(2, asistenciadto.getEstado().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, asistenciadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				asistenciadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(AsistenciaDTO asistenciadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, asistenciadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(AsistenciaDTO asistenciadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(asistenciadto.getAlumno()!=null)
				statement.setInt(1, asistenciadto.getAlumno().getId());
			else
				statement.setObject(1, null);
			if(asistenciadto.getEstado()!=null)
				statement.setInt(2, asistenciadto.getEstado().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, asistenciadto.getDisponibleEnSistema());
			statement.setInt(4, asistenciadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<AsistenciaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AsistenciaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<AsistenciaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AsistenciaDTO> readByList(List<Integer> ids)
	{
		List<AsistenciaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
