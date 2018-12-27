package presentacion.vista;


import java.awt.Dimension;


import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
public class PanelEliminacionDeshabilitacion extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnDeshabilitar;
	private JButton btnEliminar;
	
	public PanelEliminacionDeshabilitacion()
	{
		setPreferredSize(new Dimension(504, 106));
		setLayout(null);
		
		JLabel lblElijaComoEliminar = new JLabel("Elija como eliminar el curso:");
		lblElijaComoEliminar.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblElijaComoEliminar.setBounds(29, 11, 256, 26);
		add(lblElijaComoEliminar);
		
		btnDeshabilitar = new JButton("Deshabilitar pero conservar en sistema");
	
		btnDeshabilitar.setBounds(10, 62, 279, 23);
		add(btnDeshabilitar);
		
		btnEliminar = new JButton("Eliminar para siempre\r\n");
		
		btnEliminar.setBounds(299, 62, 195, 23);
		add(btnEliminar);
		
		
	}

	public JButton getBtnDeshabilitar() {
		return btnDeshabilitar;
	}

	public JButton getBtnEliminar() {
		return btnEliminar;
	}

	public void setBtnDeshabilitar(JButton btnDeshabilitar) {
		this.btnDeshabilitar = btnDeshabilitar;
	}

	public void setBtnEliminar(JButton btnEliminar) {
		this.btnEliminar = btnEliminar;
	}
}
