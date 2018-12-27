package presentacion.components.abm;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import javafx.beans.Observable;
import presentacion.components.tabla.FilterPanel;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;

public class ABMGenerico<T> extends JPanel
{
	private static final long serialVersionUID = 6957498062174376710L;
	private TablaGenerica<T> tabla;
	private JPanel botonera;
	private JButton btnCrear;
	private JButton btnModificar;
	private JButton btnEliminar;

	public ABMGenerico(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer, FilterPanel<T> filterPanel, Observable... observables)
	{
		initialize(columnNames, transformer, obtainer, filterPanel);
		for(Observable obs : observables)
			obs.addListener(tabla);
	}

	public ABMGenerico(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer, Observable... observables)
	{
		initialize(columnNames, transformer, obtainer, null);
		for(Observable obs : observables)
			obs.addListener(tabla);
	}
	
	private void initialize(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer, FilterPanel<T> filterPanel)
	{
		setLayout(new BorderLayout());
		
		if(filterPanel == null)
			filterPanel = FilterPanel.stringFilterPanel(columnNames, transformer);
		
		tabla =	new TablaGenerica<>(
				columnNames, 
				transformer,  
				obtainer,
				filterPanel);
		add(tabla, BorderLayout.CENTER);
		
		btnCrear = new JButton("Crear");
		btnModificar = new JButton("Modificar");
		btnEliminar = new JButton("Eliminar");

		btnModificar.setEnabled(false);
		btnEliminar.setEnabled(false);
		
		botonera = new JPanel();
		
		add(botonera, BorderLayout.SOUTH);
		
		tabla.addSelectionListener(this::actualizarBotones);
	}

	public void setEditor(Editor<T> editor)
	{
		btnCrear.addActionListener(e -> editor.crear());
		btnModificar.addActionListener(e -> editor.modificar(tabla.getSelectedElem()));
		btnEliminar.addActionListener(e -> editor.eliminar(tabla.getSelectedElem()));
		tabla.addDoubleClickListener(() -> { if(tabla.getSelectedElem() != null) editor.modificar(tabla.getSelectedElem()); });
		
		botonera.add(btnCrear);
		botonera.add(btnModificar);
		botonera.add(btnEliminar);
	}
	
	private void actualizarBotones(T elem)
	{
		btnModificar.setEnabled(elem != null);
		btnEliminar.setEnabled(elem != null);
	}
	
	public TablaGenerica<T> getTabla()
	{
		return tabla;
	}

	public JPanel getBotonera()
	{
		return botonera;
	}

	public JButton getBtnCrear()
	{
		return btnCrear;
	}

	public JButton getBtnModificar()
	{
		return btnModificar;
	}

	public JButton getBtnEliminar()
	{
		return btnEliminar;
	}
}
