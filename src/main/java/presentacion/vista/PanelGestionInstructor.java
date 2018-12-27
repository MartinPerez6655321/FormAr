package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import dto.InstructorDTO;
import presentacion.components.tabla.TablaGenerica;
import util.EstilosYColores;

import javax.swing.JButton;

public class PanelGestionInstructor extends JPanel 
{
	private static final long serialVersionUID = 2579077397936124760L;
	TablaGenerica<InstructorDTO> table;
	private JButton btnAgregarInstructor;
	private JButton btnAsignacionDeSala;
	private EstilosYColores style = EstilosYColores.getInstance();

	public PanelGestionInstructor()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);

		btnAgregarInstructor = new JButton("Agregar Instructor");
		btnAgregarInstructor.setBackground(style.getBackgroundButtonCreate());
		btnAgregarInstructor.setForeground(style.getForegroundButtonCreate());
		buttonPane.add(btnAgregarInstructor);
		
		btnAsignacionDeSala = new JButton("Consultar disponibilidad y asignaciones");
		buttonPane.add(btnAsignacionDeSala);
	}
	
	public TablaGenerica<InstructorDTO> getTable()
	{
		return table;
	}
	
	public void setTable(TablaGenerica<InstructorDTO> table)
	{
		this.table = table;
		add(this.table, BorderLayout.CENTER);
	}

	public JButton getBtnAgregarInstructor() {
		return btnAgregarInstructor;
	}
	
	public JButton getBtnAsignacionDeSala()
	{
		return btnAsignacionDeSala;
	}
}
