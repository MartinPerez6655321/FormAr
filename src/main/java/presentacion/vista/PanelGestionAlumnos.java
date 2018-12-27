package presentacion.vista;

import javax.swing.JPanel;

import dto.AlumnoDTO;
import presentacion.components.tabla.TablaGenerica;
import util.EstilosYColores;

import java.awt.BorderLayout;
import javax.swing.JButton;

public class PanelGestionAlumnos extends JPanel
{
	private static final long serialVersionUID = -7863190660839358912L;
	private JButton btnAgregarAlumno;
	private JButton btnGestionarPagos;
	private JButton btnConsultarInscripciones;

	private TablaGenerica<AlumnoDTO> table;
	private JButton btnGenerarReporte;
	private JButton btnReporteAnual;
	private JButton btnImprimirReporte;
	private EstilosYColores style = EstilosYColores.getInstance();

	public PanelGestionAlumnos()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);

		btnAgregarAlumno = new JButton("Agregar Alumno");
		btnAgregarAlumno.setBackground(style.getBackgroundButtonCreate());
		btnAgregarAlumno.setForeground(style.getForegroundButtonCreate());
		buttonPane.add(btnAgregarAlumno);
		
		btnGestionarPagos = new JButton("Gestionar pagos");
		buttonPane.add(btnGestionarPagos);
		
		btnConsultarInscripciones = new JButton("Consultar inscripciones");
		buttonPane.add(btnConsultarInscripciones);
		
		btnGenerarReporte = new JButton("Generar reporte acad\u00E9mico");
		buttonPane.add(btnGenerarReporte);
		
		btnReporteAnual = new JButton("Generar reporte anual");
		buttonPane.add(btnReporteAnual);
		
		btnImprimirReporte = new JButton("Imprimir reporte");
		buttonPane.add(btnImprimirReporte);
	}

	public TablaGenerica<AlumnoDTO> getTable() {
		return table;
	}
	
	public void setTable(TablaGenerica<AlumnoDTO> table)
	{
		this.table = table; 
		add(this.table, BorderLayout.CENTER);
	}
	
	public JButton getBtnAgregarAlumno() {
		return btnAgregarAlumno;
	}
	
	public JButton getBtnGestionarPagos()
	{
		return btnGestionarPagos;
	}
	
	public JButton getBtnConsultarInscripciones()
	{
		return btnConsultarInscripciones;
	}

	public JButton getBtnGenerarReporte() {
		return btnGenerarReporte;
	}

	public void setBtnGenerarReporte(JButton btnGenerarReporte) {
		this.btnGenerarReporte = btnGenerarReporte;
	}

	public JButton getBtnReporteAnual() {
		return btnReporteAnual;
	}

	public void setBtnReporteAnual(JButton btnReporteAnual) {
		this.btnReporteAnual = btnReporteAnual;
	}

	public JButton getBtnImprimirReporte() {
		return btnImprimirReporte;
	}

	public void setBtnImprimirReporte(JButton btnImprimirReporte) {
		this.btnImprimirReporte = btnImprimirReporte;
	}
}
