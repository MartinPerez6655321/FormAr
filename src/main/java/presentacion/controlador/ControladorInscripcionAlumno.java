package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import dto.AlumnoDTO;
import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;
import dto.EmpresaDTO;
import dto.InscripcionAlumnoDTO;
import dto.InstructorDTO;
import modelo.ModeloCursos;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaInscripcionAlumno;
import util.ValidadorLogico;

public class ControladorInscripcionAlumno extends KeyAdapter implements ActionListener {
	private ModeloCursos model = ModeloCursos.getInstance();
	private ModeloPersonas modeloPersonas = ModeloPersonas.getInstance();
	private VentanaInscripcionAlumno vista;
	private CursadaDTO cursadaSeleccionada;
	private List<AlumnoDTO> alumnos_en_tabla;
	private List<AlumnoDTO> alumnos_filtrados;

	private boolean esEmpresa;
	private EmpresaDTO empresaRecibida;
	private boolean esSeleccionar;
	private List<AlumnoDTO> alumnosRecibidos;
	private boolean esQuitar;

	public ControladorInscripcionAlumno(VentanaInscripcionAlumno vista, CursadaDTO cursada) {
		this.vista = vista;
		this.cursadaSeleccionada = cursada;
		this.vista.getBtnConcretarInscripcion().addActionListener(this);
		this.vista.getTextFieldFiltro().addKeyListener(this);
		this.alumnos_filtrados = new ArrayList<AlumnoDTO>();

	}

	public ControladorInscripcionAlumno(VentanaInscripcionAlumno ventanaInscAlumno, CursadaDTO cursada,
			EmpresaDTO empresaRecibida, boolean b) {

		this.empresaRecibida = empresaRecibida;
		esEmpresa = b;
		this.vista = ventanaInscAlumno;
		this.cursadaSeleccionada = cursada;
		this.vista.getBtnConcretarInscripcion().addActionListener(this);
		this.vista.getTextFieldFiltro().addKeyListener(this);
		this.alumnos_filtrados = new ArrayList<AlumnoDTO>();

	}

