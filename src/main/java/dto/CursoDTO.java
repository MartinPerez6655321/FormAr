package dto;

public class CursoDTO
{

	private Integer id;
	private String nombre;
	private Integer precio;
	private String codigo;
	private String descripcion;
	private Integer horas;
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

	public Integer getPrecio()
	{
		return precio;
	}

	public void setPrecio(Integer precio)
	{
		this.precio = precio;
	}

	public String getCodigo()
	{
		return codigo;
	}

	public void setCodigo(String codigo)
	{
		this.codigo = codigo;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public Integer getHoras()
	{
		return horas;
	}

	public void setHoras(Integer horas)
	{
		this.horas = horas;
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
		if(!(o instanceof CursoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((CursoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getNombre();
		ret = ret + ", " + getPrecio();
		ret = ret + ", " + getCodigo();
		ret = ret + ", " + getDescripcion();
		ret = ret + ", " + getHoras();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
