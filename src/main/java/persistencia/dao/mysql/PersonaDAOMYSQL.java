package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.PersonaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PersonaDAOMYSQL
{
	private static final String insert = "INSERT INTO persona_table (nombre, apellido, telefono, email, dni, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM persona_table WHERE id=?";
	private static final String readall = "SELECT * FROM persona_table";
	private static final String update = "UPDATE persona_table SET nombre=?, apellido=?, telefono=?, email=?, dni=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM persona_table WHERE id=?";
	private static PersonaDAOMYSQL instance;
	
	private PersonaDAOMYSQL(){}
	
	public static PersonaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new PersonaDAOMYSQL();
		return instance;
	}
	
	private List<PersonaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<PersonaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			PersonaDTO elem = new PersonaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setApellido(resultSet.getString("apellido"));
			elem.setTelefono(resultSet.getString("telefono"));
			elem.setEmail(resultSet.getString("email"));
			elem.setDni(resultSet.getString("dni"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(PersonaDTO personadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, personadto.getNombre());
			statement.setString(2, personadto.getApellido());
			statement.setString(3, personadto.getTelefono());
			statement.setString(4, personadto.getEmail());
			statement.setString(5, personadto.getDni());
			statement.setBoolean(6, personadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				personadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PersonaDTO personadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, personadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(PersonaDTO personadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, personadto.getNombre());
			statement.setString(2, personadto.getApellido());
			statement.setString(3, personadto.getTelefono());
			statement.setString(4, personadto.getEmail());
			statement.setString(5, personadto.getDni());
			statement.setBoolean(6, personadto.getDisponibleEnSistema());
			statement.setInt(7, personadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<PersonaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PersonaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<PersonaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PersonaDTO> readByList(List<Integer> ids)
	{
		List<PersonaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
