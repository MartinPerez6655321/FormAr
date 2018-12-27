package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dto.CursadaDTO;
import modelo.ModeloCursos;
import modelo.ModeloPersonas;
import modelo.ModeloSala;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelAgendaSemanal;
import presentacion.vista.PanelConsultarInscriptos;
import presentacion.vista.PanelGestionMisCursadas;
import presentacion.vista.PanelProgramaDeCursada;
import presentacion.vista.VentanaPlanillaAsistencia;
import presentacion.vista.VentanaPlanillaEvaluaciones;
import util.ValidadorCampos;

public class ControladorPanelGestionMisCursadas implements ActionListener
{
	private PanelGestionMisCursadas view;
	private ModeloCursos modeloCursos;
	private ModeloPersonas modeloPersonas;
	private CursadaDTO selectedItem;
	
	public ControladorPanelGestionMisCursadas(PanelGestionMisCursadas panelGestionAsistencia) 
	{
		modeloCursos = ModeloCursos.getInstance();
		modeloPersonas = ModeloPersonas.getInstance();
		this.view = panelGestionAsistencia;
		
		setSelectedItem(null);
		
		view.getBtnVerAgendaSemanal().addActionListener(e -> mostrarAgendaSemanal());

		view.getBtnGestionarAsistencia().addActionListener(this);
		view.getBtnGestionarEvaluaciones().addActionListener(this);
		
		view.getBtnPrograma().addActionListener(e -> mostrarVistaPrograma());
		
		view.setTable(crearTabla());
		view.getTable().addDoubleClickListener(()->verInscriptos());
	}

	private TablaGenerica<CursadaDTO> crearTabla() 
	{
		Transformer<CursadaDTO> transformer = elem -> new String[] 
				{ 
					elem.getNombre(), 
					elem.getSala() == null? "Sin sala asignada" : elem.getSala().getAlias() + "(" + elem.getSala().getCodigo() + ")",
					elem.getPrograma().isEmpty()? "Sin programa" : "Programa subido",
					ValidadorCampos.convertirFecha(elem.getInicio()),
					ValidadorCampos.convertirFecha(elem.getFin()),
					ValidadorCampos.getHorarios(elem.getHorarios()),
					elem.getEstado().getNombre()
					
				};
		
		Obtainer<CursadaDTO> obtainer = () -> modeloCursos.getCursadasPorInstructor(modeloPersonas.getInstructor(modeloPersonas.getUsuarioActual()));
		
		String[] columnNames = new String[] 
				{ 
					"Nombre", 
					"Sala", 
					"Programa",
					"Comienza",
					"Finaliza",
					"Horarios",
					"Estado"
				};
		
		TablaGenerica<CursadaDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,
				obtainer,
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		modeloPersonas.addListener(table);
		modeloCursos.addListener(table);

		ModeloSala.getInstance().addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}
	
	private void setSelectedItem(CursadaDTO cursada) 
	{
		selectedItem = cursada;
		this.view.getBtnGestionarAsistencia().setEnabled(cursada!=null);
		this.view.getBtnGestionarEvaluaciones().setEnabled(cursada!=null);
		this.view.getBtnPrograma().setEnabled(cursada!=null);
	}
	
	private void mostrarAgendaSemanal()
	{
		PanelAgendaSemanal agendaSemanal = new PanelAgendaSemanal();
		new ControladorAgendaSemanal(agendaSemanal, view.getTable().getElems());
		Modal.showDialog(agendaSemanal, "Agenda semanal");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (this.view.getBtnGestionarAsistencia()==e.getSource())
		{
			VentanaPlanillaAsistencia ventanaPlanillaAsistencia=new VentanaPlanillaAsistencia();
			new ControladorVentanaPlanillaAsistencia(ventanaPlanillaAsistencia, selectedItem);
			
			Modal.showDialog(ventanaPlanillaAsistencia, "Tomar Asistencia", ventanaPlanillaAsistencia.getBtnOk());
		} else if (this.view.getBtnGestionarEvaluaciones()==e.getSource()) {
			VentanaPlanillaEvaluaciones ventanaPlanillaEvaluaciones=new VentanaPlanillaEvaluaciones();
			new ControladorVentanaPlanillaEvaluaciones(ventanaPlanillaEvaluaciones, selectedItem);
			Modal.showDialog(ventanaPlanillaEvaluaciones, "Subir planilla", ventanaPlanillaEvaluaciones.getBtnCerrar());
		}
	}
	
	private void mostrarVistaPrograma()
	{
		PanelProgramaDeCursada panel = new PanelProgramaDeCursada();
		new ControladorPanelProgramaDeCursada(panel, selectedItem);
		
		Modal dialog = new Modal(panel);
		dialog.setTitle("Programa de " + selectedItem.getNombre());
		dialog.setResizable(true);
		
		dialog.setVisible(true);
	}
	
	private void verInscriptos()
	{
		PanelConsultarInscriptos panelConsultarInscriptos=new PanelConsultarInscriptos();
		new ControladorPanelConsultarInscriptos(panelConsultarInscriptos,selectedItem);
		Modal.showDialog(panelConsultarInscriptos, "Inscriptos actuales", panelConsultarInscriptos.getBtnAceptar());
	}
}
