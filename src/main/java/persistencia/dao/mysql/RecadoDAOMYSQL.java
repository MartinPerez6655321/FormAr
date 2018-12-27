package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.RecadoDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class RecadoDAOMYSQL
{
	private static final String insert = "INSERT INTO recado_table (mensaje, emisor, receptor, asunto, fecha, hora, estado, disponibilidadEmisor, disponibilidadReceptor) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM recado_table WHERE id=?";
	private static final String readall = "SELECT * FROM recado_table";
	private static final String update = "UPDATE recado_table SET mensaje=?, emisor=?, receptor=?, asunto=?, fecha=?, hora=?, estado=?, disponibilidadEmisor=?, disponibilidadReceptor=? WHERE id=?";
	private static final String readByID = "SELECT * FROM recado_table WHERE id=?";
	private static RecadoDAOMYSQL instance;
	
	private RecadoDAOMYSQL(){}
	
	public static RecadoDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new RecadoDAOMYSQL();
		return instance;
	}
	
	private List<RecadoDTO> query(PreparedStatement statement) throws SQLException
	{
		List<RecadoDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			RecadoDTO elem = new RecadoDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setMensaje(resultSet.getString("mensaje"));
			elem.setEmisor(UsuarioDAOMYSQL.getInstance().readById(resultSet.getInt("emisor")));
			elem.setReceptor(UsuarioDAOMYSQL.getInstance().readById(resultSet.getInt("receptor")));
			elem.setAsunto(resultSet.getString("asunto"));
			elem.setFecha(resultSet.getDate("fecha"));
			elem.setHora(resultSet.getTime("hora"));
			elem.setEstado(EstadoDeRecadoDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibilidadEmisor(resultSet.getBoolean("disponibilidadEmisor"));
			elem.setDisponibilidadReceptor(resultSet.getBoolean("disponibilidadReceptor"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(RecadoDTO recadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, recadodto.getMensaje());
			if(recadodto.getEmisor()!=null)
				statement.setInt(2, recadodto.getEmisor().getId());
			else
				statement.setObject(2, null);
			if(recadodto.getReceptor()!=null)
				statement.setInt(3, recadodto.getReceptor().getId());
			else
				statement.setObject(3, null);
			statement.setString(4, recadodto.getAsunto());
			statement.setDate(5, new java.sql.Date(recadodto.getFecha().getTime()));
			statement.setTime(6, recadodto.getHora());
			if(recadodto.getEstado()!=null)
				statement.setInt(7, recadodto.getEstado().getId());
			else
				statement.setObject(7, null);
			statement.setBoolean(8, recadodto.getDisponibilidadEmisor());
			statement.setBoolean(9, recadodto.getDisponibilidadReceptor());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				recadodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(RecadoDTO recadodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, recadodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(RecadoDTO recadodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, recadodto.getMensaje());
			if(recadodto.getEmisor()!=null)
				statement.setInt(2, recadodto.getEmisor().getId());
			else
				statement.setObject(2, null);
			if(recadodto.getReceptor()!=null)
				statement.setInt(3, recadodto.getReceptor().getId());
			else
				statement.setObject(3, null);
			statement.setString(4, recadodto.getAsunto());
			statement.setDate(5, new java.sql.Date(recadodto.getFecha().getTime()));
			statement.setTime(6, recadodto.getHora());
			if(recadodto.getEstado()!=null)
				statement.setInt(7, recadodto.getEstado().getId());
			else
				statement.setObject(7, null);
			statement.setBoolean(8, recadodto.getDisponibilidadEmisor());
			statement.setBoolean(9, recadodto.getDisponibilidadReceptor());
			statement.setInt(10, recadodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<RecadoDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public RecadoDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<RecadoDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<RecadoDTO> readByList(List<Integer> ids)
	{
		List<RecadoDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
