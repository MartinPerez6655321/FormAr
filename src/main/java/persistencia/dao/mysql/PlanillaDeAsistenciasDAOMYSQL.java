package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.AsistenciaDTO;
import dto.PlanillaDeAsistenciasDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PlanillaDeAsistenciasDAOMYSQL
{
	private static final String insert = "INSERT INTO planilladeasistencias_table (fecha, horario, disponibleEnSistema) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM planilladeasistencias_table WHERE id=?";
	private static final String readall = "SELECT * FROM planilladeasistencias_table";
	private static final String update = "UPDATE planilladeasistencias_table SET fecha=?, horario=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM planilladeasistencias_table WHERE id=?";
	private static final String readList = "SELECT * FROM [table_name] WHERE idplanilladeasistencias=?";
	private static final String deleteList = "DELETE FROM [table_name] WHERE idplanilladeasistencias=? AND id[fk_name]=?";
	private static final String insertList = "INSERT INTO [table_name] (idplanilladeasistencias, id[fk_name]) VALUES(?, ?)";
	private static PlanillaDeAsistenciasDAOMYSQL instance;
	
	private PlanillaDeAsistenciasDAOMYSQL(){}
	
	public static PlanillaDeAsistenciasDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new PlanillaDeAsistenciasDAOMYSQL();
		return instance;
	}
	
	private List<PlanillaDeAsistenciasDTO> query(PreparedStatement statement) throws SQLException
	{
		List<PlanillaDeAsistenciasDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			PlanillaDeAsistenciasDTO elem = new PlanillaDeAsistenciasDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFecha(resultSet.getDate("fecha"));
			elem.setHorario(HorarioDAOMYSQL.getInstance().readById(resultSet.getInt("horario")));
			elem.setAsistencias(readAsistenciasList(elem));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(PlanillaDeAsistenciasDTO planilladeasistenciasdto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(planilladeasistenciasdto.getFecha().getTime()));
			if(planilladeasistenciasdto.getHorario()!=null)
				statement.setInt(2, planilladeasistenciasdto.getHorario().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, planilladeasistenciasdto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				planilladeasistenciasdto.setId(generatedKey.getInt(1));
				saveAsistenciasList(planilladeasistenciasdto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PlanillaDeAsistenciasDTO planilladeasistenciasdto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, planilladeasistenciasdto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(PlanillaDeAsistenciasDTO planilladeasistenciasdto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(planilladeasistenciasdto.getFecha().getTime()));
			if(planilladeasistenciasdto.getHorario()!=null)
				statement.setInt(2, planilladeasistenciasdto.getHorario().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, planilladeasistenciasdto.getDisponibleEnSistema());
			statement.setInt(4, planilladeasistenciasdto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				saveAsistenciasList(planilladeasistenciasdto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<PlanillaDeAsistenciasDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PlanillaDeAsistenciasDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<PlanillaDeAsistenciasDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PlanillaDeAsistenciasDTO> readByList(List<Integer> ids)
	{
		List<PlanillaDeAsistenciasDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
	
	private void saveAsistenciasList(PlanillaDeAsistenciasDTO planilladeasistenciasdto)
	{
		List<AsistenciaDTO> asistenciasInDB = readAsistenciasList(planilladeasistenciasdto);
		
		for(AsistenciaDTO newAsistenciaDTO : planilladeasistenciasdto.getAsistencias())
			if(!asistenciasInDB.contains(newAsistenciaDTO))
				insertAsistenciaDTORelation(planilladeasistenciasdto, newAsistenciaDTO);
		
		for(AsistenciaDTO oldAsistenciaDTO : asistenciasInDB)
			if(!planilladeasistenciasdto.getAsistencias().contains(oldAsistenciaDTO))
				deleteAsistenciaDTORelation(planilladeasistenciasdto, oldAsistenciaDTO);
	}
	
	public boolean insertAsistenciaDTORelation(PlanillaDeAsistenciasDTO planilladeasistenciasdto, AsistenciaDTO asistencia)
	{
		try
		{
			if(asistencia.getId() == null)
				AsistenciaDAOMYSQL.getInstance().create(asistencia);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "planilladeasistenciasHasAsistencias")
				.replace("[fk_name]", "asistencia"));
			
			statement.setInt(1, planilladeasistenciasdto.getId());
			statement.setInt(2, asistencia.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteAsistenciaDTORelation(PlanillaDeAsistenciasDTO planilladeasistenciasdto, AsistenciaDTO asistencia)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "planilladeasistenciasHasAsistencias")
				.replace("[fk_name]", "asistencia"));
			
			statement.setInt(1, planilladeasistenciasdto.getId());
			statement.setInt(2, asistencia.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private List<AsistenciaDTO> readAsistenciasList(PlanillaDeAsistenciasDTO planilladeasistenciasdto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "planilladeasistenciasHasAsistencias")
				.replace("[fk_name]", "asistencia"));
			statement.setInt(1, planilladeasistenciasdto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idasistencia"));
			}
			return AsistenciaDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
