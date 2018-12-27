package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;

import connections.ConnectionManager;
import dto.CuotaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CuotaDAOMYSQL
{
	private static final String insert = "INSERT INTO cuota_table (fechaLimite, monto, disponibleEnSistema) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM cuota_table WHERE id=?";
	private static final String readall = "SELECT * FROM cuota_table";
	private static final String update = "UPDATE cuota_table SET fechaLimite=?, monto=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM cuota_table WHERE id=?";
	private static CuotaDAOMYSQL instance;
	
	private CuotaDAOMYSQL(){}
	
	public static CuotaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new CuotaDAOMYSQL();
		return instance;
	}
	
	private List<CuotaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<CuotaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			CuotaDTO elem = new CuotaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFechaLimite(resultSet.getDate("fechaLimite"));
			elem.setMonto(resultSet.getInt("monto"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(CuotaDTO cuotadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(cuotadto.getFechaLimite().getTime()));
			statement.setInt(2, cuotadto.getMonto());
			statement.setBoolean(3, cuotadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				cuotadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(CuotaDTO cuotadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, cuotadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(CuotaDTO cuotadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(cuotadto.getFechaLimite().getTime()));
			statement.setInt(2, cuotadto.getMonto());
			statement.setBoolean(3, cuotadto.getDisponibleEnSistema());
			statement.setInt(4, cuotadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<CuotaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CuotaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<CuotaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CuotaDTO> readByList(List<Integer> ids)
	{
		List<CuotaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
