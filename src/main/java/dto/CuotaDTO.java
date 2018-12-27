package dto;

import java.util.Date;

public class CuotaDTO
{

	private Integer id;
	private Date fechaLimite;
	private Integer monto;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Date getFechaLimite()
	{
		return fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite)
	{
		this.fechaLimite = fechaLimite;
	}

	public Integer getMonto()
	{
		return monto;
	}

	public void setMonto(Integer monto)
	{
		this.monto = monto;
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
		if(!(o instanceof CuotaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((CuotaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFechaLimite();
		ret = ret + ", " + getMonto();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
