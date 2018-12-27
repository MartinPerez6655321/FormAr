package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.DiaDeLaSemanaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class DiaDeLaSemanaDAOMYSQL
{
	private static final String insert = "INSERT INTO diadelasemana_table (nombre, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM diadelasemana_table WHERE id=?";
	private static final String readall = "SELECT * FROM diadelasemana_table";
	private static final String update = "UPDATE diadelasemana_table SET nombre=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM diadelasemana_table WHERE id=?";
	private static DiaDeLaSemanaDAOMYSQL instance;
	
	private DiaDeLaSemanaDAOMYSQL(){}
	
	public static DiaDeLaSemanaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new DiaDeLaSemanaDAOMYSQL();
		return instance;
	}
	
	private List<DiaDeLaSemanaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<DiaDeLaSemanaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			DiaDeLaSemanaDTO elem = new DiaDeLaSemanaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(DiaDeLaSemanaDTO diadelasemanadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, diadelasemanadto.getNombre());
			statement.setBoolean(2, diadelasemanadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				diadelasemanadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(DiaDeLaSemanaDTO diadelasemanadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, diadelasemanadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(DiaDeLaSemanaDTO diadelasemanadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, diadelasemanadto.getNombre());
			statement.setBoolean(2, diadelasemanadto.getDisponibleEnSistema());
			statement.setInt(3, diadelasemanadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<DiaDeLaSemanaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public DiaDeLaSemanaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<DiaDeLaSemanaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<DiaDeLaSemanaDTO> readByList(List<Integer> ids)
	{
		List<DiaDeLaSemanaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
