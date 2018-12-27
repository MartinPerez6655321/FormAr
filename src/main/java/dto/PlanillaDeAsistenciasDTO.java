package dto;

import java.util.List;

import dto.AsistenciaDTO;

import java.util.Date;

public class PlanillaDeAsistenciasDTO
{

	private Integer id;
	private Date fecha;
	private HorarioDTO horario;
	private List<AsistenciaDTO> asistencias;
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

	public HorarioDTO getHorario()
	{
		return horario;
	}

	public void setHorario(HorarioDTO horario)
	{
		this.horario = horario;
	}

	public List<AsistenciaDTO> getAsistencias()
	{
		return asistencias;
	}

	public void setAsistencias(List<AsistenciaDTO> asistencias)
	{
		this.asistencias = asistencias;
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
		if(!(o instanceof PlanillaDeAsistenciasDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((PlanillaDeAsistenciasDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getFecha();
		ret = ret + ", " + getHorario();
		ret = ret + ", " + getAsistencias();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
