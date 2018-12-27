package presentacion.controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import dto.CuotaDTO;
import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;
import dto.CursoDTO;
import dto.DiaDeLaSemanaDTO;
import dto.EmpresaDTO;
import dto.EstadoCursadaDTO;
import dto.EstadoDePeriodoDeInscripcionDTO;
import dto.HorarioDTO;
import dto.InscripcionAlumnoDTO;
import dto.InstructorDTO;
import dto.NotificacionDTO;
import dto.PagoEmpresaDTO;
import dto.PeriodoInscripcionDTO;
import dto.PlanillaDeAsistenciasDTO;
import dto.PlanillaDeParcialesDTO;
import modelo.ModeloCursos;
import modelo.ModeloNotificaciones;
import modelo.ModeloPersonas;
import presentacion.vista.VentanaAgregarCursada;
import util.ValidadorCampos;
import util.ValidadorLogico;

public class ControladorAgregarCursada implements ActionListener
{
	private ModeloNotificaciones modelNotificaciones;
	private ModeloCursos model;
	private VentanaAgregarCursada vista;
	private CursoDTO cursoSeleccionado;
	private List<HorarioDTO> horarios;
	
	
	
	private boolean esEmpresa;
	
	private EmpresaDTO empresaRecibida;
	public ControladorAgregarCursada(VentanaAgregarCursada vista, CursoDTO curso)
	{
		modelNotificaciones = ModeloNotificaciones.getInstance();
		this.model=ModeloCursos.getInstance();
		this.vista = vista;
		this.cursoSeleccionado = curso;
		this.horarios = new LinkedList<>();
		this.vista.getBtnAgregar().addActionListener(this);
		this.vista.getBtnAsignarHorarios().addActionListener(e -> asignarHorarios());
		this.vista.getPrecio().setText(curso.getPrecio().toString());
		this.vista.getDateFechaInicio().addPropertyChangeListener(e -> actualizarFechaFin());
		this.vista.getTextFieldHorasTotales().setText(Integer.toString(curso.getHoras()));
	}
	

	public ControladorAgregarCursada(VentanaAgregarCursada ventana, CursoDTO curso, boolean esEmpresa,EmpresaDTO empresa) 
	{	modelNotificaciones = ModeloNotificaciones.getInstance();
		this.model=ModeloCursos.getInstance();
		this.empresaRecibida=empresa;
		this.esEmpresa=true;
		this.vista=ventana;
		this.cursoSeleccionado=curso;
		this.horarios = new LinkedList<>();
		this.vista.getBtnAgregar().addActionListener(this);
		this.vista.getBtnAsignarHorarios().addActionListener(e -> asignarHorarios());
		this.vista.getPrecio().setText(curso.getPrecio().toString());
		this.vista.getDateFechaInicio().addPropertyChangeListener(e -> actualizarFechaFin());
		this.vista.getTextFieldHorasTotales().setText(Integer.toString(curso.getHoras()));
		
	}

	private void asignarHorarios() 
	{
		horarios = ControladorAsignarHorarios.administrarHorarios(horarios);
		actualizarFechaFin();
		this.vista.actualizarHorarios(horarios);
	}
	
	private void actualizarFechaFin()
	{
		if(horarios.isEmpty() || vista.getDateFechaInicio().getDate() == null)
			vista.getDateFechaFin().setDate(null);
		else {
			int minutosRestantes = cursoSeleccionado.getHoras()*60;
			
			Date fechaFin = new Date(vista.getDateFechaInicio().getDate().getTime());
			fechaFin.setDate(fechaFin.getDate() - 1);
			
			List<DiaDeLaSemanaDTO> dias = model.getDias();
			int diaIndex = fechaFin.getDay();
			
			while(minutosRestantes > 0)
			{
				if(diaIndex >= dias.size())
					diaIndex = diaIndex - dias.size();
				
				for(HorarioDTO horario : horarios)
				{
					if(horario.getDiaDeLaSemana().equals(dias.get(diaIndex)))
					{
						minutosRestantes = minutosRestantes - 
								(((horario.getHoraFin().getHours() - horario.getHoraInicio().getHours()) * 60) + 
								horario.getHoraFin().getMinutes() - horario.getHoraInicio().getMinutes());
					}
				}
				fechaFin.setDate(fechaFin.getDate() + 1);
				diaIndex++;
			}
			vista.getDateFechaFin().setDate(fechaFin);
		}
	}
	
