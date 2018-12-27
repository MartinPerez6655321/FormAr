package dto;

import java.util.Date;

public class PagoDTO
{

	private Integer id;
	private Date fecha;
	private AlumnoDTO alumno;
	private CuotaDTO cuota;
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

	public AlumnoDTO getAlumno()
	{
		return alumno;
	}

	public void setAlumno(AlumnoDTO alumno)
	{
		this.alumno = alumno;
	}

	public CuotaDTO getCuota()
	{
		return cuota;
	}

	public void setCuota(CuotaDTO cuota)
	{
		this.cuota = cuota;
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
		if(!(o instanceof PagoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((PagoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFecha();
		ret = ret + ", " + getAlumno();
		ret = ret + ", " + getCuota();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
