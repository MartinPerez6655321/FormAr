package presentacion.controlador;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import presentacion.components.Modal;
import presentacion.components.listagenerica.ListaGenerica;
import presentacion.vista.subcomponentes.BotonAlumno;


public class ControladorAsignar
{
	public static List<AlumnoDTO> administrarAlumnos(List<AlumnoDTO> alumnos)
	{
		ListaGenerica<BotonAlumno> lista = new ListaGenerica<>(() -> new BotonAlumno(ControladorEdicion.crearAlumno()));
		
		for(AlumnoDTO alumno : alumnos)
			lista.addElement(new BotonAlumno(alumno));
		
		lista.setPreferredSize(new Dimension(250, 300));
		Modal.showDialog(lista, "Elegir alumnos");
		
		List<AlumnoDTO> ret = new LinkedList<>();
		for(BotonAlumno btnAlumno : lista)
			ret.add(btnAlumno.getAlumno());
		return ret;
	}

	public static List<CursadaDTO> administrarCursadas(List<CursadaDTO> cursadas) 
	{
//		ListaGenerica<CursadaDTO> lista = new ListaGenerica<>(() -> new BotonCursadas(ControladorEdicion.crearCursada()));
//		
		return null;
	}
	

	
}
