package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import dto.EventoInteresadoDTO;
import dto.InteresadoDTO;
import modelo.ModeloEventos;
import presentacion.reportes.ReporteInteracciones;
import presentacion.vista.VistaReporteInteraccionesCurso;

public class ControladorVistaReporteInteraccionesCurso {
	
	private ModeloEventos modelo;
	private VistaReporteInteraccionesCurso vista;
	private InteresadoDTO interesado;
	
	
	
	public ControladorVistaReporteInteraccionesCurso(VistaReporteInteraccionesCurso vistaReporteInteraccionesCurso,InteresadoDTO interesado) {
		this.vista=vistaReporteInteraccionesCurso;
		this.interesado=interesado;
		this.modelo=ModeloEventos.getInstance();
		this.llenarComboBox();
		this.vista.getBtnGenerarReporte().addActionListener(e->generarReporte());
	}



	private void generarReporte() {
		ReporteInteracciones reporteInteracciones = new ReporteInteracciones(interesado,this.vista.getComboBoxCurso().getSelectedItem().toString());
		reporteInteracciones.mostrar();
	}



	private void llenarComboBox() {
		List<String> cursos=new ArrayList<>();
		for(EventoInteresadoDTO e:modelo.getEventosInteresadoPorInteresado(interesado)){
			if(e.getCurso()!=null && !cursos.contains(e.getCurso().getNombre())) {
				cursos.add(e.getCurso().getNombre());
			}
		}
		for(String e:cursos) {
			this.vista.getComboBoxCurso().addItem(e);
		}
	}

}
