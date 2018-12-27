package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.InteresadoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class InteresadoDAOMYSQL
{
	private static final String insert = "INSERT INTO interesado_table (persona, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM interesado_table WHERE id=?";
	private static final String readall = "SELECT * FROM interesado_table";
	private static final String update = "UPDATE interesado_table SET persona=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM interesado_table WHERE id=?";
	private static InteresadoDAOMYSQL instance;
	
	private InteresadoDAOMYSQL(){}
	
	public static InteresadoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new InteresadoDAOMYSQL();
		return instance;
	}
	
	private List<InteresadoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<InteresadoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			InteresadoDTO elem = new InteresadoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setPersona(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("persona")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(InteresadoDTO interesadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(interesadodto.getPersona()!=null)
				statement.setInt(1, interesadodto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, interesadodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				interesadodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(InteresadoDTO interesadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, interesadodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(InteresadoDTO interesadodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(interesadodto.getPersona()!=null)
				statement.setInt(1, interesadodto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, interesadodto.getDisponibleEnSistema());
			statement.setInt(3, interesadodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<InteresadoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InteresadoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<InteresadoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<InteresadoDTO> readByList(List<Integer> ids)
	{
		List<InteresadoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
