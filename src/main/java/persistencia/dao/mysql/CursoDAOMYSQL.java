package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.CursoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CursoDAOMYSQL
{
	private static final String insert = "INSERT INTO curso_table (nombre, precio, codigo, descripcion, horas, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM curso_table WHERE id=?";
	private static final String readall = "SELECT * FROM curso_table";
	private static final String update = "UPDATE curso_table SET nombre=?, precio=?, codigo=?, descripcion=?, horas=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM curso_table WHERE id=?";
	private static CursoDAOMYSQL instance;
	
	private CursoDAOMYSQL(){}
	
	public static CursoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new CursoDAOMYSQL();
		return instance;
	}
	
	private List<CursoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<CursoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			CursoDTO elem = new CursoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setPrecio(resultSet.getInt("precio"));
			elem.setCodigo(resultSet.getString("codigo"));
			elem.setDescripcion(resultSet.getString("descripcion"));
			elem.setHoras(resultSet.getInt("horas"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(CursoDTO cursodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, cursodto.getNombre());
			statement.setInt(2, cursodto.getPrecio());
			statement.setString(3, cursodto.getCodigo());
			statement.setString(4, cursodto.getDescripcion());
			statement.setInt(5, cursodto.getHoras());
			statement.setBoolean(6, cursodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				cursodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(CursoDTO cursodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, cursodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(CursoDTO cursodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, cursodto.getNombre());
			statement.setInt(2, cursodto.getPrecio());
			statement.setString(3, cursodto.getCodigo());
			statement.setString(4, cursodto.getDescripcion());
			statement.setInt(5, cursodto.getHoras());
			statement.setBoolean(6, cursodto.getDisponibleEnSistema());
			statement.setInt(7, cursodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<CursoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CursoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<CursoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CursoDTO> readByList(List<Integer> ids)
	{
		List<CursoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
