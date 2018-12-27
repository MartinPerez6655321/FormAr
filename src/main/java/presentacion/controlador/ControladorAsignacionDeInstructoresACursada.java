package presentacion.controlador;

import java.util.LinkedList;
import java.util.List;

import dto.CursadaDTO;
import dto.InstructorDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import modelo.ModeloCursos;
import presentacion.components.listagenerica.ListaGenerica;
import presentacion.components.tabla.TablaGenerica;
import presentacion.vista.VistaAsignacionDeInstructoresACursada;
import presentacion.vista.subcomponentes.InstructorLabel;

public class ControladorAsignacionDeInstructoresACursada implements InvalidationListener
{
	private VistaAsignacionDeInstructoresACursada vista;
	private CursadaDTO cursada;
	private ModeloCursos model = ModeloCursos.getInstance();
	private ListaGenerica<InstructorLabel> listaGenerica;
	
	public ControladorAsignacionDeInstructoresACursada(VistaAsignacionDeInstructoresACursada vistaAsignacionDeSalasACursada, CursadaDTO cursada)
	{
		this.cursada = cursada;

		vista = vistaAsignacionDeSalasACursada;
		model.addListener(this);
		
		vista.getAceptarButton().addActionListener(e -> aceptar());

		listaGenerica = new ListaGenerica<>(() -> 
		{
			InstructorDTO selectedItem = 
					TablaGenerica.seleccionSimpleFiltro(obtenerInstructoresDisponibles(), 
							"Seleccionar instructor", 
							"Instructor", 
							instructor -> instructor.getPersona().getApellido() + ", " + instructor.getPersona().getNombre());
			if(selectedItem == null)
				return null;
			return new InstructorLabel(selectedItem);
		});
		
		vista.setListaGenerica(listaGenerica);
		
		vista.setCursada(cursada);
	}
	
	private List<InstructorDTO> obtenerInstructoresDisponibles()
	{
		List<InstructorDTO> ret = new LinkedList<>(model.getInstructoresDisponiblesPara(cursada));

		for(InstructorLabel il : listaGenerica)
			ret.remove(il.getInstructor());
		
		return ret;
	}
	
	private void aceptar()
	{
		List<InstructorDTO> instructores = new LinkedList<>();
		for(InstructorLabel il : listaGenerica)
			instructores.add(il.getInstructor());
		cursada.setInstructores(instructores);
		model.modificarCursada(cursada);
	}

	@Override
	public void invalidated(Observable observable)
	{
		vista.setCursada(cursada);
	}
}
