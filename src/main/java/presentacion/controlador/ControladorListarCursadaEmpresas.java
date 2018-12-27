package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;

import dto.EmpresaDTO;
import dto.InscripcionAlumnoDTO;
import modelo.ModeloCursos;
import presentacion.components.Modal;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VentanaCuotasEmpresa;
import presentacion.vista.VentanaInscripcionAlumno;
import presentacion.vista.VentanaListarCursadaEmpresas;

public class ControladorListarCursadaEmpresas implements ActionListener {

	private VentanaListarCursadaEmpresas view;
	private ModeloCursos model;
	private CursadaEmpresaDTO selectedItem;
	private EmpresaDTO empresaRecibida;
	private boolean esAgregar;

	public ControladorListarCursadaEmpresas(VentanaListarCursadaEmpresas vista, EmpresaDTO empresa) {
		this.view = vista;
		this.model = ModeloCursos.getInstance();
		empresaRecibida = empresa;

		setSelectedItem(null);

		view.setTable(crearTabla());
		view.getBtnGestionarInscripciones().addActionListener(this);
		view.getBtnSeleccionar().setVisible(false);
		view.getBtnGestionarPagos().addActionListener(this);
		
	}

	public ControladorListarCursadaEmpresas(VentanaListarCursadaEmpresas vista, EmpresaDTO empresa, boolean b) {
		esAgregar = b;
		this.view = vista;
		this.model = ModeloCursos.getInstance();
		empresaRecibida = empresa;

		setSelectedItem(null);

		view.setTable(crearTabla());
		view.getBtnGestionarInscripciones().setVisible(false);
		view.getBtnSeleccionar().setVisible(true);
		view.getBtnSeleccionar().addActionListener(this);
		view.getBtnCerrar().setVisible(false);
		view.getBtnGestionarPagos().setVisible(false);
		

	}

	private TablaGenerica<CursadaEmpresaDTO> crearTabla() {
		Transformer<CursadaEmpresaDTO> transformer = elem -> new String[] { elem.getCursada().getNombre(),
				getAlumnos(elem.getCursada()),elem.getCursada().getEstado().getNombre() };

		Obtainer<CursadaEmpresaDTO> obtainer = new Obtainer<CursadaEmpresaDTO>() {

			@Override
			public List<CursadaEmpresaDTO> obtain() 
			{

				if (!esAgregar)
					return model.getCursadasPorEmpresa(empresaRecibida);
				return filtrarCursadas(model.getCursadasEmpresas());
			}

			private List<CursadaEmpresaDTO> filtrarCursadas(List<CursadaEmpresaDTO> cursadasEmpresas) {
				List<CursadaEmpresaDTO> ret = new ArrayList<>();
				List<CursadaEmpresaDTO> ret2 = new ArrayList<>();

				for (CursadaEmpresaDTO cursadaEmpresaDTO : cursadasEmpresas) {
					ret.add(cursadaEmpresaDTO);
					ret2.add(cursadaEmpresaDTO);

				}

				for (CursadaEmpresaDTO cursada : ret) {
					for (CursadaEmpresaDTO cursadaEmpresaDTO : empresaRecibida.getCursadas()) {
						if (cursada.equals(cursadaEmpresaDTO))
							ret2.remove(cursadaEmpresaDTO);
					}
				}

				return ret2;

			}

		};

		String[] columnNames = new String[] { "Nombre", "Alumnos","Estado cursada" };

		TablaGenerica<CursadaEmpresaDTO> table = new TablaGenerica<>(columnNames, transformer, obtainer,
				FilterPanel.stringFilterPanel(columnNames, transformer));

		model.addListener(table);

		table.addSelectionListener(this::setSelectedItem);

		return table;
	}

	private String getAlumnos(CursadaDTO cursada) {
		String ret = "";
		for (InscripcionAlumnoDTO inscripcion : cursada.getInscripciones()) {
			ret = ret + " " + inscripcion.getAlumno().getPersona().getNombre() + " "
					+ inscripcion.getAlumno().getPersona().getApellido() + "/";
		}
		return ret;
	}

	private void setSelectedItem(CursadaEmpresaDTO cursadaEmpresa) {
		selectedItem = cursadaEmpresa;
		this.view.getBtnGestionarInscripciones().setEnabled(cursadaEmpresa != null  &&cursadaEmpresa.getCursada().getEstado().getId().equals(2));
		this.view.getBtnSeleccionar().setEnabled(cursadaEmpresa != null && !cursadaEmpresa.getCursada().getEstado().getId().equals(3));
		this.view.getBtnGestionarPagos().setEnabled(cursadaEmpresa!=null );
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.view.getBtnGestionarInscripciones()) {
			VentanaInscripcionAlumno ventanaInscAlumno = new VentanaInscripcionAlumno();

			ControladorInscripcionAlumno controller = new ControladorInscripcionAlumno(ventanaInscAlumno,
					this.selectedItem.getCursada(),empresaRecibida, true);
			controller.initialize();
		}

		if (e.getSource() == this.view.getBtnSeleccionar()) {

			empresaRecibida.getCursadas().add(selectedItem);
			model.modificarEmpresa(empresaRecibida);

		}
		if (e.getSource()==this.view.getBtnGestionarPagos())
		{
			VentanaCuotasEmpresa vistaCuotasEmpresa=new VentanaCuotasEmpresa();
			new ControladorVentanaCuotasEmpresa(vistaCuotasEmpresa,selectedItem,empresaRecibida);
			Modal.showDialog(vistaCuotasEmpresa, "Cuotas de la cursada: "+selectedItem.getCursada().getNombre(), vistaCuotasEmpresa.getBtnPagar());
			
		}

	}

}
