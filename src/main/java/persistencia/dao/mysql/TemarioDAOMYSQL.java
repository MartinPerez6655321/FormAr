package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.TemaDTO;
import dto.TemarioDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class TemarioDAOMYSQL
{
	private static final String insert = "INSERT INTO temario_table (descripcion, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM temario_table WHERE id=?";
	private static final String readall = "SELECT * FROM temario_table";
	private static final String update = "UPDATE temario_table SET descripcion=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM temario_table WHERE id=?";
	private static final String readList = "SELECT * FROM [table_name] WHERE idtemario=?";
	private static final String deleteList = "DELETE FROM [table_name] WHERE idtemario=? AND id[fk_name]=?";
	private static final String insertList = "INSERT INTO [table_name] (idtemario, id[fk_name]) VALUES(?, ?)";
	private static TemarioDAOMYSQL instance;
	
	private TemarioDAOMYSQL(){}
	
	public static TemarioDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new TemarioDAOMYSQL();
		return instance;
	}
	
	private List<TemarioDTO> query(PreparedStatement statement) throws SQLException
	{
		List<TemarioDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			TemarioDTO elem = new TemarioDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setDescripcion(resultSet.getString("descripcion"));
			elem.setTemas(readTemasList(elem));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(TemarioDTO temariodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, temariodto.getDescripcion());
			statement.setBoolean(2, temariodto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				temariodto.setId(generatedKey.getInt(1));
				saveTemasList(temariodto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(TemarioDTO temariodto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, temariodto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(TemarioDTO temariodto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, temariodto.getDescripcion());
			statement.setBoolean(2, temariodto.getDisponibleEnSistema());
			statement.setInt(3, temariodto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				saveTemasList(temariodto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<TemarioDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public TemarioDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<TemarioDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<TemarioDTO> readByList(List<Integer> ids)
	{
		List<TemarioDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
	
	private void saveTemasList(TemarioDTO temariodto)
	{
		List<TemaDTO> temasInDB = readTemasList(temariodto);
		
		for(TemaDTO newTemaDTO : temariodto.getTemas())
			if(!temasInDB.contains(newTemaDTO))
				insertTemaDTORelation(temariodto, newTemaDTO);
		
		for(TemaDTO oldTemaDTO : temasInDB)
			if(!temariodto.getTemas().contains(oldTemaDTO))
				deleteTemaDTORelation(temariodto, oldTemaDTO);
	}
	
	public boolean insertTemaDTORelation(TemarioDTO temariodto, TemaDTO tema)
	{
		try
		{
			if(tema.getId() == null)
				TemaDAOMYSQL.getInstance().create(tema);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "temarioHasTemas")
				.replace("[fk_name]", "tema"));
			
			statement.setInt(1, temariodto.getId());
			statement.setInt(2, tema.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteTemaDTORelation(TemarioDTO temariodto, TemaDTO tema)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "temarioHasTemas")
				.replace("[fk_name]", "tema"));
			
			statement.setInt(1, temariodto.getId());
			statement.setInt(2, tema.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private List<TemaDTO> readTemasList(TemarioDTO temariodto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "temarioHasTemas")
				.replace("[fk_name]", "tema"));
			statement.setInt(1, temariodto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idtema"));
			}
			return TemaDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
