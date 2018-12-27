package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import dto.AlumnoDTO;
import dto.CursadaEmpresaDTO;
import dto.EmpresaDTO;
import modelo.ModeloCursos;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelGestionEmpresa;

import presentacion.vista.VentanaAgregarEmpresa;
import presentacion.vista.VentanaListarCursadaEmpresas;
import presentacion.vista.VentanaListarCursos;

public class ControladorPanelGestionEmpresa implements ActionListener
{
	private ModeloCursos model;
	private PanelGestionEmpresa vista;
	private EmpresaDTO selectedItem;
	
	public ControladorPanelGestionEmpresa(PanelGestionEmpresa vista)
	{
		model = ModeloCursos.getInstance();
		this.vista = vista;
		vista.getBtnAgregarEmpresa().addActionListener( e -> agregarEmpresa() );
		vista.getBtnAgregarCurso().addActionListener(this);
		vista.getBtnGestionarCursadasEmpresa().addActionListener(this);
		setSelectedItem(null);
		
		
		
		vista.setTable(crearTabla());
		vista.getTable().addDoubleClickListener(()->this.editarEmpresa());
		
	}

	private void editarEmpresa() 
	{
		ControladorAgregarEmpresa controlador = new ControladorAgregarEmpresa(new VentanaAgregarEmpresa(),selectedItem);
		controlador.initialize();
	}

	public TablaGenerica<EmpresaDTO> crearTabla()
	{
		String[] columnNames = new String[] 
					{ 
						"Nombre",
						"Empleados",
						"Cursos activos"
					};
		
		Transformer<EmpresaDTO> transformer = elem -> new String[] 
					{ 
						elem.getNombre(),
						getAlumnos(elem.getAlumnos()),
						getCursadas(elem.getCursadas())
					};
		
		TablaGenerica<EmpresaDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,
				() -> model.getEmpresas(),
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}

	

	private String getCursadas(List<CursadaEmpresaDTO> cursadas) 
	{
		String ret="";
	
		for (CursadaEmpresaDTO cursadaEmpresaDTO : cursadas) 
		{
			
			if(cursadaEmpresaDTO.getCursada().getEstado().getId()== model.getEstadosCursada().get(0).getId() || cursadaEmpresaDTO.getCursada().getEstado().getId()== model.getEstadosCursada().get(1).getId() || cursadaEmpresaDTO.getCursada().getEstado().getId()== model.getEstadosCursada().get(4).getId()) {
				ret=ret+" "+cursadaEmpresaDTO.getCursada().getNombre()+"/";}
		}
		
		
		return ret;
	}

	private String getAlumnos(List<AlumnoDTO> alumnos) 
	{
		String ret="";
		
		for (AlumnoDTO alumnoDTO : alumnos)
		{
			ret=ret+" "+alumnoDTO.getPersona().getNombre()+" "+alumnoDTO.getPersona().getApellido()+"/";
		}
		
		
		return ret;
		
		
	}

	private void setSelectedItem(EmpresaDTO elem) 
	{
		
		this.selectedItem = elem;
		this.vista.getBtnAgregarCurso().setEnabled(elem!=null);
		
		this.vista.getBtnGestionarCursadasEmpresa().setEnabled(elem!=null);
	}
	
	private void agregarEmpresa() 
	{
		
		ControladorAgregarEmpresa controlador = new ControladorAgregarEmpresa(new VentanaAgregarEmpresa());
		controlador.initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==this.vista.getBtnAgregarCurso())
		{
			VentanaListarCursos ventanaListarCursos=new VentanaListarCursos();
			new ControladoListarCursos(ventanaListarCursos,this.selectedItem);
			Modal.showDialog(ventanaListarCursos, "Cursos",ventanaListarCursos.getBtnSeleccionarCursadExistente());
		}
		if (e.getSource()==this.vista.getBtnGestionarCursadasEmpresa())
		{
			VentanaListarCursadaEmpresas ventanaListarCursadaEmpresas = new VentanaListarCursadaEmpresas();
			new ControladorListarCursadaEmpresas(ventanaListarCursadaEmpresas,selectedItem);
			Modal modal = new Modal(ventanaListarCursadaEmpresas, ventanaListarCursadaEmpresas.getBtnCerrar());
			modal.setTitle("Cursadas de la empresa: "+selectedItem.getNombre());
			modal.setModal(false);
			modal.setVisible(true);
		}
	}
}
