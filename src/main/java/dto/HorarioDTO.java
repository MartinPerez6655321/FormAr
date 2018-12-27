package dto;

import java.sql.Time;

public class HorarioDTO
{

	private Integer id;
	private Time horaInicio;
	private Time horaFin;
	private DiaDeLaSemanaDTO diaDeLaSemana;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Time getHoraInicio()
	{
		return horaInicio;
	}

	public void setHoraInicio(Time horaInicio)
	{
		this.horaInicio = horaInicio;
	}

	public Time getHoraFin()
	{
		return horaFin;
	}

	public void setHoraFin(Time horaFin)
	{
		this.horaFin = horaFin;
	}

	public DiaDeLaSemanaDTO getDiaDeLaSemana()
	{
		return diaDeLaSemana;
	}

	public void setDiaDeLaSemana(DiaDeLaSemanaDTO diaDeLaSemana)
	{
		this.diaDeLaSemana = diaDeLaSemana;
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
		if(!(o instanceof HorarioDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((HorarioDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getHoraInicio();
		ret = ret + ", " + getHoraFin();
		ret = ret + ", " + getDiaDeLaSemana();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
