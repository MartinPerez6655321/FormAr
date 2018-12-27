package persistencia.dao.mysql;

import java.util.LinkedList;
import com.mysql.jdbc.Statement;

import connections.ConnectionManager;
import dto.CuotaDTO;
import dto.CursadaDTO;
import dto.HorarioDTO;
import dto.InscripcionAlumnoDTO;
import dto.InstructorDTO;
import dto.PeriodoInscripcionDTO;
import dto.PlanillaDeAsistenciasDTO;
import dto.PlanillaDeParcialesDTO;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.sql.PreparedStatement;

public class CursadaDAOMYSQL
{
	private static final String insert = "INSERT INTO cursada_table (nombre, vacantes, vacantesMinimas, inicio, fin, montoTotal, sala, curso, programa, estado, disponibleEnSistema) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String delete = "DELETE FROM cursada_table WHERE id=?";
	private static final String readall = "SELECT * FROM cursada_table";
	private static final String update = "UPDATE cursada_table SET nombre=?, vacantes=?, vacantesMinimas=?, inicio=?, fin=?, montoTotal=?, sala=?, curso=?, programa=?, estado=?, disponibleEnSistema=? WHERE id=?";
	private static final String readByID = "SELECT * FROM cursada_table WHERE id=?";
	private static final String readList = "SELECT * FROM [table_name] WHERE idcursada=?";
	private static final String deleteList = "DELETE FROM [table_name] WHERE idcursada=? AND id[fk_name]=?";
	private static final String insertList = "INSERT INTO [table_name] (idcursada, id[fk_name]) VALUES(?, ?)";
	private static CursadaDAOMYSQL instance;
	
	private CursadaDAOMYSQL(){}
	
	public static CursadaDAOMYSQL getInstance()
	{
		if (instance == null)
			instance = new CursadaDAOMYSQL();
		return instance;
	}
	
	private List<CursadaDTO> query(PreparedStatement statement) throws SQLException
	{
		List<CursadaDTO> ret = new LinkedList<>();
		ResultSet resultSet = statement.executeQuery();
		
		while(resultSet.next())
		{
			CursadaDTO elem = new CursadaDTO();
			elem.setId(resultSet.getInt("id"));
			elem.setNombre(resultSet.getString("nombre"));
			elem.setVacantes(resultSet.getInt("vacantes"));
			elem.setVacantesMinimas(resultSet.getInt("vacantesMinimas"));
			elem.setInicio(resultSet.getDate("inicio"));
			elem.setFin(resultSet.getDate("fin"));
			elem.setMontoTotal(resultSet.getInt("montoTotal"));
			elem.setHorarios(readHorariosList(elem));
			elem.setPeriodosDeInscripcion(readPeriodosDeInscripcionList(elem));
			elem.setInstructores(readInstructoresList(elem));
			elem.setSala(SalaDAOMYSQL.getInstance().readById(resultSet.getInt("sala")));
			elem.setCurso(CursoDAOMYSQL.getInstance().readById(resultSet.getInt("curso")));
			elem.setPrograma(resultSet.getString("programa"));
			elem.setAsistencias(readAsistenciasList(elem));
			elem.setParciales(readParcialesList(elem));
			elem.setInscripciones(readInscripcionesList(elem));
			elem.setCuotas(readCuotasList(elem));
			elem.setEstado(EstadoCursadaDAOMYSQL.getInstance().readById(resultSet.getInt("estado")));
			elem.setDisponibleEnSistema(resultSet.getBoolean("disponibleEnSistema"));
			ret.add(elem);
		}
		return ret;
	}
	
	public boolean create(CursadaDTO cursadadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, cursadadto.getNombre());
			statement.setInt(2, cursadadto.getVacantes());
			statement.setInt(3, cursadadto.getVacantesMinimas());
			statement.setDate(4, new java.sql.Date(cursadadto.getInicio().getTime()));
			statement.setDate(5, new java.sql.Date(cursadadto.getFin().getTime()));
			statement.setInt(6, cursadadto.getMontoTotal());
			if(cursadadto.getSala()!=null)
				statement.setInt(7, cursadadto.getSala().getId());
			else
				statement.setObject(7, null);
			if(cursadadto.getCurso()!=null)
				statement.setInt(8, cursadadto.getCurso().getId());
			else
				statement.setObject(8, null);
			statement.setString(9, cursadadto.getPrograma());
			if(cursadadto.getEstado()!=null)
				statement.setInt(10, cursadadto.getEstado().getId());
			else
				statement.setObject(10, null);
			statement.setBoolean(11, cursadadto.getDisponibleEnSistema());
		
