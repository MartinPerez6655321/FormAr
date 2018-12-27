package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.ParcialDTO;
import dto.PlanillaDeParcialesDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class PlanillaDeParcialesDAOMYSQL
{
	private static final String insert = "INSERT INTO planilladeparciales_table (fecha, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM planilladeparciales_table WHERE id=?";
	private static final String readall = "SELECT * FROM planilladeparciales_table";
	private static final String update = "UPDATE planilladeparciales_table SET fecha=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM planilladeparciales_table WHERE id=?";
	private static final String readList = "SELECT * FROM [table_name] WHERE idplanilladeparciales=?";
	private static final String deleteList = "DELETE FROM [table_name] WHERE idplanilladeparciales=? AND id[fk_name]=?";
	private static final String insertList = "INSERT INTO [table_name] (idplanilladeparciales, id[fk_name]) VALUES(?, ?)";
	private static PlanillaDeParcialesDAOMYSQL instance;
	
	private PlanillaDeParcialesDAOMYSQL(){}
	
	public static PlanillaDeParcialesDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new PlanillaDeParcialesDAOMYSQL();
		return instance;
	}
	
	private List<PlanillaDeParcialesDTO> query(PreparedStatement statement) throws SQLException
	{
		List<PlanillaDeParcialesDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			PlanillaDeParcialesDTO elem = new PlanillaDeParcialesDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setFecha(resultSet.getDate("fecha"));
			elem.setParciales(readParcialesList(elem));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(PlanillaDeParcialesDTO planilladeparcialesdto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(planilladeparcialesdto.getFecha().getTime()));
			statement.setBoolean(2, planilladeparcialesdto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				planilladeparcialesdto.setId(generatedKey.getInt(1));
				saveParcialesList(planilladeparcialesdto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(PlanillaDeParcialesDTO planilladeparcialesdto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, planilladeparcialesdto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(PlanillaDeParcialesDTO planilladeparcialesdto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setDate(1, new java.sql.Date(planilladeparcialesdto.getFecha().getTime()));
			statement.setBoolean(2, planilladeparcialesdto.getDisponibleEnSistema());
			statement.setInt(3, planilladeparcialesdto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				saveParcialesList(planilladeparcialesdto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<PlanillaDeParcialesDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public PlanillaDeParcialesDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<PlanillaDeParcialesDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<PlanillaDeParcialesDTO> readByList(List<Integer> ids)
	{
		List<PlanillaDeParcialesDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
	
	private void saveParcialesList(PlanillaDeParcialesDTO planilladeparcialesdto)
	{
		List<ParcialDTO> parcialesInDB = readParcialesList(planilladeparcialesdto);
		
		for(ParcialDTO newParcialDTO : planilladeparcialesdto.getParciales())
			if(!parcialesInDB.contains(newParcialDTO))
				insertParcialDTORelation(planilladeparcialesdto, newParcialDTO);
		
		for(ParcialDTO oldParcialDTO : parcialesInDB)
			if(!planilladeparcialesdto.getParciales().contains(oldParcialDTO))
				deleteParcialDTORelation(planilladeparcialesdto, oldParcialDTO);
	}
	
	public boolean insertParcialDTORelation(PlanillaDeParcialesDTO planilladeparcialesdto, ParcialDTO parcial)
	{
		try
		{
			if(parcial.getId() == null)
				ParcialDAOMYSQL.getInstance().create(parcial);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "planilladeparcialesHasParciales")
				.replace("[fk_name]", "parcial"));
			
			statement.setInt(1, planilladeparcialesdto.getId());
			statement.setInt(2, parcial.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteParcialDTORelation(PlanillaDeParcialesDTO planilladeparcialesdto, ParcialDTO parcial)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "planilladeparcialesHasParciales")
				.replace("[fk_name]", "parcial"));
			
			statement.setInt(1, planilladeparcialesdto.getId());
			statement.setInt(2, parcial.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private List<ParcialDTO> readParcialesList(PlanillaDeParcialesDTO planilladeparcialesdto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "planilladeparcialesHasParciales")
				.replace("[fk_name]", "parcial"));
			statement.setInt(1, planilladeparcialesdto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idparcial"));
			}
			return ParcialDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
