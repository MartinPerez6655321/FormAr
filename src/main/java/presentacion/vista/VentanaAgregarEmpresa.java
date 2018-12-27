package presentacion.vista;

import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.util.List;

import javax.swing.JButton;
import javax.swing.SwingConstants;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import util.EstilosYColores;
import java.awt.Font;

public class VentanaAgregarEmpresa {

	private JDialog frame;
	private JTextField textFieldNombre;
	private JButton btnAgregar;
	
	private JButton btnAsignarAlumnos; 
	private JButton btnQuitarAlumnos;
	
	private JLabel lblAluumnosString;
	
	private JButton btnGuardarCambios;

	
	private EstilosYColores style = EstilosYColores.getInstance();

	public VentanaAgregarEmpresa() {
		initialize();
	}
	
	public JButton getGuardarCambios() {
		return btnGuardarCambios;
	}

	public void setGuardarCambios(JButton guardarCambios) {
		this.btnGuardarCambios = guardarCambios;
	}

	private void initialize() {
		
		frame = new JDialog();
		frame.setSize(273, 344);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setModal(true);
		frame.setResizable(false);
		frame.setTitle("Agregar Empresa");
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setBounds(23, 281, 221, 23);
		btnAgregar.setBackground(style.getBackgroundButtonStandard());
		btnAgregar.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnAgregar);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setBounds(23, 26, 75, 14);
		frame.getContentPane().add(lblNewLabel);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldNombre.setBounds(108, 23, 136, 20);
		frame.getContentPane().add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		btnAsignarAlumnos = new JButton("Asignar Alumno");

		btnAsignarAlumnos.setBounds(23, 162, 221, 23);
		frame.getContentPane().add(btnAsignarAlumnos);
		
		
		btnGuardarCambios=new JButton("Guardar cambios");
		btnGuardarCambios.setBounds(23, 258, 221, 23);
		btnGuardarCambios.setBackground(style.getBackgroundButtonStandard());
		btnGuardarCambios.setForeground(style.getForegroundButtonStandard());
		
		frame.getContentPane().add(btnGuardarCambios);
	
		btnQuitarAlumnos=new JButton("Quitar Alumno");
		
		btnQuitarAlumnos.setBounds(23,185,221,23);
		frame.getContentPane().add(btnQuitarAlumnos);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(23, 89, 221, 62);
		frame.getContentPane().add(scrollPane2);
		
		lblAluumnosString = new JLabel();
		scrollPane2.setViewportView(lblAluumnosString);
		
		JLabel lblAlumnos = new JLabel("Empleados");
		lblAlumnos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAlumnos.setBounds(23, 64, 75, 14);
		frame.getContentPane().add(lblAlumnos);
		

		
	}
	
	public JButton getBtnQuitarAlumnos() {
		return btnQuitarAlumnos;
	}

	public JTextField getTextFieldNombre() {
		return textFieldNombre;
	}
	
	public JButton getBtnAgregarEmpresa() {
		return btnAgregar;
	}

	public JButton getBtnAsignarAlumnos() {
		return btnAsignarAlumnos;
	}
	
	
	public void actualizarAlumnos(List<AlumnoDTO> alumnos)
	{
		String text = "<html>";
		
		if(alumnos.isEmpty())
			text = text + "Sin alumnos asignados";
		else
			text = text + util.Strings.alumnosListString(alumnos);

		text = text + "</html>";
		lblAluumnosString.setText(text);
	}
	public void actualizarCursadas(List<CursadaDTO> cursadas)
	{
		String text = "<html>";
		
		if(cursadas.isEmpty())
			text = text + "Sin alumnos asignados";
		else
			text = text + util.Strings.cursadasListString(cursadas);

		text = text + "</html>";
		lblAluumnosString.setText(text);
	}

	public void show() {
		frame.setVisible(true);
	}
	
    public void close()
	{
    	frame.dispose();
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	

	

	
}