package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.EventoInteresadoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EventoInteresadoDAOMYSQL
{
	private static final String insert = "INSERT INTO eventointeresado_table (curso, descripcion, fechaDeLlamado, horaDeLlamado, fechaDeCumplimiento, horaDeCumplimiento, personalAdministrativo, interesado, administrativoAsignado, supervisor, estado, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM eventointeresado_table WHERE id=?";
	private static final String readall = "SELECT * FROM eventointeresado_table";
	private static final String update = "UPDATE eventointeresado_table SET curso=?, descripcion=?, fechaDeLlamado=?, horaDeLlamado=?, fechaDeCumplimiento=?, horaDeCumplimiento=?, personalAdministrativo=?, interesado=?, administrativoAsignado=?, supervisor=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM eventointeresado_table WHERE id=?";
	private static EventoInteresadoDAOMYSQL instance;
	
	private EventoInteresadoDAOMYSQL(){}
	
	public static EventoInteresadoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EventoInteresadoDAOMYSQL();
		return instance;
	}
	
	private List<EventoInteresadoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EventoInteresadoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EventoInteresadoDTO elem = new EventoInteresadoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setCurso(CursoDAOMYSQL.getInstance().readById(resultSet.getInt("curso")));
			elem.setDescripcion(resultSet.getString("descripcion"));
			elem.setFechaDeLlamado(resultSet.getDate("fechaDeLlamado"));
			elem.setHoraDeLlamado(resultSet.getTime("horaDeLlamado"));
			elem.setFechaDeCumplimiento(resultSet.getDate("fechaDeCumplimiento"));
			elem.setHoraDeCumplimiento(resultSet.getTime("horaDeCumplimiento"));
			elem.setPersonalAdministrativo(PersonalAdministrativoDAOMYSQL.getInstance().readById(resultSet.getInt("personalAdministrativo")));
			elem.setInteresado(InteresadoDAOMYSQL.getInstance().readById(resultSet.getInt("interesado")));
			elem.setAdministrativoAsignado(PersonalAdministrativoDAOMYSQL.getInstance().readById(resultSet.getInt("administrativoAsignado")));
			elem.setSupervisor(SupervisorDAOMYSQL.getInstance().readById(resultSet.getInt("supervisor")));
			elem.setEstado(EstadoEventoDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EventoInteresadoDTO eventointeresadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(eventointeresadodto.getCurso()!=null)
				statement.setInt(1, eventointeresadodto.getCurso().getId());
			else
				statement.setObject(1, null);
			statement.setString(2, eventointeresadodto.getDescripcion());
			statement.setDate(3, new java.sql.Date(eventointeresadodto.getFechaDeLlamado().getTime()));
			statement.setTime(4, eventointeresadodto.getHoraDeLlamado());
			statement.setDate(5, new java.sql.Date(eventointeresadodto.getFechaDeCumplimiento().getTime()));
			statement.setTime(6, eventointeresadodto.getHoraDeCumplimiento());
			if(eventointeresadodto.getPersonalAdministrativo()!=null)
				statement.setInt(7, eventointeresadodto.getPersonalAdministrativo().getId());
			else
				statement.setObject(7, null);
			if(eventointeresadodto.getInteresado()!=null)
				statement.setInt(8, eventointeresadodto.getInteresado().getId());
			else
				statement.setObject(8, null);
			if(eventointeresadodto.getAdministrativoAsignado()!=null)
				statement.setInt(9, eventointeresadodto.getAdministrativoAsignado().getId());
			else
				statement.setObject(9, null);
			if(eventointeresadodto.getSupervisor()!=null)
				statement.setInt(10, eventointeresadodto.getSupervisor().getId());
			else
				statement.setObject(10, null);
			if(eventointeresadodto.getEstado()!=null)
				statement.setInt(11, eventointeresadodto.getEstado().getId());
			else
				statement.setObject(11, null);
			statement.setBoolean(12, eventointeresadodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				eventointeresadodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EventoInteresadoDTO eventointeresadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, eventointeresadodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EventoInteresadoDTO eventointeresadodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(eventointeresadodto.getCurso()!=null)
				statement.setInt(1, eventointeresadodto.getCurso().getId());
			else
				statement.setObject(1, null);
			statement.setString(2, eventointeresadodto.getDescripcion());
			statement.setDate(3, new java.sql.Date(eventointeresadodto.getFechaDeLlamado().getTime()));
			statement.setTime(4, eventointeresadodto.getHoraDeLlamado());
			statement.setDate(5, new java.sql.Date(eventointeresadodto.getFechaDeCumplimiento().getTime()));
			statement.setTime(6, eventointeresadodto.getHoraDeCumplimiento());
			if(eventointeresadodto.getPersonalAdministrativo()!=null)
				statement.setInt(7, eventointeresadodto.getPersonalAdministrativo().getId());
			else
				statement.setObject(7, null);
			if(eventointeresadodto.getInteresado()!=null)
				statement.setInt(8, eventointeresadodto.getInteresado().getId());
			else
				statement.setObject(8, null);
			if(eventointeresadodto.getAdministrativoAsignado()!=null)
				statement.setInt(9, eventointeresadodto.getAdministrativoAsignado().getId());
			else
				statement.setObject(9, null);
			if(eventointeresadodto.getSupervisor()!=null)
				statement.setInt(10, eventointeresadodto.getSupervisor().getId());
			else
				statement.setObject(10, null);
			if(eventointeresadodto.getEstado()!=null)
				statement.setInt(11, eventointeresadodto.getEstado().getId());
			else
				statement.setObject(11, null);
			statement.setBoolean(12, eventointeresadodto.getDisponibleEnSistema());
			statement.setInt(13, eventointeresadodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EventoInteresadoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EventoInteresadoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EventoInteresadoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EventoInteresadoDTO> readByList(List<Integer> ids)
	{
		List<EventoInteresadoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
