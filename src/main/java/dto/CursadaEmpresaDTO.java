package dto;

import java.util.List;

import dto.PagoEmpresaDTO;

public class CursadaEmpresaDTO
{

	private Integer id;
	private CursadaDTO cursada;
	private List<PagoEmpresaDTO> pagos;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public CursadaDTO getCursada()
	{
		return cursada;
	}

	public void setCursada(CursadaDTO cursada)
	{
		this.cursada = cursada;
	}

	public List<PagoEmpresaDTO> getPagos()
	{
		return pagos;
	}

	public void setPagos(List<PagoEmpresaDTO> pagos)
	{
		this.pagos = pagos;
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
		if(!(o instanceof CursadaEmpresaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((CursadaEmpresaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getCursada();
		ret = ret + ", " + getPagos();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
