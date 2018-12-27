package presentacion.controlador;


import java.util.List;

import dto.AsistenciaDTO;
import dto.CursadaDTO;

import dto.PlanillaDeAsistenciasDTO;
import modelo.ModeloCursos;

import presentacion.components.Modal;
import presentacion.components.tabla.DoubleClickListener;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VentanaEditarAsistencia;
import presentacion.vista.VentanaTomarAsistencia;

public class ControladorVentanaTomarAsistencia implements DoubleClickListener {

	private VentanaTomarAsistencia view;
	private ModeloCursos model;

	private CursadaDTO cursadaRecibida;
	private PlanillaDeAsistenciasDTO planillaRecibida;

	private AsistenciaDTO selectedItem;

	public ControladorVentanaTomarAsistencia(VentanaTomarAsistencia ventanaTomarAsistencia, CursadaDTO cursadaRecibida,
			PlanillaDeAsistenciasDTO selectedItem) {
		this.cursadaRecibida = cursadaRecibida;
		this.model = ModeloCursos.getInstance();
		this.view = ventanaTomarAsistencia;
		this.planillaRecibida = selectedItem;

		setSelectedItem(null);

		view.setTable(crearTabla());
		view.getTable().addDoubleClickListener(this);

	}

	private TablaGenerica<AsistenciaDTO> crearTabla() {
		Transformer<AsistenciaDTO> transformer = elem -> new String[] {
				elem.getAlumno().getPersona().getNombre() + " " + elem.getAlumno().getPersona().getApellido(),
				elem.getEstado().getNombre() };

		Obtainer<AsistenciaDTO> obtainer = new Obtainer<AsistenciaDTO>() {

			@Override
			public List<AsistenciaDTO> obtain() {

				return planillaRecibida.getAsistencias();
			}

		};

		String[] columnNames = new String[] { "Alumno", "Estado", };

		TablaGenerica<AsistenciaDTO> table = new TablaGenerica<>(columnNames, transformer, obtainer,
				FilterPanel.stringFilterPanel(columnNames, transformer));

		model.addListener(table);

		table.addSelectionListener(this::setSelectedItem);

		

		return table;

	}

	

	private void setSelectedItem(AsistenciaDTO asistencia) {
		this.selectedItem = asistencia;

	}

	@Override
	public void notifyDoubleClick() {
		VentanaEditarAsistencia ventanaEditarAsistencia = new VentanaEditarAsistencia();
		new ControladorVentanaEditarAsistencia(ventanaEditarAsistencia, selectedItem, planillaRecibida,
				cursadaRecibida);
		Modal.showDialog(ventanaEditarAsistencia,
				"Editar asistencia del alumno" + selectedItem.getAlumno().getPersona().getNombre() + " "
						+ selectedItem.getAlumno().getPersona().getApellido(),
				ventanaEditarAsistencia.getBtnGuardar());
	}

}
