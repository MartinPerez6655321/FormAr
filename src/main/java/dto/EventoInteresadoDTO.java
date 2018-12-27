package dto;

import java.sql.Time;
import java.util.Date;

public class EventoInteresadoDTO
{

	private Integer id;
	private CursoDTO curso;
	private String descripcion;
	private Date fechaDeLlamado;
	private Time horaDeLlamado;
	private Date fechaDeCumplimiento;
	private Time horaDeCumplimiento;
	private PersonalAdministrativoDTO personalAdministrativo;
	private InteresadoDTO interesado;
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

	public CursoDTO getCurso()
	{
		return curso;
	}

	public void setCurso(CursoDTO curso)
	{
		this.curso = curso;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public Date getFechaDeLlamado()
	{
		return fechaDeLlamado;
	}

	public void setFechaDeLlamado(Date fechaDeLlamado)
	{
		this.fechaDeLlamado = fechaDeLlamado;
	}

	public Time getHoraDeLlamado()
	{
		return horaDeLlamado;
	}

	public void setHoraDeLlamado(Time horaDeLlamado)
	{
		this.horaDeLlamado = horaDeLlamado;
	}

	public Date getFechaDeCumplimiento()
	{
		return fechaDeCumplimiento;
	}

	public void setFechaDeCumplimiento(Date fechaDeCumplimiento)
	{
		this.fechaDeCumplimiento = fechaDeCumplimiento;
	}

	public Time getHoraDeCumplimiento()
	{
		return horaDeCumplimiento;
	}

	public void setHoraDeCumplimiento(Time horaDeCumplimiento)
	{
		this.horaDeCumplimiento = horaDeCumplimiento;
	}

	public PersonalAdministrativoDTO getPersonalAdministrativo()
	{
		return personalAdministrativo;
	}

	public void setPersonalAdministrativo(PersonalAdministrativoDTO personalAdministrativo)
	{
		this.personalAdministrativo = personalAdministrativo;
	}

	public InteresadoDTO getInteresado()
	{
		return interesado;
	}

	public void setInteresado(InteresadoDTO interesado)
	{
		this.interesado = interesado;
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
		if(!(o instanceof EventoInteresadoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((EventoInteresadoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getCurso();
		ret = ret + ", " + getDescripcion();
		ret = ret + ", " + getFechaDeLlamado();
		ret = ret + ", " + getHoraDeLlamado();
		ret = ret + ", " + getFechaDeCumplimiento();
		ret = ret + ", " + getHoraDeCumplimiento();
		ret = ret + ", " + getPersonalAdministrativo();
		ret = ret + ", " + getInteresado();
		ret = ret + ", " + getAdministrativoAsignado();
		ret = ret + ", " + getSupervisor();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
