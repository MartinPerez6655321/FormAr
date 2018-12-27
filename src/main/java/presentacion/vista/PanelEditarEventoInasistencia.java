package presentacion.vista;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.text.DateFormatter;

import com.toedter.calendar.JDateChooser;

public class PanelEditarEventoInasistencia extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textFieldAusente;
	private JButton btnAceptar;
	private JSpinner spinnerHora;
	private JTextField textFieldResponsable;
	private JButton btnAsignarResponsable;
	private JTextField textFieldCurso;

	public PanelEditarEventoInasistencia() {
		
		setLayout(null);
		setPreferredSize(new Dimension(462, 368));
		
	
		
		JLabel lblAusente = new JLabel("Ausente:");
		lblAusente.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAusente.setBounds(20, 39, 81, 31);
		add(lblAusente);
		
		textFieldAusente = new JTextField();
		textFieldAusente.setBounds(142, 46, 288, 20);
		add(textFieldAusente);
		textFieldAusente.setColumns(10);
		textFieldAusente.setEditable(false);
		
		
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(183, 328, 199, 23);
		add(btnAceptar);
		JLabel lblResponsable = new JLabel("Responsable\r\n:");
		lblResponsable.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblResponsable.setBounds(20, 193, 114, 31);
		add(lblResponsable);
		
		textFieldResponsable = new JTextField();
		textFieldResponsable.setEditable(false);
		textFieldResponsable.setColumns(10);
		textFieldResponsable.setBounds(141, 200, 146, 20);
		add(textFieldResponsable);
		
		btnAsignarResponsable = new JButton("Asignar Responsable");
		btnAsignarResponsable.setBounds(297, 200, 133, 20);
		add(btnAsignarResponsable);
		
		JLabel lblCurso = new JLabel("<html>Curso: </html>");
		lblCurso.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCurso.setBounds(20, 107, 114, 42);
		add(lblCurso);
		
		
		
		JLabel lblHoraDeCumplimiento = new JLabel("<html>Hora de cumplimiento:</html>");
		lblHoraDeCumplimiento.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblHoraDeCumplimiento.setBounds(20, 261, 113, 42);
		add(lblHoraDeCumplimiento);
		Date date = new Date(); 
		
		
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		
		spinnerHora = new javax.swing.JSpinner(sm);
		spinnerHora.setFont(new Font("Tahoma", Font.PLAIN, 15));
		JSpinner.DateEditor de_spinnerHora = new JSpinner.DateEditor(spinnerHora, "HH:mm");
		DateFormatter formatter = (DateFormatter)de_spinnerHora.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); 
		formatter.setOverwriteMode(true);
		
		
		spinnerHora.setEditor(de_spinnerHora);
		spinnerHora.setBounds(141, 281, 290, 20);
		
		add(spinnerHora);
		
		textFieldCurso = new JTextField();
		textFieldCurso.setBounds(142, 120, 288, 20);
		add(textFieldCurso);
		textFieldCurso.setColumns(10);
		textFieldCurso.setEditable(false);
		
		
	}

	public JTextField getTextFieldAusente() {
		return textFieldAusente;
	}

	public void setTextFieldAusente(JTextField textFieldAusente) {
		this.textFieldAusente = textFieldAusente;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}


	public JSpinner getSpinnerHora() {
		return spinnerHora;
	}

	public void setSpinnerHora(JSpinner spinnerHora) {
		this.spinnerHora = spinnerHora;
	}

	public JTextField getTextFieldResponsable() {
		return textFieldResponsable;
	}

	public void setTextFieldResponsable(JTextField textFieldResponsable) {
		this.textFieldResponsable = textFieldResponsable;
	}

	public JButton getBtnAsignarResponsable() {
		return btnAsignarResponsable;
	}

	public void setBtnAsignarResponsable(JButton btnAsignarResponsable) {
		this.btnAsignarResponsable = btnAsignarResponsable;
	}

	public JTextField getTextFieldCurso() {
		return textFieldCurso;
	}

	public void setTextFieldCurso(JTextField textFieldCurso) {
		this.textFieldCurso = textFieldCurso;
	}
}
