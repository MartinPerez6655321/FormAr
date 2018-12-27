package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.HorarioDTO;
import dto.InstructorDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class InstructorDAOMYSQL
{
	private static final String insert = "INSERT INTO instructor_table (persona, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM instructor_table WHERE id=?";
	private static final String readall = "SELECT * FROM instructor_table";
	private static final String update = "UPDATE instructor_table SET persona=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM instructor_table WHERE id=?";
	private static final String readList = "SELECT * FROM [table_name] WHERE idinstructor=?";
	private static final String deleteList = "DELETE FROM [table_name] WHERE idinstructor=? AND id[fk_name]=?";
	private static final String insertList = "INSERT INTO [table_name] (idinstructor, id[fk_name]) VALUES(?, ?)";
	private static InstructorDAOMYSQL instance;
	
	private InstructorDAOMYSQL(){}
	
	public static InstructorDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new InstructorDAOMYSQL();
		return instance;
	}
	
	private List<InstructorDTO> query(PreparedStatement statement) throws SQLException
	{
		List<InstructorDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			InstructorDTO elem = new InstructorDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setDisponibilidades(readDisponibilidadesList(elem));
			elem.setPersona(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("persona")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(InstructorDTO instructordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(instructordto.getPersona()!=null)
				statement.setInt(1, instructordto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, instructordto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				instructordto.setId(generatedKey.getInt(1));
				saveDisponibilidadesList(instructordto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(InstructorDTO instructordto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, instructordto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(InstructorDTO instructordto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(instructordto.getPersona()!=null)
				statement.setInt(1, instructordto.getPersona().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, instructordto.getDisponibleEnSistema());
			statement.setInt(3, instructordto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				saveDisponibilidadesList(instructordto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<InstructorDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public InstructorDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<InstructorDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<InstructorDTO> readByList(List<Integer> ids)
	{
		List<InstructorDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
	
	private void saveDisponibilidadesList(InstructorDTO instructordto)
	{
		List<HorarioDTO> disponibilidadesInDB = readDisponibilidadesList(instructordto);
		
		for(HorarioDTO newHorarioDTO : instructordto.getDisponibilidades())
			if(!disponibilidadesInDB.contains(newHorarioDTO))
				insertHorarioDTORelation(instructordto, newHorarioDTO);
		
		for(HorarioDTO oldHorarioDTO : disponibilidadesInDB)
			if(!instructordto.getDisponibilidades().contains(oldHorarioDTO))
				deleteHorarioDTORelation(instructordto, oldHorarioDTO);
	}
	
	public boolean insertHorarioDTORelation(InstructorDTO instructordto, HorarioDTO horario)
	{
		try
		{
			if(horario.getId() == null)
				HorarioDAOMYSQL.getInstance().create(horario);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "instructorHasDisponibilidades")
				.replace("[fk_name]", "horario"));
			
			statement.setInt(1, instructordto.getId());
			statement.setInt(2, horario.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteHorarioDTORelation(InstructorDTO instructordto, HorarioDTO horario)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "instructorHasDisponibilidades")
				.replace("[fk_name]", "horario"));
			
			statement.setInt(1, instructordto.getId());
			statement.setInt(2, horario.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private List<HorarioDTO> readDisponibilidadesList(InstructorDTO instructordto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "instructorHasDisponibilidades")
				.replace("[fk_name]", "horario"));
			statement.setInt(1, instructordto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idhorario"));
			}
			return HorarioDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
