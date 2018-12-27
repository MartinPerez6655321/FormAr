package presentacion.components.horarios;

import java.awt.BorderLayout;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dto.DiaDeLaSemanaDTO;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

@SuppressWarnings("deprecation")
public class GraficoHorariosDia extends JPanel
{
	private static final long serialVersionUID = -7778995636443377631L;
	private static final int SIDE_PADDING = 3;
	private List<JPanel> periodComponents;
	private List<JLabel> periodLabels;
	private transient List<Period> periods;
	private JPanel panelLabel;
	private Time startHeight;
	private Time endHeight;
	private JPanel content;
	
	public GraficoHorariosDia(DiaDeLaSemanaDTO dia, List<Period> periods)
	{
		setLayout(new BorderLayout());
		content = new JPanel();
		content.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		add(content, BorderLayout.CENTER);
		
		content.setLayout(null);
		
		periodComponents = new LinkedList<>();
		periodLabels = new LinkedList<>();
		this.periods = flipList(periods);
		
		for(int i = 0; i < this.periods.size(); i++)
		{
			JPanel comp = new JPanel();
			comp.setBackground(this.periods.get(i).getColor());
			content.add(comp);
			periodComponents.add(comp);
			comp.setLayout(new BorderLayout());
			
			JLabel lbl = new JLabel(getLabelString(this.periods.get(i)));
			comp.setToolTipText(getTooltipString(this.periods.get(i)));
			comp.add(lbl, BorderLayout.CENTER);
			periodLabels.add(lbl);
		}
		
		JLabel lblDia = new JLabel(dia.getNombre());
		lblDia.setHorizontalAlignment(SwingConstants.CENTER);
		
		panelLabel = new JPanel();
		add(panelLabel, BorderLayout.NORTH);
		panelLabel.setLayout(new BorderLayout());
		panelLabel.add(lblDia, BorderLayout.CENTER);
	}
	
	private static List<Period> flipList(List<Period> periods)
	{
		List<Period> ret = new LinkedList<Period>();
		for(int i = periods.size() -1; i >= 0 ; i--)
			ret.add(periods.get(i));
		return ret;
	}
	
	private static String getLabelString(Period period)
	{
		if(period.getString()!=null && !period.getString().isEmpty())
			return html(period.getString());
		else
			return html(time(period.getStart()) + "-" + time(period.getEnd()));
	}
	
	private static String getTooltipString(Period period)
	{
		if(period.getString()!=null && !period.getString().isEmpty())
			return html(period.getString() + ":\n" + time(period.getStart()) + "-" + time(period.getEnd()) + "hs.");
		else
			return html(time(period.getStart()) + "-" + time(period.getEnd()) + "hs.");
	}

	private static String time(Time t)
	{
		return dosDigitos(t.getHours()) + ":" + dosDigitos(t.getMinutes());
	}
	
	private static String html(String string) 
	{
		return "<html>" + string.replace("\n", "<br>") + "</html>";
	}
	
	private static String dosDigitos(int numero)
	{
		return numero<10? "0" + numero : String.valueOf(numero);
	}
	
	public void updateComponents(int width, int height)
	{
		double heightStart = startHeight==null? 0 : getPositionByHeightAndTime(height, startHeight);
		double heightMultiplier = endHeight==null || (getPositionByHeightAndTime(height, endHeight) - getPositionByHeightAndTime(height, startHeight)) == 0
				? 1 : 
			((height) / (getPositionByHeightAndTime(height, endHeight) - getPositionByHeightAndTime(height, startHeight)));
		
		for(int i = 0; i < periods.size(); i++)
		{
			double y = getPositionByHeightAndTime(height, periods.get(i).getStart());
			double heightOfPeriod = getPositionByHeightAndTime(height, periods.get(i).getEnd()) - y;
			y = y - heightStart;
			
			periodComponents.get(i).setBounds(
					SIDE_PADDING, 
					(int)(y * heightMultiplier) + SIDE_PADDING, 
					width - SIDE_PADDING*2, 
					(int)(heightOfPeriod * heightMultiplier));
		}
		panelLabel.setBounds(0, 0, getWidth(), 0);
		revalidate();
	}
	
	private static double getPositionByHeightAndTime(int height, Time t)
	{
		double ret = height * getTimeInt(t);
		ret = ret / (24.0 * 60.0);
		return ret;
	}
	
	private static int getTimeInt(Time t) 
	{
		return ((t.getHours()*60) + t.getMinutes());
	}
	
	public void setLimits(Time start, Time end) 
	{
		startHeight = start;
		endHeight = end;
	}
}
