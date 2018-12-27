package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPanel;

import dto.PersonalAdministrativoDTO;
import presentacion.components.tabla.TablaGenerica;

public class PanelVistaAsignarEventos extends JPanel {
	private static final long serialVersionUID = 700700293597197598L;
	private JButton aceptarButton;
	private TablaGenerica<PersonalAdministrativoDTO> table;

	public PanelVistaAsignarEventos()
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(605, 470));
		
		JPanel buttonPane = new JPanel();
		
		aceptarButton = new JButton("Aceptar");
		buttonPane.add(aceptarButton);
		
		add(buttonPane, BorderLayout.SOUTH);
	}
	
	


	public JButton getAceptarButton()
	{
		return aceptarButton;
	}




	public TablaGenerica<PersonalAdministrativoDTO> getTable() {
		return table;
	}




	public void setTable(TablaGenerica<PersonalAdministrativoDTO> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}
}
