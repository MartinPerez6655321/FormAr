package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.PersonalAdministrativoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PersonalAdministrativoDAOMYSQL
{
	private static final String insert = "INSERT INTO personaladministrativo_table (persona, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM personaladministrativo_table WHERE id=?";
	private static final String readall = "SELECT * FROM personaladministrativo_table";
	private static final String update = "UPDATE personaladministrativo_table SET persona=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM personaladministrativo_table WHERE id=?";
	private static PersonalAdministrativoDAOMYSQL instance;
	
	private PersonalAdministrativoDAOMYSQL(){}
	
	public static PersonalAdministrativoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new PersonalAdministrativoDAOMYSQL();
		return instance;
	}
	
	private List<PersonalAdministrativoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<PersonalAdministrativoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			PersonalAdministrativoDTO elem = new PersonalAdministrativoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setPersona(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("persona")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(PersonalAdministrativoDTO personaladministrativodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(personaladministrativodto.getPersona()!=null)
				statement.setInt(1, personaladministrativodto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, personaladministrativodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				personaladministrativodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PersonalAdministrativoDTO personaladministrativodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, personaladministrativodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(PersonalAdministrativoDTO personaladministrativodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(personaladministrativodto.getPersona()!=null)
				statement.setInt(1, personaladministrativodto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, personaladministrativodto.getDisponibleEnSistema());
			statement.setInt(3, personaladministrativodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<PersonalAdministrativoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PersonalAdministrativoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<PersonalAdministrativoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PersonalAdministrativoDTO> readByList(List<Integer> ids)
	{
		List<PersonalAdministrativoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
