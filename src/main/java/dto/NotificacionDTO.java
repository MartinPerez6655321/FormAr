package dto;

import java.sql.Time;
import java.util.Date;

public class NotificacionDTO
{

	private Integer id;
	private String titulo;
	private String descripcion;
	private EventoInasistenciaDTO eventoInasistencia;
	private EventoSupervisorDTO eventoSupervisor;
	private EventoInteresadoDTO eventoInteresado;
	private UsuarioDTO usuario;
	private CursadaDTO cursada;
	private RecadoDTO recado;
	private Date fecha;
	private Time hora;
	private String codigoVista;
	private String codigoTab;
	private Boolean visto;
	private Boolean disponibleEnSistema;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getTitulo()
	{
		return titulo;
	}

	public void setTitulo(String titulo)
	{
		this.titulo = titulo;
	}

	public String getDescripcion()
	{
		return descripcion;
	}

	public void setDescripcion(String descripcion)
	{
		this.descripcion = descripcion;
	}

	public EventoInasistenciaDTO getEventoInasistencia()
	{
		return eventoInasistencia;
	}

	public void setEventoInasistencia(EventoInasistenciaDTO eventoInasistencia)
	{
		this.eventoInasistencia = eventoInasistencia;
	}

	public EventoSupervisorDTO getEventoSupervisor()
	{
		return eventoSupervisor;
	}

	public void setEventoSupervisor(EventoSupervisorDTO eventoSupervisor)
	{
		this.eventoSupervisor = eventoSupervisor;
	}

	public EventoInteresadoDTO getEventoInteresado()
	{
		return eventoInteresado;
	}

	public void setEventoInteresado(EventoInteresadoDTO eventoInteresado)
	{
		this.eventoInteresado = eventoInteresado;
	}

	public UsuarioDTO getUsuario()
	{
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario)
	{
		this.usuario = usuario;
	}

	public CursadaDTO getCursada()
	{
		return cursada;
	}

	public void setCursada(CursadaDTO cursada)
	{
		this.cursada = cursada;
	}

	public RecadoDTO getRecado()
	{
		return recado;
	}

	public void setRecado(RecadoDTO recado)
	{
		this.recado = recado;
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

	public String getCodigoVista()
	{
		return codigoVista;
	}

	public void setCodigoVista(String codigoVista)
	{
		this.codigoVista = codigoVista;
	}

	public String getCodigoTab()
	{
		return codigoTab;
	}

	public void setCodigoTab(String codigoTab)
	{
		this.codigoTab = codigoTab;
	}

	public Boolean getVisto()
	{
		return visto;
	}

	public void setVisto(Boolean visto)
	{
		this.visto = visto;
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
		if(!(o instanceof NotificacionDTO))
			return false;
		if(getId()==null)
			return false;
		return getId().equals(((NotificacionDTO)o).getId());
	}
	
	@Override
	public String toString()
	{
		String ret = "";
		ret = ret + getTitulo();
		ret = ret + ", " + getDescripcion();
		ret = ret + ", " + getEventoInasistencia();
		ret = ret + ", " + getEventoSupervisor();
		ret = ret + ", " + getEventoInteresado();
		ret = ret + ", " + getUsuario();
		ret = ret + ", " + getCursada();
		ret = ret + ", " + getRecado();
		ret = ret + ", " + getFecha();
		ret = ret + ", " + getHora();
		ret = ret + ", " + getCodigoVista();
		ret = ret + ", " + getCodigoTab();
		ret = ret + ", " + getVisto();
		ret = ret + ", " + getDisponibleEnSistema();
		return ret;
	}
}
