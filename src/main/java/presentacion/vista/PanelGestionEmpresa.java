package presentacion.vista;

import javax.swing.JPanel;

import dto.EmpresaDTO;
import presentacion.components.tabla.TablaGenerica;
import util.EstilosYColores;

import java.awt.BorderLayout;
import javax.swing.JButton;

public class PanelGestionEmpresa extends JPanel
{
	private static final long serialVersionUID = -7863190660839358912L;
	private TablaGenerica<EmpresaDTO> table;
	private JButton btnAgregarEmpresa;
	private JButton btnAgregarCurso;
	private JButton btnGestionarCursadasEmpresa;
	
	private EstilosYColores style = EstilosYColores.getInstance();

	public PanelGestionEmpresa()
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);

		btnAgregarEmpresa = new JButton("Agregar Empresa");
		btnAgregarEmpresa.setBackground(style.getBackgroundButtonCreate());
		btnAgregarEmpresa.setForeground(style.getForegroundButtonCreate());
		buttonPane.add(btnAgregarEmpresa);
		
		
		btnAgregarCurso=new JButton("Agregar cursada");
		buttonPane.add(btnAgregarCurso);
		
		btnGestionarCursadasEmpresa=new JButton("Gestionar cursadas");
		buttonPane.add(btnGestionarCursadasEmpresa);
		
	}

	public TablaGenerica<EmpresaDTO> getTable() {
		return table;
	}
	
	public JButton getBtnAgregarCurso() {
		return btnAgregarCurso;
	}

	public void setTable(TablaGenerica<EmpresaDTO> table)
	{
		this.table = table; 
		add(this.table, BorderLayout.CENTER);
	}
	
	public JButton getBtnAgregarEmpresa() {
		return btnAgregarEmpresa;
	}

	public JButton getBtnGestionarCursadasEmpresa() {
		return btnGestionarCursadasEmpresa;
	}
	
}
