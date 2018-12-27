package dto;

public class PersonaDTO
{

	private Integer id;
	private String nombre;
	private String apellido;
	private String telefono;
	private String email;
	private String dni;
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

	public String getApellido()
	{
		return apellido;
	}

	public void setApellido(String apellido)
	{
		this.apellido = apellido;
	}

	public String getTelefono()
	{
		return telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getDni()
	{
		return dni;
	}

	public void setDni(String dni)
	{
		this.dni = dni;
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
		if(!(o instanceof PersonaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((PersonaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getNombre();
		ret = ret + ", " + getApellido();
		ret = ret + ", " + getTelefono();
		ret = ret + ", " + getEmail();
		ret = ret + ", " + getDni();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
