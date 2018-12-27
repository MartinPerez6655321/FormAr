package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.CursadaEmpresaDTO;
import dto.PagoEmpresaDTO;
import modelo.ModeloCursos;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CursadaEmpresaDAOMYSQL
{
	private static final String insert = "INSERT INTO cursadaempresa_table (cursada, disponibleEnSistema) VALUES(?, ?)";
	private static final String delete = "DELETE FROM cursadaempresa_table WHERE id=?";
	private static final String readall = "SELECT * FROM cursadaempresa_table";
	private static final String update = "UPDATE cursadaempresa_table SET cursada=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM cursadaempresa_table WHERE id=?";
	private static final String readList = "SELECT * FROM [table_name] WHERE idcursadaempresa=?";
	private static final String deleteList = "DELETE FROM [table_name] WHERE idcursadaempresa=? AND id[fk_name]=?";
	private static final String insertList = "INSERT INTO [table_name] (idcursadaempresa, id[fk_name]) VALUES(?, ?)";
	private static CursadaEmpresaDAOMYSQL instance;
	
	private CursadaEmpresaDAOMYSQL(){}
	
	public static CursadaEmpresaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new CursadaEmpresaDAOMYSQL();
		return instance;
	}
	
	private List<CursadaEmpresaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<CursadaEmpresaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			CursadaEmpresaDTO elem = new CursadaEmpresaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setCursada(ModeloCursos.getInstance().cursadaPorId(resultSet.getInt("cursada")));
			elem.setPagos(readPagosList(elem));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(CursadaEmpresaDTO cursadaempresadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			if(cursadaempresadto.getCursada()!=null)
				statement.setInt(1, cursadaempresadto.getCursada().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, cursadaempresadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				cursadaempresadto.setId(generatedKey.getInt(1));
				savePagosList(cursadaempresadto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(CursadaEmpresaDTO cursadaempresadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, cursadaempresadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(CursadaEmpresaDTO cursadaempresadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			if(cursadaempresadto.getCursada()!=null)
				statement.setInt(1, cursadaempresadto.getCursada().getId());
			else
				statement.setObject(1, null);
			statement.setBoolean(2, cursadaempresadto.getDisponibleEnSistema());
			statement.setInt(3, cursadaempresadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				savePagosList(cursadaempresadto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<CursadaEmpresaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CursadaEmpresaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<CursadaEmpresaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CursadaEmpresaDTO> readByList(List<Integer> ids)
	{
		List<CursadaEmpresaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
	
	private void savePagosList(CursadaEmpresaDTO cursadaempresadto)
	{
		List<PagoEmpresaDTO> pagosInDB = readPagosList(cursadaempresadto);
		
		for(PagoEmpresaDTO newPagoEmpresaDTO : cursadaempresadto.getPagos())
			if(!pagosInDB.contains(newPagoEmpresaDTO))
				insertPagoEmpresaDTORelation(cursadaempresadto, newPagoEmpresaDTO);
		
		for(PagoEmpresaDTO oldPagoEmpresaDTO : pagosInDB)
			if(!cursadaempresadto.getPagos().contains(oldPagoEmpresaDTO))
				deletePagoEmpresaDTORelation(cursadaempresadto, oldPagoEmpresaDTO);
	}
	
	public boolean insertPagoEmpresaDTORelation(CursadaEmpresaDTO cursadaempresadto, PagoEmpresaDTO pagoempresa)
	{
		try
		{
			if(pagoempresa.getId() == null)
				PagoEmpresaDAOMYSQL.getInstance().create(pagoempresa);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaempresaHasPagos")
				.replace("[fk_name]", "pagoempresa"));
			
			statement.setInt(1, cursadaempresadto.getId());
			statement.setInt(2, pagoempresa.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePagoEmpresaDTORelation(CursadaEmpresaDTO cursadaempresadto, PagoEmpresaDTO pagoempresa)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaempresaHasPagos")
				.replace("[fk_name]", "pagoempresa"));
			
			statement.setInt(1, cursadaempresadto.getId());
			statement.setInt(2, pagoempresa.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private List<PagoEmpresaDTO> readPagosList(CursadaEmpresaDTO cursadaempresadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaempresaHasPagos")
				.replace("[fk_name]", "pagoempresa"));
			statement.setInt(1, cursadaempresadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idpagoempresa"));
			}
			return PagoEmpresaDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
