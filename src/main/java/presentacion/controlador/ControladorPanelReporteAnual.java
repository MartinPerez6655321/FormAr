package presentacion.controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import dto.AlumnoDTO;
import dto.CursadaDTO;
import modelo.ModeloCursos;
import presentacion.reportes.ReporteAlumnoAnual;
import presentacion.vista.VistaPanelReporteAnual;
import util.Strings;

public class ControladorPanelReporteAnual {
	VistaPanelReporteAnual vista;
	AlumnoDTO alumno;
	ModeloCursos modelo;

	public ControladorPanelReporteAnual(VistaPanelReporteAnual vistaPanelReporteAnual, AlumnoDTO alumno) {
		this.vista=vistaPanelReporteAnual;
		this.alumno=alumno;
		modelo=ModeloCursos.getInstance();
		
		
		llenarComboBox();
		this.vista.getBtnGenerarReporte().addActionListener(e->generarReporteAnual());
		
		
	}

	private void generarReporteAnual() {
		ReporteAlumnoAnual reporteAlumno = new ReporteAlumnoAnual(alumno,this.vista.getComboBox().getSelectedItem().toString());
		reporteAlumno.mostrar();
	}

	private void llenarComboBox() {
		
		List<String> años=new ArrayList<>();
		for(CursadaDTO e: modelo.getCursadasPorAlumno(alumno)) {
			if(!años.contains(getStringYearOfDate(e.getFin())) && e.getEstado().getId()==4) { 
				años.add(getStringYearOfDate(e.getFin()));
				}
		}
		
		for(String e:años) {
			this.vista.getComboBox().addItem(e);
		}
		
	}

	private String getStringYearOfDate(Date date) {
		String retConHoras=Strings.formatoFecha(date);
		return retConHoras.substring(6, 10);
	}
	
}
