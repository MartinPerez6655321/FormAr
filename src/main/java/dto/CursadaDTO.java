package dto;

import java.util.List;

import dto.CuotaDTO;
import dto.HorarioDTO;
import dto.InscripcionAlumnoDTO;
import dto.InstructorDTO;
import dto.PeriodoInscripcionDTO;
import dto.PlanillaDeAsistenciasDTO;
import dto.PlanillaDeParcialesDTO;

import java.util.Date;

public class CursadaDTO
{

	private Integer id;
	private String nombre;
	private Integer vacantes;
	private Integer vacantesMinimas;
	private Date inicio;
	private Date fin;
	private Integer montoTotal;
	private List<HorarioDTO> horarios;
	private List<PeriodoInscripcionDTO> periodosDeInscripcion;
	private List<InstructorDTO> instructores;
	private SalaDTO sala;
	private CursoDTO curso;
	private String programa;
	private List<PlanillaDeAsistenciasDTO> asistencias;
	private List<PlanillaDeParcialesDTO> parciales;
	private List<InscripcionAlumnoDTO> inscripciones;
	private List<CuotaDTO> cuotas;
	private EstadoCursadaDTO estado;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getNombre()
	{
		return nombre;
	}

	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}

	public Integer getVacantes()
	{
		return vacantes;
	}

	public void setVacantes(Integer vacantes)
	{
		this.vacantes = vacantes;
	}

	public Integer getVacantesMinimas()
	{
		return vacantesMinimas;
	}

	public void setVacantesMinimas(Integer vacantesMinimas)
	{
		this.vacantesMinimas = vacantesMinimas;
	}

	public Date getInicio()
	{
		return inicio;
	}

	public void setInicio(Date inicio)
	{
		this.inicio = inicio;
	}

	public Date getFin()
	{
		return fin;
	}

	public void setFin(Date fin)
	{
		this.fin = fin;
	}

	public Integer getMontoTotal()
	{
		return montoTotal;
	}

	public void setMontoTotal(Integer montoTotal)
	{
		this.montoTotal = montoTotal;
	}

	public List<HorarioDTO> getHorarios()
	{
		return horarios;
	}

	public void setHorarios(List<HorarioDTO> horarios)
	{
		this.horarios = horarios;
	}

	public List<PeriodoInscripcionDTO> getPeriodosDeInscripcion()
	{
		return periodosDeInscripcion;
	}

	public void setPeriodosDeInscripcion(List<PeriodoInscripcionDTO> periodosDeInscripcion)
	{
		this.periodosDeInscripcion = periodosDeInscripcion;
	}

	public List<InstructorDTO> getInstructores()
	{
		return instructores;
	}

	public void setInstructores(List<InstructorDTO> instructores)
	{
		this.instructores = instructores;
	}

	public SalaDTO getSala()
	{
		return sala;
	}

	public void setSala(SalaDTO sala)
	{
		this.sala = sala;
	}

	public CursoDTO getCurso()
	{
		return curso;
	}

	public void setCurso(CursoDTO curso)
	{
		this.curso = curso;
	}

	public String getPrograma()
	{
		return programa;
	}

	public void setPrograma(String programa)
	{
		this.programa = programa;
	}

	public List<PlanillaDeAsistenciasDTO> getAsistencias()
	{
		return asistencias;
	}

	public void setAsistencias(List<PlanillaDeAsistenciasDTO> asistencias)
	{
		this.asistencias = asistencias;
	}

	public List<PlanillaDeParcialesDTO> getParciales()
	{
		return parciales;
	}

	public void setParciales(List<PlanillaDeParcialesDTO> parciales)
	{
		this.parciales = parciales;
	}

	public List<InscripcionAlumnoDTO> getInscripciones()
	{
		return inscripciones;
	}

	public void setInscripciones(List<InscripcionAlumnoDTO> inscripciones)
	{
		this.inscripciones = inscripciones;
	}

	public List<CuotaDTO> getCuotas()
	{
		return cuotas;
	}

	public void setCuotas(List<CuotaDTO> cuotas)
	{
		this.cuotas = cuotas;
	}

	public EstadoCursadaDTO getEstado()
	{
		return estado;
	}

	public void setEstado(EstadoCursadaDTO estado)
	{
		this.estado = estado;
	}

	public Boolean getDisponibleEnSistema()
	{
		return disponibleEnSistema;
	}

	public void setDisponibleEnSistema(Boolean disponibleEnSistema)
	{
		this.disponibleEnSistema = disponibleEnSistema;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof CursadaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((CursadaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getNombre();
		ret = ret + ", " + getVacantes();
		ret = ret + ", " + getVacantesMinimas();
		ret = ret + ", " + getInicio();
		ret = ret + ", " + getFin();
		ret = ret + ", " + getMontoTotal();
		ret = ret + ", " + getHorarios();
		ret = ret + ", " + getPeriodosDeInscripcion();
		ret = ret + ", " + getInstructores();
		ret = ret + ", " + getSala();
		ret = ret + ", " + getCurso();
		ret = ret + ", " + getPrograma();
		ret = ret + ", " + getAsistencias();
		ret = ret + ", " + getParciales();
		ret = ret + ", " + getInscripciones();
		ret = ret + ", " + getCuotas();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
