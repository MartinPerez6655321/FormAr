package dto;

public class UsuarioDTO
{

	private Integer id;
	private String email;
	private String password;
	private PersonaDTO persona;
	private Boolean administrador;
	private Boolean supervisor;
	private Boolean administrativo;
	private Boolean instructor;
	private Boolean alumno;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public PersonaDTO getPersona()
	{
		return persona;
	}

	public void setPersona(PersonaDTO persona)
	{
		this.persona = persona;
	}

	public Boolean getAdministrador()
	{
		return administrador;
	}

	public void setAdministrador(Boolean administrador)
	{
		this.administrador = administrador;
	}

	public Boolean getSupervisor()
	{
		return supervisor;
	}

	public void setSupervisor(Boolean supervisor)
	{
		this.supervisor = supervisor;
	}

	public Boolean getAdministrativo()
	{
		return administrativo;
	}

	public void setAdministrativo(Boolean administrativo)
	{
		this.administrativo = administrativo;
	}

	public Boolean getInstructor()
	{
		return instructor;
	}

	public void setInstructor(Boolean instructor)
	{
		this.instructor = instructor;
	}

	public Boolean getAlumno()
	{
		return alumno;
	}

	public void setAlumno(Boolean alumno)
	{
		this.alumno = alumno;
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
		if(!(o instanceof UsuarioDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((UsuarioDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getEmail();
		ret = ret + ", " + getPassword();
		ret = ret + ", " + getPersona();
		ret = ret + ", " + getAdministrador();
		ret = ret + ", " + getSupervisor();
		ret = ret + ", " + getAdministrativo();
		ret = ret + ", " + getInstructor();
		ret = ret + ", " + getAlumno();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
