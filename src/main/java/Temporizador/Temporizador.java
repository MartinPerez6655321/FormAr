package Temporizador;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dto.AlumnoDTO;
import dto.CuotaDTO;
import dto.CursadaDTO;
import dto.CursadaEmpresaDTO;
import dto.EmpresaDTO;
import dto.NotificacionDTO;
import dto.PagoDTO;
import dto.PagoEmpresaDTO;
import dto.PeriodoInscripcionDTO;
import modelo.ModeloCursos;
import modelo.ModeloNotificaciones;
import modelo.ModeloRecados;
import presentacion.controlador.ControladorPanelNotificacion;
import presentacion.vista.VentanaPrincipalTabs;
import util.ValidadorLogico;

public class Temporizador extends Thread
{

	boolean cronometroActivo;
	
	private ModeloCursos modelCurso = ModeloCursos.getInstance();
	
	
	private ModeloNotificaciones modelNotificacion=ModeloNotificaciones.getInstance();
	private ControladorPanelNotificacion controladorPanelNotificacion;
	
	
	
	private VentanaPrincipalTabs vista;
	private ModeloRecados modelRecado = ModeloRecados.getInstance();
	
	public Temporizador(ControladorPanelNotificacion controladorPanelNotificacion,VentanaPrincipalTabs vista)

	{
		
		super();
		this.controladorPanelNotificacion=controladorPanelNotificacion;
		this.vista = vista;
		cronometroActivo = true;
		start();
	}

