package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.AlumnoDTO;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaInscripcionAlumno;

public class ControladorSeleccionDeAlumno implements ActionListener, KeyListener {
	private ModeloPersonas modeloPersonas = ModeloPersonas.getInstance();
	private VentanaInscripcionAlumno vista;
	private List<AlumnoDTO> alumnos_en_tabla;
	private List<AlumnoDTO> alumnos_filtrados;
	private AlumnoDTO alumnoSelect;

	public ControladorSeleccionDeAlumno(VentanaInscripcionAlumno vista) {
		this.vista = vista;
		this.vista.getBtnConcretarInscripcion().addActionListener(this);
		this.vista.getTextFieldFiltro().addKeyListener(this);
		this.alumnos_filtrados = new ArrayList<AlumnoDTO>();
	}

	public void initialize() {
		this.reiniciarTabla();
		this.llenarTabla();
		this.vista.show();
	}

	private void reiniciarTabla() {
		this.vista.getModelAlumnos().setRowCount(0); // Para vaciar la tabla
		this.vista.getModelAlumnos().setColumnCount(0);
		this.vista.getModelAlumnos().setColumnIdentifiers(this.vista.getNombreColumnasAlumnos());
	}

	private void llenarTabla() {
		this.alumnos_en_tabla = modeloPersonas.getAlumnos();
		for (int i = 0; i < this.alumnos_en_tabla.size(); i++) {
			if (alumnos_en_tabla.get(i).getDisponibleEnSistema()) {
				alumnos_filtrados.add(alumnos_en_tabla.get(i));
				Object[] fila = { alumnos_en_tabla.get(i).getLegajo(), alumnos_en_tabla.get(i).getPersona().getNombre(),
						alumnos_en_tabla.get(i).getPersona().getApellido() };
				this.vista.getModelAlumnos().addRow(fila);
			}
		}
	}

	public void filtrarTablaTipo(String tipo) {
		reiniciarTabla();
		this.alumnos_filtrados.clear();

		for (int i = 0; i < alumnos_en_tabla.size(); i++) {
			if (alumnos_en_tabla.get(i).getDisponibleEnSistema()) {
				String getNombre = alumnos_en_tabla.get(i).getPersona().getNombre().toUpperCase();
				if (getNombre.indexOf(tipo.toUpperCase()) != -1) {
					alumnos_filtrados.add(alumnos_en_tabla.get(i));
					Object[] fila = { alumnos_en_tabla.get(i).getLegajo(),
							alumnos_en_tabla.get(i).getPersona().getNombre(),
							alumnos_en_tabla.get(i).getPersona().getApellido() };
					this.vista.getModelAlumnos().addRow(fila);
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.vista.getBtnConcretarInscripcion()) {
			if (this.vista.getTablaAlumnos().getSelectedRow()<0) {
				JOptionPane.showMessageDialog(null, "Seleccione un alumno, por favor");
				return;
			}
			this.alumnoSelect = this.alumnos_filtrados.get(this.vista.getTablaAlumnos().getSelectedRow());
			this.vista.close();
		}
	}
	
	public AlumnoDTO getAlumnoSelect() {
		return alumnoSelect;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		String tipo = this.vista.getTextFieldFiltro().getText();
		this.filtrarTablaTipo(tipo);
	}

}
