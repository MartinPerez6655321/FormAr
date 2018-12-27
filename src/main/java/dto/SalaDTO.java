package dto;

public class SalaDTO
{

	private Integer id;
	private String alias;
	private String codigo;
	private Integer capacidad;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getAlias()
	{
		return alias;
	}

	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public Integer getCapacidad()
	{
		return capacidad;
	}

	public void setCapacidad(Integer capacidad)
	{
		this.capacidad = capacidad;
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
		if(!(o instanceof SalaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((SalaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getAlias();
		ret = ret + ", " + getCodigo();
		ret = ret + ", " + getCapacidad();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
