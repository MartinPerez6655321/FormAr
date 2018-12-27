package presentacion.controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.EstadoDeRecadoDTO;
import dto.RecadoDTO;
import modelo.ModeloPersonas;
import modelo.ModeloRecados;
import presentacion.components.tabla.FilterComponent;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.PanelGestionBandejaDeEntrada;
import presentacion.vista.VentanaCrearRecado;
import presentacion.vista.VentanaVerRecado;
import util.RTFDocumentToString;
import util.RenderForMessage;

public class ControladorPanelGestionBandejaDeEntrada
{
	private ModeloRecados model;
	private RecadoDTO selectedItem;
	private PanelGestionBandejaDeEntrada panel;
	
	public ControladorPanelGestionBandejaDeEntrada(PanelGestionBandejaDeEntrada vista)
	{
		model = ModeloRecados.getInstance();
		this.panel = vista;
		setSelectedItem(null);
		panel.setTable(crearTabla());
		TablaGenerica<RecadoDTO> tabla = panel.getTable();
		tabla.getTable().setDefaultRenderer(Object.class, new RenderForMessage(3));
		panel.getBtnCrearRecado().addActionListener ( e -> crearRecado() );
		panel.getBtnEliminarRecado().addActionListener( e -> eliminarRecado() );
		panel.getTable().addDoubleClickListener(() -> verRecado());
	}

	private void setSelectedItem(RecadoDTO recado) 
	{
		selectedItem = recado;
		this.panel.getBtnEliminarRecado().setEnabled(selectedItem!=null);
	}
	
	public TablaGenerica<RecadoDTO> crearTabla()
	{
		Transformer<RecadoDTO> transformer = elem -> new String[] { elem.getAsunto() ,
					this.formatearFecha(elem) ,
					elem.getEmisor().getPersona().getApellido()+" "+elem.getEmisor().getPersona().getNombre() ,
					elem.getEstado().getNombre()
		};
		
		List<String> filterNames = new LinkedList<>();
		List<FilterComponent<RecadoDTO>> filters = new LinkedList<>();
		
		FilterPanel<RecadoDTO> filterPanel = new FilterPanel<>(filterNames, filters);
		
		Obtainer<RecadoDTO> obtainer = new Obtainer<RecadoDTO>()
		{
			
			public List<RecadoDTO> obtain()
			{
				List<RecadoDTO> recadosDisponibles = new ArrayList<>();
				for(RecadoDTO recado : model.getRecados()) 
				{
					if(recado.getReceptor().equals(ModeloPersonas.getInstance().getUsuarioActual()) && recado.getDisponibilidadReceptor()) 
					{ 
						recadosDisponibles.add(recado);
					}
				}
				return recadosDisponibles;
			}
	
		};
		
		TablaGenerica<RecadoDTO> table = new TablaGenerica<>(
				new String[] { "Asunto", "Fecha", "Emisor", "Estado"}, 
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
	
	private void crearRecado() {
		ControladorCrearRecado controlador = new ControladorCrearRecado(new VentanaCrearRecado());
		controlador.initialize();
	}
	
	private void eliminarRecado() {
		if(JOptionPane.showConfirmDialog(null,"<html>¿Est\u00E1 seguro que quiere eliminar recado?</html>", "Eliminar Recado",JOptionPane.YES_NO_OPTION)==0) 
		{
			this.selectedItem.setDisponibilidadReceptor(false);
			this.model.modificarRecado(selectedItem);
			this.model.actualizarRecados();
		}
	}
	
	private void verRecado() {
		this.visualizarRecado(this.selectedItem);
	}
	
	private void visualizarRecado(RecadoDTO recado) {
		VentanaVerRecado verMensaje = new VentanaVerRecado();
		verMensaje.getLblEmisorReceptor().setText("Emisor");
		verMensaje.getTxtEmisor().setText(recado.getEmisor().getPersona().getApellido()+" "+recado.getEmisor().getPersona().getNombre());
		verMensaje.getTxtEmisor().setEditable(false);
		verMensaje.getTxtFecha().setText(recado.getFecha().toString());
		verMensaje.getTxtFecha().setEditable(false);
		verMensaje.getTxtAsunto().setText(recado.getAsunto());
		verMensaje.getTxtAsunto().setEditable(false);
		verMensaje.getTxtHora().setText(recado.getHora().toString());
		verMensaje.getTxtHora().setEditable(false);
		
		RTFDocumentToString.stringToRTFDocument(verMensaje.getTextAreaMensaje(), recado.getMensaje());
		verMensaje.getTextAreaMensaje().setEditable(false);
		List<EstadoDeRecadoDTO> recados = this.model.getEstadoRecados();
		recado.setEstado(recados.get(0));
		this.model.modificarRecado(recado);
		this.model.actualizarRecados();
		verMensaje.show();
	}
	
}
