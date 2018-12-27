package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.HorarioDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class HorarioDAOMYSQL
{
	private static final String insert = "INSERT INTO horario_table (horaInicio, horaFin, diaDeLaSemana, disponibleEnSistema) VALUES(?, ?, ?, ?)";
	private static final String delete = "DELETE FROM horario_table WHERE id=?";
	private static final String readall = "SELECT * FROM horario_table";
	private static final String update = "UPDATE horario_table SET horaInicio=?, horaFin=?, diaDeLaSemana=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM horario_table WHERE id=?";
	private static HorarioDAOMYSQL instance;
	
	private HorarioDAOMYSQL(){}
	
	public static HorarioDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new HorarioDAOMYSQL();
		return instance;
	}
	
	private List<HorarioDTO> query(PreparedStatement statement) throws SQLException
	{
		List<HorarioDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			HorarioDTO elem = new HorarioDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setHoraInicio(resultSet.getTime("horaInicio"));
			elem.setHoraFin(resultSet.getTime("horaFin"));
			elem.setDiaDeLaSemana(DiaDeLaSemanaDAOMYSQL.getInstance().readById(resultSet.getInt("diaDeLaSemana")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(HorarioDTO horariodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setTime(1, horariodto.getHoraInicio());
			statement.setTime(2, horariodto.getHoraFin());
			if(horariodto.getDiaDeLaSemana()!=null)
				statement.setInt(3, horariodto.getDiaDeLaSemana().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, horariodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				horariodto.setId(generatedKey.getInt(1));
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(HorarioDTO horariodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, horariodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(HorarioDTO horariodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setTime(1, horariodto.getHoraInicio());
			statement.setTime(2, horariodto.getHoraFin());
			if(horariodto.getDiaDeLaSemana()!=null)
				statement.setInt(3, horariodto.getDiaDeLaSemana().getId());
			else
				statement.setObject(3, null);
			statement.setBoolean(4, horariodto.getDisponibleEnSistema());
			statement.setInt(5, horariodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<HorarioDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public HorarioDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<HorarioDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<HorarioDTO> readByList(List<Integer> ids)
	{
		List<HorarioDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
}
