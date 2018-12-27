package presentacion.vista.subcomponentes;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelCargandoPrograma extends JPanel
{
	private static final long serialVersionUID = -7012485855801862541L;
	private static final String TEXT = "El programa se est\u00E1 descargando, por favor aguarde";
	private static final String[] TEXT_SUFFIX = new String[] {".", "..", "..."};
	
	private static PanelCargandoPrograma instance;
	private int index;
	
	private PanelCargandoPrograma()
	{
		setLayout(new BorderLayout());
		
		try {
			ImageIcon icon = new ImageIcon(this.getClass().getResource("/Imagenes/loading.gif"));
			add(new JLabel(icon), BorderLayout.CENTER);
		} catch(Exception e) {
			usarTexto();
		}
	}
	
	private void usarTexto() 
	{
		JLabel lbl = new JLabel(TEXT);
		add(lbl, BorderLayout.NORTH);
		Timer t = new Timer(500, 
				e -> {
					lbl.setText(TEXT + TEXT_SUFFIX[index]);
					index = index==TEXT_SUFFIX.length-1? 0 : index + 1; 
				});
		t.start();
	}
	
	public static PanelCargandoPrograma getInstance() 
	{
		if(instance == null)
			instance = new PanelCargandoPrograma();
		return instance;
	}
}
