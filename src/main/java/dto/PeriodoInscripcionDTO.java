package dto;

import java.util.Date;

public class PeriodoInscripcionDTO
{

	private Integer id;
	private Date inicio;
	private Date fin;
	private EstadoDePeriodoDeInscripcionDTO estado;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getInicio()
	{
		return inicio;
	}

	public void setInicio(Date inicio)
	{
		this.inicio = inicio;
	}

	public Date getFin()
	{
		return fin;
	}

	public void setFin(Date fin)
	{
		this.fin = fin;
	}

	public EstadoDePeriodoDeInscripcionDTO getEstado()
	{
		return estado;
	}

	public void setEstado(EstadoDePeriodoDeInscripcionDTO estado)
	{
		this.estado = estado;
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
		if(!(o instanceof PeriodoInscripcionDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((PeriodoInscripcionDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getInicio();
		ret = ret + ", " + getFin();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