	public void run() {
		try {
			while (cronometroActivo) 
			{
				actualizar();
				Thread.sleep(5000);
				
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	private void actualizar() 
	{
		cambioDeEstadoCursada();
		verificarNotificaciones();
		actualizarVistaPrincipalTabs();
		
	}
	public void actualizarVistaPrincipalTabs()
	{
		int cantEntradas = this.modelRecado.cantidadDeRecadosNoVistos();
		this.vista.getMnRecado().setText("Recados ("+cantEntradas+")");
		this.vista.getBtnBandejaDeEntrada().setText("Recibidos ("+cantEntradas+")");
		if(cantEntradas!=0)
		{
			this.vista.getMnRecado().setForeground(Color.RED);
			this.vista.getBtnBandejaDeEntrada().setBackground(Color.orange);
			this.vista.getBtnBandejaDeEntrada().setForeground(Color.darkGray);
		}
		else {
			this.vista.getMnRecado().setForeground(null);
			this.vista.getBtnBandejaDeEntrada().setBackground(null);
			this.vista.getBtnBandejaDeEntrada().setForeground(null);
		}
	}
	public void verificarNotificaciones() 
	{
		Date fechaHoy = new java.util.Date();

		for (NotificacionDTO notificacionDTO : modelNotificacion.getNotificacionesPorUsuarioActual()) {

			if (notificacionDTO.getDisponibleEnSistema() != true) {

				if (ValidadorLogico.fechaValidaNotificacion(fechaHoy, notificacionDTO)) 
				{
					notificacionDTO.setDisponibleEnSistema(true);

					modelNotificacion.modificarNotificacion(notificacionDTO);
					this.controladorPanelNotificacion.llenarNotificaciones(getNotificacionesValidas(modelNotificacion.getNotificacionesPorUsuarioActual()));
					
					
				}

			}

		}

	}

	private List<NotificacionDTO> getNotificacionesValidas(List<NotificacionDTO> notificacionesPorUsuario)
	{
		List<NotificacionDTO> lista=new ArrayList<>();
		for (NotificacionDTO notificacionDTO : notificacionesPorUsuario) 
		{
			if (ValidadorLogico.fechaValidaNotificacion( new java.util.Date(),notificacionDTO))
				lista.add(notificacionDTO);
		}
		return lista;
	}
	

	private void cambioDeEstadoCursada() {
		Date hoy = new java.util.Date();

		for (CursadaDTO cursada : modelCurso.getCursadas()) {

			if (cursada.getEstado().getId() == 2 && cursada.getPeriodosDeInscripcion().get(0).getFin().before(hoy)) 
			{

				if (esCursadaEmpresa(cursada)) {
					
					
					pagarCuotasEmpleadoEmpresa(cursada);

				}

				PeriodoInscripcionDTO periodo = cursada.getPeriodosDeInscripcion().get(0);

				periodo.setEstado(modelCurso.getEstadosDePeriodoDeInscripcion().get(1));

				modelCurso.modificarPeriodoDeInscripcion(periodo);

				cursada.setEstado(modelCurso.getEstadosCursada().get(4));
				modelCurso.modificarCursada(cursada);
			}
			if (cursada.getEstado().getNombre().equals("En curso") && cursada.getFin().before(hoy)) {
				cursada.setEstado(modelCurso.getEstadosCursada().get(3));
				liberarRecursos(cursada);
				modelCurso.modificarCursada(cursada);
			}

		}

		for (CursadaDTO cursada : modelCurso.getCursadas()) {
			for (PeriodoInscripcionDTO periodo : cursada.getPeriodosDeInscripcion()) {

				if (periodo.getEstado().getId().equals(4) && periodo.getInicio().before(hoy)) {

					PeriodoInscripcionDTO periodo1 = cursada.getPeriodosDeInscripcion().get(0);

					periodo1.setEstado(modelCurso.getEstadosDePeriodoDeInscripcion().get(0));

					modelCurso.modificarPeriodoDeInscripcion(periodo1);
					modelCurso.modificarCursada(cursada);
				}
			}
		}
	}

	private void liberarRecursos(CursadaDTO cursada) {
		if (cursada.getSala() != null) {
			cursada.setSala(null);
		}
		if (cursada.getInstructores() != null) {
			cursada.getInstructores().clear();
		}

	}

	private void pagarCuotasEmpleadoEmpresa(CursadaDTO cursada) {

		for (AlumnoDTO alumno : modelCurso.getAlumnosporInscripciones(cursada.getInscripciones())) {
			for (CuotaDTO cuota : cursada.getCuotas()) {
				registarPago(alumno, cursada, cuota);
			}
		}

		for (EmpresaDTO empresa : modelCurso.getEmpresas()) 
		{

			
			for (CursadaEmpresaDTO cursadaEmpresa : getCursadaEmpresa(cursada)) 
			{
				
				if (empresa.getCursadas().contains(cursadaEmpresa)) 
				{

					List<PagoEmpresaDTO> pagosEmpresa = this.crearPagos(empresa,cursadaEmpresa, cursada.getInicio(), cursada.getFin(),
							calcularPrecioTotal(cursada, empresa));
					
					
					pagosEmpresa.addAll(cursadaEmpresa.getPagos());
					cursadaEmpresa.setPagos(pagosEmpresa);
					
					
					
					modelCurso.modificarCursadaEmpresa(cursadaEmpresa);

					modelCurso.modificarEmpresa(empresa);

				}

			}
		}

	}

	private int calcularPrecioTotal(CursadaDTO cursada, EmpresaDTO empresa) {

		List<AlumnoDTO> empleadosEmpresa = empresa.getAlumnos();

		int ret = 0;

		List<AlumnoDTO> alumnosEnComun = new ArrayList<>();
		for (AlumnoDTO alumnoDTO : empleadosEmpresa) 
		{
			for (AlumnoDTO empleado : modelCurso.getAlumnosporInscripciones(cursada.getInscripciones())) 
			{
				if (!alumnosEnComun.contains(empleado) && alumnoDTO.equals(empleado))
					alumnosEnComun.add(empleado);

			}

		}
		ret = alumnosEnComun.size() * cursada.getMontoTotal();

		return ret;
	}

	private void registarPago(AlumnoDTO alumno, CursadaDTO cursada, CuotaDTO cuotaSeleccionada) {
		PagoDTO pago = new PagoDTO();

		pago.setAlumno(alumno);
		pago.setCuota(cuotaSeleccionada);
		pago.setDisponibleEnSistema(true);
		pago.setFecha(new java.util.Date());

		modelCurso.agregarPago(pago);
	}

	private boolean esCursadaEmpresa(CursadaDTO cursada) {

		for (CursadaEmpresaDTO cursadaEmpresa : modelCurso.getCursadasEmpresas()) {

			if (cursadaEmpresa.getCursada().equals(cursada))
				return true;
		}

		return false;
	}



	private List<CursadaEmpresaDTO> getCursadaEmpresa(CursadaDTO cursada) {
		List<CursadaEmpresaDTO> ret = new ArrayList<>();
		for (CursadaEmpresaDTO cursadaEmpresa : modelCurso.getCursadasEmpresas()) {

			if (cursadaEmpresa.getCursada().equals(cursada))
				ret.add(cursadaEmpresa);
		}

		return ret;
	}

	@Deprecated
	private List<PagoEmpresaDTO> crearPagos(EmpresaDTO empresa, CursadaEmpresaDTO cursadaEmpresa, Date fechaInicio, Date fechaFin, int precio) {
		List<PagoEmpresaDTO> pagos = new LinkedList<>();
		if (precio==0)
			return pagos;
		
		int year = fechaFin.getYear();
		int month = fechaFin.getMonth();
		int day = fechaFin.getDate();

		int cantidadDeMeses = ModeloCursos.cantidadDeMeses(fechaInicio, fechaFin)+1;
		System.out.println(cantidadDeMeses);
		
		
		
		if (cantidadDeMeses == 0) {
			PagoEmpresaDTO pago = new PagoEmpresaDTO();
			pago.setFechaLimite(new Date(year, month, day));
			pago.setMonto(precio);
			pago.setDisponibleEnSistema(true);
			pago.setEmpresa(empresa.getId());
			pago.setRealizado(new Date(year - 10, month, day));
			pagos.add(pago);
		}

		for (int i = 0; i < cantidadDeMeses; i++) {
			PagoEmpresaDTO pago = new PagoEmpresaDTO();
			month--;
			if (month == -1) 
			{
				month = 11;
				year--;
			}
			
			pago.setFechaLimite(new Date(year, month+1, day));
			pago.setMonto(precio / cantidadDeMeses);
			pago.setDisponibleEnSistema(true);
			pago.setEmpresa(empresa.getId());
			pago.setRealizado(new Date(year - 10, month, day));
			pagos.add(pago);
		}

		// Agrego la diferencia del total a la primer cuota
		int diferenciaPrimerCuota = precio;
		for (PagoEmpresaDTO pago : pagos)
			diferenciaPrimerCuota -= pago.getMonto();
		pagos.get(0).setMonto(pagos.get(0).getMonto() + diferenciaPrimerCuota);

		return pagos;
	}

	
}
