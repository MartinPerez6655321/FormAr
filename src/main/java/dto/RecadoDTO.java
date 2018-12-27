package dto;

import java.sql.Time;
import java.util.Date;

public class RecadoDTO
{

	private Integer id;
	private String mensaje;
	private UsuarioDTO emisor;
	private UsuarioDTO receptor;
	private String asunto;
	private Date fecha;
	private Time hora;
	private EstadoDeRecadoDTO estado;
	private Boolean disponibilidadEmisor;
	private Boolean disponibilidadReceptor;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getMensaje()
	{
		return mensaje;
	}

	public void setMensaje(String mensaje)
	{
		this.mensaje = mensaje;
	}

	public UsuarioDTO getEmisor()
	{
		return emisor;
	}

	public void setEmisor(UsuarioDTO emisor)
	{
		this.emisor = emisor;
	}

	public UsuarioDTO getReceptor()
	{
		return receptor;
	}

	public void setReceptor(UsuarioDTO receptor)
	{
		this.receptor = receptor;
	}

	public String getAsunto()
	{
		return asunto;
	}

	public void setAsunto(String asunto)
	{
		this.asunto = asunto;
	}

	public Date getFecha()
	{
		return fecha;
	}

	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}

	public Time getHora()
	{
		return hora;
	}

	public void setHora(Time hora)
	{
		this.hora = hora;
	}

	public EstadoDeRecadoDTO getEstado()
	{
		return estado;
	}

	public void setEstado(EstadoDeRecadoDTO estado)
	{
		this.estado = estado;
	}

	public Boolean getDisponibilidadEmisor()
	{
		return disponibilidadEmisor;
	}

	public void setDisponibilidadEmisor(Boolean disponibilidadEmisor)
	{
		this.disponibilidadEmisor = disponibilidadEmisor;
	}

	public Boolean getDisponibilidadReceptor()
	{
		return disponibilidadReceptor;
	}

	public void setDisponibilidadReceptor(Boolean disponibilidadReceptor)
	{
		this.disponibilidadReceptor = disponibilidadReceptor;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof RecadoDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((RecadoDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getMensaje();
		ret = ret + ", " + getEmisor();
		ret = ret + ", " + getReceptor();
		ret = ret + ", " + getAsunto();
		ret = ret + ", " + getFecha();
		ret = ret + ", " + getHora();
		ret = ret + ", " + getEstado();
		ret = ret + ", " + getDisponibilidadEmisor();
		ret = ret + ", " + getDisponibilidadReceptor();
		return ret;
	}
}
