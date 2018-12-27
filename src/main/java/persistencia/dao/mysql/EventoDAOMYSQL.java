package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.EventoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EventoDAOMYSQL
{
	private static final String insert = "INSERT INTO evento_table (descripcion, fecha, hora, personalAdministrativo, interesado, alumno, administrativoAsignado, supervisor, estado, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM evento_table WHERE id=?";
	private static final String readall = "SELECT * FROM evento_table";
	private static final String update = "UPDATE evento_table SET descripcion=?, fecha=?, hora=?, personalAdministrativo=?, interesado=?, alumno=?, administrativoAsignado=?, supervisor=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM evento_table WHERE id=?";
	private static EventoDAOMYSQL instance;
	
	private EventoDAOMYSQL(){}
	
	public static EventoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EventoDAOMYSQL();
		return instance;
	}
	
	private List<EventoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EventoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EventoDTO elem = new EventoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setDescripcion(resultSet.getString("descripcion"));
			elem.setFecha(resultSet.getDate("fecha"));
			elem.setHora(resultSet.getTime("hora"));
			elem.setPersonalAdministrativo(PersonalAdministrativoDAOMYSQL.getInstance().readById(resultSet.getInt("personalAdministrativo")));
			elem.setInteresado(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("interesado")));
			elem.setAlumno(AlumnoDAOMYSQL.getInstance().readById(resultSet.getInt("alumno")));
			elem.setAdministrativoAsignado(PersonalAdministrativoDAOMYSQL.getInstance().readById(resultSet.getInt("administrativoAsignado")));
			elem.setSupervisor(SupervisorDAOMYSQL.getInstance().readById(resultSet.getInt("supervisor")));
			elem.setEstado(EstadoEventoDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EventoDTO eventodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, eventodto.getDescripcion());
			statement.setDate(2, new java.sql.Date(eventodto.getFecha().getTime()));
			statement.setTime(3, eventodto.getHora());
			if(eventodto.getPersonalAdministrativo()!=null)
				statement.setInt(4, eventodto.getPersonalAdministrativo().getId());
			else
				statement.setObject(4, null);
			if(eventodto.getInteresado()!=null)
				statement.setInt(5, eventodto.getInteresado().getId());
			else
				statement.setObject(5, null);
			if(eventodto.getAlumno()!=null)
				statement.setInt(6, eventodto.getAlumno().getId());
			else
				statement.setObject(6, null);
			if(eventodto.getAdministrativoAsignado()!=null)
				statement.setInt(7, eventodto.getAdministrativoAsignado().getId());
			else
				statement.setObject(7, null);
			if(eventodto.getSupervisor()!=null)
				statement.setInt(8, eventodto.getSupervisor().getId());
			else
				statement.setObject(8, null);
			if(eventodto.getEstado()!=null)
				statement.setInt(9, eventodto.getEstado().getId());
			else
				statement.setObject(9, null);
			statement.setBoolean(10, eventodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				eventodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EventoDTO eventodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, eventodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EventoDTO eventodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, eventodto.getDescripcion());
			statement.setDate(2, new java.sql.Date(eventodto.getFecha().getTime()));
			statement.setTime(3, eventodto.getHora());
			if(eventodto.getPersonalAdministrativo()!=null)
				statement.setInt(4, eventodto.getPersonalAdministrativo().getId());
			else
				statement.setObject(4, null);
			if(eventodto.getInteresado()!=null)
				statement.setInt(5, eventodto.getInteresado().getId());
			else
				statement.setObject(5, null);
			if(eventodto.getAlumno()!=null)
				statement.setInt(6, eventodto.getAlumno().getId());
			else
				statement.setObject(6, null);
			if(eventodto.getAdministrativoAsignado()!=null)
				statement.setInt(7, eventodto.getAdministrativoAsignado().getId());
			else
				statement.setObject(7, null);
			if(eventodto.getSupervisor()!=null)
				statement.setInt(8, eventodto.getSupervisor().getId());
			else
				statement.setObject(8, null);
			if(eventodto.getEstado()!=null)
				statement.setInt(9, eventodto.getEstado().getId());
			else
				statement.setObject(9, null);
			statement.setBoolean(10, eventodto.getDisponibleEnSistema());
			statement.setInt(11, eventodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EventoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EventoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EventoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EventoDTO> readByList(List<Integer> ids)
	{
		List<EventoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
