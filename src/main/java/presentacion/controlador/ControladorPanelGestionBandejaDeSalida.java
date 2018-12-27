package presentacion.controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.RecadoDTO;
import modelo.ModeloPersonas;
import modelo.ModeloRecados;
import presentacion.components.tabla.FilterComponent;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelGestionBandejaDeSalida;
import presentacion.vista.VentanaVerRecado;
import util.RTFDocumentToString;

public class ControladorPanelGestionBandejaDeSalida
{
	private ModeloRecados model;
	private RecadoDTO selectedItem;
	private PanelGestionBandejaDeSalida panel;
	
	public ControladorPanelGestionBandejaDeSalida(PanelGestionBandejaDeSalida vista)
	{
		model = ModeloRecados.getInstance();
		this.panel = vista;
		setSelectedItem(null);
		panel.setTable(crearTabla());
		panel.getBtnEliminar().addActionListener ( e -> eliminarRecado() );
		panel.getTable().addDoubleClickListener(() -> verRecado());
	}

	private void setSelectedItem(RecadoDTO recado) 
	{
		selectedItem = recado;
		this.panel.getBtnEliminar().setEnabled(selectedItem!=null);
	}
	
	public TablaGenerica<RecadoDTO> crearTabla()
	{
		Transformer<RecadoDTO> transformer = elem -> new String[] { elem.getAsunto() ,
					this.formatearFecha(elem) ,
					elem.getHora().toString() ,
					elem.getReceptor().getPersona().getApellido()+" "+elem.getReceptor().getPersona().getNombre()
		};
		
		List<String> filterNames = new LinkedList<>();
		List<FilterComponent<RecadoDTO>> filters = new LinkedList<>();
		
//		filterNames.add("Estado");
//		filters.add(new TextFieldFilter<RecadoDTO>(elem -> new String[] { elem.getEstado().getNombre() }));
		
		FilterPanel<RecadoDTO> filterPanel = new FilterPanel<>(filterNames, filters);
		
		Obtainer<RecadoDTO> obtainer = new Obtainer<RecadoDTO>()
		{
			
			public List<RecadoDTO> obtain()
			{
				List<RecadoDTO> recadosDisponibles = new ArrayList<>();
				for(RecadoDTO recado : model.getRecados()) 
				{
					if(recado.getEmisor().equals(ModeloPersonas.getInstance().getUsuarioActual()) && recado.getDisponibilidadEmisor()) 
					{ 
						recadosDisponibles.add(recado);
					}
				}
				return recadosDisponibles;
			}
	
		};
		
		TablaGenerica<RecadoDTO> table = new TablaGenerica<>(
				new String[] { "Asunto", "Fecha", "Hora","Receptor" }, 
				transformer,  
				obtainer,
				filterPanel);
		
		model.addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}

	private String formatearFecha(RecadoDTO recado) {
		return new SimpleDateFormat("dd-MM-yyyy").format(recado.getFecha());
	}
	
	private void eliminarRecado() {
		if(JOptionPane.showConfirmDialog(null,"<html>¿Est\u00E1 seguro que quiere eliminar recado?</html>", "Eliminar Recado",JOptionPane.YES_NO_OPTION)==0) 
		{
			this.selectedItem.setDisponibilidadEmisor(false);
			this.model.modificarRecado(selectedItem);
			this.model.actualizarRecados();
		}
	}
	
	private void verRecado() {
		this.visualizarRecado(this.selectedItem);
	}
	
	private void visualizarRecado(RecadoDTO recado) {
		VentanaVerRecado verMensaje = new VentanaVerRecado();
		verMensaje.getLblEmisorReceptor().setText("Receptor");
		verMensaje.getTxtEmisor().setText(recado.getReceptor().getPersona().getApellido()+" "+recado.getReceptor().getPersona().getNombre());
		verMensaje.getTxtEmisor().setEditable(false);
		verMensaje.getTxtFecha().setText(recado.getFecha().toString());
		verMensaje.getTxtFecha().setEditable(false);
		verMensaje.getTxtAsunto().setText(recado.getAsunto());
		verMensaje.getTxtAsunto().setEditable(false);
		verMensaje.getTxtHora().setText(recado.getHora().toString());
		verMensaje.getTxtHora().setEditable(false);
		
		RTFDocumentToString.stringToRTFDocument(verMensaje.getTextAreaMensaje(), recado.getMensaje());
		verMensaje.getTextAreaMensaje().setEditable(false);
		verMensaje.show();
	}
	
}
