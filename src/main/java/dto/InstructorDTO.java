package dto;

import java.util.List;

import dto.HorarioDTO;

public class InstructorDTO
{

	private Integer id;
	private List<HorarioDTO> disponibilidades;
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

	public List<HorarioDTO> getDisponibilidades()
	{
		return disponibilidades;
	}

	public void setDisponibilidades(List<HorarioDTO> disponibilidades)
	{
		this.disponibilidades = disponibilidades;
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
		if(!(o instanceof InstructorDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((InstructorDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getDisponibilidades();
		ret = ret + ", " + getPersona();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
