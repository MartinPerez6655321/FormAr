package modelo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dto.CursoDTO;
import dto.EstadoEventoDTO;
import dto.EventoInasistenciaDTO;
import dto.EventoInteresadoDTO;
import dto.EventoSupervisorDTO;
import dto.InteresadoDTO;
import dto.NotificacionDTO;
import dto.PersonalAdministrativoDTO;
import observer.AbstractObservable;
import persistencia.dao.mysql.EstadoEventoDAOMYSQL;
import persistencia.dao.mysql.EventoInasistenciaDAOMYSQL;
import persistencia.dao.mysql.EventoInteresadoDAOMYSQL;
import persistencia.dao.mysql.EventoSupervisorDAOMYSQL;

public class ModeloEventos extends AbstractObservable{
	
	private EventoInteresadoDAOMYSQL eventoInteresadoDao;
	private EventoSupervisorDAOMYSQL eventoSupervisorDao;
	private EventoInasistenciaDAOMYSQL eventoInasistenciaDao;
	private EstadoEventoDAOMYSQL estadosEventosDao; 
	
	private List<EventoInteresadoDTO> eventosInteresados;
	private List<EventoSupervisorDTO> eventosSupervisor;
	private List<EventoInasistenciaDTO> eventosInasistencias;
	private List<EstadoEventoDTO> estadosEvento;
	
	private static ModeloEventos instance;
	
	public static ModeloEventos getInstance()
	{
		if(instance == null)
			instance = new ModeloEventos();
		return instance;
	}
	
	private ModeloEventos()
	{
		eventoInteresadoDao=EventoInteresadoDAOMYSQL.getInstance();
		eventoSupervisorDao=EventoSupervisorDAOMYSQL.getInstance();
		eventoInasistenciaDao=EventoInasistenciaDAOMYSQL.getInstance();
		estadosEventosDao=EstadoEventoDAOMYSQL.getInstance();
		
		actualizarListas();
	}
	
	public List<EventoInteresadoDTO> getEventosInteresados(){
		
		return eventosInteresados;

	}
	
	public List<EventoInasistenciaDTO> getEventosInasistencia(){
		
		return eventosInasistencias;
	}
	
	public List<EventoSupervisorDTO> getEventosSupervisor()
	{
		
		return eventosSupervisor;
	}
	
