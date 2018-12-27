package dto;

import java.util.Date;

public class PagoEmpresaDTO
{

	private Integer id;
	private Date fechaLimite;
	private Date realizado;
	private Integer monto;
	private Integer empresa;
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

	public Date getRealizado()
	{
		return realizado;
	}

	public void setRealizado(Date realizado)
	{
		this.realizado = realizado;
	}

	public Integer getMonto()
	{
		return monto;
	}

	public void setMonto(Integer monto)
	{
		this.monto = monto;
	}

	public Integer getEmpresa()
	{
		return empresa;
	}

	public void setEmpresa(Integer empresa)
	{
		this.empresa = empresa;
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
		if(!(o instanceof PagoEmpresaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((PagoEmpresaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFechaLimite();
		ret = ret + ", " + getRealizado();
		ret = ret + ", " + getMonto();
		ret = ret + ", " + getEmpresa();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
