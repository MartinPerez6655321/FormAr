package dto;

public class EstadoEventoDTO
{

	private Integer id;
	private String nombre;
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
		if(!(o instanceof EstadoEventoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((EstadoEventoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getNombre();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