			if(statement.executeUpdate() > 0)
			{
				ResultSet generatedKey = statement.getGeneratedKeys();
				generatedKey.next();
				cursadadto.setId(generatedKey.getInt(1));
				saveHorariosList(cursadadto);
				savePeriodosDeInscripcionList(cursadadto);
				saveInstructoresList(cursadadto);
				saveAsistenciasList(cursadadto);
				saveParcialesList(cursadadto);
				saveInscripcionesList(cursadadto);
				saveCuotasList(cursadadto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(CursadaDTO cursadadto)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(delete);
			statement.setInt(1, cursadadto.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean update(CursadaDTO cursadadto)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(update);
			statement.setString(1, cursadadto.getNombre());
			statement.setInt(2, cursadadto.getVacantes());
			statement.setInt(3, cursadadto.getVacantesMinimas());
			statement.setDate(4, new java.sql.Date(cursadadto.getInicio().getTime()));
			statement.setDate(5, new java.sql.Date(cursadadto.getFin().getTime()));
			statement.setInt(6, cursadadto.getMontoTotal());
			if(cursadadto.getSala()!=null)
				statement.setInt(7, cursadadto.getSala().getId());
			else
				statement.setObject(7, null);
			if(cursadadto.getCurso()!=null)
				statement.setInt(8, cursadadto.getCurso().getId());
			else
				statement.setObject(8, null);
			statement.setString(9, cursadadto.getPrograma());
			if(cursadadto.getEstado()!=null)
				statement.setInt(10, cursadadto.getEstado().getId());
			else
				statement.setObject(10, null);
			statement.setBoolean(11, cursadadto.getDisponibleEnSistema());
			statement.setInt(12, cursadadto.getId());
			
			if(statement.executeUpdate() > 0)
			{
				saveHorariosList(cursadadto);
				savePeriodosDeInscripcionList(cursadadto);
				saveInstructoresList(cursadadto);
				saveAsistenciasList(cursadadto);
				saveParcialesList(cursadadto);
				saveInscripcionesList(cursadadto);
				saveCuotasList(cursadadto);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<CursadaDTO> readAll()
	{
		try
		{
			return query(ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readall));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public CursadaDTO readById(int id)
	{
		try
		{
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(readByID);
			statement.setInt(1, id);
			List<CursadaDTO> result = query(statement);
			if(!result.isEmpty())
				return result.get(0);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CursadaDTO> readByList(List<Integer> ids)
	{
		List<CursadaDTO> ret = new LinkedList<>();
		for(Integer id : ids)
			ret.add(readById(id));
		return ret;
	}
	
	private void saveHorariosList(CursadaDTO cursadadto)
	{
		List<HorarioDTO> horariosInDB = readHorariosList(cursadadto);
		
		for(HorarioDTO newHorarioDTO : cursadadto.getHorarios())
			if(!horariosInDB.contains(newHorarioDTO))
				insertHorarioDTORelation(cursadadto, newHorarioDTO);
		
		for(HorarioDTO oldHorarioDTO : horariosInDB)
			if(!cursadadto.getHorarios().contains(oldHorarioDTO))
				deleteHorarioDTORelation(cursadadto, oldHorarioDTO);
	}
	
	public boolean insertHorarioDTORelation(CursadaDTO cursadadto, HorarioDTO horario)
	{
		try
		{
			if(horario.getId() == null)
				HorarioDAOMYSQL.getInstance().create(horario);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaHasHorarios")
				.replace("[fk_name]", "horario"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, horario.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteHorarioDTORelation(CursadaDTO cursadadto, HorarioDTO horario)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaHasHorarios")
				.replace("[fk_name]", "horario"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, horario.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void savePeriodosDeInscripcionList(CursadaDTO cursadadto)
	{
		List<PeriodoInscripcionDTO> periodosDeInscripcionInDB = readPeriodosDeInscripcionList(cursadadto);
		
		for(PeriodoInscripcionDTO newPeriodoInscripcionDTO : cursadadto.getPeriodosDeInscripcion())
			if(!periodosDeInscripcionInDB.contains(newPeriodoInscripcionDTO))
				insertPeriodoInscripcionDTORelation(cursadadto, newPeriodoInscripcionDTO);
		
		for(PeriodoInscripcionDTO oldPeriodoInscripcionDTO : periodosDeInscripcionInDB)
			if(!cursadadto.getPeriodosDeInscripcion().contains(oldPeriodoInscripcionDTO))
				deletePeriodoInscripcionDTORelation(cursadadto, oldPeriodoInscripcionDTO);
	}
	
	public boolean insertPeriodoInscripcionDTORelation(CursadaDTO cursadadto, PeriodoInscripcionDTO periodoinscripcion)
	{
		try
		{
			if(periodoinscripcion.getId() == null)
				PeriodoInscripcionDAOMYSQL.getInstance().create(periodoinscripcion);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaHasPeriodosDeInscripcion")
				.replace("[fk_name]", "periodoinscripcion"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, periodoinscripcion.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePeriodoInscripcionDTORelation(CursadaDTO cursadadto, PeriodoInscripcionDTO periodoinscripcion)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaHasPeriodosDeInscripcion")
				.replace("[fk_name]", "periodoinscripcion"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, periodoinscripcion.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveInstructoresList(CursadaDTO cursadadto)
	{
		List<InstructorDTO> instructoresInDB = readInstructoresList(cursadadto);
		
		for(InstructorDTO newInstructorDTO : cursadadto.getInstructores())
			if(!instructoresInDB.contains(newInstructorDTO))
				insertInstructorDTORelation(cursadadto, newInstructorDTO);
		
		for(InstructorDTO oldInstructorDTO : instructoresInDB)
			if(!cursadadto.getInstructores().contains(oldInstructorDTO))
				deleteInstructorDTORelation(cursadadto, oldInstructorDTO);
	}
	
	public boolean insertInstructorDTORelation(CursadaDTO cursadadto, InstructorDTO instructor)
	{
		try
		{
			if(instructor.getId() == null)
				InstructorDAOMYSQL.getInstance().create(instructor);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaHasInstructores")
				.replace("[fk_name]", "instructor"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, instructor.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteInstructorDTORelation(CursadaDTO cursadadto, InstructorDTO instructor)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaHasInstructores")
				.replace("[fk_name]", "instructor"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, instructor.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveAsistenciasList(CursadaDTO cursadadto)
	{
		List<PlanillaDeAsistenciasDTO> asistenciasInDB = readAsistenciasList(cursadadto);
		
		for(PlanillaDeAsistenciasDTO newPlanillaDeAsistenciasDTO : cursadadto.getAsistencias())
			if(!asistenciasInDB.contains(newPlanillaDeAsistenciasDTO))
				insertPlanillaDeAsistenciasDTORelation(cursadadto, newPlanillaDeAsistenciasDTO);
		
		for(PlanillaDeAsistenciasDTO oldPlanillaDeAsistenciasDTO : asistenciasInDB)
			if(!cursadadto.getAsistencias().contains(oldPlanillaDeAsistenciasDTO))
				deletePlanillaDeAsistenciasDTORelation(cursadadto, oldPlanillaDeAsistenciasDTO);
	}
	
	public boolean insertPlanillaDeAsistenciasDTORelation(CursadaDTO cursadadto, PlanillaDeAsistenciasDTO planilladeasistencias)
	{
		try
		{
			if(planilladeasistencias.getId() == null)
				PlanillaDeAsistenciasDAOMYSQL.getInstance().create(planilladeasistencias);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaHasAsistencias")
				.replace("[fk_name]", "planilladeasistencias"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, planilladeasistencias.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePlanillaDeAsistenciasDTORelation(CursadaDTO cursadadto, PlanillaDeAsistenciasDTO planilladeasistencias)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaHasAsistencias")
				.replace("[fk_name]", "planilladeasistencias"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, planilladeasistencias.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveParcialesList(CursadaDTO cursadadto)
	{
		List<PlanillaDeParcialesDTO> parcialesInDB = readParcialesList(cursadadto);
		
		for(PlanillaDeParcialesDTO newPlanillaDeParcialesDTO : cursadadto.getParciales())
			if(!parcialesInDB.contains(newPlanillaDeParcialesDTO))
				insertPlanillaDeParcialesDTORelation(cursadadto, newPlanillaDeParcialesDTO);
		
		for(PlanillaDeParcialesDTO oldPlanillaDeParcialesDTO : parcialesInDB)
			if(!cursadadto.getParciales().contains(oldPlanillaDeParcialesDTO))
				deletePlanillaDeParcialesDTORelation(cursadadto, oldPlanillaDeParcialesDTO);
	}
	
	public boolean insertPlanillaDeParcialesDTORelation(CursadaDTO cursadadto, PlanillaDeParcialesDTO planilladeparciales)
	{
		try
		{
			if(planilladeparciales.getId() == null)
				PlanillaDeParcialesDAOMYSQL.getInstance().create(planilladeparciales);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaHasParciales")
				.replace("[fk_name]", "planilladeparciales"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, planilladeparciales.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deletePlanillaDeParcialesDTORelation(CursadaDTO cursadadto, PlanillaDeParcialesDTO planilladeparciales)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaHasParciales")
				.replace("[fk_name]", "planilladeparciales"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, planilladeparciales.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveInscripcionesList(CursadaDTO cursadadto)
	{
		List<InscripcionAlumnoDTO> inscripcionesInDB = readInscripcionesList(cursadadto);
		
		for(InscripcionAlumnoDTO newInscripcionAlumnoDTO : cursadadto.getInscripciones())
			if(!inscripcionesInDB.contains(newInscripcionAlumnoDTO))
				insertInscripcionAlumnoDTORelation(cursadadto, newInscripcionAlumnoDTO);
		
		for(InscripcionAlumnoDTO oldInscripcionAlumnoDTO : inscripcionesInDB)
			if(!cursadadto.getInscripciones().contains(oldInscripcionAlumnoDTO))
				deleteInscripcionAlumnoDTORelation(cursadadto, oldInscripcionAlumnoDTO);
	}
	
	public boolean insertInscripcionAlumnoDTORelation(CursadaDTO cursadadto, InscripcionAlumnoDTO inscripcionalumno)
	{
		try
		{
			if(inscripcionalumno.getId() == null)
				InscripcionAlumnoDAOMYSQL.getInstance().create(inscripcionalumno);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaHasInscripciones")
				.replace("[fk_name]", "inscripcionalumno"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, inscripcionalumno.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteInscripcionAlumnoDTORelation(CursadaDTO cursadadto, InscripcionAlumnoDTO inscripcionalumno)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaHasInscripciones")
				.replace("[fk_name]", "inscripcionalumno"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, inscripcionalumno.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void saveCuotasList(CursadaDTO cursadadto)
	{
		List<CuotaDTO> cuotasInDB = readCuotasList(cursadadto);
		
		for(CuotaDTO newCuotaDTO : cursadadto.getCuotas())
			if(!cuotasInDB.contains(newCuotaDTO))
				insertCuotaDTORelation(cursadadto, newCuotaDTO);
		
		for(CuotaDTO oldCuotaDTO : cuotasInDB)
			if(!cursadadto.getCuotas().contains(oldCuotaDTO))
				deleteCuotaDTORelation(cursadadto, oldCuotaDTO);
	}
	
	public boolean insertCuotaDTORelation(CursadaDTO cursadadto, CuotaDTO cuota)
	{
		try
		{
			if(cuota.getId() == null)
				CuotaDAOMYSQL.getInstance().create(cuota);
			
			PreparedStatement statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(insertList
				.replace("[table_name]", "cursadaHasCuotas")
				.replace("[fk_name]", "cuota"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, cuota.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteCuotaDTORelation(CursadaDTO cursadadto, CuotaDTO cuota)
	{
		try
		{
			PreparedStatement statement;
			statement = ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(deleteList
				.replace("[table_name]", "cursadaHasCuotas")
				.replace("[fk_name]", "cuota"));
			
			statement.setInt(1, cursadadto.getId());
			statement.setInt(2, cuota.getId());
			
			if(statement.executeUpdate() > 0)
				return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private List<HorarioDTO> readHorariosList(CursadaDTO cursadadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaHasHorarios")
				.replace("[fk_name]", "horario"));
			statement.setInt(1, cursadadto.getId());
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
	
	private List<PeriodoInscripcionDTO> readPeriodosDeInscripcionList(CursadaDTO cursadadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaHasPeriodosDeInscripcion")
				.replace("[fk_name]", "periodoinscripcion"));
			statement.setInt(1, cursadadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idperiodoinscripcion"));
			}
			return PeriodoInscripcionDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<InstructorDTO> readInstructoresList(CursadaDTO cursadadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaHasInstructores")
				.replace("[fk_name]", "instructor"));
			statement.setInt(1, cursadadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idinstructor"));
			}
			return InstructorDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<PlanillaDeAsistenciasDTO> readAsistenciasList(CursadaDTO cursadadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaHasAsistencias")
				.replace("[fk_name]", "planilladeasistencias"));
			statement.setInt(1, cursadadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idplanilladeasistencias"));
			}
			return PlanillaDeAsistenciasDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<PlanillaDeParcialesDTO> readParcialesList(CursadaDTO cursadadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaHasParciales")
				.replace("[fk_name]", "planilladeparciales"));
			statement.setInt(1, cursadadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idplanilladeparciales"));
			}
			return PlanillaDeParcialesDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<InscripcionAlumnoDTO> readInscripcionesList(CursadaDTO cursadadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaHasInscripciones")
				.replace("[fk_name]", "inscripcionalumno"));
			statement.setInt(1, cursadadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idinscripcionalumno"));
			}
			return InscripcionAlumnoDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private List<CuotaDTO> readCuotasList(CursadaDTO cursadadto)
	{
		try
		{
			List<Integer> ids = new LinkedList<>();
			PreparedStatement statement = 
				ConnectionManager.getConnectionManager().getSQLConnection().prepareStatement(
				readList.replace("[table_name]", "cursadaHasCuotas")
				.replace("[fk_name]", "cuota"));
			statement.setInt(1, cursadadto.getId());
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next())
			{
				ids.add(resultSet.getInt("idcuota"));
			}
			return CuotaDAOMYSQL.getInstance().readByList(ids);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
