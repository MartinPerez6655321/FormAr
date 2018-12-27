package presentacion.controlador;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

import dto.EstadoDeRecadoDTO;
import dto.RecadoDTO;
import modelo.ModeloPersonas;
import modelo.ModeloRecados;
import presentacion.vista.VentanaCrearRecado;
import util.RTFDocumentToString;
import util.ValidadorCampos;

public class ControladorCrearRecado
{
	private ModeloRecados model;
	private VentanaCrearRecado vista;
	private ControladorVentanaAsignarUsuario controlador;
	
	public ControladorCrearRecado(VentanaCrearRecado view)
	{
		model = ModeloRecados.getInstance();
		this.vista = view;
		this.vista.getTxtReceptor().setEditable(false);
		Date fechaActual = new Date();
		String fechaActualString = new SimpleDateFormat("dd-MM-yyyy").format(fechaActual);
		this.vista.getTxtFecha().setText(fechaActualString);
		this.vista.getTxtFecha().setEditable(false);
		
		vista.getBtnCrearRecado().addActionListener ( e -> crearRecado() );
		vista.getBtnAsignarReceptor().addActionListener( e -> asignarReceptor() );
		
	}

	public void initialize()
	{
		this.vista.show();
	}
	
	private void crearRecado()
	{
		if (!ValidadorCampos.validarVacio(this.vista.getTxtReceptor().getText()) ||
			!ValidadorCampos.validarVacio(this.vista.getTxtAsunto().getText()) ||
			!ValidadorCampos.validarVacio(this.vista.getTextAreaMensaje().getText())) 
		{
			JOptionPane.showMessageDialog(null, "Faltan completar campos al recado");
			return;
		}
		if(this.controlador!=null)
		{
			if(this.controlador.getUsuarioSeleccionado()!=null)
			{
				RecadoDTO newRecado = new RecadoDTO();
				newRecado.setEmisor(ModeloPersonas.getInstance().getUsuarioActual());
				newRecado.setReceptor(this.controlador.getUsuarioSeleccionado());
				newRecado.setAsunto(this.vista.getTxtAsunto().getText());
				Date fechaActual = new Date();
				newRecado.setFecha(fechaActual);
				newRecado.setHora((new java.sql.Time(fechaActual.getTime())));
				List<EstadoDeRecadoDTO> recados = this.model.getEstadoRecados();
				newRecado.setEstado(recados.get(1));
				newRecado.setMensaje(RTFDocumentToString.documentToString(this.vista.getTextAreaMensaje()));
				newRecado.setDisponibilidadEmisor(true);
				newRecado.setDisponibilidadReceptor(true);
				this.model.agregarRecado(newRecado);
				this.vista.close();
				JOptionPane.showMessageDialog(null, "Recado Enviado!!");
			}
		}
	}
	
	private void asignarReceptor() {
		controlador = new ControladorVentanaAsignarUsuario(this.vista);
		controlador.initialize();
	}
	
}
