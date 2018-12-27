package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.PagoEmpresaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PagoEmpresaDAOMYSQL
{
	private static final String insert = "INSERT INTO pagoempresa_table (fechaLimite, realizado, monto, empresa, disponibleEnSistema) VALUES(?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM pagoempresa_table WHERE id=?";
	private static final String readall = "SELECT * FROM pagoempresa_table";
	private static final String update = "UPDATE pagoempresa_table SET fechaLimite=?, realizado=?, monto=?, empresa=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM pagoempresa_table WHERE id=?";
	private static PagoEmpresaDAOMYSQL instance;
	
	private PagoEmpresaDAOMYSQL(){}
	
	public static PagoEmpresaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new PagoEmpresaDAOMYSQL();
		return instance;
	}
	
	private List<PagoEmpresaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<PagoEmpresaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			PagoEmpresaDTO elem = new PagoEmpresaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFechaLimite(resultSet.getDate("fechaLimite"));
			elem.setRealizado(resultSet.getDate("realizado"));
			elem.setMonto(resultSet.getInt("monto"));
			elem.setEmpresa(resultSet.getInt("empresa"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(PagoEmpresaDTO pagoempresadto)
	{
		try
		{
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(pagoempresadto.getFechaLimite().getTime()));
			statement.setDate(2, new java.sql.Date(pagoempresadto.getRealizado().getTime()));
			statement.setInt(3, pagoempresadto.getMonto());
			
			statement.setInt(4, pagoempresadto.getEmpresa());
			statement.setBoolean(5, pagoempresadto.getDisponibleEnSistema());
			
			if(statement.executeUpdate() > 0)
			{
				
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				pagoempresadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) 
		{
			
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PagoEmpresaDTO pagoempresadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, pagoempresadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(PagoEmpresaDTO pagoempresadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(pagoempresadto.getFechaLimite().getTime()));
			statement.setDate(2, new java.sql.Date(pagoempresadto.getRealizado().getTime()));
			statement.setInt(3, pagoempresadto.getMonto());
			statement.setInt(4, pagoempresadto.getEmpresa());
			statement.setBoolean(5, pagoempresadto.getDisponibleEnSistema());
			statement.setInt(6, pagoempresadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<PagoEmpresaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PagoEmpresaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<PagoEmpresaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PagoEmpresaDTO> readByList(List<Integer> ids)
	{
		List<PagoEmpresaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
