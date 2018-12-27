package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Date;

import javax.swing.JOptionPane;



import dto.CursadaDTO;

import dto.ParcialDTO;
import dto.PersonaDTO;
import dto.PlanillaDeAsistenciasDTO;
import dto.PlanillaDeParcialesDTO;
import modelo.ModeloCursos;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaCrearPlanillaEvaluaciones;

public class ControladorVentanaCrearPlanillaEvaluaciones implements ActionListener {
	private VentanaCrearPlanillaEvaluaciones view;
	private ModeloCursos model;
	private CursadaDTO cursadaRecibida;
	private PlanillaDeParcialesDTO planillaEditar;

	public ControladorVentanaCrearPlanillaEvaluaciones(VentanaCrearPlanillaEvaluaciones ventana, CursadaDTO cursada) {
		this.view = ventana;
		this.cursadaRecibida = cursada;
		this.model = ModeloCursos.getInstance();

		view.getBtnGuardar().addActionListener(this);

	}

	public ControladorVentanaCrearPlanillaEvaluaciones(VentanaCrearPlanillaEvaluaciones ventanaPlanilla,
			CursadaDTO cursadaRecibida, PlanillaDeParcialesDTO planillaRecibida) {
		this.view = ventanaPlanilla;
		this.cursadaRecibida = cursadaRecibida;
		this.model = ModeloCursos.getInstance();
		this.planillaEditar = planillaRecibida;

		view.getBtnGuardar().addActionListener(this);
		
		view.getDateFecha().setDate(this.planillaEditar.getFecha());
		

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (view.getDateFecha() == null) {
			JOptionPane.showMessageDialog(null, "Completa la fecha");
			return;
		}
		if (!fechaCorrecta(view.getDateFecha().getDate())) {
			JOptionPane.showMessageDialog(null,
					"La fecha es incorrecta. Recuerde que debe coincidir con un d\u00EDa de cursada.");
			return;
		}

		if (e.getSource() == view.getBtnGuardar() && this.planillaEditar == null) 
		{

			PlanillaDeParcialesDTO planilla = new PlanillaDeParcialesDTO();
			planilla.setDisponibleEnSistema(true);
			
			planilla.setParciales(new ArrayList<ParcialDTO>());
			planilla.setFecha(this.view.getDateFecha().getDate());

			cursadaRecibida.getParciales().add(planilla);
		
			
			
			for (PersonaDTO persona : model.getAlumnosPorCursada(cursadaRecibida)) 
			{
				ParcialDTO parcial=new ParcialDTO();
				parcial.setAlumno(ModeloPersonas.getInstance().getAlumnoPorPersona(persona));
				parcial.setDisponibleEnSistema(true);
				parcial.setEstado(model.getEstadoEvaluacion().get(0));
				parcial.setNota(-1);
				parcial.setObservaciones("ninguna");
				planilla.getParciales().add(parcial);
			}
			
			
			model.modificarCursada(cursadaRecibida);

		}

		if (e.getSource() == view.getBtnGuardar() && this.planillaEditar != null) {

			planillaEditar.setFecha(this.view.getDateFecha().getDate());
			model.modificarCursada(cursadaRecibida);

		}

	}

	@SuppressWarnings("deprecation")
	private boolean fechaCorrecta(Date date) {

		for (PlanillaDeAsistenciasDTO planilla : cursadaRecibida.getAsistencias()) {
			if (planilla.getFecha().getYear()==date.getYear() &&
					planilla.getFecha().getMonth()==date.getMonth()&&
					planilla.getFecha().getDate()==date.getDate())
				return true;

		}
		return false;
	}

}
