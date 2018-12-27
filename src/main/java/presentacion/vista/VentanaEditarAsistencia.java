package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import dto.EstadoAsistenciaDTO;

public class VentanaEditarAsistencia extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3347184533416468971L;

	private JButton btnGuardar;

	private JComboBox comboEstados;

	public VentanaEditarAsistencia() {

		setLayout(new BorderLayout(0, 0));

		setPreferredSize(new Dimension(300, 75));

		JPanel panel = new JPanel();
		add(panel, BorderLayout.CENTER);

		panel.setLayout(null);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(37, 36, 230, 23);
		panel.add(btnGuardar);

		comboEstados = new JComboBox();
		comboEstados.setBounds(86, 11, 118, 20);
		panel.add(comboEstados);

	}

	public JButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(JButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public JComboBox getComboEstados() {
		return comboEstados;
	}

	public void setComboEstados(JComboBox comboEstados) {
		this.comboEstados = comboEstados;
	}

	

	public void llenarCombo(List<EstadoAsistenciaDTO> estadoAsistencia) 
	{
		for (EstadoAsistenciaDTO estadoAsistenciaDTO : estadoAsistencia) 
		{
			if (!estadoAsistenciaDTO.getNombre().equals("-"))
				this.comboEstados.addItem(estadoAsistenciaDTO.getNombre());
			
		}
		
	}

}