	public boolean agregarEventoInteresado(EventoInteresadoDTO eventoInteresado)
	{
		if(eventoInteresadoDao.create(eventoInteresado))
		{
			eventosInteresados.add(eventoInteresado);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarEventoInteresado(EventoInteresadoDTO eventoInteresado)
	{
		if(eventoInteresadoDao.delete(eventoInteresado))
		{
			eventosInteresados.remove(eventoInteresado);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarEventoInteresado(EventoInteresadoDTO evento)
	{
		if(eventoInteresadoDao.update(evento))
		{
			for (NotificacionDTO notificacion : ModeloNotificaciones.getInstance().getNotificaciones()) 
			{
				if (notificacion.getEventoInteresado()!=null && notificacion.getEventoInteresado().getId().equals(evento.getId()))
				{
					notificacion.setEventoInteresado(evento);
					notificacion.setUsuario(ModeloPersonas.getInstance().getUsuarioPersona(evento.getAdministrativoAsignado().getPersona()));
					notificacion.setDescripcion("Se le asigno la tarea de: "+evento.getDescripcion());
					notificacion.setFecha(evento.getFechaDeCumplimiento());
					notificacion.setHora(evento.getHoraDeCumplimiento());
					
					ModeloNotificaciones.getInstance().modificarNotificacion(notificacion);
				}
					
				
			}
			
			
			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}
	
	public boolean agregarEventoSupervisor(EventoSupervisorDTO eventoSupervisor)
	{
		if(eventoSupervisorDao.create(eventoSupervisor))
		{
			eventosSupervisor.add(eventoSupervisor);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarEventoSupervisor(EventoSupervisorDTO eventoSupervisor)
	{
		if(eventoSupervisorDao.delete(eventoSupervisor))
		{
			eventosSupervisor.remove(eventoSupervisor);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarEventoSupervisor(EventoSupervisorDTO evento)
	{
		if(eventoSupervisorDao.update(evento))
		{
			
			for (NotificacionDTO notificacion : ModeloNotificaciones.getInstance().getNotificaciones()) 
			{
				if (notificacion.getEventoSupervisor()!=null && notificacion.getEventoSupervisor().getId().equals(evento.getId()))
				{
					notificacion.setEventoSupervisor(evento);
					notificacion.setUsuario(ModeloPersonas.getInstance().getUsuarioPersona(evento.getAdministrativoAsignado().getPersona()));
					notificacion.setDescripcion("Se le asigno la tarea de: "+evento.getDescripcion());
					notificacion.setFecha(evento.getFechaDeCumplimiento());
					notificacion.setHora(evento.getHoraDeCumplimiento());
					ModeloNotificaciones.getInstance().modificarNotificacion(notificacion);
				}
					
				
			}
			
			
			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}
	
	public boolean agregarEventoInasistencia(EventoInasistenciaDTO eventoInasistencia)
	{
		if(eventoInasistenciaDao.create(eventoInasistencia))
		{
			eventosInasistencias.add(eventoInasistencia);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarEventoInasistencia(EventoInasistenciaDTO eventoInasistencia)
	{
		if(eventoInasistenciaDao.delete(eventoInasistencia))
		{
			eventosInasistencias.remove(eventoInasistencia);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarEventoInasistencia(EventoInasistenciaDTO evento)
	{
		if(eventoInasistenciaDao.update(evento))
		{
			for (NotificacionDTO notificacion : ModeloNotificaciones.getInstance().getNotificaciones()) 
			{
				if (notificacion.getEventoInasistencia()!=null && notificacion.getEventoInasistencia().getId().equals(evento.getId()))
				{
					notificacion.setEventoInasistencia(evento);
					notificacion.setUsuario(ModeloPersonas.getInstance().getUsuarioPersona(evento.getAdministrativoAsignado().getPersona()));
					notificacion.setFecha(evento.getFechaDeInasistencia());
					notificacion.setHora(evento.getHoraDeCumplimiento());
					notificacion.setCursada(evento.getCursada());
					
					ModeloNotificaciones.getInstance().modificarNotificacion(notificacion);
				}
					
				
			}
			
			
			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
	}
	
	public EventoInteresadoDTO getEventoInteresadoPorEventoGeneralPorId(int id) {
		for(EventoInteresadoDTO e:this.getEventosInteresados()) {
			if(e.getId()==id) {
				return e;
			}
		}
		return null;
	}

	public EventoSupervisorDTO getEventoSupervisorPorEventoGeneralPorId(int id) {
		for(EventoSupervisorDTO e:this.getEventosSupervisor()) {
			if(e.getId()==id) {
				return e;
			}
		}
		return null;
		
	}
	
	public EventoInasistenciaDTO getEventoInasistenciaPorEventoGeneralPorId(int id) {
		for(EventoInasistenciaDTO e:this.getEventosInasistencia()) {
			if(e.getId()==id) {
				return e;
			}
		}
		return null;
		
	}

	public List<EventoSupervisorDTO> getEventosSupervisorPorAdministrativo(PersonalAdministrativoDTO administrativo) {
		List<EventoSupervisorDTO> ret=new ArrayList<>();
		
		for(EventoSupervisorDTO e:this.getEventosSupervisor()) {
			if(e.getAdministrativoAsignado()!=null && e.getAdministrativoAsignado().getId()==administrativo.getId())
			{
				ret.add(e);
			}
		}
		
		return ret;
	}
	
	public List<EventoInteresadoDTO> getEventosInteresadosPorAdministrativo(PersonalAdministrativoDTO administrativo) {
		List<EventoInteresadoDTO> ret=new ArrayList<>();
		
		for(EventoInteresadoDTO e:this.getEventosInteresados()) {
			if(e.getAdministrativoAsignado()!=null && e.getAdministrativoAsignado().getId()==administrativo.getId())
			{
				ret.add(e);
			}
		}
		
		return ret;
	}
	
	public List<EventoInasistenciaDTO> getEventosInasistenciasPorAdministrativo(PersonalAdministrativoDTO administrativo) {
		List<EventoInasistenciaDTO> ret=new ArrayList<>();
		
		for(EventoInasistenciaDTO e:this.getEventosInasistencia()) {
			if(e.getAdministrativoAsignado()!=null && e.getAdministrativoAsignado().getId()==administrativo.getId())
			{
				ret.add(e);
			}
		}
		
		return ret;
	}

	public List<EventoInteresadoDTO> getEventosInteresadoPorInteresado(InteresadoDTO interesado) {
		List<EventoInteresadoDTO> ret=new ArrayList<>();
		
		for(EventoInteresadoDTO e:this.getEventosInteresados()) {
			if(e.getInteresado().getId()==interesado.getId()) {
				ret.add(e);
			}
		}
		
		return ret;
	}
	public List<EstadoEventoDTO> getEstadosEventos(){
		return estadosEvento;
	}
	

	
	private void actualizarListas() 
	{
		eventosInteresados = eventoInteresadoDao.readAll();
		eventosSupervisor = eventoSupervisorDao.readAll();
		eventosInasistencias = eventoInasistenciaDao.readAll();
		estadosEvento = estadosEventosDao.readAll();
	}
}
