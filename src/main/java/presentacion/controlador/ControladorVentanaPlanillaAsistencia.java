package presentacion.controlador;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dto.AsistenciaDTO;
import dto.CursadaDTO;

import dto.PersonaDTO;
import dto.PlanillaDeAsistenciasDTO;
import modelo.ModeloCursos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.DoubleClickListener;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;

import presentacion.vista.VentanaPlanillaAsistencia;
import presentacion.vista.VentanaTomarAsistencia;
import util.ValidadorCampos;

public class ControladorVentanaPlanillaAsistencia implements DoubleClickListener {

	private VentanaPlanillaAsistencia view;
	private ModeloCursos model;
	private ModeloPersonas modeloPersonas;
	private PlanillaDeAsistenciasDTO selectedItem;

	private CursadaDTO cursadaRecibida;

	public ControladorVentanaPlanillaAsistencia(VentanaPlanillaAsistencia ventanaPlanillaAsistencia,
			CursadaDTO cursada) {
		this.cursadaRecibida = cursada;
		model = ModeloCursos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.view = ventanaPlanillaAsistencia;

		setSelectedItem(null);

		view.setTable(crearTabla());
		view.getTable().addDoubleClickListener(this);
	}
	
	private TablaGenerica<PlanillaDeAsistenciasDTO> crearTabla() 
	{
		
		Transformer<PlanillaDeAsistenciasDTO> transformer = elem -> new String[]
				{
				ValidadorCampos.convertirFecha(elem.getFecha()),
				ValidadorCampos.getHorarios(elem.getHorario()) };

		Obtainer<PlanillaDeAsistenciasDTO> obtainer = new Obtainer<PlanillaDeAsistenciasDTO>() {

			@Override
			public List<PlanillaDeAsistenciasDTO> obtain() {
				return cursadaRecibida.getAsistencias();
			}

		};

		String[] columnNames = new String[] { "Fecha", "Hora", };

		TablaGenerica<PlanillaDeAsistenciasDTO> table = new TablaGenerica<>(columnNames, transformer, obtainer,
				FilterPanel.stringFilterPanel(columnNames, transformer));

		model.addListener(table);

		table.addSelectionListener(this::setSelectedItem);

		return table;
	}

	private void setSelectedItem(PlanillaDeAsistenciasDTO planilla) {
		selectedItem = planilla;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void notifyDoubleClick() {

		Date diaActual = new java.util.Date();
		if ((selectedItem.getFecha().getDate() == diaActual.getDate()
				&& selectedItem.getFecha().getMonth() == diaActual.getMonth()
				&& selectedItem.getFecha().getYear() == diaActual.getYear())
				|| selectedItem.getFecha().before(diaActual)) {
			if (selectedItem.getAsistencias().isEmpty()) 
			{
				for (PersonaDTO persona : model.getAlumnosPorCursada(cursadaRecibida)) 
				{
					AsistenciaDTO asistencia = new AsistenciaDTO();
					asistencia.setAlumno(modeloPersonas.getAlumnoPorPersona(persona));
					asistencia.setDisponibleEnSistema(true);
					asistencia.setEstado(model.getEstadoAsistencia().get(0));
					selectedItem.getAsistencias().add(asistencia);
				}

				model.modificarPlanillaDeAsistencias(selectedItem);

			}

			VentanaTomarAsistencia ventanaTomarAsistencia = new VentanaTomarAsistencia();
			new ControladorVentanaTomarAsistencia(ventanaTomarAsistencia, this.cursadaRecibida, selectedItem);

			Modal.showDialog(ventanaTomarAsistencia, "Tomar Asistencia", ventanaTomarAsistencia.getBtnOk());
		} else {
			JOptionPane.showMessageDialog(null, "Aun no es el d\u00EDa correspondiente para cargar la planilla");
		}

	}

	

}
