package dto;

public class PersonalAdministrativoDTO
{

	private Integer id;
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
		if(!(o instanceof PersonalAdministrativoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((PersonalAdministrativoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getPersona();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
