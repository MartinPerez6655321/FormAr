package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;

import connections.ConnectionManager;
import dto.EventoInasistenciaDTO;
import modelo.ModeloCursos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EventoInasistenciaDAOMYSQL
{
	private static final String insert = "INSERT INTO eventoinasistencia_table (fechaDeInasistencia, horaDeCumplimiento, alumno, cursada, administrativoAsignado, estado, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM eventoinasistencia_table WHERE id=?";
	private static final String readall = "SELECT * FROM eventoinasistencia_table";
	private static final String update = "UPDATE eventoinasistencia_table SET fechaDeInasistencia=?, horaDeCumplimiento=?, alumno=?, cursada=?, administrativoAsignado=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM eventoinasistencia_table WHERE id=?";
	private static EventoInasistenciaDAOMYSQL instance;
	
	private EventoInasistenciaDAOMYSQL(){}
	
	public static EventoInasistenciaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EventoInasistenciaDAOMYSQL();
		return instance;
	}
	
	private List<EventoInasistenciaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EventoInasistenciaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EventoInasistenciaDTO elem = new EventoInasistenciaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFechaDeInasistencia(resultSet.getDate("fechaDeInasistencia"));
			elem.setHoraDeCumplimiento(resultSet.getTime("horaDeCumplimiento"));
			elem.setAlumno(AlumnoDAOMYSQL.getInstance().readById(resultSet.getInt("alumno")));
			elem.setCursada(ModeloCursos.getInstance().cursadaPorId(resultSet.getInt("cursada")));
			elem.setAdministrativoAsignado(PersonalAdministrativoDAOMYSQL.getInstance().readById(resultSet.getInt("administrativoAsignado")));
			elem.setEstado(EstadoEventoDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EventoInasistenciaDTO eventoinasistenciadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(eventoinasistenciadto.getFechaDeInasistencia().getTime()));
			statement.setTime(2, eventoinasistenciadto.getHoraDeCumplimiento());
			if(eventoinasistenciadto.getAlumno()!=null)
				statement.setInt(3, eventoinasistenciadto.getAlumno().getId());
			else
				statement.setObject(3, null);
			if(eventoinasistenciadto.getCursada()!=null)
				statement.setInt(4, eventoinasistenciadto.getCursada().getId());
			else
				statement.setObject(4, null);
			if(eventoinasistenciadto.getAdministrativoAsignado()!=null)
				statement.setInt(5, eventoinasistenciadto.getAdministrativoAsignado().getId());
			else
				statement.setObject(5, null);
			if(eventoinasistenciadto.getEstado()!=null)
				statement.setInt(6, eventoinasistenciadto.getEstado().getId());
			else
				statement.setObject(6, null);
			statement.setBoolean(7, eventoinasistenciadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				eventoinasistenciadto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EventoInasistenciaDTO eventoinasistenciadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, eventoinasistenciadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EventoInasistenciaDTO eventoinasistenciadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(eventoinasistenciadto.getFechaDeInasistencia().getTime()));
			statement.setTime(2, eventoinasistenciadto.getHoraDeCumplimiento());
			if(eventoinasistenciadto.getAlumno()!=null)
				statement.setInt(3, eventoinasistenciadto.getAlumno().getId());
			else
				statement.setObject(3, null);
			if(eventoinasistenciadto.getCursada()!=null)
				statement.setInt(4, eventoinasistenciadto.getCursada().getId());
			else
				statement.setObject(4, null);
			if(eventoinasistenciadto.getAdministrativoAsignado()!=null)
				statement.setInt(5, eventoinasistenciadto.getAdministrativoAsignado().getId());
			else
				statement.setObject(5, null);
			if(eventoinasistenciadto.getEstado()!=null)
				statement.setInt(6, eventoinasistenciadto.getEstado().getId());
			else
				statement.setObject(6, null);
			statement.setBoolean(7, eventoinasistenciadto.getDisponibleEnSistema());
			statement.setInt(8, eventoinasistenciadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EventoInasistenciaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EventoInasistenciaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EventoInasistenciaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EventoInasistenciaDTO> readByList(List<Integer> ids)
	{
		List<EventoInasistenciaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
