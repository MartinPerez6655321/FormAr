package presentacion.vista;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dto.CursadaDTO;
import presentacion.controlador.ControladorAsignadorHorarioCursada;

public class VistaAgregarHorario extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnAsignarHorario;
	private JTextField InicioHora;
	private JTextField FinHora;
	private JTextField InicioMinuto;
	private JTextField FinMinuto;
	private JComboBox<String> comboBox;

	public VistaAgregarHorario(CursadaDTO selectedItem) {
		setLayout(null);
		setPreferredSize(new Dimension(330, 267));

		btnAsignarHorario = new JButton("Asignar Horario");
		btnAsignarHorario.setBounds(30, 214, 271, 23);
		add(btnAsignarHorario);

		JLabel lblDiaDeLa = new JLabel("D\u00EDa de la semana");
		lblDiaDeLa.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblDiaDeLa.setBounds(30, 41, 144, 14);
		add(lblDiaDeLa);

		comboBox = new JComboBox<>();
		comboBox.setBounds(163, 40, 138, 20);
		add(comboBox);

		JLabel lblHoraInicio = new JLabel("Hora Inicio");
		lblHoraInicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHoraInicio.setBounds(30, 100, 125, 23);
		add(lblHoraInicio);

		JLabel lblHoraFin = new JLabel("Hora Fin");
		lblHoraFin.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHoraFin.setBounds(30, 157, 105, 23);
		add(lblHoraFin);

		InicioHora = new JTextField();
		InicioHora.setBounds(163, 103, 57, 20);
		add(InicioHora);
		InicioHora.setColumns(10);
		

		FinHora = new JTextField();
		FinHora.setColumns(10);
		FinHora.setBounds(163, 160, 57, 20);
		add(FinHora);
		

		InicioMinuto = new JTextField();
		InicioMinuto.setColumns(10);
		InicioMinuto.setBounds(244, 103, 57, 20);
		add(InicioMinuto);
		

		FinMinuto = new JTextField();
		FinMinuto.setColumns(10);
		FinMinuto.setBounds(244, 160, 57, 20);
		add(FinMinuto);
		FinMinuto.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {

				char caracter = e.getKeyChar();

				if (((caracter < '0') || (caracter > '9')) && (caracter != '\b') && caracter != '-')
					e.consume();

				if (FinMinuto.getText().length() == 2)
					e.consume();

			}
		});
		

		JLabel label = new JLabel(":");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(209, 106, 46, 14);
		add(label);

		JLabel label_1 = new JLabel(":");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(209, 163, 46, 14);
		add(label_1);

		new ControladorAsignadorHorarioCursada(this, selectedItem);
	}

	public JButton getBtnAsignarHorario() {
		return btnAsignarHorario;
	}

	public JTextField getInicioHora() {
		return InicioHora;
	}

	public JTextField getFinHora() {
		return FinHora;
	}

	public JTextField getInicioMinuto() {
		return InicioMinuto;
	}

	public JTextField getFinMinuto() {
		return FinMinuto;
	}

	public JComboBox<String> getComboBox() {
		return comboBox;
	}

	public void setBtnAsignarHorario(JButton btnAsignarHorario) {
		this.btnAsignarHorario = btnAsignarHorario;
	}

	public void setInicioHora(JTextField inicioHora) {
		InicioHora = inicioHora;
	}

	public void setFinHora(JTextField finHora) {
		FinHora = finHora;
	}

	public void setInicioMinuto(JTextField inicioMinuto) {
		InicioMinuto = inicioMinuto;
	}

	public void setFinMinuto(JTextField finMinuto) {
		FinMinuto = finMinuto;
	}
}
