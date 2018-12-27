package presentacion.controlador;

import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;


import dto.AlumnoDTO;
import dto.CuotaDTO;
import dto.CursadaDTO;
import modelo.ModeloCursos;
import modelo.ModeloPersonas;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.reportes.ReporteAlumno;
import presentacion.vista.PanelGestionAlumnos;
import presentacion.vista.PanelGestionPagosDeAlumno;
import presentacion.vista.VentanaAgregarAlumno;
import presentacion.vista.VentanaEditarAlumno;
import presentacion.vista.VistaPanelReporteAnual;
import util.Strings;

public class ControladorPanelGestionAlumnos
{
	private ModeloCursos model;
	private ModeloPersonas modeloPersonas;
	private PanelGestionAlumnos vista;
	private AlumnoDTO selectedItem;
	
	public ControladorPanelGestionAlumnos(PanelGestionAlumnos vista)
	{
		model = ModeloCursos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.vista = vista;
		vista.getBtnAgregarAlumno().addActionListener( e -> agregarAlumno() );
		vista.getBtnGestionarPagos().addActionListener( e -> mostrarGestionarPagos() );
		vista.getBtnConsultarInscripciones().addActionListener( e -> mostrarConsultarInscripciones() );
		setSelectedItem(null);
		vista.getBtnGenerarReporte().addActionListener(e->generarReporte());
		vista.getBtnReporteAnual().addActionListener(e->abrirVentanaReporteAnual());
		vista.getBtnImprimirReporte().addActionListener(e->imprimirReporte());
		
		
	
		vista.setTable(crearTabla());
		
	
		vista.getTable().addDoubleClickListener(()->this.editarSelectedAlumno());
	}
	
	private void imprimirReporte() {
		if(this.hayCursadasFinalizadas()) {
			ReporteAlumno reporteAlumno = new ReporteAlumno(selectedItem);
			reporteAlumno.imprimir();}
		else {
			JOptionPane.showMessageDialog(null, "Este alumno no posee cursadas finalizadas");
		}
	}

	private void abrirVentanaReporteAnual() {
		if(this.hayCursadasFinalizadas()) {
			VistaPanelReporteAnual vistaPanelReporteAnual=new VistaPanelReporteAnual();
			new ControladorPanelReporteAnual(vistaPanelReporteAnual,selectedItem);
			Modal.showDialog(vistaPanelReporteAnual, "Elija el a\u00F1o del reporte", vistaPanelReporteAnual.getBtnGenerarReporte());}
		else {
			JOptionPane.showMessageDialog(null, "Este alumno no posee cursadas finalizadas");
		}
		}

	private void generarReporte() {
		
		if(this.hayCursadasFinalizadas()) {
			ReporteAlumno reporteAlumno = new ReporteAlumno(selectedItem);
			reporteAlumno.mostrar();}
		else {
			JOptionPane.showMessageDialog(null, "Este alumno no posee cursadas finalizadas");
		}
		
	}

	private void agregarAlumno() 
	{
		ControladorAgregarAlumno controlador = new ControladorAgregarAlumno(new VentanaAgregarAlumno());
		controlador.initialize();
	}
	
	private void mostrarGestionarPagos() 
	{
		PanelGestionPagosDeAlumno gestionarPagos = new PanelGestionPagosDeAlumno();
		new ControladorPanelGestionPagosDeAlumno(gestionarPagos,selectedItem);
		Modal dialog=new Modal(gestionarPagos);
		dialog.setTitle("Cuotas de: "+this.selectedItem.getPersona().getNombre()+" "+this.selectedItem.getPersona().getApellido());
		dialog.setModal(false);
		dialog.setVisible(true);
	
	}
	
	private void mostrarConsultarInscripciones()
	{
		Transformer<CursadaDTO> transformer = elem -> new String[] { elem.getNombre(), elem.getCurso().getNombre(), Strings.formatoFecha(elem.getInicio()), Strings.formatoFecha(elem.getFin())};
		
		String[] columnNames = new String[] { "Comisi\u00F3n", "Curso", "Inicio", "Fin" };
		
		TablaGenerica<CursadaDTO> tabla = new TablaGenerica<>(
				columnNames, 
				transformer, 
				() -> model.getCursadasPorAlumno(selectedItem),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		Modal.showDialog(tabla, "Cursos de " + selectedItem.getPersona().getApellido() + ", " + selectedItem.getPersona().getNombre() + ": " + selectedItem.getLegajo());
	}
	
	public TablaGenerica<AlumnoDTO> crearTabla()
	{
		String[] columnNames = new String[] 
					{ 
						"Apellido",
						"Nombre",
						"Legajo",
						"Estado"
					};
		
		Transformer<AlumnoDTO> transformer = elem -> new String[] 
					{ 
						elem.getPersona().getApellido(),
						elem.getPersona().getNombre(),
						elem.getLegajo(),
						estadoDeAlumno(elem)
					};
		
		TablaGenerica<AlumnoDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,
				() -> modeloPersonas.getAlumnos(),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		modeloPersonas.addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}
	
	private String estadoDeAlumno(AlumnoDTO alumno)
	{
		List<CuotaDTO> cuotasPagas = model.cuotasPagasPorAlumno(alumno);
		
		for(CursadaDTO cursada : model.getCursadasPorAlumno(alumno))
			for(CuotaDTO cuota : cursada.getCuotas())
				if(cuota.getFechaLimite().before(new Date()) && !cuotasPagas.contains(cuota))
					return "Moroso";
		
		if(cuotasPagas.isEmpty())
			return "Sin inscripciones";
		return "Al d\u00EDa";
	}
	
	private void setSelectedItem(AlumnoDTO elem) 
	{
		this.selectedItem = elem;
		vista.getBtnGestionarPagos().setEnabled(elem!=null && tieneInscripciones(elem));
		vista.getBtnConsultarInscripciones().setEnabled(elem!=null);
		vista.getBtnGenerarReporte().setEnabled(elem!=null);
		vista.getBtnReporteAnual().setEnabled(elem!=null );
		vista.getBtnImprimirReporte().setEnabled(elem!=null);
		
		
	}
	private boolean tieneInscripciones(AlumnoDTO elem) {
		return !model.getCursadasPorAlumno(elem).isEmpty();
	}

	private boolean hayCursadasFinalizadas() {
		
		for(CursadaDTO e: model.getCursadasPorAlumno(selectedItem)) {
			if(e.getEstado().getId().equals(4)) { 
				return true;
				}
		}
		return false;
	}

	private void editarSelectedAlumno() {
		this.editarUsuario(this.selectedItem);
	}
	
	private void editarUsuario(AlumnoDTO alumno) 
	{	
		ControladorEditarAlumno controlador = new ControladorEditarAlumno(new VentanaEditarAlumno(), this.modeloPersonas.getUsuarioPersona(alumno.getPersona()));
		controlador.initialize();
	}
}
