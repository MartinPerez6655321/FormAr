package persistencia.dao.mysql;

import java.util.List;
import java.util.LinkedList;
import com.mysql.jdbc.Statement;
import connections.ConnectionManager;
import dto.AlumnoDTO;
import dto.CursadaEmpresaDTO;
import dto.EmpresaDTO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class EmpresaDAOMYSQL
{
	private static final String insert = "INSERT INTO empresa_table (nombre, contacto, disponibleEnSistema) VALUES(?, ?, ?)";
	private static final String delete = "DELETE FROM empresa_table WHERE id=?";
	private static final String readall = "SELECT * FROM empresa_table";
	private static final String update = "UPDATE empresa_table SET nombre=?, contacto=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM empresa_table WHERE id=?";
	private static final String readList = "SELECT * FROM [table_name] WHERE idempresa=?";
	private static final String deleteList = "DELETE FROM [table_name] WHERE idempresa=? AND id[fk_name]=?";
	private static final String insertList = "INSERT INTO [table_name] (idempresa, id[fk_name]) VALUES(?, ?)";
	private static EmpresaDAOMYSQL instance;
	
	private EmpresaDAOMYSQL(){}
	
	public static EmpresaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new EmpresaDAOMYSQL();
		return instance;
	}
	
	private List<EmpresaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<EmpresaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			EmpresaDTO elem = new EmpresaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setContacto(PersonaDAOMYSQL.getInstance().readById(resultSet.getInt("contacto")));
			elem.setAlumnos(readAlumnosList(elem));
			elem.setCursadas(readCursadasList(elem));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(EmpresaDTO empresadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, empresadto.getNombre());
			if(empresadto.getContacto()!=null)
				statement.setInt(2, empresadto.getContacto().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, empresadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				empresadto.setId(generatedKey.getInt(1));
				saveAlumnosList(empresadto);
				saveCursadasList(empresadto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(EmpresaDTO empresadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, empresadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(EmpresaDTO empresadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, empresadto.getNombre());
			if(empresadto.getContacto()!=null)
				statement.setInt(2, empresadto.getContacto().getId());
			else
				statement.setObject(2, null);
			statement.setBoolean(3, empresadto.getDisponibleEnSistema());
			statement.setInt(4, empresadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				saveAlumnosList(empresadto);
				saveCursadasList(empresadto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<EmpresaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public EmpresaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<EmpresaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<EmpresaDTO> readByList(List<Integer> ids)
	{
		List<EmpresaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
	
	private void saveAlumnosList(EmpresaDTO empresadto)
	{
		List<AlumnoDTO> alumnosInDB = readAlumnosList(empresadto);
		
		for(AlumnoDTO newAlumnoDTO : empresadto.getAlumnos())
			if(!alumnosInDB.contains(newAlumnoDTO))
				insertAlumnoDTORelation(empresadto, newAlumnoDTO);
		
		for(AlumnoDTO oldAlumnoDTO : alumnosInDB)
			if(!empresadto.getAlumnos().contains(oldAlumnoDTO))
				deleteAlumnoDTORelation(empresadto, oldAlumnoDTO);
	}
	
	public boolean insertAlumnoDTORelation(EmpresaDTO empresadto, AlumnoDTO alumno)
	{
		try
		{
			if(alumno.getId() == null)
				AlumnoDAOMYSQL.getInstance().create(alumno);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "empresaHasAlumnos")
				.replace("[fk_name]", "alumno"));
			
			statement.setInt(1, empresadto.getId());
			statement.setInt(2, alumno.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteAlumnoDTORelation(EmpresaDTO empresadto, AlumnoDTO alumno)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "empresaHasAlumnos")
				.replace("[fk_name]", "alumno"));
			
			statement.setInt(1, empresadto.getId());
			statement.setInt(2, alumno.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveCursadasList(EmpresaDTO empresadto)
	{
		List<CursadaEmpresaDTO> cursadasInDB = readCursadasList(empresadto);
		
		for(CursadaEmpresaDTO newCursadaEmpresaDTO : empresadto.getCursadas())
			if(!cursadasInDB.contains(newCursadaEmpresaDTO))
				insertCursadaEmpresaDTORelation(empresadto, newCursadaEmpresaDTO);
		
		for(CursadaEmpresaDTO oldCursadaEmpresaDTO : cursadasInDB)
			if(!empresadto.getCursadas().contains(oldCursadaEmpresaDTO))
				deleteCursadaEmpresaDTORelation(empresadto, oldCursadaEmpresaDTO);
	}
	
	public boolean insertCursadaEmpresaDTORelation(EmpresaDTO empresadto, CursadaEmpresaDTO cursadaempresa)
	{
		try
		{
			if(cursadaempresa.getId() == null)
				CursadaEmpresaDAOMYSQL.getInstance().create(cursadaempresa);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "empresaHasCursadas")
				.replace("[fk_name]", "cursadaempresa"));
			
			statement.setInt(1, empresadto.getId());
			statement.setInt(2, cursadaempresa.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteCursadaEmpresaDTORelation(EmpresaDTO empresadto, CursadaEmpresaDTO cursadaempresa)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "empresaHasCursadas")
				.replace("[fk_name]", "cursadaempresa"));
			
			statement.setInt(1, empresadto.getId());
			statement.setInt(2, cursadaempresa.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private List<AlumnoDTO> readAlumnosList(EmpresaDTO empresadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "empresaHasAlumnos")
				.replace("[fk_name]", "alumno"));
			statement.setInt(1, empresadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idalumno"));
			}
			return AlumnoDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<CursadaEmpresaDTO> readCursadasList(EmpresaDTO empresadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "empresaHasCursadas")
				.replace("[fk_name]", "cursadaempresa"));
			statement.setInt(1, empresadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idcursadaempresa"));
			}
			return CursadaEmpresaDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
