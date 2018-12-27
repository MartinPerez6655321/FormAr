package presentacion.controlador;

import javax.swing.JOptionPane;
import dto.CursoDTO;
import dto.EventoInteresadoDTO;
import modelo.ModeloCursos;
import modelo.ModeloEventos;
import presentacion.vista.PanelEliminacionDeshabilitacion;

public class ControladorPanelEliminacionDeshabilitacion {
	private PanelEliminacionDeshabilitacion vista;
	private ModeloEventos modeloEventos;
	private ModeloCursos model;
	private CursoDTO curso;

	public ControladorPanelEliminacionDeshabilitacion(PanelEliminacionDeshabilitacion panel, CursoDTO curso) {
		this.vista=panel;
		this.modeloEventos=ModeloEventos.getInstance();
		this.curso=curso;
		this.model=ModeloCursos.getInstance();
		
		this.vista.getBtnDeshabilitar().addActionListener(e->deshabilitar());
		this.vista.getBtnEliminar().addActionListener(e->eliminar());
	}

	private void eliminar() {
		if(!tieneCursadasAsociadas()) {
			if(JOptionPane.showConfirmDialog(null,"<html>¿Est\u00E1 seguro que quiere eliminar este curso?</html>", "Eliminar Curso",JOptionPane.YES_NO_OPTION)==0) {
				
				eliminarEventosCurso(curso);
				eliminarCurso(curso);
	
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "No es posible eliminar este curso porque tiene cursadas asociadas.");
				}
		
	}
	
	private void eliminarEventosCurso(CursoDTO curso) {
		for(EventoInteresadoDTO e:modeloEventos.getEventosInteresados()) {
			if(e.getCurso()!=null && e.getCurso().getId()==curso.getId()) {
				modeloEventos.eliminarEventoInteresado(e);
			}
		}
	}

	private void eliminarCurso(CursoDTO curso) {
		model.eliminarCurso(curso);
	}

	private void deshabilitar() {
		if(!tieneCursadasAsociadas()) {
		if(JOptionPane.showConfirmDialog(null,"<html>¿Est\u00E1 seguro que quiere deshabilitar este curso?</html>", "Deshabilitar Curso",JOptionPane.YES_NO_OPTION)==0) {
			deshabilitarCurso(curso);
			}
		}
		else {
			JOptionPane.showMessageDialog(null, "No es posible deshabilitar este curso porque tiene cursadas asociadas.");
			}
		
	}

	
	
	private void deshabilitarCurso(CursoDTO curso) {
		curso.setDisponibleEnSistema(false);
		model.modificarCurso(curso);
		
	}

	private boolean tieneCursadasAsociadas() {
		return !model.getCursadasPorCurso(curso).isEmpty();
	}
	
	
}
