package dto;

import java.util.List;

import dto.ParcialDTO;

import java.util.Date;

public class PlanillaDeParcialesDTO
{

	private Integer id;
	private Date fecha;
	private List<ParcialDTO> parciales;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	public List<ParcialDTO> getParciales()
	{
		return parciales;
	}

	public void setParciales(List<ParcialDTO> parciales)
	{
		this.parciales = parciales;
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
		if(!(o instanceof PlanillaDeParcialesDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((PlanillaDeParcialesDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFecha();
		ret = ret + ", " + getParciales();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
