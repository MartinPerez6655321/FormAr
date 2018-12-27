package dto;

import java.util.Date;

public class InscripcionAlumnoDTO
{

	private Integer id;
	private Date fecha;
	private AlumnoDTO alumno;
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
		if(!(o instanceof InscripcionAlumnoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((InscripcionAlumnoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFecha();
		ret = ret + ", " + getAlumno();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
