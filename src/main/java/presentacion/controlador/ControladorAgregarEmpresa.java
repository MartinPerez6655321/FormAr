package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.AlumnoDTO;
import dto.CursadaEmpresaDTO;
import dto.EmpresaDTO;
import modelo.ModeloCursos;
import presentacion.vista.VentanaAgregarEmpresa;
import presentacion.vista.VentanaInscripcionAlumno;

public class ControladorAgregarEmpresa implements ActionListener
{
	private ModeloCursos model = ModeloCursos.getInstance();
	private VentanaAgregarEmpresa vista;
	private List<AlumnoDTO> alumnos;
	private ControladorInscripcionAlumno controladorAlumno;
	private EmpresaDTO empresaRecibida;
	
	public ControladorAgregarEmpresa(VentanaAgregarEmpresa vista)
	{
		this.vista = vista;
		this.alumnos = new ArrayList<>();
		this.vista.actualizarAlumnos(alumnos);
		this.vista.getBtnAgregarEmpresa().addActionListener(this);
		this.vista.getBtnAsignarAlumnos().addActionListener(e -> asignarAlumnos());
		this.vista.getBtnQuitarAlumnos().addActionListener(e -> QuitarAlumnos());
		this.vista.getGuardarCambios().setVisible(false);
		
		
	}
	
	private void QuitarAlumnos() 
	{
		
		VentanaInscripcionAlumno ventanaInscAlumno = new VentanaInscripcionAlumno();
		if (controladorAlumno==null)
		{
			ControladorInscripcionAlumno controller = new ControladorInscripcionAlumno(ventanaInscAlumno,alumnos, false,empresaRecibida);
			controller.initialize();
		}
		this.vista.actualizarAlumnos(this.alumnos);
	}

	public ControladorAgregarEmpresa(VentanaAgregarEmpresa ventanaAgregarEmpresa, EmpresaDTO empresaRecibida) 
	{
		
		this.vista = ventanaAgregarEmpresa;
		this.alumnos = empresaRecibida.getAlumnos();
		this.vista.actualizarAlumnos(alumnos);
		this.empresaRecibida=empresaRecibida;
		this.vista.getBtnAgregarEmpresa().setVisible(false);
		this.vista.getGuardarCambios().setVisible(true);
		this.vista.getGuardarCambios().addActionListener(this);
		this.vista.getBtnAsignarAlumnos().addActionListener(e -> asignarAlumnos());
		this.vista.getTextFieldNombre().setText(empresaRecibida.getNombre());
		this.vista.getBtnQuitarAlumnos().addActionListener(e->QuitarAlumnos());
		this.vista.getBtnQuitarAlumnos().setVisible(true);
		
		
	}

	public void initialize()
	{
		this.vista.show();

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
	
		if(e.getSource()== this.vista.getBtnAgregarEmpresa()) 
		{
			
			if (this.vista.getTextFieldNombre().getText().isEmpty())
				{
					JOptionPane.showMessageDialog(null, "Completar el nombre");
					return;
				}
			
			EmpresaDTO newEmpresa = new EmpresaDTO();
			newEmpresa.setNombre(this.vista.getTextFieldNombre().getText());
			newEmpresa.setDisponibleEnSistema(true);
			newEmpresa.setAlumnos(alumnos);
			
			newEmpresa.setCursadas(new ArrayList<CursadaEmpresaDTO>());
			this.model.agregarEmpresa(newEmpresa);
			this.vista.close();
		}
		if (e.getSource()==this.vista.getGuardarCambios())
		{
			if (this.vista.getTextFieldNombre().getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Completar el nombre");
				return;
			}
			
			EmpresaDTO newEmpresa = this.empresaRecibida;
			newEmpresa.setNombre(this.vista.getTextFieldNombre().getText());
			newEmpresa.setDisponibleEnSistema(true);
			newEmpresa.setAlumnos(alumnos);
			
			
			this.model.modificarEmpresa(newEmpresa);
			this.vista.close();
			
		}
		
		
	}

	private void asignarAlumnos() 
	{
		VentanaInscripcionAlumno ventanaInscAlumno = new VentanaInscripcionAlumno();
		if (controladorAlumno==null)
		{
			ControladorInscripcionAlumno controller = new ControladorInscripcionAlumno(ventanaInscAlumno,alumnos, true,empresaRecibida);
			controller.initialize();
		}
		this.vista.actualizarAlumnos(this.alumnos);

		
		
	}
	
}
