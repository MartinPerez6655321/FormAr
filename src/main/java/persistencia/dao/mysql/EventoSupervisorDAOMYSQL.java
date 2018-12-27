package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.EventoSupervisorDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EventoSupervisorDAOMYSQL
{
	private static final String insert = "INSERT INTO eventosupervisor_table (descripcion, fechaDeCumplimiento, horaDeCumplimiento, administrativoAsignado, supervisor, estado, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM eventosupervisor_table WHERE id=?";
	private static final String readall = "SELECT * FROM eventosupervisor_table";
	private static final String update = "UPDATE eventosupervisor_table SET descripcion=?, fechaDeCumplimiento=?, horaDeCumplimiento=?, administrativoAsignado=?, supervisor=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM eventosupervisor_table WHERE id=?";
	private static EventoSupervisorDAOMYSQL instance;
	
	private EventoSupervisorDAOMYSQL(){}
	
	public static EventoSupervisorDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EventoSupervisorDAOMYSQL();
		return instance;
	}
	
	private List<EventoSupervisorDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EventoSupervisorDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EventoSupervisorDTO elem = new EventoSupervisorDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setDescripcion(resultSet.getString("descripcion"));
			elem.setFechaDeCumplimiento(resultSet.getDate("fechaDeCumplimiento"));
			elem.setHoraDeCumplimiento(resultSet.getTime("horaDeCumplimiento"));
			elem.setAdministrativoAsignado(PersonalAdministrativoDAOMYSQL.getInstance().readById(resultSet.getInt("administrativoAsignado")));
			elem.setSupervisor(SupervisorDAOMYSQL.getInstance().readById(resultSet.getInt("supervisor")));
			elem.setEstado(EstadoEventoDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EventoSupervisorDTO eventosupervisordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, eventosupervisordto.getDescripcion());
			statement.setDate(2, new java.sql.Date(eventosupervisordto.getFechaDeCumplimiento().getTime()));
			statement.setTime(3, eventosupervisordto.getHoraDeCumplimiento());
			if(eventosupervisordto.getAdministrativoAsignado()!=null)
				statement.setInt(4, eventosupervisordto.getAdministrativoAsignado().getId());
			else
				statement.setObject(4, null);
			if(eventosupervisordto.getSupervisor()!=null)
				statement.setInt(5, eventosupervisordto.getSupervisor().getId());
			else
				statement.setObject(5, null);
			if(eventosupervisordto.getEstado()!=null)
				statement.setInt(6, eventosupervisordto.getEstado().getId());
			else
				statement.setObject(6, null);
			statement.setBoolean(7, eventosupervisordto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				eventosupervisordto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EventoSupervisorDTO eventosupervisordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, eventosupervisordto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EventoSupervisorDTO eventosupervisordto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, eventosupervisordto.getDescripcion());
			statement.setDate(2, new java.sql.Date(eventosupervisordto.getFechaDeCumplimiento().getTime()));
			statement.setTime(3, eventosupervisordto.getHoraDeCumplimiento());
			if(eventosupervisordto.getAdministrativoAsignado()!=null)
				statement.setInt(4, eventosupervisordto.getAdministrativoAsignado().getId());
			else
				statement.setObject(4, null);
			if(eventosupervisordto.getSupervisor()!=null)
				statement.setInt(5, eventosupervisordto.getSupervisor().getId());
			else
				statement.setObject(5, null);
			if(eventosupervisordto.getEstado()!=null)
				statement.setInt(6, eventosupervisordto.getEstado().getId());
			else
				statement.setObject(6, null);
			statement.setBoolean(7, eventosupervisordto.getDisponibleEnSistema());
			statement.setInt(8, eventosupervisordto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EventoSupervisorDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EventoSupervisorDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EventoSupervisorDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EventoSupervisorDTO> readByList(List<Integer> ids)
	{
		List<EventoSupervisorDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
