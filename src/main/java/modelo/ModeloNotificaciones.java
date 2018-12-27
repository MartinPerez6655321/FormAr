package modelo;

import java.util.ArrayList;
import java.util.List;

import dto.EventoSupervisorDTO;
import dto.NotificacionDTO;
import observer.AbstractObservable;
import persistencia.dao.mysql.NotificacionDAOMYSQL;

public class ModeloNotificaciones extends AbstractObservable {


	private NotificacionDAOMYSQL notificacionDao;

	private List<NotificacionDTO> notificaciones;
	private static ModeloNotificaciones instance;
	
	public static ModeloNotificaciones getInstance()
	{
		if(instance == null)
			instance = new ModeloNotificaciones();
		return instance;
	}
	
	private ModeloNotificaciones() 
	{
		notificacionDao= NotificacionDAOMYSQL.getInstance();
		notificaciones=notificacionDao.readAll();
	}
	
	public List<NotificacionDTO> getNotificaciones()
	{				
		return notificaciones;				
	}
	
	public boolean modificarNotificacion(NotificacionDTO notificacion)
	{
		if(this.notificacionDao.update(notificacion))
		{

			invalidateObservers();
			return false;
		} else {
			invalidateObservers();
			return true;
		}
		
		
		
		
	}

	public void agregarNotificacion(NotificacionDTO notificacion) 
	{
		this.notificacionDao.create(notificacion);
		this.notificaciones.add(notificacion);
		invalidateObservers();		
	}

	public boolean eliminarNotificacion(NotificacionDTO notificacion) {
		if(notificacionDao.delete(notificacion))
		{
			notificacionDao.delete(notificacion);
			invalidateObservers();
			return true;
		}
		return false;
		
	}
	public NotificacionDTO getNotificacionPorId(int id)
	{
		for(NotificacionDTO e:this.getNotificaciones()) 
		{
			if(e.getId()==id) {
				return e;
			}
		}
		return null;
	}
	public List<NotificacionDTO> getNotificacionesPorUsuarioActual() 
	{
		List<NotificacionDTO> ret = new ArrayList<>();
		for (NotificacionDTO notificacionDTO : this.getNotificaciones())
		{
			if (notificacionDTO.getUsuario()!=null && notificacionDTO.getUsuario().equals(ModeloPersonas.getInstance().getUsuarioActual()))
				ret.add(notificacionDTO);
		}
		return ret;
		
	}
	
}
