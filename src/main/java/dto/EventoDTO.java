package dto;

import java.sql.Time;
import java.util.Date;

public class EventoDTO
{

	private Integer id;
	private String descripcion;
	private Date fecha;
	private Time hora;
	private PersonalAdministrativoDTO personalAdministrativo;
	private PersonaDTO interesado;
	private AlumnoDTO alumno;
	private PersonalAdministrativoDTO administrativoAsignado;
	private SupervisorDTO supervisor;
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

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	public Time getHora()
	{
		return hora;
	}

	public void setHora(Time hora)
	{
		this.hora = hora;
	}

	public PersonalAdministrativoDTO getPersonalAdministrativo()
	{
		return personalAdministrativo;
	}

	public void setPersonalAdministrativo(PersonalAdministrativoDTO personalAdministrativo)
	{
		this.personalAdministrativo = personalAdministrativo;
	}

	public PersonaDTO getInteresado()
	{
		return interesado;
	}

	public void setInteresado(PersonaDTO interesado)
	{
		this.interesado = interesado;
	}

	public AlumnoDTO getAlumno()
	{
		return alumno;
	}

	public void setAlumno(AlumnoDTO alumno)
	{
		this.alumno = alumno;
	}

	public PersonalAdministrativoDTO getAdministrativoAsignado()
	{
		return administrativoAsignado;
	}

	public void setAdministrativoAsignado(PersonalAdministrativoDTO administrativoAsignado)
	{
		this.administrativoAsignado = administrativoAsignado;
	}

	public SupervisorDTO getSupervisor()
	{
		return supervisor;
	}

	public void setSupervisor(SupervisorDTO supervisor)
	{
		this.supervisor = supervisor;
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
		if(!(o instanceof EventoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((EventoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getDescripcion();
		ret = ret + ", " + getFecha();
		ret = ret + ", " + getHora();
		ret = ret + ", " + getPersonalAdministrativo();
		ret = ret + ", " + getInteresado();
		ret = ret + ", " + getAlumno();
		ret = ret + ", " + getAdministrativoAsignado();
		ret = ret + ", " + getSupervisor();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
