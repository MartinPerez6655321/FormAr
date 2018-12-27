package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dto.CursadaDTO;
import presentacion.components.tabla.TablaGenerica;

public class PanelEstadoCursada extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5773953605158688855L;
	private TablaGenerica<CursadaDTO> table;

	
	private JPanel botonera;
	private JButton btnIniciarCursada;
	private JButton btnCancelarCursada;
	

	public PanelEstadoCursada() {
		initialize();
	}
	
	private void initialize()
	{	
		setLayout(new BorderLayout(0, 0));

		
		btnIniciarCursada = new JButton("Iniciar cursada");
		
		botonera = new JPanel();
		botonera.add(btnIniciarCursada);
		
		btnCancelarCursada = new JButton("Cancelar Cursada");
		
		
		
		botonera.add(btnCancelarCursada);
		add(botonera, BorderLayout.SOUTH);
		
		
		
		
		
	}

	public TablaGenerica<CursadaDTO> getTable() {
		return table;
		
	}

	public void setTable(TablaGenerica<CursadaDTO> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}


	public JButton getBtnExtenderPeriodoInscripcion() {
		return btnIniciarCursada;
	}


	public JButton getBtnCancelarCursada() {
		return btnCancelarCursada;
	}


	public void setBtnExtenderPeriodoInscripcion(JButton btnExtenderPeriodoInscripcion) {
		this.btnIniciarCursada = btnExtenderPeriodoInscripcion;
	}


	public void setBtnCancelarCursada(JButton btnCancelarCursada) {
		this.btnCancelarCursada = btnCancelarCursada;
	}



	
}
