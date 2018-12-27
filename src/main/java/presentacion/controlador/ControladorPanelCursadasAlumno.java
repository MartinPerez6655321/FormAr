package presentacion.controlador;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;
import dto.InstructorDTO;
import modelo.ModeloCursos;
import modelo.ModeloPersonas;
import modelo.ModeloSala;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelCursadasAlumno;
import presentacion.vista.PanelGestionCuotasAlumno;
import presentacion.vista.PanelProgramaDeCursada;
import presentacion.vista.PanelTramitesAlumno;
import util.ValidadorCampos;

public class ControladorPanelCursadasAlumno {
	
	PanelCursadasAlumno panel;
	ModeloPersonas model;
	CursadaDTO selectedItem;
	AlumnoDTO alumnoActual;
	

	public ControladorPanelCursadasAlumno(PanelCursadasAlumno panelCursadasAlumno) {
		this.panel=panelCursadasAlumno;
		model=ModeloPersonas.getInstance();
		setSelectedItem(null);
		
		this.panel.getBtnPrograma().addActionListener(e->verPrograma());
		this.panel.getBtnDescargarPrograma().addActionListener(e->descargarPrograma());
		this.panel.getBtnTramites().addActionListener(e->abrirVistaTramites());
		this.panel.getBtnCuotas().addActionListener(e->verCuotas());
		
		alumnoActual = model.getAlumno(model.getUsuarioActual());
		this.panel.setTable(crearTabla());
		
		
		
	}
	
	
	
	private void verCuotas() {
		
		if(!esUnaCursadaEmpresa(selectedItem)) {
			PanelGestionCuotasAlumno gestionarPagos = new PanelGestionCuotasAlumno();
			new ControladorPanelGestionCuotasAlumno(gestionarPagos,this.alumnoActual,this.selectedItem);
			Modal dialog=new Modal(gestionarPagos);
			dialog.setTitle("Cuotas de cursada: " + this.selectedItem.getNombre()+" ("+this.selectedItem.getCurso().getNombre()+")");
			dialog.setModal(false);
			dialog.setVisible(true);}
		else {
			JOptionPane.showMessageDialog(null, "Esta es una cursada a pagar por la empresa");
		}
		
	}



	private boolean esUnaCursadaEmpresa(CursadaDTO selectedItem2) {
		for(CursadaEmpresaDTO e:ModeloCursos.getInstance().getCursadasEmpresas()) {
			if(e.getCursada().getId()==selectedItem2.getId()) {
				return true;
			}
		}
		return false;
	}



	private void abrirVistaTramites() {
		PanelTramitesAlumno panelTramites=new PanelTramitesAlumno();
		new ControladorPanelTramitesAlumno(panelTramites,alumnoActual);
		Modal.showDialog(panelTramites, "Tr\u00E1mites", panelTramites.getBtnGenerarReporteAcadmico(),panelTramites.getBtnSolicitarReporte());
		
	}



	private TablaGenerica<CursadaDTO> crearTabla() {
		Transformer<CursadaDTO> transformer = elem -> new String[] 
				{ 
					elem.getNombre(),
					elem.getCurso().getNombre(),
					elem.getSala() == null? "Sin sala asignada" : elem.getSala().getAlias() + "(" + elem.getSala().getCodigo() + ")",
					elem.getPrograma().isEmpty()? "Sin programa" : "Programa subido",
					ValidadorCampos.convertirFecha(elem.getInicio()),
					ValidadorCampos.convertirFecha(elem.getFin()),
					ValidadorCampos.getHorarios(elem.getHorarios()),
					verInstructores(elem.getInstructores())
					
				};
	
	
		
		String[] columnNames = new String[] 
				{ 
					"Nombre",
					"Curso",
					"Sala", 
					"Programa",
					"Fecha de comienzo",
					"Fecha de finalizaci\u00F3n",
					"Horarios",
					"Instructor"
				};
		
		TablaGenerica<CursadaDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,
				() -> ModeloCursos.getInstance().getCursadasPorAlumno(alumnoActual),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		ModeloCursos.getInstance().addListener(table);

		ModeloSala.getInstance().addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}



	private String verInstructores(List<InstructorDTO> instructores) {
		String ret="";
		for(InstructorDTO e:instructores) {
			ret=ret+e.getPersona().getNombre()+" "+e.getPersona().getApellido()+" ";
		}
		
		return ret;
	}



	private void descargarPrograma()
	{
		if(!this.selectedItem.getPrograma().equals("")) {
			this.descargar();
		}
		else {
			JOptionPane.showMessageDialog(null, "Esta cursada no posee un programa cargado a\u00FAn.", "Sin programa", JOptionPane.WARNING_MESSAGE, null);
		}
	}

	private void descargar() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		if(chooser.showSaveDialog(panel) == JFileChooser.APPROVE_OPTION)
		{
			try(FileOutputStream fos = new FileOutputStream(chooser.getSelectedFile()+".pdf"))
			{
				fos.write(Base64.getDecoder().decode(this.selectedItem.getPrograma()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	private void verPrograma() {
		if(!this.selectedItem.getPrograma().equals("")) {
			PanelProgramaDeCursada panel = new PanelProgramaDeCursada();
			panel.getBtnCargar().setVisible(false);
			panel.getBtnEliminar().setVisible(false);
			
			new ControladorPanelProgramaDeCursada(panel, selectedItem);
			
			Modal dialog = new Modal(panel);
			dialog.setTitle("Programa de " + selectedItem.getNombre());
			dialog.setResizable(true);
			
			dialog.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Esta cursada no posee un programa cargado a\u00FAn.", "Sin programa", JOptionPane.WARNING_MESSAGE, null);
		}
		
		
	}



	private void setSelectedItem(CursadaDTO cursada) 
	{
		selectedItem = cursada;
		this.panel.getBtnDescargarPrograma().setEnabled(cursada!=null);
		this.panel.getBtnPrograma().setEnabled(cursada!=null);
		this.panel.getBtnCuotas().setEnabled(cursada!=null);
	}

}
