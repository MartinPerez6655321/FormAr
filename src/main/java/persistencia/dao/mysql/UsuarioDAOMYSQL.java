package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;

import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.UsuarioDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class UsuarioDAOMYSQL
{
	private static final String insert = "INSERT INTO usuario_table (email, password, persona, administrador, supervisor, administrativo, instructor, alumno, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM usuario_table WHERE id=?";
	private static final String readall = "SELECT * FROM usuario_table";
	private static final String update = "UPDATE usuario_table SET email=?, password=?, persona=?, administrador=?, supervisor=?, administrativo=?, instructor=?, alumno=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM usuario_table WHERE id=?";
	private static UsuarioDAOMYSQL instance;
	
	private UsuarioDAOMYSQL(){}
	
	public static UsuarioDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new UsuarioDAOMYSQL();
		return instance;
	}
	
	private List<UsuarioDTO> query(PreparedStatement statement) throws SQLException
	{
		List<UsuarioDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			UsuarioDTO elem = new UsuarioDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setEmail(resultSet.getString("email"));
			elem.setPassword(resultSet.getString("password"));
			elem.setPersona(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("persona")));
			elem.setAdministrador(resultSet.getBoolean("administrador"));
			elem.setSupervisor(resultSet.getBoolean("supervisor"));
			elem.setAdministrativo(resultSet.getBoolean("administrativo"));
			elem.setInstructor(resultSet.getBoolean("instructor"));
			elem.setAlumno(resultSet.getBoolean("alumno"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(UsuarioDTO usuariodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, usuariodto.getEmail());
			statement.setString(2, usuariodto.getPassword());
			if(usuariodto.getPersona()!=null)
				statement.setInt(3, usuariodto.getPersona().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, usuariodto.getAdministrador());
			statement.setBoolean(5, usuariodto.getSupervisor());
			statement.setBoolean(6, usuariodto.getAdministrativo());
			statement.setBoolean(7, usuariodto.getInstructor());
			statement.setBoolean(8, usuariodto.getAlumno());
			statement.setBoolean(9, usuariodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				usuariodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(UsuarioDTO usuariodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, usuariodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(UsuarioDTO usuariodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, usuariodto.getEmail());
			statement.setString(2, usuariodto.getPassword());
			if(usuariodto.getPersona()!=null)
				statement.setInt(3, usuariodto.getPersona().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, usuariodto.getAdministrador());
			statement.setBoolean(5, usuariodto.getSupervisor());
			statement.setBoolean(6, usuariodto.getAdministrativo());
			statement.setBoolean(7, usuariodto.getInstructor());
			statement.setBoolean(8, usuariodto.getAlumno());
			statement.setBoolean(9, usuariodto.getDisponibleEnSistema());
			statement.setInt(10, usuariodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<UsuarioDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UsuarioDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<UsuarioDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<UsuarioDTO> readByList(List<Integer> ids)
	{
		List<UsuarioDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
