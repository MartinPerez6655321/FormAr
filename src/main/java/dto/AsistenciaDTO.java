package dto;

public class AsistenciaDTO
{

	private Integer id;
	private AlumnoDTO alumno;
	private EstadoAsistenciaDTO estado;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public AlumnoDTO getAlumno()
	{
		return alumno;
	}

	public void setAlumno(AlumnoDTO alumno)
	{
		this.alumno = alumno;
	}

	public EstadoAsistenciaDTO getEstado()
	{
		return estado;
	}

	public void setEstado(EstadoAsistenciaDTO estado)
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
		if(!(o instanceof AsistenciaDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((AsistenciaDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getAlumno();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
