package dto;

import java.sql.Time;
import java.util.Date;

public class EventoInasistenciaDTO
{

	private Integer id;
	private Date fechaDeInasistencia;
	private Time horaDeCumplimiento;
	private AlumnoDTO alumno;
	private CursadaDTO cursada;
	private PersonalAdministrativoDTO administrativoAsignado;
	private EstadoEventoDTO estado;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getFechaDeInasistencia()
	{
		return fechaDeInasistencia;
	}

	public void setFechaDeInasistencia(Date fechaDeInasistencia)
	{
		this.fechaDeInasistencia = fechaDeInasistencia;
	}

	public Time getHoraDeCumplimiento()
	{
		return horaDeCumplimiento;
	}

	public void setHoraDeCumplimiento(Time horaDeCumplimiento)
	{
		this.horaDeCumplimiento = horaDeCumplimiento;
	}

	public AlumnoDTO getAlumno()
	{
		return alumno;
	}

	public void setAlumno(AlumnoDTO alumno)
	{
		this.alumno = alumno;
	}

	public CursadaDTO getCursada()
	{
		return cursada;
	}

	public void setCursada(CursadaDTO cursada)
	{
		this.cursada = cursada;
	}

	public PersonalAdministrativoDTO getAdministrativoAsignado()
	{
		return administrativoAsignado;
	}

	public void setAdministrativoAsignado(PersonalAdministrativoDTO administrativoAsignado)
	{
		this.administrativoAsignado = administrativoAsignado;
	}

	public EstadoEventoDTO getEstado()
	{
		return estado;
	}

	public void setEstado(EstadoEventoDTO estado)
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
		if(!(o instanceof EventoInasistenciaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((EventoInasistenciaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFechaDeInasistencia();
		ret = ret + ", " + getHoraDeCumplimiento();
		ret = ret + ", " + getAlumno();
		ret = ret + ", " + getCursada();
		ret = ret + ", " + getAdministrativoAsignado();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
