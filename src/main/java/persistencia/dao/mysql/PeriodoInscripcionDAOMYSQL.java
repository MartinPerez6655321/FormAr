package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.PeriodoInscripcionDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PeriodoInscripcionDAOMYSQL
{
	private static final String insert = "INSERT INTO periodoinscripcion_table (inicio, fin, estado, disponibleEnSistema) VALUES(?, ?, ?, ?)";
	private static final String delete = "DELETE FROM periodoinscripcion_table WHERE id=?";
	private static final String readall = "SELECT * FROM periodoinscripcion_table";
	private static final String update = "UPDATE periodoinscripcion_table SET inicio=?, fin=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM periodoinscripcion_table WHERE id=?";
	private static PeriodoInscripcionDAOMYSQL instance;
	
	private PeriodoInscripcionDAOMYSQL(){}
	
	public static PeriodoInscripcionDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new PeriodoInscripcionDAOMYSQL();
		return instance;
	}
	
	private List<PeriodoInscripcionDTO> query(PreparedStatement statement) throws SQLException
	{
		List<PeriodoInscripcionDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			PeriodoInscripcionDTO elem = new PeriodoInscripcionDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setInicio(resultSet.getDate("inicio"));
			elem.setFin(resultSet.getDate("fin"));
			elem.setEstado(EstadoDePeriodoDeInscripcionDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(PeriodoInscripcionDTO periodoinscripciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(periodoinscripciondto.getInicio().getTime()));
			statement.setDate(2, new java.sql.Date(periodoinscripciondto.getFin().getTime()));
			if(periodoinscripciondto.getEstado()!=null)
				statement.setInt(3, periodoinscripciondto.getEstado().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, periodoinscripciondto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				periodoinscripciondto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PeriodoInscripcionDTO periodoinscripciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, periodoinscripciondto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(PeriodoInscripcionDTO periodoinscripciondto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(periodoinscripciondto.getInicio().getTime()));
			statement.setDate(2, new java.sql.Date(periodoinscripciondto.getFin().getTime()));
			if(periodoinscripciondto.getEstado()!=null)
				statement.setInt(3, periodoinscripciondto.getEstado().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, periodoinscripciondto.getDisponibleEnSistema());
			statement.setInt(5, periodoinscripciondto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<PeriodoInscripcionDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PeriodoInscripcionDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<PeriodoInscripcionDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PeriodoInscripcionDTO> readByList(List<Integer> ids)
	{
		List<PeriodoInscripcionDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
