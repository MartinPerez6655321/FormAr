package presentacion.vista;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;

import com.toedter.calendar.JDateChooser;

import util.EstilosYColores;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class VistaRegistrarEventoNuevo extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldInteresado;
	private JButton btnAsignarInteresado;
	private JButton btnAceptar;
	private JTextArea textAreaDescripcion;
	private JDateChooser dateFechaLimite;
	private JLabel lblFechaDeCumplimiento;
	private JSpinner spinnerHora;
	private JComboBox<String> comboBoxCurso;
	private JLabel lblCaracteresRestantes;
	private EstilosYColores style = EstilosYColores.getInstance();
	
	public VistaRegistrarEventoNuevo() {
		setLayout(null);
		setPreferredSize(new Dimension(457, 507));
		
		JLabel lblDescripcion = new JLabel("Descripci\u00F3n");
		lblDescripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDescripcion.setBounds(21, 20, 93, 31);
		add(lblDescripcion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(142, 24, 290, 101);
		add(scrollPane);
		
		textAreaDescripcion = new JTextArea();
		scrollPane.setViewportView(textAreaDescripcion);
		textAreaDescripcion.setLineWrap(true);
		
		JLabel lblInteresado = new JLabel("Interesado");
		lblInteresado.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblInteresado.setBounds(21, 161, 81, 31);
		add(lblInteresado);
		
		textFieldInteresado = new JTextField();
		textFieldInteresado.setBounds(144, 166, 126, 20);
		add(textFieldInteresado);
		textFieldInteresado.setColumns(10);
		textFieldInteresado.setEditable(false);
		
		btnAsignarInteresado = new JButton("Asignar Interesado");
		btnAsignarInteresado.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAsignarInteresado.setBounds(280, 166, 152, 20);
		add(btnAsignarInteresado);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(21, 442, 411, 31);
		btnAceptar.setBackground(style.getBackgroundButtonStandard());
		btnAceptar.setForeground(style.getForegroundButtonStandard());
		add(btnAceptar);
		
		dateFechaLimite = new JDateChooser();
		dateFechaLimite.setBounds(142, 241, 290, 20);
		add(dateFechaLimite);
		
		lblFechaDeCumplimiento = new JLabel("<html>Fecha de cumplimiento</html>");
		lblFechaDeCumplimiento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaDeCumplimiento.setBounds(21, 219, 114, 54);
		add(lblFechaDeCumplimiento);
		
		JLabel lblCursoDeInteres = new JLabel("<html>Curso de inter\u00E9s</html>");
		lblCursoDeInteres.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCursoDeInteres.setBounds(21, 374, 114, 42);
		add(lblCursoDeInteres);
		
		comboBoxCurso = new JComboBox();
		comboBoxCurso.setBounds(142, 385, 290, 20);
		add(comboBoxCurso);
		
		JLabel lblHoraDeCumplimiento = new JLabel("<html>Hora de cumplimiento</html>");
		lblHoraDeCumplimiento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHoraDeCumplimiento.setBounds(21, 294, 113, 42);
		add(lblHoraDeCumplimiento);
		Date date = new Date(); 
		
		
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		
		spinnerHora = new javax.swing.JSpinner(sm);
		spinnerHora.setFont(new Font("Tahoma", Font.PLAIN, 11));
		JSpinner.DateEditor de_spinnerHora = new JSpinner.DateEditor(spinnerHora, "HH:mm");
		DateFormatter formatter = (DateFormatter)de_spinnerHora.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); 
		formatter.setOverwriteMode(true);
		
		lblCaracteresRestantes = new JLabel("");
		lblCaracteresRestantes.setBounds(277, 136, 155, 14);
		add(lblCaracteresRestantes);
		
		spinnerHora.setEditor(de_spinnerHora);
		spinnerHora.setBounds(142, 310, 290, 20);
		
		add(spinnerHora);
		
	}
	
	

	public JTextField getTextFieldInteresado() {
		return textFieldInteresado;
	}
	public JButton getBtnAsignarInteresado() {
		return btnAsignarInteresado;
	}
	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	public JTextArea getTextAreaDescripcion() {
		return textAreaDescripcion;
	}
	public void setTextFieldInteresado(JTextField textFieldInteresado) {
		this.textFieldInteresado = textFieldInteresado;
	}
	public void setBtnAsignarInteresado(JButton btnAsignarInteresado) {
		this.btnAsignarInteresado = btnAsignarInteresado;
	}
	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}
	public void setTextAreaDescripcion(JTextArea textAreaDescripcion) {
		this.textAreaDescripcion = textAreaDescripcion;
	}
	public JDateChooser getDateFechaLimite() {
		return dateFechaLimite;
	}
	public JLabel getLblFechaDeRealizacin() {
		return lblFechaDeCumplimiento;
	}
	public void setDateFechaLimite(JDateChooser dateFechaLimite) {
		this.dateFechaLimite = dateFechaLimite;
	}
	public void setLblFechaDeRealizacin(JLabel lblFechaDeRealizacin) {
		this.lblFechaDeCumplimiento = lblFechaDeRealizacin;
	}
	public JSpinner getSpinnerHora() {
		return spinnerHora;
	}
	public JComboBox<String>  getComboBoxCurso() {
		return comboBoxCurso;
	}
	public void setSpinnerHora(JSpinner spinnerHora) {
		this.spinnerHora = spinnerHora;
	}
	public void setComboBoxCurso(JComboBox<String> comboBoxCurso) {
		this.comboBoxCurso = comboBoxCurso;
	}



	public JLabel getLblCaracteresRestantes() {
		return lblCaracteresRestantes;
	}



	public void setLblCaracteresRestantes(JLabel lblCaracteresRestantes) {
		this.lblCaracteresRestantes = lblCaracteresRestantes;
	}
}
