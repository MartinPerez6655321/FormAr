package presentacion.controlador;

import java.util.ArrayList;
import java.util.List;

import dto.CursoDTO;
import dto.EventoInteresadoDTO;
import dto.NotificacionDTO;
import modelo.ModeloEventos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VistaEventos;
import presentacion.vista.VistaRegistrarEventoNuevo;
import util.Strings;

public class ControladorEventosInteresados {
	private VistaEventos vista;
	private ModeloEventos modeloEventos;
	private ModeloNotificaciones modelo;
	private ModeloPersonas modeloPersonas;
	private EventoInteresadoDTO selectedItem;
	private boolean completados;
	

	public ControladorEventosInteresados(VistaEventos vistaEventosInteresadoPendientes,boolean completados ) {
		this.vista=vistaEventosInteresadoPendientes;
		this.modeloEventos=ModeloEventos.getInstance();
		this.modelo=ModeloNotificaciones.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.completados=completados;
		this.vista.setTable(crearTabla());
		
		
		if(completados) {
			vista.getBtnCrearEvento().setVisible(false);
			vista.getBtnMarcarCumplido().setVisible(false);
		}
		vista.getBtnCrearEvento().addActionListener(e->registrarEvento());
		vista.getBtnMarcarCumplido().addActionListener(e->marcarComoCumplido());
		
		this.setSelectedItem(null);
		
		if(!completados) {
			vista.getTable().addDoubleClickListener(()->this.editarSelectedEvento());}
		
	}

	
	
	private void editarSelectedEvento() {
		
		VistaRegistrarEventoNuevo vistaRegistrarEventoNuevo = new VistaRegistrarEventoNuevo();
		Modal modal=new Modal(vistaRegistrarEventoNuevo);
		new ControladorEditarEventoInteresado(vistaRegistrarEventoNuevo,modal,this.selectedItem);
		modal.setVisible(true);
		
	}
	
	

	

	


	private TablaGenerica<EventoInteresadoDTO> crearTabla() {
		Transformer<EventoInteresadoDTO> transformer = elem -> new String[] 
				{ 
					elem.getDescripcion(), 
					elem.getInteresado().getPersona().getNombre()+" "+elem.getInteresado().getPersona().getApellido(), 
					getCurso(elem.getCurso()), 
					Strings.formatoFecha(elem.getFechaDeLlamado())+" "+Strings.formatoHorario(elem.getHoraDeLlamado()), 
					Strings.formatoFecha(elem.getFechaDeCumplimiento())+" "+Strings.formatoHorario(elem.getHoraDeCumplimiento())
					};
	
		String[] columnNames = new String[] 
				{ 
					"Descripci\u00F3n", "Interesado","Curso","Fecha y hora de llamado","Fecha y hora de cumplimiento"
				};
	
		TablaGenerica<EventoInteresadoDTO> table = new TablaGenerica<>(
			columnNames, 
			transformer,
			() -> filtro(modeloEventos.getEventosInteresadosPorAdministrativo(modeloPersonas.getPersonalAdministrativoActual(modeloPersonas.getUsuarioActual()))),
			FilterPanel.stringFilterPanel(columnNames, transformer));
	
		modeloEventos.addListener(table);
		modelo.addListener(table);
		modeloPersonas.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
	
		return table;
	}
	private String getCurso(CursoDTO curso) {
		String ret;
		
		if(curso==null) {
			ret="N/A";
		}
		else {
			ret=curso.getNombre();
		}
		
		
		return ret;
	}

	
	
	private List<EventoInteresadoDTO> filtro (List<EventoInteresadoDTO> lista){
		List<EventoInteresadoDTO> ret=new ArrayList<>();
		
		if(completados) {
			for(EventoInteresadoDTO e:lista) {
				if(e.getEstado().getId().equals(1)) {
					ret.add(e);
				}
			}
		}
		else {
			for(EventoInteresadoDTO e:lista) {
				if(e.getEstado().getId().equals(2)) {
					ret.add(e);
				}
			}
		}
		
		return ret;
	}
	
	private void setSelectedItem(EventoInteresadoDTO evento) 
	{
		selectedItem = evento;
		vista.getBtnMarcarCumplido().setEnabled(selectedItem!=null );
		vista.getBtnCrearEvento().setEnabled(true);

	}
	
	private void registrarEvento() {
		VistaRegistrarEventoNuevo vistaRegistrarEventoNuevo = new VistaRegistrarEventoNuevo();
		Modal modal=new Modal(vistaRegistrarEventoNuevo);
		new ControladorRegistrarEventoInteresado(vistaRegistrarEventoNuevo,0,modal);
		modal.setVisible(true);
	
	}
	
	private void marcarComoCumplido() 
	{
		
		EventoInteresadoDTO e=selectedItem;
		e.setEstado(modeloEventos.getEstadosEventos().get(0));
		modeloEventos.modificarEventoInteresado(e);
		
		for (NotificacionDTO notificacion: modelo.getNotificacionesPorUsuarioActual())
		{
			if (notificacion.getEventoInteresado()!=null && notificacion.getEventoInteresado().equals(e))
			{
				String[] partes=notificacion.getCodigoTab().split("-");
				notificacion.setCodigoTab(partes[0]+"-1");
				modelo.modificarNotificacion(notificacion);
			}
		}
		
	}

}
