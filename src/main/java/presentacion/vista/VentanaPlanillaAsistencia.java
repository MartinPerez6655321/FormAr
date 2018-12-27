package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.PlanillaDeAsistenciasDTO;
import presentacion.components.tabla.TablaGenerica;

public class VentanaPlanillaAsistencia extends JPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -537594611645965876L;

	private TablaGenerica<PlanillaDeAsistenciasDTO> table;
	
	private JButton btnOk;
	
	public VentanaPlanillaAsistencia()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		this.btnOk=new JButton("Cerrar");
		buttonPane.add(btnOk);
		
	}

	public TablaGenerica<PlanillaDeAsistenciasDTO> getTable() {
		return table;
	}

	public void setTable(TablaGenerica<PlanillaDeAsistenciasDTO> table)
	{
		this.table = table;
		add(this.table, BorderLayout.CENTER);
	}

	public JButton getBtnOk() {
		return btnOk;
	}

	public void setBtnOk(JButton btnOk) {
		this.btnOk = btnOk;
	}
	
	
	
}
