package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.CursadaDTO;
import dto.InstructorDTO;
import presentacion.components.listagenerica.ListaGenerica;
import presentacion.vista.subcomponentes.InstructorLabel;

public class VistaAsignacionDeInstructoresACursada extends JPanel
{
	private static final long serialVersionUID = 700700293597197598L;
	private JButton aceptarButton;
	private ListaGenerica<InstructorLabel> listaGenerica;

	public VistaAsignacionDeInstructoresACursada()
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(250, 300));
		
		JPanel buttonPane = new JPanel();
		
		aceptarButton = new JButton("Aceptar");
		buttonPane.add(aceptarButton);

		
		
		add(buttonPane, BorderLayout.SOUTH);
	}
	
	public void setListaGenerica(ListaGenerica<InstructorLabel> lista) 
	{
		this.listaGenerica = lista;
		add(listaGenerica, BorderLayout.CENTER);
	}

	public void setCursada(CursadaDTO cursada)
	{
		listaGenerica.clear();
		for(InstructorDTO instructor : cursada.getInstructores())
			listaGenerica.addElement(new InstructorLabel(instructor));
	}

	public JButton getAceptarButton()
	{
		return aceptarButton;
	}
}
