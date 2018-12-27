package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import dto.CursadaDTO;
import presentacion.components.tabla.TablaGenerica;
import javax.swing.JButton;

public class PanelGestionCursada extends JPanel 
{
	private static final long serialVersionUID = 2579077397936124760L;
	private TablaGenerica<CursadaDTO> table;
	private JButton btnAsignacionDeSala;
	private JButton btnDesasignarSala;
	private JButton btnAsignacionDeInstructores;
	private JButton btnCerrarCursada;
	private JButton btnAgregarHorario;
	private JButton btnEliminar;

	public PanelGestionCursada()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);

		btnAsignacionDeSala = new JButton("Asignacion de sala");
		buttonPane.add(btnAsignacionDeSala);
		
		btnDesasignarSala = new JButton("Desasignar sala");
		buttonPane.add(btnDesasignarSala);
		
		btnAsignacionDeInstructores = new JButton("Asignaci\u00F3n de instructores");
		buttonPane.add(btnAsignacionDeInstructores);
		
		
		btnCerrarCursada = new JButton("Cancelar Cursada");
		buttonPane.add(btnCerrarCursada);
		
		btnAgregarHorario = new JButton("Agregar Horario");
		btnAgregarHorario.setVisible(false);
		buttonPane.add(btnAgregarHorario);
		
		btnEliminar = new JButton("Eliminar");
		buttonPane.add(btnEliminar);
		
	}

	public TablaGenerica<CursadaDTO> getTable()
	{
		return table;
	}
	
	public void setTable(TablaGenerica<CursadaDTO> table)
	{
		this.table = table; 
		add(table, BorderLayout.CENTER);
	}

	public JButton getBtnAsignacionDeSala()
	{
		return btnAsignacionDeSala;
	}

	public JButton getbtnDesasignarSala()
	{
		return btnDesasignarSala;
	}

	public JButton getBtnAsignacionDeInstructores()
	{
		return btnAsignacionDeInstructores;
	}

	public JButton getBtnCerrarCursada()
	{
		return btnCerrarCursada;
	}

	public void setBtnCerrarCursada(JButton btnCerrarCursada)
	{
		this.btnCerrarCursada = btnCerrarCursada;
	}

	public JButton getBtnAgregarHorario() {
		return btnAgregarHorario;
	}

	public void setBtnAgregarHorario(JButton btnAgregarHorario) {
		this.btnAgregarHorario = btnAgregarHorario;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
}
