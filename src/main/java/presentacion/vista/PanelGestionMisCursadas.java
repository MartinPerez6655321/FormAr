package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


import dto.CursadaDTO;
import presentacion.components.tabla.TablaGenerica;

public class PanelGestionMisCursadas extends JPanel
{
	private static final long serialVersionUID = -5706746948844580105L;

	private JButton btnVerAgendaSemanal;
	private JButton btnGestionarAsistencia;
	private JButton btnGestionarEvaluaciones;
	private JButton btnPrograma;

	private TablaGenerica<CursadaDTO> table;
	
	public PanelGestionMisCursadas()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnVerAgendaSemanal = new JButton("Ver agenda semanal");
		buttonPane.add(btnVerAgendaSemanal);

		btnGestionarAsistencia = new JButton("Gestionar Asistencia");
		buttonPane.add(btnGestionarAsistencia);
		
		btnGestionarEvaluaciones = new JButton("Gestionar Evaluaciones");
		buttonPane.add(btnGestionarEvaluaciones);
		
		btnPrograma = new JButton("Programa");
		buttonPane.add(btnPrograma);
	}
	
	public JButton getBtnVerAgendaSemanal()
	{
		return btnVerAgendaSemanal;
	}

	public JButton getBtnGestionarAsistencia()
	{
		return btnGestionarAsistencia;
	}

	public TablaGenerica<CursadaDTO> getTable()
	{
		return table;
	}

	public void setTable(TablaGenerica<CursadaDTO> table) 
	{
		this.table = table; 
		add(this.table, BorderLayout.CENTER);
	}

	public JButton getBtnGestionarEvaluaciones()
	{
		return btnGestionarEvaluaciones;
	}
	
	public JButton getBtnPrograma()
	{
		return btnPrograma;
	}
}
