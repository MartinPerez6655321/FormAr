package presentacion.controlador;

import java.sql.Time;

import dto.DiaDeLaSemanaDTO;
import dto.HorarioDTO;
import modelo.ModeloCursos;
import presentacion.components.Modal;
import presentacion.vista.PanelEdicionHorario;

public class ControladorEdicionHorario
{
	private static ModeloCursos model = ModeloCursos.getInstance();
	private ControladorEdicionHorario(){}
	
	public static void modificarHorario(HorarioDTO horario)
	{
		PanelEdicionHorario panel = new PanelEdicionHorario();
		panel.getPickerInicio().setTime(horario.getHoraInicio().toLocalTime());
		panel.getPickerFin().setTime(horario.getHoraFin().toLocalTime());
		panel.getCmbDia().setSelectedItem(horario.getDiaDeLaSemana());
		inicializar(panel);
		
		panel.getBtnOk().addActionListener(e -> accionModificar(panel, horario));
		
		Modal.showDialog(panel, "Editando horario", panel.getBtnOk());
	}
	
	private static void accionModificar(PanelEdicionHorario panel, HorarioDTO horario)
	{
		horario.setHoraInicio(Time.valueOf(panel.getPickerInicio().getTime()));
		horario.setHoraFin(Time.valueOf(panel.getPickerFin().getTime()));
		horario.setDiaDeLaSemana((DiaDeLaSemanaDTO)panel.getCmbDia().getSelectedItem());
		
		model.modificarHorario(horario);
	}
	
	public static HorarioDTO crearHorario()
	{
		PanelEdicionHorario panel = new PanelEdicionHorario();
		inicializar(panel);
		
		Modal.showDialog(panel, "Agregando horario", panel.getBtnOk());
		HorarioDTO horario = new HorarioDTO();
		
		horario.setDisponibleEnSistema(true);
		horario.setHoraInicio(Time.valueOf(panel.getPickerInicio().getTime()));
		horario.setHoraFin(Time.valueOf(panel.getPickerFin().getTime()));
		horario.setDiaDeLaSemana((DiaDeLaSemanaDTO)panel.getCmbDia().getSelectedItem());
		
		model.agregarHorario(horario);
		return horario;
	}
	
	private static void inicializar(PanelEdicionHorario panel)
	{
		actualizar(panel);
		panel.getPickerInicio().addTimeChangeListener(e -> actualizar(panel));
		panel.getPickerFin().addTimeChangeListener(e -> actualizar(panel));
		for(DiaDeLaSemanaDTO dia : model.getDias())
			panel.getCmbDia().addItem(dia);
	}
	
	private static void actualizar(PanelEdicionHorario panel)
	{
		panel.getBtnOk().setEnabled( 
				panel.getPickerInicio().getTime() != null &&
				panel.getPickerFin().getTime() != null && 
				panel.getPickerFin().getTime().isAfter(panel.getPickerInicio().getTime()));
	}
}