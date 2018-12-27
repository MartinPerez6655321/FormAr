package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import dto.CursadaDTO;
import dto.PlanillaDeParcialesDTO;
import modelo.ModeloCursos;
import presentacion.components.Modal;
import presentacion.components.tabla.DoubleClickListener;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;
import presentacion.vista.VentanaCrearPlanillaEvaluaciones;

import presentacion.vista.VentanaPlanillaEvaluaciones;

import presentacion.vista.VentanaTomarParcial;
import util.ValidadorCampos;

public class ControladorVentanaPlanillaEvaluaciones implements ActionListener, DoubleClickListener 
{
	private VentanaPlanillaEvaluaciones view;
	private ModeloCursos model;
	private PlanillaDeParcialesDTO selectedItem;

	private CursadaDTO cursadaRecibida;
	
	public ControladorVentanaPlanillaEvaluaciones(VentanaPlanillaEvaluaciones ventana,CursadaDTO cursada)
	{
		this.view=ventana;
		this.cursadaRecibida=cursada;
		this.model=ModeloCursos.getInstance();
		

		setSelectedItem(null);

		view.getBtnAgregarPlanilla().addActionListener(this);;
		view.getBtnEditarPlanilla().addActionListener(this);
		view.setTable(crearTabla());
		view.getTable().addDoubleClickListener(this);
	}
	
	private TablaGenerica<PlanillaDeParcialesDTO> crearTabla() 
	{
		Transformer<PlanillaDeParcialesDTO> transformer = elem -> new String[] 
				{ 
					ValidadorCampos.convertirFecha(elem.getFecha())
				};
		
		Obtainer<PlanillaDeParcialesDTO> obtainer = cursadaRecibida::getParciales;
		
		String[] columnNames = new String[] 
				{ 
					"Fecha evaluaci\u00F3n", 
				};
		
		TablaGenerica<PlanillaDeParcialesDTO> table = new TablaGenerica<>(
				columnNames, 
				transformer,
				obtainer,
				FilterPanel.stringFilterPanel(columnNames, transformer));
		
		model.addListener(table);
		
		table.addSelectionListener(this::setSelectedItem);
		
		return table;
	}

	private void setSelectedItem(PlanillaDeParcialesDTO planilla) 
	{
		this.view.getBtnEditarPlanilla().setEnabled(planilla!=null);;
		this.selectedItem=planilla;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==this.view.getBtnAgregarPlanilla())
		{
			if (!cursadaRecibida.getEstado().getId().equals(1))
			{
				JOptionPane.showMessageDialog(null, "No puedes crear planillas nuevas en una cursada que aun no comienza o finaliz\u00F3");
				return;
			}
			
			VentanaCrearPlanillaEvaluaciones ventanaPlanilla=new VentanaCrearPlanillaEvaluaciones();
			
			
			
			new ControladorVentanaCrearPlanillaEvaluaciones(ventanaPlanilla, cursadaRecibida);
			
			Modal.showDialog(ventanaPlanilla, "Cargar planilla", ventanaPlanilla.getBtnGuardar());
		}
		
		if (e.getSource()==this.view.getBtnEditarPlanilla()) 
		{
			if (!cursadaRecibida.getEstado().getId().equals(1))
			{
				JOptionPane.showMessageDialog(null, "No puedes editar planillas en una cursada ya finalizada");
				return;
			}
			
			VentanaCrearPlanillaEvaluaciones ventanaPlanilla=new VentanaCrearPlanillaEvaluaciones();
			new ControladorVentanaCrearPlanillaEvaluaciones(ventanaPlanilla, cursadaRecibida,this.selectedItem);
			
			Modal.showDialog(ventanaPlanilla, "Editar planilla", ventanaPlanilla.getBtnGuardar());
		}
	}

	@Override
	public void notifyDoubleClick() 
	{
		
		
		VentanaTomarParcial ventanaTomarParciales=new VentanaTomarParcial();
		new ControladorVentanaTomarParciales(ventanaTomarParciales, selectedItem);
		Modal.showDialog(ventanaTomarParciales, "Notas", ventanaTomarParciales.getBtnOk());
	
	}
}
