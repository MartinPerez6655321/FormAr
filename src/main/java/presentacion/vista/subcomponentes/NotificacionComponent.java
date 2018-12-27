package presentacion.vista.subcomponentes;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import dto.NotificacionDTO;
import util.Strings;

public class NotificacionComponent extends JPanel
{
	private static final long serialVersionUID = -7450933976766526878L;

	public NotificacionComponent(JList<? extends NotificacionDTO> list, NotificacionDTO value, int index,
			boolean isSelected, boolean cellHasFocus)
	{
		super();
		
		setLayout(new BorderLayout());
		
		
		JLabel labelFecha =new JLabel ("<html>" + Strings.formatoFecha(value.getFecha()) + "<br>" + value.getHora() + "</html>");
		labelFecha.setBorder(new BevelBorder(BevelBorder.RAISED));
		add(labelFecha,BorderLayout.WEST);
		
		
		
		JLabel label = new JLabel("<html><bold>&nbsp;" +value.getTitulo() + "</bold><br>&nbsp;" + value.getDescripcion() + "</html>");
		add(label,BorderLayout.CENTER);
		
		
		
		
		setBorder(new BevelBorder(BevelBorder.RAISED));
		
		
		if (isSelected)
			{
				
				setBackground(Color.DARK_GRAY);
				labelFecha.setForeground(Color.WHITE);
				label.setForeground(Color.WHITE);
				
			}
		
		else if (value.getVisto())
		{	
			
			
		setBackground(Color.WHITE);
			labelFecha.setForeground(Color.BLACK);
			label.setForeground(Color.BLACK);
			
		
		
		}
		else if (!value.getVisto())
		{
			
		setBackground(Color.ORANGE);
			labelFecha.setForeground(Color.WHITE);
			label.setForeground(Color.WHITE);
			
		
		
		
		}
			
		
		
		label.setHorizontalAlignment(JLabel.LEFT);
		add(label);
	}

	private String getEstado(Boolean visto) {
		
		if (visto)
			return "Visto";
		return "No visto";
	}
}
