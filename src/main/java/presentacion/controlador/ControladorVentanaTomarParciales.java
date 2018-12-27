package presentacion.controlador;

import java.util.List;

import dto.EstadoEvaluacionDTO;
import dto.ParcialDTO;
import dto.PlanillaDeParcialesDTO;
import modelo.ModeloCursos;
import presentacion.components.Modal;
import presentacion.components.tabla.DoubleClickListener;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VentanaEditarParcial;
import presentacion.vista.VentanaTomarParcial;

public class ControladorVentanaTomarParciales implements DoubleClickListener {

	private VentanaTomarParcial view;
	private ModeloCursos model = ModeloCursos.getInstance();

	private PlanillaDeParcialesDTO planillaRecibida;

	private ParcialDTO selectedItem;

	public ControladorVentanaTomarParciales(VentanaTomarParcial ventanaTomarParciales,
			PlanillaDeParcialesDTO planilla) {
		this.view = ventanaTomarParciales;
		this.planillaRecibida = planilla;

		setSelectedItem(null);

		view.setTable(crearTabla());
		view.geTable().addDoubleClickListener(this);

	}

	private TablaGenerica<ParcialDTO> crearTabla() {
		Transformer<ParcialDTO> transformer = elem -> new String[] {
				elem.getAlumno().getPersona().getNombre() + " " + elem.getAlumno().getPersona().getApellido(),
				elem.getEstado().getNombre(), transcribirNota(elem.getNota(),elem.getEstado()) };

		Obtainer<ParcialDTO> obtainer = new Obtainer<ParcialDTO>() {

			@Override
			public List<ParcialDTO> obtain() {
				
				
				return planillaRecibida.getParciales();
			}

		};

		String[] columnNames = new String[] { "Alumno", "Estado", "Nota" };

		TablaGenerica<ParcialDTO> table = new TablaGenerica<>(columnNames, transformer, obtainer,
				FilterPanel.stringFilterPanel(columnNames, transformer));

		model.addListener(table);

		table.addSelectionListener(this::setSelectedItem);

		return table;
	}

	private String transcribirNota(Integer nota, EstadoEvaluacionDTO estadoEvaluacionDTO) 
	{
		if (estadoEvaluacionDTO.getId().equals(4) || estadoEvaluacionDTO.getId().equals(1))
			return "-";
		
		return String.valueOf((float)nota/10);
	}

	private void setSelectedItem(ParcialDTO parcial) {
		this.selectedItem = parcial;

	}

	@Override
	public void notifyDoubleClick() 
	{
		VentanaEditarParcial ventanaEditarParciales=new VentanaEditarParcial();
		new ControladorVentanaEditarParciales(ventanaEditarParciales,selectedItem);
		Modal.showDialog(ventanaEditarParciales, "Editar evaluaci\u00F3n del alumno"+selectedItem.getAlumno().getPersona().getNombre()+" "+selectedItem.getAlumno().getPersona().getApellido(), ventanaEditarParciales.getBtnGuardar());
		
	}

}
