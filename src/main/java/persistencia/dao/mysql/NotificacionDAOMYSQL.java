package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.NotificacionDTO;
import modelo.ModeloCursos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class NotificacionDAOMYSQL
{
	private static final String insert = "INSERT INTO notificacion_table (titulo, descripcion, eventoInasistencia, eventoSupervisor, eventoInteresado, usuario, cursada, recado, fecha, hora, codigoVista, codigoTab, visto, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM notificacion_table WHERE id=?";
	private static final String readall = "SELECT * FROM notificacion_table";
	private static final String update = "UPDATE notificacion_table SET titulo=?, descripcion=?, eventoInasistencia=?, eventoSupervisor=?, eventoInteresado=?, usuario=?, cursada=?, recado=?, fecha=?, hora=?, codigoVista=?, codigoTab=?, visto=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM notificacion_table WHERE id=?";
	private static NotificacionDAOMYSQL instance;
	
	private NotificacionDAOMYSQL(){}
	
	public static NotificacionDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new NotificacionDAOMYSQL();
		return instance;
	}
	
	private List<NotificacionDTO> query(PreparedStatement statement) throws SQLException
	{
		List<NotificacionDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			NotificacionDTO elem = new NotificacionDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setTitulo(resultSet.getString("titulo"));
			elem.setDescripcion(resultSet.getString("descripcion"));
			elem.setEventoInasistencia(EventoInasistenciaDAOMYSQL.getInstance().readById(resultSet.getInt("eventoInasistencia")));
			elem.setEventoSupervisor(EventoSupervisorDAOMYSQL.getInstance().readById(resultSet.getInt("eventoSupervisor")));
			elem.setEventoInteresado(EventoInteresadoDAOMYSQL.getInstance().readById(resultSet.getInt("eventoInteresado")));
			elem.setUsuario(UsuarioDAOMYSQL.getInstance().readById(resultSet.getInt("usuario")));
			elem.setCursada(ModeloCursos.getInstance().cursadaPorId(resultSet.getInt("cursada")));
			elem.setRecado(RecadoDAOMYSQL.getInstance().readById(resultSet.getInt("recado")));
			elem.setFecha(resultSet.getDate("fecha"));
			elem.setHora(resultSet.getTime("hora"));
			elem.setCodigoVista(resultSet.getString("codigoVista"));
			elem.setCodigoTab(resultSet.getString("codigoTab"));
			elem.setVisto(resultSet.getBoolean("visto"));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(NotificacionDTO notificaciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, notificaciondto.getTitulo());
			statement.setString(2, notificaciondto.getDescripcion());
			if(notificaciondto.getEventoInasistencia()!=null)
				statement.setInt(3, notificaciondto.getEventoInasistencia().getId());
			else
				statement.setObject(3, null);
			if(notificaciondto.getEventoSupervisor()!=null)
				statement.setInt(4, notificaciondto.getEventoSupervisor().getId());
			else
				statement.setObject(4, null);
			if(notificaciondto.getEventoInteresado()!=null)
				statement.setInt(5, notificaciondto.getEventoInteresado().getId());
			else
				statement.setObject(5, null);
			if(notificaciondto.getUsuario()!=null)
				statement.setInt(6, notificaciondto.getUsuario().getId());
			else
				statement.setObject(6, null);
			if(notificaciondto.getCursada()!=null)
				statement.setInt(7, notificaciondto.getCursada().getId());
			else
				statement.setObject(7, null);
			if(notificaciondto.getRecado()!=null)
				statement.setInt(8, notificaciondto.getRecado().getId());
			else
				statement.setObject(8, null);
			statement.setDate(9, new java.sql.Date(notificaciondto.getFecha().getTime()));
			statement.setTime(10, notificaciondto.getHora());
			statement.setString(11, notificaciondto.getCodigoVista());
			statement.setString(12, notificaciondto.getCodigoTab());
			statement.setBoolean(13, notificaciondto.getVisto());
			statement.setBoolean(14, notificaciondto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				notificaciondto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(NotificacionDTO notificaciondto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, notificaciondto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(NotificacionDTO notificaciondto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, notificaciondto.getTitulo());
			statement.setString(2, notificaciondto.getDescripcion());
			if(notificaciondto.getEventoInasistencia()!=null)
				statement.setInt(3, notificaciondto.getEventoInasistencia().getId());
			else
				statement.setObject(3, null);
			if(notificaciondto.getEventoSupervisor()!=null)
				statement.setInt(4, notificaciondto.getEventoSupervisor().getId());
			else
				statement.setObject(4, null);
			if(notificaciondto.getEventoInteresado()!=null)
				statement.setInt(5, notificaciondto.getEventoInteresado().getId());
			else
				statement.setObject(5, null);
			if(notificaciondto.getUsuario()!=null)
				statement.setInt(6, notificaciondto.getUsuario().getId());
			else
				statement.setObject(6, null);
			if(notificaciondto.getCursada()!=null)
				statement.setInt(7, notificaciondto.getCursada().getId());
			else
				statement.setObject(7, null);
			if(notificaciondto.getRecado()!=null)
				statement.setInt(8, notificaciondto.getRecado().getId());
			else
				statement.setObject(8, null);
			statement.setDate(9, new java.sql.Date(notificaciondto.getFecha().getTime()));
			statement.setTime(10, notificaciondto.getHora());
			statement.setString(11, notificaciondto.getCodigoVista());
			statement.setString(12, notificaciondto.getCodigoTab());
			statement.setBoolean(13, notificaciondto.getVisto());
			statement.setBoolean(14, notificaciondto.getDisponibleEnSistema());
			statement.setInt(15, notificaciondto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<NotificacionDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public NotificacionDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<NotificacionDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<NotificacionDTO> readByList(List<Integer> ids)
	{
		List<NotificacionDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
