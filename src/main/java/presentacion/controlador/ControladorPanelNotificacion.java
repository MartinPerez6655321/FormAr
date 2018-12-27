package presentacion.controlador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import dto.NotificacionDTO;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.vista.PanelNotificaciones;
import presentacion.vista.VentanaPrincipalTabs;
import presentacion.vista.VistaAdministrativo;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorPanelNotificacion implements ListSelectionListener, MouseListener {

	private ModeloNotificaciones model;
	private NotificacionDTO NotificacionSeleccionada;
	private PanelNotificaciones view;

	private ControladorVistaAdministrativo controladorAdministrativo;
	private VistaAdministrativo vistaAdministrativo;
	private VentanaPrincipalTabs vistaTabs;

	private List<NotificacionDTO> listaNotificaciones;

	public ControladorPanelNotificacion(PanelNotificaciones vista, VentanaPrincipalTabs vistaTabs,
			ControladorVistaAdministrativo controladorAdministrativo, VistaAdministrativo vistaAdministrativo) {
		this.vistaAdministrativo = vistaAdministrativo;
		this.view = vista;
		this.controladorAdministrativo = controladorAdministrativo;
		this.vistaTabs = vistaTabs;
		model = ModeloNotificaciones.getInstance();
		this.listaNotificaciones = model.getNotificaciones();

		this.view.llenarPanel(filtarNoticiaciones(this.listaNotificaciones));

		this.view.getListNotificacion().addListSelectionListener(this);
		this.view.getListNotificacion().addMouseListener(this);
		
	}

	private List<NotificacionDTO> filtarNoticiaciones(List<NotificacionDTO> notificaciones) 
	{
		List<NotificacionDTO> lista = new ArrayList<>();
		for (NotificacionDTO notificacionDTO : notificaciones) 
		{
			
			if (ValidadorLogico.fechaValidaNotificacion(new java.util.Date(), notificacionDTO) && notificacionDTO.getUsuario()!=null && notificacionDTO.getUsuario().equals(ModeloPersonas.getInstance().getUsuarioActual()))
				lista.add(notificacionDTO);
		}
		return lista;
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

		this.NotificacionSeleccionada = this.view.getListNotificacion().getSelectedValue();

		if (NotificacionSeleccionada != null && !NotificacionSeleccionada.getVisto()) {
			this.NotificacionSeleccionada.setVisto(true);

			model.modificarNotificacion(NotificacionSeleccionada);
			this.listaNotificaciones = model.getNotificacionesPorUsuarioActual();
			this.llenarNotificaciones(filtarNoticiaciones(listaNotificaciones));

		}
		if (NotificacionSeleccionada != null) {
			navegar(this.NotificacionSeleccionada.getCodigoVista(), this.NotificacionSeleccionada.getCodigoTab(),
					NotificacionSeleccionada);
		}

	}

	public void navegar(String rol, String tab, NotificacionDTO noti) {
		String[] partes = noti.getCodigoTab().split("-");
		this.vistaTabs.getBtnPersonalAdministrativo().doClick();

		if (rol.equals("c") && partes[0].equals("5")) {
			vistaAdministrativo.getTabbedPane().setSelectedIndex(6);
			if (partes[1].equals("0")) {
				llenarFiltroInteresadoPendiente(noti);
			} else {

				llenarFiltroInteresadoCompletado(noti);

			}

		} else if ((rol.equals("c") && partes[0].equals("6"))) {
			vistaAdministrativo.getTabbedPane().setSelectedIndex(7);
			if (partes[1].equals("0")) {
				llenarFiltroSupervisorPendientes(noti);

			} else {
				llenarFiltroSupervisorCompletados(noti);

			}

		} else if ((rol.equals("c") && partes[0].equals("7"))) {
			vistaAdministrativo.getTabbedPane().setSelectedIndex(8);
			if (partes[1].equals("0")) {
				llenarFiltroInasistenciaPendientes(noti);

			} else {

				llenarFiltroInasistenciaCompletados(noti);

			}

		}

		if (rol.equals("c") && tab.equals("8")) {
			if (noti.getCursada().getEstado().getId().equals(5))
			{
				vistaAdministrativo.getTabbedPane().setSelectedIndex(10);
				this.controladorAdministrativo.getControladorEstadoCursada().getVentana().getTable().getFilterPanel()
						.clearFilters();
				this.controladorAdministrativo.getControladorEstadoCursada().getVentana().getTable().getFilterPanel()
						.getFilterComponents().get(0).setFilter(noti.getCursada().getNombre());
			}
			else
			{
				vistaAdministrativo.getTabbedPane().setSelectedIndex(2);
				this.controladorAdministrativo.getControladorGestionCursada().getView().getTable().getFilterPanel()
				.clearFilters();
				this.controladorAdministrativo.getControladorGestionCursada().getView().getTable().getFilterPanel()
				.getFilterComponents().get(0).setFilter(noti.getCursada().getNombre());
				this.controladorAdministrativo.getControladorGestionCursada().getView().getTable().getFilterPanel()
				.getFilterComponents().get(2).setFilter(noti.getCursada().getEstado().getNombre());
				
			}
			

		}

	}

	@SuppressWarnings("deprecation")
	private void llenarFiltroInasistenciaCompletados(NotificacionDTO noti) {
		this.controladorAdministrativo.getVistaEventosInasistencia().getTabbedPane().setSelectedIndex(1);
		this.controladorAdministrativo.getVistaEventosInasistenciaCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(0)
				.setFilter(noti.getEventoInasistencia().getAlumno().getPersona().getNombre());
		this.controladorAdministrativo.getVistaEventosInasistenciaCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(1).setFilter(noti.getEventoInasistencia().getCursada().getNombre());
		this.controladorAdministrativo.getVistaEventosInasistenciaCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(2)
				.setFilter(noti.getEventoInasistencia().getAlumno().getPersona().getTelefono());
		this.controladorAdministrativo.getVistaEventosInasistenciaCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(3)
				.setFilter(noti.getEventoInasistencia().getAlumno().getPersona().getEmail());
		this.controladorAdministrativo.getVistaEventosInasistenciaCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(5)
				.setFilter(noti.getEventoInasistencia().getHoraDeCumplimiento().getHours() + ":" + ValidadorCampos
						.getDosDigitos(noti.getEventoInasistencia().getHoraDeCumplimiento().getMinutes()));
	}

	@SuppressWarnings("deprecation")
	private void llenarFiltroInasistenciaPendientes(NotificacionDTO noti) {
		this.controladorAdministrativo.getVistaEventosInasistencia().getTabbedPane().setSelectedIndex(0);
		this.controladorAdministrativo.getVistaEventosInasistenciaPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(0)
				.setFilter(noti.getEventoInasistencia().getAlumno().getPersona().getNombre());
		this.controladorAdministrativo.getVistaEventosInasistenciaPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(1).setFilter(noti.getEventoInasistencia().getCursada().getNombre());
		this.controladorAdministrativo.getVistaEventosInasistenciaPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(2)
				.setFilter(noti.getEventoInasistencia().getAlumno().getPersona().getTelefono());
		this.controladorAdministrativo.getVistaEventosInasistenciaPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(3)
				.setFilter(noti.getEventoInasistencia().getAlumno().getPersona().getEmail());
		this.controladorAdministrativo.getVistaEventosInasistenciaPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(5)
				.setFilter(noti.getEventoInasistencia().getHoraDeCumplimiento().getHours() + ":" + ValidadorCampos
						.getDosDigitos(noti.getEventoInasistencia().getHoraDeCumplimiento().getMinutes()));
	}

	@SuppressWarnings("deprecation")
	private void llenarFiltroSupervisorCompletados(NotificacionDTO noti) {
		this.controladorAdministrativo.getVistaEventosSupervisor().getTabbedPane().setSelectedIndex(1);
		this.controladorAdministrativo.getVistaEventosSupervisorCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(0).setFilter(noti.getEventoSupervisor().getDescripcion());
		this.controladorAdministrativo.getVistaEventosSupervisorCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(1)
				.setFilter(noti.getEventoSupervisor().getHoraDeCumplimiento().getHours() + ":" + ValidadorCampos
						.getDosDigitos(noti.getEventoSupervisor().getHoraDeCumplimiento().getMinutes()));
		this.controladorAdministrativo.getVistaEventosSupervisorCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(2)
				.setFilter(noti.getEventoSupervisor().getSupervisor().getPersona().getNombre() + " "
						+ noti.getEventoSupervisor().getSupervisor().getPersona().getApellido());
	}

	@SuppressWarnings("deprecation")
	private void llenarFiltroSupervisorPendientes(NotificacionDTO noti) {
		this.controladorAdministrativo.getVistaEventosSupervisor().getTabbedPane().setSelectedIndex(0);
		this.controladorAdministrativo.getVistaEventosSupervisorPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(0).setFilter(noti.getEventoSupervisor().getDescripcion());
		this.controladorAdministrativo.getVistaEventosSupervisorPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(1)
				.setFilter(noti.getEventoSupervisor().getHoraDeCumplimiento().getHours() + ":" + ValidadorCampos
						.getDosDigitos(noti.getEventoSupervisor().getHoraDeCumplimiento().getMinutes()));
		this.controladorAdministrativo.getVistaEventosSupervisorPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(2)
				.setFilter(noti.getEventoSupervisor().getSupervisor().getPersona().getNombre() + " "
						+ noti.getEventoSupervisor().getSupervisor().getPersona().getApellido());
	}

	@SuppressWarnings("deprecation")
	private void llenarFiltroInteresadoCompletado(NotificacionDTO noti) {
		this.controladorAdministrativo.getVistaEventosInteresado().getTabbedPane().setSelectedIndex(1);
		this.controladorAdministrativo.getVistaEventosInteresadoCompletados().getTable().getFilterPanel()
				.clearFilters();
		this.controladorAdministrativo.getVistaEventosInteresadoCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(0).setFilter(noti.getEventoInteresado().getDescripcion());
		this.controladorAdministrativo.getVistaEventosInteresadoCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(1)
				.setFilter(noti.getEventoInteresado().getInteresado().getPersona().getNombre() + " "
						+ noti.getEventoInteresado().getInteresado().getPersona().getApellido());
		this.controladorAdministrativo.getVistaEventosInteresadoCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(2).setFilter(noti.getEventoInteresado().getCurso().getNombre());
		this.controladorAdministrativo.getVistaEventosInteresadoCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(3).setFilter(noti.getEventoInteresado().getHoraDeLlamado().getHours() + ":"
						+ ValidadorCampos.getDosDigitos(noti.getEventoInteresado().getHoraDeLlamado().getMinutes()));
		this.controladorAdministrativo.getVistaEventosInteresadoCompletados().getTable().getFilterPanel()
				.getFilterComponents().get(4)
				.setFilter(noti.getEventoInteresado().getHoraDeCumplimiento().getHours() + ":" + ValidadorCampos
						.getDosDigitos(noti.getEventoInteresado().getHoraDeCumplimiento().getMinutes()));
	}

	@SuppressWarnings("deprecation")
	private void llenarFiltroInteresadoPendiente(NotificacionDTO noti) {
		this.controladorAdministrativo.getVistaEventosInteresado().getTabbedPane().setSelectedIndex(0);

		this.controladorAdministrativo.getVistaEventosInteresadoPendientes().getTable().getFilterPanel().clearFilters();
		this.controladorAdministrativo.getVistaEventosInteresadoPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(0).setFilter(noti.getEventoInteresado().getDescripcion());
		this.controladorAdministrativo.getVistaEventosInteresadoPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(1)
				.setFilter(noti.getEventoInteresado().getInteresado().getPersona().getNombre() + " "
						+ noti.getEventoInteresado().getInteresado().getPersona().getApellido());
		this.controladorAdministrativo.getVistaEventosInteresadoPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(2).setFilter(noti.getEventoInteresado().getCurso().getNombre());
		this.controladorAdministrativo.getVistaEventosInteresadoPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(3).setFilter(noti.getEventoInteresado().getHoraDeLlamado().getHours() + ":"
						+ ValidadorCampos.getDosDigitos(noti.getEventoInteresado().getHoraDeLlamado().getMinutes()));
		this.controladorAdministrativo.getVistaEventosInteresadoPendientes().getTable().getFilterPanel()
				.getFilterComponents().get(4)
				.setFilter(noti.getEventoInteresado().getHoraDeCumplimiento().getHours() + ":" + ValidadorCampos
						.getDosDigitos(noti.getEventoInteresado().getHoraDeCumplimiento().getMinutes()));
	}

	public void llenarNotificaciones(List<NotificacionDTO> notificaciones) {
		this.view.llenarPanel(notificaciones);
	}

	public PanelNotificaciones getView() {
		return view;

	}

	public List<NotificacionDTO> getListaNotificaciones() {
		return listaNotificaciones;
	}

	public void setListaNotificaciones(List<NotificacionDTO> listaNotificaciones) {
		this.listaNotificaciones = listaNotificaciones;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (NotificacionSeleccionada != null) {
			navegar(this.NotificacionSeleccionada.getCodigoVista(), this.NotificacionSeleccionada.getCodigoTab(),
					NotificacionSeleccionada);
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
