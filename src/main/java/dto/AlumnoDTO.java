package dto;

import java.util.Date;

public class AlumnoDTO
{

	private Integer id;
	private Date fechaDeCreacion;
	private String legajo;
	private PersonaDTO persona;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getFechaDeCreacion()
	{
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion)
	{
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public String getLegajo()
	{
		return legajo;
	}

	public void setLegajo(String legajo)
	{
		this.legajo = legajo;
	}

	public PersonaDTO getPersona()
	{
		return persona;
	}

	public void setPersona(PersonaDTO persona)
	{
		this.persona = persona;
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
		if(!(o instanceof AlumnoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((AlumnoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFechaDeCreacion();
		ret = ret + ", " + getLegajo();
		ret = ret + ", " + getPersona();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
