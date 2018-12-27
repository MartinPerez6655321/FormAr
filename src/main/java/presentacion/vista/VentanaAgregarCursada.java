package presentacion.vista;

import com.toedter.calendar.JDateChooser;

import dto.HorarioDTO;
import util.EstilosYColores;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JSpinner;

import java.awt.Font;
import java.util.List;

import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class VentanaAgregarCursada {

	private JDialog frame;
	private JDateChooser dateFechaFin;
	private JDateChooser dateFechaInicio;
	private JDateChooser datePeriodoFechaInicio; 
	private JDateChooser datePeriodoFechaFin; 
	private JTextField nombre;
	private JTextField precio;
	private JSpinner vacantesLimite;
	private JSpinner vacantesMinimas;
	private JButton btnAgregarCursada;
	private JLabel lblHorariosString;
	private JButton btnAsignarHorarios; 
	private JTextField textFieldHorasTotales;
	private EstilosYColores style = EstilosYColores.getInstance();
	
	public VentanaAgregarCursada() {
		initialize();
	}
	
	private void initialize() {
		frame = new JDialog();
		frame.setModal(true);
		frame.setTitle("Agregar Cursada");
		frame.setBounds(100, 100, 367, 611);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		JLabel lblFechaInicio = new JLabel("Fecha Inicio");
		lblFechaInicio.setBounds(31, 148, 87, 20);
		frame.getContentPane().add(lblFechaInicio);
		
		JLabel lblFechaFin = new JLabel("Fecha Fin");
		lblFechaFin.setBounds(31, 341, 87, 20);
		frame.getContentPane().add(lblFechaFin);

		JLabel lblVacantesMinimas = new JLabel("Cupo m\u00EDnimo");
		lblVacantesMinimas.setBounds(31, 110, 112, 14);
		frame.getContentPane().add(lblVacantesMinimas);
		
		JLabel lblPeriodoDeInscripcion = new JLabel("Periodo de Inscripci\u00F3n");
		lblPeriodoDeInscripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPeriodoDeInscripcion.setBounds(31, 429, 212, 14);
		frame.getContentPane().add(lblPeriodoDeInscripcion);
		
		JLabel label = new JLabel("Fecha Inicio");
		label.setBounds(31, 462, 87, 20);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("Fecha Fin");
		label_1.setBounds(31, 496, 87, 20);
		frame.getContentPane().add(label_1);
		
		JLabel lblComision = new JLabel("Comisi\u00F3n");
		lblComision.setBounds(31, 27, 87, 14);
		frame.getContentPane().add(lblComision);
		
		JLabel lblVacantesLimite = new JLabel("Cupo m\u00E1ximo");
		lblVacantesLimite.setBounds(31, 65, 112, 14);
		frame.getContentPane().add(lblVacantesLimite);

		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(31, 387, 87, 20);
		frame.getContentPane().add(lblPrecio);

		nombre = new JTextField();
		nombre.setHorizontalAlignment(SwingConstants.CENTER);
		nombre.setBounds(155, 24, 176, 20);
		frame.getContentPane().add(nombre);
		nombre.setColumns(10);
		
		vacantesLimite = new JSpinner();
		vacantesLimite.setModel(new SpinnerNumberModel(1, 1, null, 1));
		vacantesLimite.setBounds(156, 61, 175, 23);
		frame.getContentPane().add(vacantesLimite);
		
		vacantesMinimas = new JSpinner();
		vacantesMinimas.setModel(new SpinnerNumberModel(1, 1, null, 1));
		vacantesMinimas.setBounds(156, 106, 175, 22);
		frame.getContentPane().add(vacantesMinimas);

		dateFechaInicio = new JDateChooser();
		dateFechaInicio.setBounds(156, 148, 175, 20);
		frame.getContentPane().add(dateFechaInicio);
		
		dateFechaFin = new JDateChooser();
		dateFechaFin.setBounds(156, 341, 175, 20);
		dateFechaFin.setEnabled(false);
		frame.getContentPane().add(dateFechaFin);
		
		precio = new JTextField();
		precio.setHorizontalAlignment(SwingConstants.CENTER);
		precio.setBounds(156, 387, 175, 20);
		frame.getContentPane().add(precio);
		precio.setColumns(10);
		
		datePeriodoFechaInicio = new JDateChooser();
		datePeriodoFechaInicio.setBounds(156, 462, 175, 20);
		frame.getContentPane().add(datePeriodoFechaInicio);
		
		datePeriodoFechaFin = new JDateChooser();
		datePeriodoFechaFin.setBounds(156, 496, 175, 20);
		frame.getContentPane().add(datePeriodoFechaFin);

		btnAgregarCursada = new JButton("Agregar");
		btnAgregarCursada.setBounds(31, 541, 300, 23);
		btnAgregarCursada.setBackground(style.getBackgroundButtonStandard());
		btnAgregarCursada.setForeground(style.getForegroundButtonStandard());
		frame.getContentPane().add(btnAgregarCursada);
		
		JLabel lblHorarios = new JLabel("Horarios");
		lblHorarios.setBounds(31, 194, 112, 14);
		frame.getContentPane().add(lblHorarios);
		
		btnAsignarHorarios = new JButton("Asignar horarios");
		btnAsignarHorarios.setBounds(31, 300, 300, 23);
		frame.getContentPane().add(btnAsignarHorarios);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 219, 300, 70);
		frame.getContentPane().add(scrollPane);
		
		lblHorariosString = new JLabel();
		scrollPane.setViewportView(lblHorariosString);
		
		JLabel lblHorasTotalesDel = new JLabel("Horas totales del curso: ");
		lblHorasTotalesDel.setBounds(155, 194, 148, 14);
		frame.getContentPane().add(lblHorasTotalesDel);
		
		textFieldHorasTotales = new JTextField();
		textFieldHorasTotales.setBounds(299, 194, 31, 14);
		textFieldHorasTotales.setEditable(false);
		textFieldHorasTotales.setHorizontalAlignment(JTextField.CENTER);
		frame.getContentPane().add(textFieldHorasTotales);
		textFieldHorasTotales.setColumns(10);
		
	}
	
	public void actualizarHorarios(List<HorarioDTO> horarios)
	{
		String text = "<html>";
		
		if(horarios.isEmpty())
			text = text + "Sin horarios asignados";
		else
			text = text + util.Strings.horariosListString(horarios);
		
		text = text + "</html>";
		lblHorariosString.setText(text);
	}

	public JButton getBtnAsignarHorarios()
	{
		return btnAsignarHorarios;
	}

	public JTextField getNombre() {
		return nombre;
	}

	public JSpinner getVacantesLimite() {
		return vacantesLimite;
	}
	
	public JTextField getPrecio() {
		return precio;
	}

	public JDateChooser getDateFechaFin() {
		return dateFechaFin;
	}

	public JDateChooser getDateFechaInicio() {
		return dateFechaInicio;
	}
	
	public JDateChooser getDatePeriodoFechaInicio() {
		return datePeriodoFechaInicio;
	}

	public JDateChooser getDatePeriodoFechaFin() {
		return datePeriodoFechaFin;
	}

	public JSpinner getVacantesMinimas() {
		return vacantesMinimas;
	}
	
	public JButton getBtnAgregar() {
		return btnAgregarCursada;
	}
	
	public void show() {
		frame.setVisible(true);
	}
	
    public void close()
	{
    	frame.dispose();
	}

	public JTextField getTextFieldHorasTotales() {
		return textFieldHorasTotales;
	}

	public void setTextFieldHorasTotales(JTextField textFieldHorasTotales) {
		this.textFieldHorasTotales = textFieldHorasTotales;
	}
}
