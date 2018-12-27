package modelo;

import java.util.List;

import dto.EstadoDeRecadoDTO;
import dto.RecadoDTO;
import dto.UsuarioDTO;
import observer.AbstractObservable;
import persistencia.dao.mysql.EstadoDeRecadoDAOMYSQL;
import persistencia.dao.mysql.RecadoDAOMYSQL;

public class ModeloRecados extends AbstractObservable{
	
	private EstadoDeRecadoDAOMYSQL estadoDeRecadoDao;
	private RecadoDAOMYSQL recadoDao;
	

	private List<RecadoDTO> recados;
	private List<EstadoDeRecadoDTO> estadoDeRecados;
	
	private static ModeloRecados instance;
	
	
	public static ModeloRecados getInstance()
	{
		if(instance == null)
			instance = new ModeloRecados();
		return instance;
	}
	public ModeloRecados() {
		estadoDeRecadoDao=EstadoDeRecadoDAOMYSQL.getInstance();
		recadoDao=RecadoDAOMYSQL.getInstance();
		recados = recadoDao.readAll();
		estadoDeRecados = estadoDeRecadoDao.readAll();
		
	}
	
	public List<RecadoDTO> getRecados()
	{
		return recados;
	}

	public List<EstadoDeRecadoDTO> getEstadoRecados()
	{
		return estadoDeRecados;
	}
	
	public boolean modificarRecado(RecadoDTO recado)
	{
		if(recadoDao.update(recado))
		{
			for (RecadoDTO recadoDto: this.recados)
			{
				if (recado.getId().equals(recadoDto.getId()))
				{
					recadoDto=recado;
					break;
				}
			}
			
			
			invalidateObservers();
			return false;
		} else 
		{
			
			invalidateObservers();
			return true;
			
		}
	}
	
	public boolean agregarRecado(RecadoDTO recado)
	{
		if(recadoDao.create(recado))
		{
			recados.add(recado);
			invalidateObservers();
			return true;
		} else {
			return false;
		}
	}
	
	public void actualizarRecados()
	{
		recados = recadoDao.readAll();
		invalidateObservers();
	}
	public int cantidadDeRecadosNoVistos() 
	{
		this.recados = this.recadoDao.readAll();
		UsuarioDTO user = ModeloPersonas.getInstance().getUsuarioActual();
		int cantRecados = 0;
		for(RecadoDTO recado:this.recados)
		{
			if(recado.getReceptor().equals(user) && recado.getEstado().getId().equals(2)) 
			{ 
				cantRecados++;
			}
		}
		return cantRecados;
	}
	
}
