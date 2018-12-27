package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import dto.EventoInteresadoDTO;
import modelo.ModeloEventos;
import presentacion.reportes.ReporteGeneralInteracciones;
import presentacion.vista.VistaReporteInteraccionesCurso;

public class ControladorVistaReporteGeneralInteraccionesCurso {
	
	private ModeloEventos modeloEventos;
	private VistaReporteInteraccionesCurso vista;
	
	public ControladorVistaReporteGeneralInteraccionesCurso(VistaReporteInteraccionesCurso vistaReporteInteraccionesCurso) {
		this.modeloEventos=ModeloEventos.getInstance();
		this.vista=vistaReporteInteraccionesCurso;
		this.llenarComboBox();
		this.vista.getBtnGenerarReporte().addActionListener(e->generarReporte());
		
	}

	private void generarReporte() {
		ReporteGeneralInteracciones reporteInteracciones = new ReporteGeneralInteracciones(this.vista.getComboBoxCurso().getSelectedItem().toString());
		reporteInteracciones.mostrar();
	}

	private void llenarComboBox() {
		List<String> cursos=new ArrayList<>();
		for(EventoInteresadoDTO e: modeloEventos.getEventosInteresados()) {
			if(e.getCurso()!=null && !cursos.contains(e.getCurso().getNombre())) {
				cursos.add(e.getCurso().getNombre());
			}
		}
		for(String e:cursos) {
			this.vista.getComboBoxCurso().addItem(e);
		}
		
	}

}
