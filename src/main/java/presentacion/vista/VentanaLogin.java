package presentacion.vista;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import util.EstilosYColores;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Toolkit;

public class VentanaLogin {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField textFieldUsuario;
	
	private JPasswordField textFieldContraseña;
	private JButton btnAcceso ;
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaLogin() {
		frame = new JFrame();
		frame.setTitle("Login");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 370, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.setContentPane(contentPane);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		try 
		{
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaLogin.class.getResource("/Imagenes/icon.png")));
			
			JLabel ImagenPresentacion = new JLabel();
			ImageIcon icon = new ImageIcon(this.getClass().getResource("/Imagenes/iconLogin.png"));
			ImagenPresentacion.setBounds(18,20,327,143);
			ImagenPresentacion.setIcon(icon);
			frame.getContentPane().add(ImagenPresentacion);
			
			JLabel ImagenLosPalomos = new JLabel();
			ImageIcon icon2 = new ImageIcon(this.getClass().getResource("/Imagenes/iconLosPalomos.png"));
			ImagenLosPalomos.setBounds(311,330,icon2.getIconWidth(),icon2.getIconHeight());
			ImagenLosPalomos.setIcon(icon2);
			frame.getContentPane().add(ImagenLosPalomos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		btnAcceso = new JButton("Ingresar");
		btnAcceso.setBounds(18, 330, 186, 30);
		btnAcceso.setBackground(style.getBackgroundButtonStandard());
		btnAcceso.setForeground(style.getForegroundButtonStandard());
		
		textFieldUsuario = new JTextField();
		textFieldUsuario.setBounds(170, 230, 175, 20);
		textFieldUsuario.setColumns(10);
		
		textFieldContraseña = new JPasswordField();
		textFieldContraseña.setBounds(170, 276, 175, 20);
		textFieldContraseña.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(17, 233, 143, 14);
		
		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel_1.setBounds(17, 279, 143, 14);
		contentPane.setLayout(null);
		contentPane.add(btnAcceso);
		contentPane.add(lblNewLabel);
		contentPane.add(lblNewLabel_1);
		contentPane.add(textFieldContraseña);
		contentPane.add(textFieldUsuario);
		
		JLabel lblBienvenidosAFormar = new JLabel("Bienvenidos a FormAR");
		lblBienvenidosAFormar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenidosAFormar.setBounds(18, 191, 327, 14);
		contentPane.add(lblBienvenidosAFormar);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(18, 178, 327, 7);
		contentPane.add(separator);
		
		JLabel lblLosPalomosSrl = new JLabel("Los Palomos S.R.L.");
		lblLosPalomosSrl.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblLosPalomosSrl.setBounds(214, 342, 92, 14);
		contentPane.add(lblLosPalomosSrl);
		
		JLabel lblBy = new JLabel("by");
		lblBy.setFont(new Font("Tahoma", Font.ITALIC, 10));
		lblBy.setBounds(251, 330, 23, 14);
		contentPane.add(lblBy);
		
	}

	public JTextField getTextFieldUsuario() {
		return textFieldUsuario;
	}

	public void setTextFieldUsuario(JTextField textFieldEmail) {
		this.textFieldUsuario = textFieldEmail;
	}

	public JTextField getTextFieldContraseña() {
		return textFieldContraseña;
	}

	public void setTextFieldContraseña(JPasswordField textFieldContraseña) {
		this.textFieldContraseña = textFieldContraseña;
	}

	public JButton getBtnAcceso() {
		return btnAcceso;
	}

	public void setBtnAcceso(JButton btnAcceso) {
		this.btnAcceso = btnAcceso;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	  public void close()
	{
	    frame.dispose();
	}
}
