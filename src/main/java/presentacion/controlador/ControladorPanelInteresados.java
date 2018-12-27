package presentacion.controlador;



import javax.swing.JOptionPane;

import dto.EventoInteresadoDTO;
import dto.InteresadoDTO;
import modelo.ModeloEventos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelInteresados;
import presentacion.vista.VentanaAgregarAlumno;
import presentacion.vista.VistaReporteInteraccionesCurso;

public class ControladorPanelInteresados {
	
	private ModeloPersonas model;
	private InteresadoDTO selectedItem;
	private PanelInteresados vista;

	public ControladorPanelInteresados(PanelInteresados vistaInteresados) {
		this.vista=vistaInteresados;
		model=ModeloPersonas.getInstance();
		vista.setTable(crearTabla());
		this.setSelectedItem(null);
		this.vista.getBtnGenerarReporteInteracciones().addActionListener(e->generarReporteInteracciones());
		this.vista.getBtnRegistrarAlumno().addActionListener(e->registrarAlumno());
		this.vista.getBtnGenerarReporteGeneral().addActionListener(e->eleccionCursoReporte());
		
	
	}

	
	private void eleccionCursoReporte() {
		if(hayCursosReporte()) {
			VistaReporteInteraccionesCurso vistaReporteInteraccionesCurso=new VistaReporteInteraccionesCurso();
			new ControladorVistaReporteGeneralInteraccionesCurso(vistaReporteInteraccionesCurso);
			Modal.showDialog(vistaReporteInteraccionesCurso, "Seleccione curso a usar para generar el reporte ", vistaReporteInteraccionesCurso.getBtnGenerarReporte());
		}
		else {
			JOptionPane.showMessageDialog(null, "Todav\u00EDa no existe ning\u00FAn evento asociado a un curso para generar el reporte");
		}
	}


	private boolean hayCursosReporte() {
		for(EventoInteresadoDTO e : ModeloEventos.getInstance().getEventosInteresados()) {
			if(e.getCurso()!=null) {
				return true;
			}
		}
		return false;
	}


	private void registrarAlumno() 
	{
		ControladorAgregarAlumno controlador = new ControladorAgregarAlumno(new VentanaAgregarAlumno(),selectedItem);
		controlador.initialize();
		
	}


	private void generarReporteInteracciones() {
		if(tieneEventosConCursos(selectedItem)) {
			VistaReporteInteraccionesCurso vistaReporteInteraccionesCurso=new VistaReporteInteraccionesCurso();
			new ControladorVistaReporteInteraccionesCurso(vistaReporteInteraccionesCurso,selectedItem);
			Modal.showDialog(vistaReporteInteraccionesCurso, "Seleccione curso a usar para el reporte de interacciones", vistaReporteInteraccionesCurso.getBtnGenerarReporte());
		}
		else {
			JOptionPane.showMessageDialog(null, "Este interesado no tiene eventos asociados a alg\u00FAn curso");
		}
		
		}

	private boolean tieneEventosConCursos(InteresadoDTO interesadoElegido) {
		
		for(EventoInteresadoDTO e : ModeloEventos.getInstance().getEventosInteresadoPorInteresado(interesadoElegido)) {
			if(e.getCurso()!=null) {
				return true;
			}
		}

		return false;
	}


	public TablaGenerica<InteresadoDTO> crearTabla()
	{

		String[] columnNames = new String[] 
				{
						
						"Nombre y apellido",
						"Tel\u00E9fono",
						"Email"
				};
		
		Transformer<InteresadoDTO> transformer = elem -> new String[] { elem.getPersona().getNombre()+" "+elem.getPersona().getApellido(),elem.getPersona().getTelefono(),elem.getPersona().getEmail()};
		
		FilterPanel<InteresadoDTO> filterPanel = FilterPanel.stringFilterPanel(columnNames, transformer);
		
		
		
		TablaGenerica<InteresadoDTO> table = new TablaGenerica<>(columnNames, transformer,()->model.getInteresados(), filterPanel);
		
		model.addListener(table);
		table.addSelectionListener(this::setSelectedItem);
		return table;
		
		
	}
	private void setSelectedItem(InteresadoDTO elem) 
	{
		this.selectedItem = elem;
		vista.getBtnGenerarReporteInteracciones().setEnabled(elem!=null);
		vista.getBtnRegistrarAlumno().setEnabled(elem!=null);
		
		
	}
	
}
