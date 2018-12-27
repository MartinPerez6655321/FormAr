package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.AlumnoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class AlumnoDAOMYSQL
{
	private static final String insert = "INSERT INTO alumno_table (fechaDeCreacion, legajo, persona, disponibleEnSistema) VALUES(?, ?, ?, ?)";
	private static final String delete = "DELETE FROM alumno_table WHERE id=?";
	private static final String readall = "SELECT * FROM alumno_table";
	private static final String update = "UPDATE alumno_table SET fechaDeCreacion=?, legajo=?, persona=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM alumno_table WHERE id=?";
	private static AlumnoDAOMYSQL instance;
	
	private AlumnoDAOMYSQL(){}
	
	public static AlumnoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new AlumnoDAOMYSQL();
		return instance;
	}
	
	private List<AlumnoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<AlumnoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			AlumnoDTO elem = new AlumnoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFechaDeCreacion(resultSet.getDate("fechaDeCreacion"));
			elem.setLegajo(resultSet.getString("legajo"));
			elem.setPersona(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("persona")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(AlumnoDTO alumnodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(alumnodto.getFechaDeCreacion().getTime()));
			statement.setString(2, alumnodto.getLegajo());
			if(alumnodto.getPersona()!=null)
				statement.setInt(3, alumnodto.getPersona().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, alumnodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				alumnodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(AlumnoDTO alumnodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, alumnodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(AlumnoDTO alumnodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(alumnodto.getFechaDeCreacion().getTime()));
			statement.setString(2, alumnodto.getLegajo());
			if(alumnodto.getPersona()!=null)
				statement.setInt(3, alumnodto.getPersona().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, alumnodto.getDisponibleEnSistema());
			statement.setInt(5, alumnodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<AlumnoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AlumnoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<AlumnoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<AlumnoDTO> readByList(List<Integer> ids)
	{
		List<AlumnoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
