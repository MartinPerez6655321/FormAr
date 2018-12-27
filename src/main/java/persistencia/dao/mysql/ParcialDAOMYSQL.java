package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.ParcialDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class ParcialDAOMYSQL
{
	private static final String insert = "INSERT INTO parcial_table (alumno, nota, observaciones, estado, disponibleEnSistema) VALUES(?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM parcial_table WHERE id=?";
	private static final String readall = "SELECT * FROM parcial_table";
	private static final String update = "UPDATE parcial_table SET alumno=?, nota=?, observaciones=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM parcial_table WHERE id=?";
	private static ParcialDAOMYSQL instance;
	
	private ParcialDAOMYSQL(){}
	
	public static ParcialDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new ParcialDAOMYSQL();
		return instance;
	}
	
	private List<ParcialDTO> query(PreparedStatement statement) throws SQLException
	{
		List<ParcialDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			ParcialDTO elem = new ParcialDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setAlumno(AlumnoDAOMYSQL.getInstance().readById(resultSet.getInt("alumno")));
			elem.setNota(resultSet.getInt("nota"));
			elem.setObservaciones(resultSet.getString("observaciones"));
			elem.setEstado(EstadoEvaluacionDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(ParcialDTO parcialdto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(parcialdto.getAlumno()!=null)
				statement.setInt(1, parcialdto.getAlumno().getId());
			else
				statement.setObject(1, null);
			statement.setInt(2, parcialdto.getNota());
			statement.setString(3, parcialdto.getObservaciones());
			if(parcialdto.getEstado()!=null)
				statement.setInt(4, parcialdto.getEstado().getId());
			else
				statement.setObject(4, null);
			statement.setBoolean(5, parcialdto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				parcialdto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(ParcialDTO parcialdto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, parcialdto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(ParcialDTO parcialdto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(parcialdto.getAlumno()!=null)
				statement.setInt(1, parcialdto.getAlumno().getId());
			else
				statement.setObject(1, null);
			statement.setInt(2, parcialdto.getNota());
			statement.setString(3, parcialdto.getObservaciones());
			if(parcialdto.getEstado()!=null)
				statement.setInt(4, parcialdto.getEstado().getId());
			else
				statement.setObject(4, null);
			statement.setBoolean(5, parcialdto.getDisponibleEnSistema());
			statement.setInt(6, parcialdto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<ParcialDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ParcialDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<ParcialDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<ParcialDTO> readByList(List<Integer> ids)
	{
		List<ParcialDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
