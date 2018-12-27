package dto;

import java.sql.Time;
import java.util.Date;

public class EventoSupervisorDTO
{

	private Integer id;
	private String descripcion;
	private Date fechaDeCumplimiento;
	private Time horaDeCumplimiento;
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
		if(!(o instanceof EventoSupervisorDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((EventoSupervisorDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getDescripcion();
		ret = ret + ", " + getFechaDeCumplimiento();
		ret = ret + ", " + getHoraDeCumplimiento();
		ret = ret + ", " + getAdministrativoAsignado();
		ret = ret + ", " + getSupervisor();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
