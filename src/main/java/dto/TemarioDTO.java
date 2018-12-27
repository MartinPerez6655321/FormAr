package dto;

import java.util.List;

import dto.TemaDTO;

public class TemarioDTO
{

	private Integer id;
	private String descripcion;
	private List<TemaDTO> temas;
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

	public List<TemaDTO> getTemas()
	{
		return temas;
	}

	public void setTemas(List<TemaDTO> temas)
	{
		this.temas = temas;
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
		if(!(o instanceof TemarioDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((TemarioDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getDescripcion();
		ret = ret + ", " + getTemas();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
