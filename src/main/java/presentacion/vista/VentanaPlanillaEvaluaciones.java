package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.PlanillaDeParcialesDTO;
import presentacion.components.tabla.TablaGenerica;

public class VentanaPlanillaEvaluaciones extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4120266939308786454L;

	
	private TablaGenerica<PlanillaDeParcialesDTO> table;
	
	private JButton btnCerrar;
	
	private JButton btnAgregarPlanilla;
	private JButton btnEditarPlanilla;
	
	
	
	public VentanaPlanillaEvaluaciones()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		this.btnAgregarPlanilla=new JButton("Agregar planilla");
		buttonPane.add(btnAgregarPlanilla);
		
		btnEditarPlanilla = new JButton("Editar planilla");
		buttonPane.add(btnEditarPlanilla);
		
		this.btnCerrar=new JButton("Cerrar");
		buttonPane.add(btnCerrar);
		
	}



	public TablaGenerica<PlanillaDeParcialesDTO> getTable() {
		return table;
	}



	public void setTable(TablaGenerica<PlanillaDeParcialesDTO> table) {
		this.table = table;
		add(this.table, BorderLayout.CENTER);
	}



	public JButton getBtnAgregarPlanilla() {
		return btnAgregarPlanilla;
	}



	public void setBtnAgregarParcial(JButton btnAgregarParcial) {
		this.btnAgregarPlanilla = btnAgregarParcial;
	}



	public JButton getBtnCerrar() {
		return btnCerrar;
	}



	public void setBtnCerrar(JButton btnCerrar) {
		this.btnCerrar = btnCerrar;
	}



	public JButton getBtnEditarPlanilla() {
		return btnEditarPlanilla;
	}



	public void setBtnEditarPlanilla(JButton btnEditarPlanilla) {
		this.btnEditarPlanilla = btnEditarPlanilla;
	}
	
	
	
}
