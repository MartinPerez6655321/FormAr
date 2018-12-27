package presentacion.controlador;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import presentacion.vista.VentanaInscripcionAlumno;

public class ControladorEdicion
{

	private ControladorEdicion(){}
	
	public static AlumnoDTO crearAlumno()
	{
		ControladorSeleccionDeAlumno contro = new ControladorSeleccionDeAlumno(new VentanaInscripcionAlumno());
		contro.initialize();
		return contro.getAlumnoSelect();
	}
	
	public static CursadaDTO crearCursada()
	{
		return null;
	}
	
	
}