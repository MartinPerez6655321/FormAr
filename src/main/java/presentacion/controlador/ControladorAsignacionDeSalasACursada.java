package presentacion.controlador;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CursadaDTO;
import dto.HorarioDTO;
import dto.SalaDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import modelo.ModeloCursos;
import modelo.ModeloSala;
import presentacion.components.horarios.Period;
import presentacion.vista.VistaAsignacionDeSalasACursada;

public class ControladorAsignacionDeSalasACursada implements InvalidationListener
{
	private VistaAsignacionDeSalasACursada vista;
	private CursadaDTO cursada;
	private ModeloCursos model;
	private ModeloSala modeloSala;
	private SalaDTO salaSeleccionada;
	private List<SalaDTO> salasDisponibles;
	
	public ControladorAsignacionDeSalasACursada(VistaAsignacionDeSalasACursada vistaAsignacionDeSalasACursada, CursadaDTO cursada)
	{
		this.cursada = cursada;
		salasDisponibles = new LinkedList<>();
		model = ModeloCursos.getInstance();
		modeloSala= ModeloSala.getInstance();

		vista = vistaAsignacionDeSalasACursada;
		model.addListener(this);
		
		invalidated(model);
		
		setSalaSeleccionada(null);
		vista.getSalasList().addListSelectionListener(
			e -> setSalaSeleccionada(vista.getSalasList().getSelectedValue()));
		
		vista.getAsignarButton().addActionListener(e -> asignar());
	}
	
	private void asignar() 
	{
		SalaDTO sala = vista.getSalasList().getSelectedValue();
		if(cursada.getVacantes() <= sala.getCapacidad()
		|| JOptionPane.showConfirmDialog(vista, 
				"<html>Est\u00E1 seguro de que desea asignar esa sala?<br>"
				+ "La sala tiene capacidad para " + sala.getCapacidad() + 
				", mientras que la cantidad de alumnos en esta cursada podr\u00EDa ser de hasta " + cursada.getVacantes() + ".</html>", 
				"Asignaci\u00F3n de sala", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
		{
			cursada.setSala(vista.getSalasList().getSelectedValue());
			model.modificarCursada(cursada);
		}
	}
	
	private void setSalaSeleccionada(SalaDTO sala)
	{
		salaSeleccionada = sala;
		
		if(salaSeleccionada == null)
		{
			vista.getGraficoHorarios().setPeriods(cursada.getHorarios(),
				elem -> new Period(elem.getDiaDeLaSemana(), elem.getHoraInicio(), elem.getHoraFin(), "", Color.GREEN));
		} else {
			List<Period> periods = new LinkedList<>();
			
			for(HorarioDTO horario : cursada.getHorarios())
				periods.add(new Period(horario.getDiaDeLaSemana(), horario.getHoraInicio(), horario.getHoraFin(), "", Color.GREEN));
			
			for(CursadaDTO cursadaOcupandoSala : model.getCursadasPorSalaYFechas(salaSeleccionada, cursada))
			{
				for(HorarioDTO horario : cursadaOcupandoSala.getHorarios())
					periods.add(new Period(horario.getDiaDeLaSemana(), horario.getHoraInicio(), horario.getHoraFin(), cursadaOcupandoSala.getNombre(), Color.RED));
			}
			
			vista.getGraficoHorarios().setPeriods(periods);
		}
		
		vista.getAsignarButton().setEnabled(salasDisponibles.contains(salaSeleccionada));
	}

	@Override
	public void invalidated(Observable observable)
	{
		salasDisponibles = model.salasDisponiblesParaCursada(cursada);
		List<SalaDTO> salasOcupadas = new LinkedList<>(modeloSala.getSalas());
		salasOcupadas.removeAll(salasDisponibles);
		vista.setCursada(cursada, salasDisponibles, salasOcupadas);
	}
}