	public void initialize()
	{
		this.vista.show();
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.vista.getBtnAgregar())
		{
			Date fechaInicio = this.vista.getDateFechaInicio().getDate();
			Date fechaFin = this.vista.getDateFechaFin().getDate();
			Date periodoFechaInicio = this.vista.getDatePeriodoFechaInicio().getDate();
			Date periodoFechaFin = this.vista.getDatePeriodoFechaFin().getDate();
			String comision = this.vista.getNombre().getText();
			
			
			
			if (fechaInicio==null||fechaFin==null || periodoFechaInicio==null || periodoFechaFin==null) 
			{
				JOptionPane.showMessageDialog(null,"Completar todas las fechas");
				return;
			}	
			
			
			if (!ValidadorLogico.esFechaAnterior(fechaInicio, fechaFin))
			{
				JOptionPane.showMessageDialog(null,"Ingrese una fecha de inicio y fin v\u00E1lida");
				return;
			}
			
			if (!ValidadorLogico.esFechaAnterior(periodoFechaInicio, periodoFechaFin))
			{
				JOptionPane.showMessageDialog(null,"Ingresa un periodo de inicio y fin v\u00E1lidos");
				return;
			}
			
			
			if (!ValidadorLogico.esProximoAHoy(fechaInicio))
			{
				JOptionPane.showMessageDialog(null,"Ingresa un Inicio de cursada pr\u00F3ximo al d\u00EDa actual");
				return;
			}
			
			if (!ValidadorLogico.esFechaAnterior(periodoFechaFin, fechaInicio))
			{
				JOptionPane.showMessageDialog(null,"Ingresa un Inicio de cursada y un fin de inscripci\u00F3n v\u00E1lidos");
				return;
			}
			
			if (!ValidadorLogico.validarPeriodoInscripcion(periodoFechaInicio,periodoFechaFin))
			{
				JOptionPane.showMessageDialog(null,"El periodo de inscripci\u00F3n no puede ser previo al d\u00EDa de hoy");
				return;
			}
			
			if(ValidadorLogico.validarComisionVaciaCursada(comision)) {
				JOptionPane.showMessageDialog(null,"El campo comisi\u00F3n esta vac\u00EDo");
				return;
			}
				
			if (!ValidadorCampos.validarPrecio(this.vista.getPrecio().getText()))
			{
				JOptionPane.showMessageDialog(null,"El precio no es v\u00E1lido");
				return;
			}
			if (!ValidadorCampos.validarNombreCursoCursada(this.vista.getNombre().getText()))
			{
				JOptionPane.showMessageDialog(null,"El nombre no es v\u00E1lido");
				return;
			}
			int min = Integer.parseInt(this.vista.getVacantesMinimas().getValue().toString());
			int max = Integer.parseInt(this.vista.getVacantesLimite().getValue().toString());
			if(max<min) 
			{
				JOptionPane.showMessageDialog(null,"El cupo maximo y minimo no es v\\u00E1lido");
				return;
			}
			if(!ValidadorLogico.validarComisionRepetidaCursada(comision) && JOptionPane.showConfirmDialog(null, "Ya existe una comisi\u00F3n con ese nombre. ¿Desea crearla de todas formas?", "Nombre de comisi\u00F3n repetido", JOptionPane.YES_NO_OPTION)== JOptionPane.NO_OPTION) {

				return;
			}
			
				int vacantesLimite = Integer.parseInt(this.vista.getVacantesLimite().getValue().toString());
				int precio = Integer.parseInt(this.vista.getPrecio().getText());
				
				CursadaDTO newCursada = new CursadaDTO();
				newCursada.setId(0);
				newCursada.setNombre(comision);
				newCursada.setVacantes(vacantesLimite);
				newCursada.setMontoTotal(precio);
				newCursada.setDisponibleEnSistema(true);
				newCursada.setPrograma("");
				newCursada.setInicio(fechaInicio);
				
				newCursada.setFin(fechaFin);
				newCursada.setCurso(cursoSeleccionado);
				int vacantesMinima = Integer.parseInt(this.vista.getVacantesMinimas().getValue().toString());
				newCursada.setVacantesMinimas(vacantesMinima);
				//-------------------------------------------------------------
				//SETEA AUTOMATICAMENTE ESTADOPERIODOINCRIPCION y ESTADOCURSADA
				//-------------------------------------------------------------
				Date fechaActual = new Date();
				EstadoCursadaDTO estadoCursada = model.getEstadosCursada().get(1);
				EstadoDePeriodoDeInscripcionDTO estadoInscripcion = null; 
				String fechaActualString = new SimpleDateFormat("dd-MM-yyyy").format(fechaActual);
				String fechaPeriodoString = new SimpleDateFormat("dd-MM-yyyy").format(periodoFechaInicio);
				
				if(fechaActualString.equals(fechaPeriodoString)) 
				{
					estadoInscripcion = model.getEstadosDePeriodoDeInscripcion().get(0);
				}
				else 
				{
					estadoInscripcion = model.getEstadosDePeriodoDeInscripcion().get(3);
				}
				EstadoDePeriodoDeInscripcionDTO estadoInscrDTO = null;
				EstadoCursadaDTO estadoCursaDTO = null;
				for(EstadoDePeriodoDeInscripcionDTO estadoInscripcionDTO : model.getEstadosDePeriodoDeInscripcion())
				{
					if(estadoInscripcionDTO.getId()==(estadoInscripcion.getId())) {
						estadoInscrDTO = estadoInscripcionDTO;
					}
				}
				for(EstadoCursadaDTO estadocursDTO :  model.getEstadosCursada())
				{
					if(estadocursDTO.getId()==(estadoCursada.getId())) {
						estadoCursaDTO = estadocursDTO;
					}
				}
				//agregar periodo de inscripcion					
				PeriodoInscripcionDTO periodo = new PeriodoInscripcionDTO();
				periodo.setInicio(periodoFechaInicio);
				periodo.setFin(periodoFechaFin);
				periodo.setDisponibleEnSistema(true);
				periodo.setEstado(estadoInscrDTO);
				List<PeriodoInscripcionDTO> inicioPeriodos = new ArrayList<>();
				inicioPeriodos.add(periodo);
				newCursada.setPeriodosDeInscripcion(inicioPeriodos);
				//-----------------------------------------------------------------------------------
				newCursada.setHorarios(horarios);
				newCursada.setInstructores(new ArrayList<InstructorDTO>());
				newCursada.setAsistencias(new ArrayList<PlanillaDeAsistenciasDTO>());
				newCursada.setParciales(new ArrayList<PlanillaDeParcialesDTO>());
				newCursada.setInscripciones(new ArrayList<InscripcionAlumnoDTO>());
				
			
				newCursada.setCuotas(crearCuotas(fechaInicio, fechaFin, precio));
				
					
				
				newCursada.setEstado(estadoCursaDTO);
				model.agregarCursada(newCursada);
				
				newCursada.setAsistencias(ValidadorLogico.getPlanillas(newCursada));
				model.modificarCursada(newCursada);
				
				crearNotificacion(newCursada);
				
				if (this.esEmpresa)
				{
					CursadaEmpresaDTO cursadaEmpresa=new CursadaEmpresaDTO();
					cursadaEmpresa.setCursada(newCursada);
					cursadaEmpresa.setDisponibleEnSistema(true);
					cursadaEmpresa.setPagos(new ArrayList<PagoEmpresaDTO>());
					empresaRecibida.getCursadas().add(cursadaEmpresa);
					
					model.agregarCursadaEmpresa(cursadaEmpresa);
					model.modificarEmpresa(empresaRecibida);
					
				}
				
				
				this.vista.close();
			
		}
	}
	
	@SuppressWarnings("deprecation")
	private void crearNotificacion(CursadaDTO newCursada) 
	{
		NotificacionDTO notificacion=new NotificacionDTO();
		notificacion.setCursada(newCursada);
		notificacion.setDescripcion("La cursada: "+newCursada.getNombre()+" tiene pendiente de aprobaci\u00F3n.");
		notificacion.setDisponibleEnSistema(false);
		notificacion.setTitulo("Cursada pendiente de aprobaci\u00F3n");
		notificacion.setUsuario(ModeloPersonas.getInstance().getUsuarioActual());
		notificacion.setFecha(newCursada.getInicio());
		notificacion.setHora(new Time(00,00,00));
		notificacion.setVisto(false);
		notificacion.setCodigoVista("c");
		notificacion.setCodigoTab("8");

		modelNotificaciones.agregarNotificacion(notificacion);
		
		

		

	}

	@Deprecated
	private List<CuotaDTO> crearCuotas(Date fechaInicio, Date fechaFin, int precio)
	{
		List<CuotaDTO> cuotas = new LinkedList<>();
		if(precio==0)
			return cuotas;
	
		int year = fechaFin.getYear();
		int month = fechaFin.getMonth();
		int day = fechaFin.getDate();
		
		int cantidadDeMeses = ModeloCursos.cantidadDeMeses(fechaInicio, fechaFin);
		
		if(cantidadDeMeses==0)
		{
			CuotaDTO cuota = new CuotaDTO();
			cuota.setFechaLimite(new Date(year, month, day));
			cuota.setMonto(precio);
			cuota.setDisponibleEnSistema(true);
			cuotas.add(cuota);
		}
		
		for(int i = 0; i < cantidadDeMeses; i++)
		{
			CuotaDTO cuota = new CuotaDTO();
			month--;
			if(month == -1)
			{
				month = 11;
				year--;
			}
			cuota.setFechaLimite(new Date(year, month, day));
			cuota.setMonto(precio / cantidadDeMeses);
			cuota.setDisponibleEnSistema(true);
			cuotas.add(cuota);
		}
		
		//Agrego la diferencia del total a la primer cuota
		int diferenciaPrimerCuota = precio;
		for(CuotaDTO cuota : cuotas)
			diferenciaPrimerCuota-= cuota.getMonto();
		cuotas.get(0).setMonto(cuotas.get(0).getMonto() + diferenciaPrimerCuota);
		
		return cuotas;
	}
}
