package presentacion.vista;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import dto.InteresadoDTO;
import presentacion.components.tabla.TablaGenerica;

public class PanelInteresados extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnGenerarReporteInteracciones;
	private JButton btnRegistrarAlumno;
	private TablaGenerica<InteresadoDTO> table;
	private JButton btnGenerarReporteGeneral;


	public PanelInteresados() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnGenerarReporteInteracciones = new JButton("Generar reporte de interacciones");
		buttonPane.add(btnGenerarReporteInteracciones);
		

		btnRegistrarAlumno=new JButton("Registrar alumno");
		buttonPane.add(btnRegistrarAlumno);

		btnGenerarReporteGeneral = new JButton("Generar reporte general de interacciones");
		buttonPane.add(btnGenerarReporteGeneral);

		
		
		
	}

	public JButton getBtnGenerarReporteInteracciones() {
		return btnGenerarReporteInteracciones;
	}
	public void setBtnGenerarReporteInteracciones(JButton btnGenerarReporteInteracciones) {
		this.btnGenerarReporteInteracciones = btnGenerarReporteInteracciones;
	}

	public TablaGenerica<InteresadoDTO> getTable() {
		return table;
		
	}

	public void setTable(TablaGenerica<InteresadoDTO> table) {
		this.table = table;
		add(table, BorderLayout.CENTER);
	}


	public JButton getBtnRegistrarAlumno() 
{
		return btnRegistrarAlumno;
	}

	public void setBtnRegistrarAlumno(JButton btnRegistrarAlumno) 
	{
		this.btnRegistrarAlumno = btnRegistrarAlumno;
	}
	public JButton getBtnGenerarReporteGeneral() 
	{
		return btnGenerarReporteGeneral;
	}

	public void setBtnGenerarReporteGeneral(JButton btnGenerarReporteGeneral) {
		this.btnGenerarReporteGeneral = btnGenerarReporteGeneral;

	}

	
}
