package presentacion.controlador;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import dto.CursadaDTO;
import dto.DiaDeLaSemanaDTO;
import dto.HorarioDTO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import modelo.ModeloCursos;
import presentacion.vista.VistaAgregarHorario;

public class ControladorAsignadorHorarioCursada implements InvalidationListener, KeyListener,DocumentListener {
	private VistaAgregarHorario vista;
	private CursadaDTO cursada;
	private ModeloCursos model = ModeloCursos.getInstance();
	private List<HorarioDTO> horariosNuevos;
	private HorarioDTO horarioNuevo;
	

	public ControladorAsignadorHorarioCursada(VistaAgregarHorario vistaAgregarHorario, CursadaDTO cursada) {
		this.cursada = cursada;
		this.horarioNuevo = new HorarioDTO();

		vista = vistaAgregarHorario;
		model.addListener(this);

		invalidated(model);

		llenarComboBox();
		vista.getBtnAsignarHorario().setEnabled(false);

		this.vista.addKeyListener(this);
		
		this.vista.getFinMinuto().addKeyListener(this);
		this.vista.getFinHora().addKeyListener(this);
		this.vista.getInicioHora().addKeyListener(this);
		this.vista.getInicioMinuto().addKeyListener(this);
		
		this.vista.getInicioHora().getDocument().addDocumentListener(this);
		this.vista.getInicioMinuto().getDocument().addDocumentListener(this);
		this.vista.getFinHora().getDocument().addDocumentListener(this);
		this.vista.getFinMinuto().getDocument().addDocumentListener(this);
		
		
		vista.getBtnAsignarHorario().addActionListener(e -> 
		{

				generarHorario();
				model.agregarHorario(horarioNuevo);
				if (cursada.getHorarios() == null) {
					horariosNuevos.add(horarioNuevo);
					cursada.setHorarios(horariosNuevos);
					model.modificarCursada(cursada);
				} else {
					cursada.getHorarios().add(horarioNuevo);
					model.modificarCursada(cursada);
				}
				
	
			

		});
	}

	
	private void llenarComboBox() 
	{

		List<DiaDeLaSemanaDTO> dias = this.model.getDias();
		List<HorarioDTO>horariosCursada=cursada.getHorarios();
		
		if (!horariosCursada.isEmpty())
		for (DiaDeLaSemanaDTO dia : dias) 
		{
			for (int i = 0; i < horariosCursada.size(); i++)
			{
				if(horariosCursada.get(i).getDiaDeLaSemana().equals(dia))
					break;
				else if (i==horariosCursada.size()-1)
					this.vista.getComboBox().addItem(dia.getNombre());
			}
		}
		else
			for (DiaDeLaSemanaDTO dia : dias) 
			{
				this.vista.getComboBox().addItem(dia.getNombre());
			}
			
	}

	private void generarHorario() 
	{

		@SuppressWarnings("deprecation")
		Time horaInicio = new Time(Integer.parseInt(this.vista.getInicioHora().getText()),
				Integer.parseInt(this.vista.getInicioMinuto().getText()), 0);
		@SuppressWarnings("deprecation")
		Time horaFin = new Time(Integer.parseInt(this.vista.getFinHora().getText()),
				Integer.parseInt(this.vista.getFinMinuto().getText()), 0);
		DiaDeLaSemanaDTO diaDeLaSemana = new DiaDeLaSemanaDTO();

		diaDeLaSemana.setNombre(this.vista.getComboBox().getSelectedItem().toString());

		List<DiaDeLaSemanaDTO> dias = this.model.getDias();
		for (DiaDeLaSemanaDTO dia : dias) {
			if (dia.getNombre().equals(diaDeLaSemana.getNombre())) {
				diaDeLaSemana = dia;
				break;
			}
			
		}

		horarioNuevo.setHoraInicio(horaInicio);
		horarioNuevo.setHoraFin(horaFin);
		horarioNuevo.setDisponibleEnSistema(true);
		horarioNuevo.setDiaDeLaSemana(diaDeLaSemana);

	}

	@Override
	public void invalidated(Observable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		if (e.getSource() == this.vista.getFinMinuto()) 
		{
			char caracter = e.getKeyChar();
			if (((caracter < '0') || (caracter > '9')) && (caracter != '\b'))
				e.consume();
	
			if (this.vista.getFinMinuto().getText().length() == 2)
				e.consume();
			if (!this.vista.getFinMinuto().getText().equals("")) {
				
				if ((caracter != '\b')) {
					if (Integer.parseInt(this.vista.getFinMinuto().getText()
							+ Integer.parseInt(Character.toString(caracter))) > 59) {
						e.consume();
					}

				}
			}

		}
		if (e.getSource() == this.vista.getInicioMinuto()) 
		{
			char caracter = e.getKeyChar();
			if (((caracter < '0') || (caracter > '9')) && (caracter != '\b'))
				e.consume();
	
			if (this.vista.getInicioMinuto().getText().length() == 2)
				e.consume();
			if (!this.vista.getInicioMinuto().getText().equals("")) {
				
				if ((caracter != '\b')) {
					if (Integer.parseInt(this.vista.getInicioMinuto().getText()
							+ Integer.parseInt(Character.toString(caracter))) > 59) {
						e.consume();
					}

				}
			}

		}
		if (e.getSource() == this.vista.getInicioHora()) 
		{
			char caracter = e.getKeyChar();
			if (((caracter < '0') || (caracter > '9')) && (caracter != '\b'))
				e.consume();
	
			if (this.vista.getInicioHora().getText().length() == 2)
				e.consume();
			if (!this.vista.getInicioHora().getText().equals("")) {
				
				if ((caracter != '\b')) {
					if (Integer.parseInt(this.vista.getInicioHora().getText()
							+ Integer.parseInt(Character.toString(caracter))) > 24) {
						e.consume();
					}

				}
			}

		}
		if (e.getSource() == this.vista.getFinHora()) 
		{
			char caracter = e.getKeyChar();
			if (((caracter < '0') || (caracter > '9')) && (caracter != '\b'))
				e.consume();
	
			if (this.vista.getFinHora().getText().length() == 2)
				e.consume();
			if (!this.vista.getFinHora().getText().equals("")) {
				
				if ((caracter != '\b')) {
					if (Integer.parseInt(this.vista.getFinHora().getText()
							+ Integer.parseInt(Character.toString(caracter))) > 24) {
						e.consume();
					}

				}
			}

		}
		

	}
	@Override
	public void changedUpdate(DocumentEvent e) 
	{
		
		
		if(this.vista.getInicioHora().getText().isEmpty() || this.vista.getInicioMinuto().getText().isEmpty() || this.vista.getFinHora().getText().isEmpty() || this.vista.getFinMinuto().getText().isEmpty() ) {
			vista.getBtnAsignarHorario().setEnabled(false);		
		}
		else if (Integer.parseInt(this.vista.getInicioHora().getText())==Integer.parseInt(this.vista.getFinHora().getText()) )
			vista.getBtnAsignarHorario().setEnabled(false);
		else if (Integer.parseInt(this.vista.getInicioHora().getText())>Integer.parseInt(this.vista.getFinHora().getText())  )
			vista.getBtnAsignarHorario().setEnabled(false);
	
		else
			vista.getBtnAsignarHorario().setEnabled(true);				
	}
	@Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
		
	}
	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
		
	}}
