package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.InscripcionAlumnoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class InscripcionAlumnoDAOMYSQL
{
	private static final String insert = "INSERT INTO inscripcionalumno_table (fecha, alumno, disponibleEnSistema) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM inscripcionalumno_table WHERE id=?";
	private static final String readall = "SELECT * FROM inscripcionalumno_table";
	private static final String update = "UPDATE inscripcionalumno_table SET fecha=?, alumno=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM inscripcionalumno_table WHERE id=?";
	private static InscripcionAlumnoDAOMYSQL instance;
	
	private InscripcionAlumnoDAOMYSQL(){}
	
	public static InscripcionAlumnoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new InscripcionAlumnoDAOMYSQL();
		return instance;
	}
	
	private List<InscripcionAlumnoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<InscripcionAlumnoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			InscripcionAlumnoDTO elem = new InscripcionAlumnoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFecha(resultSet.getDate("fecha"));
			elem.setAlumno(AlumnoDAOMYSQL.getInstance().readById(resultSet.getInt("alumno")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(InscripcionAlumnoDTO inscripcionalumnodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(inscripcionalumnodto.getFecha().getTime()));
			if(inscripcionalumnodto.getAlumno()!=null)
				statement.setInt(2, inscripcionalumnodto.getAlumno().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, inscripcionalumnodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				inscripcionalumnodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(InscripcionAlumnoDTO inscripcionalumnodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, inscripcionalumnodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(InscripcionAlumnoDTO inscripcionalumnodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(inscripcionalumnodto.getFecha().getTime()));
			if(inscripcionalumnodto.getAlumno()!=null)
				statement.setInt(2, inscripcionalumnodto.getAlumno().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, inscripcionalumnodto.getDisponibleEnSistema());
			statement.setInt(4, inscripcionalumnodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<InscripcionAlumnoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InscripcionAlumnoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<InscripcionAlumnoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<InscripcionAlumnoDTO> readByList(List<Integer> ids)
	{
		List<InscripcionAlumnoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
