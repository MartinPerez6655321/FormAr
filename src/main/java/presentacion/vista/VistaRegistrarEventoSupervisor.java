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
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VistaRegistrarEventoSupervisor extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton btnAsignarResponsable;
	private JButton btnAceptar;
	private JTextArea textAreaDescripcion;
	private JDateChooser dateFechaLimite;
	private JLabel lblFechaDeCumplimiento;
	private JTextField textFieldResponsable;
	private JSpinner spinnerHora;
	private JLabel lblCaracteresRestantes;
	private EstilosYColores style = EstilosYColores.getInstance();
	
	public VistaRegistrarEventoSupervisor() {
		setLayout(null);
		setPreferredSize(new Dimension(454, 424));
		
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
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAceptar.setBounds(21, 360, 411, 31);
		btnAceptar.setBackground(style.getBackgroundButtonStandard());
		btnAceptar.setForeground(style.getForegroundButtonStandard());
		add(btnAceptar);
		
		dateFechaLimite = new JDateChooser();
		dateFechaLimite.setBounds(142, 237, 290, 20);
		add(dateFechaLimite);
		
		lblFechaDeCumplimiento = new JLabel("<html>Fecha de cumplimiento<html>");
		lblFechaDeCumplimiento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFechaDeCumplimiento.setBounds(21, 221, 114, 54);
		add(lblFechaDeCumplimiento);
		
		JLabel lblResponsable = new JLabel("Responsable\r\n");
		lblResponsable.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblResponsable.setBounds(21, 157, 114, 31);
		add(lblResponsable);
		
		textFieldResponsable = new JTextField();
		textFieldResponsable.setEditable(false);
		textFieldResponsable.setColumns(10);
		textFieldResponsable.setBounds(142, 164, 126, 20);
		add(textFieldResponsable);
		
		btnAsignarResponsable = new JButton("Asignar Responsable");
		btnAsignarResponsable.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnAsignarResponsable.setBounds(278, 164, 154, 20);
		add(btnAsignarResponsable);
		
		JLabel lblHoraDeCumplimiento = new JLabel("<html>Hora de cumplimiento</html>");
		lblHoraDeCumplimiento.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHoraDeCumplimiento.setBounds(22, 297, 113, 42);
		add(lblHoraDeCumplimiento);
		Date date = new Date(); 
		
		
		SpinnerDateModel sm = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
		
		spinnerHora = new javax.swing.JSpinner(sm);
		spinnerHora.setFont(new Font("Tahoma", Font.PLAIN, 11));
		JSpinner.DateEditor de_spinnerHora = new JSpinner.DateEditor(spinnerHora, "HH:mm");
		spinnerHora.setEditor(de_spinnerHora);
		DateFormatter formatter = (DateFormatter)de_spinnerHora.getTextField().getFormatter();
		formatter.setAllowsInvalid(false); 
		formatter.setOverwriteMode(true);
		spinnerHora.setBounds(142, 310, 290, 20);
		add(spinnerHora);
		
		lblCaracteresRestantes = new JLabel("");
		lblCaracteresRestantes.setBounds(277, 136, 155, 14);
		add(lblCaracteresRestantes);
		
	}
	
	
	public JButton getBtnAsignarResponsable() {
		return btnAsignarResponsable;
	}
	
	public JButton getBtnAceptar() {
		return btnAceptar;
	}
	public JTextArea getTextAreaDescripcion() {
		return textAreaDescripcion;
	}

	public JTextField getTextFieldResponsable() {
		return textFieldResponsable;
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


	public void setSpinnerHora(JSpinner spinnerHora) {
		this.spinnerHora = spinnerHora;
	}


	public JLabel getLblCaracteresRestantes() {
		return lblCaracteresRestantes;
	}


	public void setLblCaracteresRestantes(JLabel lblCaracteresRestantes) {
		this.lblCaracteresRestantes = lblCaracteresRestantes;
	}
}
