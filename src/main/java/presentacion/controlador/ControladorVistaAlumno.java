package presentacion.controlador;


import presentacion.vista.PanelCursadasAlumno;
import presentacion.vista.VistaAlumno;

public class ControladorVistaAlumno {
	
	private PanelCursadasAlumno panelCursadasAlumno;
	

	public ControladorVistaAlumno(VistaAlumno vistaAlumno) {
		panelCursadasAlumno=new PanelCursadasAlumno();
		new ControladorPanelCursadasAlumno(panelCursadasAlumno);
		
		vistaAlumno.agregarVista("Cursadas", panelCursadasAlumno);
		
	}

}