	public ControladorInscripcionAlumno(VentanaInscripcionAlumno ventanaInscAlumno, List<AlumnoDTO> alumnos,
			boolean esSeleccionar, EmpresaDTO empresaRecibida) {
		this.empresaRecibida = empresaRecibida;
		if (!esSeleccionar)
			this.esQuitar = true;

		this.alumnosRecibidos = alumnos;
		this.esSeleccionar = true;

		this.vista = ventanaInscAlumno;
		this.vista.getBtnConcretarInscripcion().addActionListener(this);
		this.vista.getTextFieldFiltro().addKeyListener(this);
		this.alumnos_filtrados = new ArrayList<AlumnoDTO>();
		this.vista.getBtnConcretarInscripcion().setText("Seleccionar");

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
		if (esQuitar) {

			this.alumnos_en_tabla = this.alumnosRecibidos;

			for (int i = 0; i < this.alumnos_en_tabla.size(); i++) {
				if (alumnos_en_tabla.get(i).getDisponibleEnSistema()) {
					alumnos_filtrados.add(alumnos_en_tabla.get(i));
					Object[] fila = { alumnos_en_tabla.get(i).getLegajo(),
							alumnos_en_tabla.get(i).getPersona().getNombre(),
							alumnos_en_tabla.get(i).getPersona().getApellido() };
					this.vista.getModelAlumnos().addRow(fila);
				}
			}
			return;
		}
		if (esSeleccionar) {

			this.alumnos_en_tabla = modeloPersonas.getAlumnos();
			List<AlumnoDTO> alumnosDeEmpresas = new ArrayList<>();
			List<AlumnoDTO> alumnosSinEmpresas = new ArrayList<>();
			for (EmpresaDTO empresa : model.getEmpresas()) {
				for (AlumnoDTO alumno : empresa.getAlumnos()) {
					if (!alumnosDeEmpresas.contains(alumno))
						alumnosDeEmpresas.add(alumno);
				}

			}
			for (AlumnoDTO alumnoDTO : alumnos_en_tabla) {
				if (!alumnosDeEmpresas.contains(alumnoDTO) && !this.alumnosRecibidos.contains(alumnoDTO))
					alumnosSinEmpresas.add(alumnoDTO);

			}

			this.alumnos_en_tabla = alumnosSinEmpresas;

			for (int i = 0; i < this.alumnos_en_tabla.size(); i++) {
				if (alumnos_en_tabla.get(i).getDisponibleEnSistema()) {
					alumnos_filtrados.add(alumnos_en_tabla.get(i));
					Object[] fila = { alumnos_en_tabla.get(i).getLegajo(),
							alumnos_en_tabla.get(i).getPersona().getNombre(),
							alumnos_en_tabla.get(i).getPersona().getApellido() };
					this.vista.getModelAlumnos().addRow(fila);
				}
			}
			return;
		}
		if (!esEmpresa) {

			this.alumnos_en_tabla = modeloPersonas.getAlumnos();

			for (int i = 0; i < this.alumnos_en_tabla.size(); i++) {
				if (alumnos_en_tabla.get(i).getDisponibleEnSistema()) {
					alumnos_filtrados.add(alumnos_en_tabla.get(i));
					Object[] fila = { alumnos_en_tabla.get(i).getLegajo(),
							alumnos_en_tabla.get(i).getPersona().getNombre(),
							alumnos_en_tabla.get(i).getPersona().getApellido() };
					this.vista.getModelAlumnos().addRow(fila);
				}
			}
			return;
		}
		if (esEmpresa) {
			this.alumnos_en_tabla = empresaRecibida.getAlumnos();

			List<AlumnoDTO> alumnos = model.getAlumnosporInscripciones(cursadaSeleccionada.getInscripciones());
			for (int i = 0; i < this.alumnos_en_tabla.size(); i++) {
				if (alumnos_en_tabla.get(i).getDisponibleEnSistema() && !alumnos.contains(alumnos_en_tabla.get(i))) {
					alumnos_filtrados.add(alumnos_en_tabla.get(i));
					Object[] fila = { alumnos_en_tabla.get(i).getLegajo(),
							alumnos_en_tabla.get(i).getPersona().getNombre(),
							alumnos_en_tabla.get(i).getPersona().getApellido() };
					this.vista.getModelAlumnos().addRow(fila);
				}
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


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == this.vista.getBtnConcretarInscripcion()) 
		{
			
			if (esQuitar)
			{
				
				if (empresaRecibida!=null)
				{
					
					boolean a=false;
					
					if(empresaRecibida.getCursadas()==null || empresaRecibida.getCursadas().isEmpty() ) {
						AlumnoDTO alumno = this.alumnos_filtrados.get(this.vista.getTablaAlumnos().getSelectedRow());				
						alumnosRecibidos.remove(alumno);	
						a=true;
						this.vista.close();
						
						return;
					}
					for(CursadaEmpresaDTO cursada:empresaRecibida.getCursadas())
					{
						
						
						if (!(model.getAlumnosporInscripciones(cursada.getCursada().getInscripciones())).contains(this.alumnos_filtrados.get(this.vista.getTablaAlumnos().getSelectedRow())))
						{
							
							AlumnoDTO alumno = this.alumnos_filtrados.get(this.vista.getTablaAlumnos().getSelectedRow());				
							alumnosRecibidos.remove(alumno);	
							a=true;
							this.vista.close();
							

							return;
						}
					}
					if (!a) {
						JOptionPane.showMessageDialog(null, "Ese empleado tiene cursadas activas");
						return;
					}
				} else {
					AlumnoDTO alumno = this.alumnos_filtrados.get(this.vista.getTablaAlumnos().getSelectedRow());
					alumnosRecibidos.remove(alumno);
					this.vista.close();
					return;
				}

			}
			if (!esSeleccionar) {
				if (this.cursadaSeleccionada.getVacantes() < 1) {
					JOptionPane.showMessageDialog(null, "Ya no hay vacantes disponibles.");
					this.vista.close();
					return;
				}

				if (this.vista.getTablaAlumnos().getSelectedRow() < 0) {
					JOptionPane.showMessageDialog(null, "Seleccione un alumno, por favor");
					return;
				}

				AlumnoDTO alumno = this.alumnos_filtrados.get(this.vista.getTablaAlumnos().getSelectedRow());

				if (this.cursadaSeleccionada.getInstructores().size() != 0) {
					for (InstructorDTO inst : this.cursadaSeleccionada.getInstructores()) {
						if (inst.getPersona().equals(alumno.getPersona())) {
							JOptionPane.showMessageDialog(null, "Es un instructor de la cursada, no puede ser alumno");
							return;
						}
					}
				}

				InscripcionAlumnoDTO newInscripcion = new InscripcionAlumnoDTO();

				newInscripcion.setAlumno(alumno);
				newInscripcion.setFecha(new Date());
				newInscripcion.setDisponibleEnSistema(true);
				if (this.cursadaSeleccionada.getInstructores().contains(
						modeloPersonas.getInstructor(modeloPersonas.getUsuarioPersona(alumno.getPersona())))) {
					JOptionPane.showMessageDialog(null, alumno.getPersona().getNombre() + " "
							+ alumno.getPersona().getApellido() + " es instructor de la cursada");
					return;
				}
				if (ValidadorLogico.validarInscripcionAlumno(alumno, this.cursadaSeleccionada)) {
					this.cursadaSeleccionada.getInscripciones().add(newInscripcion);
					this.cursadaSeleccionada.setVacantes(this.cursadaSeleccionada.getVacantes() - 1);
					model.modificarCursada(this.cursadaSeleccionada);
					this.vista.close();
				} else {
					
					JOptionPane.showMessageDialog(null, "Ese alumno ya tiene una cursada al mismo horario");
					this.vista.close();
				}
			} else {
				AlumnoDTO alumno = this.alumnos_filtrados.get(this.vista.getTablaAlumnos().getSelectedRow());
				alumnosRecibidos.add(alumno);

				this.vista.close();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		String tipo = this.vista.getTextFieldFiltro().getText();
		this.filtrarTablaTipo(tipo);
	}
}
