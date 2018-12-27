package presentacion.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;

import util.EstilosYColores;

public class PanelEditarSala extends JPanel
{
	private static final long serialVersionUID = -1501463214719541094L;
	private JTextField txtAlias;
	private JTextField txtCodigo;
	private JSpinner spinnerCapacidad;
	private EstilosYColores style = EstilosYColores.getInstance();
	
	private JButton btnOk;
	
	public PanelEditarSala()
	{
		setLayout(null);
		setPreferredSize(new Dimension(306, 182));
		
		JLabel lblAlias = new JLabel("Alias");
		lblAlias.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAlias.setBounds(19, 15, 63, 14);
		add(lblAlias);
		
		JLabel lblCdigo = new JLabel("C\u00F3digo");
		lblCdigo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCdigo.setBounds(21, 54, 63, 14);
		add(lblCdigo);
		
		JLabel lblCapacidad = new JLabel("Capacidad");
		lblCapacidad.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCapacidad.setBounds(19, 95, 65, 14);
		add(lblCapacidad);
		
		txtAlias = new JTextField();
		txtAlias.setHorizontalAlignment(SwingConstants.CENTER);
		txtAlias.setText("Alias");
		txtAlias.setBounds(94, 12, 194, 20);
		add(txtAlias);
		txtAlias.setColumns(10);
		
		txtCodigo = new JTextField();
		txtCodigo.setHorizontalAlignment(SwingConstants.CENTER);
		txtCodigo.setText("C\u00F3digo");
		txtCodigo.setBounds(94, 52, 194, 20);
		add(txtCodigo);
		txtCodigo.setColumns(10);
		
		spinnerCapacidad = new JSpinner();
		spinnerCapacidad.setModel(new SpinnerNumberModel(1, 1, null, 1));
		spinnerCapacidad.setBounds(94, 92, 194, 20);
		add(spinnerCapacidad);
		
	
		
		btnOk = new JButton("Ok");
		btnOk.setBounds(19, 139, 269, 23);
		btnOk.setBackground(style.getBackgroundButtonStandard());
		btnOk.setForeground(style.getForegroundButtonStandard());
		add(btnOk);
	}

	public JTextField getTxtAlias()
	{
		return txtAlias;
	}

	public JTextField getTxtCodigo()
	{
		return txtCodigo;
	}

	public JSpinner getSpinnerCapacidad()
	{
		return spinnerCapacidad;
	}

	

	public JButton getBtnOk()
	{
		return btnOk;
	}
}
