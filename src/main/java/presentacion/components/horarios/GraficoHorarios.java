package presentacion.components.horarios;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.data.time.Hour;

import dto.DiaDeLaSemanaDTO;
import persistencia.dao.mysql.DiaDeLaSemanaDAOMYSQL;

@SuppressWarnings("deprecation")
public class GraficoHorarios extends JPanel
{
	private static final long serialVersionUID = -8324403885699805788L;
	private static final double TOP_PADDING = 25;
	
	private JPanel timeLabelsPanel;
	private List<JLabel> timeLabels;
	private List<GraficoHorariosDia> dayComponents;
	private transient List<DiaDeLaSemanaDTO> days;
	private Time min;
	private Time max;
	
	public GraficoHorarios()
	{
		inicializar(new LinkedList<>());
	}
	
	public GraficoHorarios(List<Period> periods)
	{
		inicializar(periods);
	}
	
	private void inicializar(List<Period> periods)
	{
		dayComponents = new LinkedList<>();
		timeLabels = new LinkedList<>();
		timeLabelsPanel = new JPanel();
		timeLabelsPanel.setLayout(null);
		days = DiaDeLaSemanaDAOMYSQL.getInstance().readAll();
		
		setPeriods(periods);
		
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentShown(ComponentEvent e)
			{
				updateComponents();
			}
			
			@Override
			public void componentResized(ComponentEvent e)
			{
				updateComponents();
			}
		});
	}

	public void setPeriods(List<Period> periods)
	{
		removeAll();
		dayComponents.clear();
		
		setTimeBounds(periods);
		
		for(DiaDeLaSemanaDTO day : days) 
		{
			List<Period> periodsOfDay = new LinkedList<>();
			for(Period p : periods)
				if(p.getDay().getId().equals(day.getId()))
					periodsOfDay.add(p);
			
			GraficoHorariosDia part = new GraficoHorariosDia(day, periodsOfDay);
			dayComponents.add(part);
			
			part.setLimits(min, max);
			add(part);
		}
		
		updateComponents();
	}
	
	private void setTimeBounds(List<Period> periods)
	{
		for(Period p : periods) 
		{
			if(min==null)
				min = p.getStart();
			if(max==null)
				max = p.getEnd();

			if(min.after(p.getStart()))
				min = p.getStart();
			if(min.after(p.getEnd()))
				min = p.getEnd();
			if(max.before(p.getStart()))
				max = p.getStart();
			if(max.before(p.getEnd()))
				max = p.getEnd();
		}
		createTimeLabels();
	}
	
	private void createTimeLabels() 
	{
		timeLabels.clear();
		int horaInicial = min!=null? min.getHours() : 0;
		int horaFinal = max!=null? max.getHours() : 24;
		
		for(int i = horaInicial; i < horaFinal; i++)
			timeLabels.add(new JLabel(String.valueOf(i)));
	}
	
	public <T> void setPeriods(List<T> elems, Transformer<T> transformer)
	{
		List<Period> periods = new LinkedList<>();
		for(T elem : elems)
			periods.add(transformer.transform(elem));
		
		setPeriods(periods);
	}

	public void updateComponents()
	{
		double width = (getSize().width/(dayComponents.size()+1)) - 5;
		double height = getSize().height - TOP_PADDING;

		setPreferredSize(new Dimension((int)width, (int)height));
		updateTimeLabels(width, height);
		
		for(GraficoHorariosDia part : dayComponents)
		{
			part.setPreferredSize(new Dimension((int)width, (int)height));
			part.updateComponents((int)width, (int)height);
		}
		
		revalidate();
	}
	
	private void updateTimeLabels(double width, double height) 
	{
		timeLabelsPanel.removeAll();
		timeLabelsPanel.setPreferredSize(new Dimension(20, (int)height));
		for(int i = 0; i < timeLabels.size(); i++)
		{
			JLabel lbl = timeLabels.get(i);
			int topPadding = (int)(TOP_PADDING / (i + 1));
			lbl.setBounds(0, (int)(i * ((height)/timeLabels.size())) + topPadding, (int)width, 20);
			timeLabelsPanel.add(lbl);
		}
		add(timeLabelsPanel, 0);
	}
	
	public static <T> GraficoHorarios createComponent(List<T> elems, Transformer<T> transformer)
	{
		List<Period> periods = new LinkedList<>();
		for(T elem : elems)
			periods.add(transformer.transform(elem));
		
		return new GraficoHorarios(periods);
	}
	
	public interface Transformer<T>
	{
		public Period transform(T elem);
	}
}
