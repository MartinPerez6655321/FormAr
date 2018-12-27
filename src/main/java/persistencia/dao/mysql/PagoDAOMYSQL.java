package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.PagoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PagoDAOMYSQL
{
	private static final String insert = "INSERT INTO pago_table (fecha, alumno, cuota, disponibleEnSistema) VALUES(?, ?, ?, ?)";
	private static final String delete = "DELETE FROM pago_table WHERE id=?";
	private static final String readall = "SELECT * FROM pago_table";
	private static final String update = "UPDATE pago_table SET fecha=?, alumno=?, cuota=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM pago_table WHERE id=?";
	private static PagoDAOMYSQL instance;
	
	private PagoDAOMYSQL(){}
	
	public static PagoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new PagoDAOMYSQL();
		return instance;
	}
	
	private List<PagoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<PagoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			PagoDTO elem = new PagoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFecha(resultSet.getDate("fecha"));
			elem.setAlumno(AlumnoDAOMYSQL.getInstance().readById(resultSet.getInt("alumno")));
			elem.setCuota(CuotaDAOMYSQL.getInstance().readById(resultSet.getInt("cuota")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(PagoDTO pagodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(pagodto.getFecha().getTime()));
			if(pagodto.getAlumno()!=null)
				statement.setInt(2, pagodto.getAlumno().getId());
			else
				statement.setObject(2, null);
			if(pagodto.getCuota()!=null)
				statement.setInt(3, pagodto.getCuota().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, pagodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				pagodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PagoDTO pagodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, pagodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(PagoDTO pagodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(pagodto.getFecha().getTime()));
			if(pagodto.getAlumno()!=null)
				statement.setInt(2, pagodto.getAlumno().getId());
			else
				statement.setObject(2, null);
			if(pagodto.getCuota()!=null)
				statement.setInt(3, pagodto.getCuota().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, pagodto.getDisponibleEnSistema());
			statement.setInt(5, pagodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<PagoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PagoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<PagoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PagoDTO> readByList(List<Integer> ids)
	{
		List<PagoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
