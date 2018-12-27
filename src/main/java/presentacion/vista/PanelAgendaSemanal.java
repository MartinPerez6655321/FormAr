package presentacion.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.toedter.calendar.JDateChooser;

import dto.CursadaDTO;
import dto.HorarioDTO;
import presentacion.components.PanelHideAndShow;
import presentacion.components.horarios.GraficoHorarios;
import presentacion.components.horarios.Period;

public class PanelAgendaSemanal extends JPanel
{
	private static final int MAX_COLOR = 0xff;
	private static final int MIN_COLOR = 0x44;
	
	private static final long serialVersionUID = -8649493876653328040L;
	
	private GraficoHorarios grafico;
	private JDateChooser dateDesde;
	private JDateChooser dateHasta;
	
	public PanelAgendaSemanal() 
	{
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(800, 600));
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(150, 500));
		PanelHideAndShow hidePanel = new PanelHideAndShow(rightPanel, BorderLayout.WEST);
		
		JLabel lblDesde = new JLabel("Desde");
		rightPanel.add(lblDesde);
		
		dateDesde = new JDateChooser(new Date());
		dateDesde.setPreferredSize(new Dimension(120, 25));
		rightPanel.add(dateDesde);
		
		JLabel lblHasta = new JLabel("Hasta");
		rightPanel.add(lblHasta);
		
		Date date = new Date();
		date.setDate(date.getDate() + 7);
		dateHasta = new JDateChooser(date);
		dateHasta.setPreferredSize(new Dimension(120, 25));
		rightPanel.add(dateHasta);
		
		add(hidePanel, BorderLayout.EAST);
	}
	
	public void setCursadas(List<CursadaDTO> cursadas) 
	{
		setVisible(false);
		
		if(grafico!=null)
			remove(grafico);
		
		List<Period> periods = new LinkedList<>();
		
		for(CursadaDTO cursada : cursadas) 
		{
			Color color = colorAleatorio();
			for(HorarioDTO horario : cursada.getHorarios()) 
			{
				Period p = new Period(horario.getDiaDeLaSemana(), horario.getHoraInicio(), horario.getHoraFin(), cursada.getNombre(), color);
				periods.add(p);
			}
		}
		
		grafico = new GraficoHorarios(periods);
		add(grafico, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private Color colorAleatorio() 
	{
		return Color.decode( "#" + hexAleatorio()  + hexAleatorio()  + hexAleatorio() );
	}
	
	private String hexAleatorio() 
	{
		return String.format("%02x", (new Random().nextInt(MAX_COLOR - MIN_COLOR + 1) + MIN_COLOR));
	}

	public JDateChooser getDateDesde()
	{
		return dateDesde;
	}

	public JDateChooser getDateHasta()
	{
		return dateHasta;
	}
}
