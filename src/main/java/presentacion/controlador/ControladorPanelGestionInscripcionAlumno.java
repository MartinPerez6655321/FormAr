package presentacion.controlador;





import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;

import modelo.ModeloCursos;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelGestionInscripcionAlumno;
import presentacion.vista.VentanaInscripcionAlumno;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorPanelGestionInscripcionAlumno {
	private PanelGestionInscripcionAlumno panel;
	private ModeloCursos model;
	private CursadaDTO selectedItem;

	public ControladorPanelGestionInscripcionAlumno(PanelGestionInscripcionAlumno panel) {

		this.panel = panel;
		model = ModeloCursos.getInstance();
		setSelectedItem(null);
		panel.setTable(crearTabla());
		panel.getBtnInscribirAlumno().addActionListener(e -> inscribirAlumno());

	}

	private void inscribirAlumno() 
	{
		if (this.selectedItem == null) {
			JOptionPane.showMessageDialog(null, "Selecciona alguna cursada");
		} else {
			VentanaInscripcionAlumno ventanaInscAlumno = new VentanaInscripcionAlumno();
			if (this.selectedItem.getHorarios().isEmpty())
				JOptionPane.showMessageDialog(null, "La cursada seleccionada no tiene horarios de cursada asignados.");
			else if (!ValidadorLogico.validarInscripcionAbierta(this.selectedItem))
				JOptionPane.showMessageDialog(null, "La cursada no tiene inscripciones abiertas");

			else {

				ControladorInscripcionAlumno controller = new ControladorInscripcionAlumno(ventanaInscAlumno,
						this.selectedItem);
				controller.initialize();
			}
		}

	}

	private void setSelectedItem(CursadaDTO cursada) {
		selectedItem = cursada;
		panel.getBtnInscribirAlumno().setEnabled(selectedItem != null && selectedItem.getDisponibleEnSistema()&& !selectedItem.getEstado().getId().equals(3) && !selectedItem.getEstado().getId().equals(1)  );

	}

	public TablaGenerica<CursadaDTO> crearTabla() {
		Transformer<CursadaDTO> transformer = elem -> new String[] { elem.getNombre(),
				Integer.toString(elem.getVacantes()), Integer.toString(elem.getMontoTotal()),
				ValidadorCampos.convertirFecha(elem.getInicio()),
				ValidadorCampos.convertirFecha(elem.getFin()),
				ValidadorCampos.getHorarios(elem.getHorarios()), elem.getEstado().getNombre()

		};
		String[] columnNames = new String[] { "Nombre", "vacantes", "Precio", "Comienza", "Finaliza", "Horario",
				"Estado" };

		TablaGenerica<CursadaDTO> table = new TablaGenerica<>(columnNames, transformer,
				() -> filtrarCursadasNoEmpresas(model.getCursadas()),
				FilterPanel.stringFilterPanel(columnNames, transformer));

		model.addListener(table);
		table.addSelectionListener(this::setSelectedItem);

		return table;
	}

	private List<CursadaDTO> filtrarCursadasNoEmpresas(List<CursadaDTO> cursadas) 
	{
		List<CursadaDTO> ret = new LinkedList<>(model.getCursadas());

		
			for (CursadaEmpresaDTO cursadaEmpresaDTO : model.getCursadasEmpresas()) 
			{
				
					ret.remove(cursadaEmpresaDTO.getCursada());
					
				
			}
		
		return ret;
	}
}