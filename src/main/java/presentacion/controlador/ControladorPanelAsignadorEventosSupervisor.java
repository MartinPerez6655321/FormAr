package presentacion.controlador;

import dto.EventoInasistenciaDTO;
import dto.EventoSupervisorDTO;
import dto.NotificacionDTO;
import dto.PersonalAdministrativoDTO;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelVistaAsignarEventos;

public class ControladorPanelAsignadorEventosSupervisor {

	private PanelVistaAsignarEventos vista;
	private ModeloEventos modelEventos;
	private ModeloNotificaciones modelNotificacion;
	private PersonalAdministrativoDTO selectedItem;
	private EventoSupervisorDTO eventoSupervisorAsignar;
	private EventoInasistenciaDTO eventoInasistenciaAsignar;

	public ControladorPanelAsignadorEventosSupervisor(PanelVistaAsignarEventos vistaAsignador,
			EventoSupervisorDTO selectedItem2) {

		modelEventos = ModeloEventos.getInstance();
		this.vista = vistaAsignador;
		this.modelNotificacion = ModeloNotificaciones.getInstance();
		vista.setTable(generarTabla());
		this.eventoSupervisorAsignar = selectedItem2;

		vista.getAceptarButton().addActionListener(e -> aceptar());
		this.setSelectedItem(null);
	}

	public ControladorPanelAsignadorEventosSupervisor(PanelVistaAsignarEventos vistaAsignador,
			EventoInasistenciaDTO selectedItem2) {
		this.modelNotificacion = ModeloNotificaciones.getInstance();
		modelEventos = ModeloEventos.getInstance();
		this.vista = vistaAsignador;
		vista.setTable(generarTabla());
		this.eventoInasistenciaAsignar = selectedItem2;

		vista.getAceptarButton().addActionListener(e -> aceptar());
		this.setSelectedItem(null);
	}

	private TablaGenerica<PersonalAdministrativoDTO> generarTabla() {
		String[] columnNames = new String[] { "Nombre y apellido", "Email", "Tel\u00E9fono" };

		Transformer<PersonalAdministrativoDTO> transformer = elem -> new String[] {
				elem.getPersona().getNombre() + " " + elem.getPersona().getApellido(), elem.getPersona().getEmail(),
				elem.getPersona().getTelefono() };

		TablaGenerica<PersonalAdministrativoDTO> table = new TablaGenerica<>(columnNames, transformer,
				() -> ModeloPersonas.getInstance().getListaPersonalAdministrativo(),
				FilterPanel.stringFilterPanel(columnNames, transformer));

		modelEventos.addListener(table);
		ModeloPersonas.getInstance().addListener(table);

		table.addSelectionListener(this::setSelectedItem);
		return table;
	}

	private void setSelectedItem(PersonalAdministrativoDTO personalAdminisitrativo) {
		selectedItem = personalAdminisitrativo;
		vista.getAceptarButton().setEnabled(selectedItem != null && selectedItem.getDisponibleEnSistema());

	}

	private void aceptar() {

		if (eventoSupervisorAsignar != null) 
		{
			eventoSupervisorAsignar.setAdministrativoAsignado(selectedItem);

			modelEventos.modificarEventoSupervisor(eventoSupervisorAsignar);

		}

		else if (eventoInasistenciaAsignar != null) {
			eventoInasistenciaAsignar.setAdministrativoAsignado(selectedItem);
			modelEventos.modificarEventoInasistencia(eventoInasistenciaAsignar);



		}

	}

}
