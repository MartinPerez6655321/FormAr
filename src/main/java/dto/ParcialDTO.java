package dto;

public class ParcialDTO
{

	private Integer id;
	private AlumnoDTO alumno;
	private Integer nota;
	private String observaciones;
	private EstadoEvaluacionDTO estado;
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

	public Integer getNota()
	{
		return nota;
	}

	public void setNota(Integer nota)
	{
		this.nota = nota;
	}

	public String getObservaciones()
	{
		return observaciones;
	}

	public void setObservaciones(String observaciones)
	{
		this.observaciones = observaciones;
	}

	public EstadoEvaluacionDTO getEstado()
	{
		return estado;
	}

	public void setEstado(EstadoEvaluacionDTO estado)
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
		if(!(o instanceof ParcialDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((ParcialDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getAlumno();
		ret = ret + ", " + getNota();
		ret = ret + ", " + getObservaciones();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
