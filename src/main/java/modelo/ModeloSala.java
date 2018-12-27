package modelo;

import java.util.List;

import dto.CursadaDTO;
import dto.SalaDTO;
import observer.AbstractObservable;
import persistencia.dao.mysql.SalaDAOMYSQL;

public class ModeloSala extends AbstractObservable {

	private SalaDAOMYSQL salaDao;
	private List<SalaDTO> salas;
	private static ModeloSala instance;

	public static ModeloSala getInstance() {
		if (instance == null)
			instance = new ModeloSala();
		return instance;
	}

	private ModeloSala() {
		salaDao = SalaDAOMYSQL.getInstance();
		salas = salaDao.readAll();
	}

	public List<SalaDTO> getSalas() {
		return salas;
	}

	public boolean agregarSala(SalaDTO sala) {
		if (salaDao.create(sala)) {
			salas.add(sala);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean eliminarSala(SalaDTO sala) {
		if (salaDao.delete(sala)) {
			salas.remove(sala);
			invalidateObservers();
			return true;
		}
		return false;
	}

	public boolean modificarSala(SalaDTO sala) {
		if (salaDao.update(sala)) {

			for (CursadaDTO cursada : ModeloCursos.getInstance().getCursadas()) {
				if (cursada.getSala() != null && cursada.getSala().getId().equals(sala.getId())) {
					cursada.setSala(sala);
					ModeloCursos.getInstance().modificarCursada(cursada);
				}
			}

			invalidateObservers();
			return false;

		}

		else {
			invalidateObservers();
			return true;
		}
	}
}
