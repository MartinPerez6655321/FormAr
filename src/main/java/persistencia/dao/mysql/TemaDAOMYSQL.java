package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.TemaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TemaDAOMYSQL
{
	private static final String insert = "INSERT INTO tema_table (nombre, descripcion, disponibleEnSistema) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM tema_table WHERE id=?";
	private static final String readall = "SELECT * FROM tema_table";
	private static final String update = "UPDATE tema_table SET nombre=?, descripcion=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM tema_table WHERE id=?";
	private static TemaDAOMYSQL instance;
	
	private TemaDAOMYSQL(){}
	
	public static TemaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new TemaDAOMYSQL();
		return instance;
	}
	
	private List<TemaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<TemaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			TemaDTO elem = new TemaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setDescripcion(resultSet.getString("descripcion"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(TemaDTO temadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, temadto.getNombre());
			statement.setString(2, temadto.getDescripcion());
			statement.setBoolean(3, temadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				temadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(TemaDTO temadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, temadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(TemaDTO temadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, temadto.getNombre());
			statement.setString(2, temadto.getDescripcion());
			statement.setBoolean(3, temadto.getDisponibleEnSistema());
			statement.setInt(4, temadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<TemaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TemaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<TemaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<TemaDTO> readByList(List<Integer> ids)
	{
		List<TemaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
