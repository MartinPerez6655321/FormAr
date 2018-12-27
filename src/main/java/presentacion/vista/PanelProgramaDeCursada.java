package presentacion.vista;

import javax.swing.JPanel;

import org.icepdf.ri.common.MyAnnotationCallback;
import org.icepdf.ri.common.SwingController;
import org.icepdf.ri.common.SwingViewBuilder;

import dto.CursadaDTO;
import presentacion.vista.subcomponentes.PanelCargandoPrograma;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.swing.JButton;
import javax.swing.JLabel;

public class PanelProgramaDeCursada extends JPanel
{
	private static final long serialVersionUID = 5471558866851156419L;
	private JButton btnCargar;
	private JButton btnEliminar;
	
	private JPanel viewerComponentPanel;
	
	public PanelProgramaDeCursada()
	{
		setPreferredSize(new Dimension(1000, 600));
		setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPane = new JPanel();
		add(buttonPane, BorderLayout.SOUTH);
		
		btnCargar = new JButton("Cargar");
		buttonPane.add(btnCargar);
		
		btnEliminar = new JButton("Eliminar");
		buttonPane.add(btnEliminar);
	}
	
	public void setCursada(CursadaDTO cursada)
	{
		if(viewerComponentPanel != null)
			remove(viewerComponentPanel);
		
		add(PanelCargandoPrograma.getInstance(), BorderLayout.CENTER);
		
		new Thread(() -> 
		{
			if(!cursada.getPrograma().isEmpty())
			{
				File tmp = new File(System.getenv("APPDATA") + "/FormAR/tmp/programa.tmp");
				tmp.getParentFile().mkdirs();
				tmp.deleteOnExit();
				
				try(FileOutputStream fos = new FileOutputStream(tmp))
				{
					fos.write(Base64.getDecoder().decode(cursada.getPrograma()));
					
					SwingController controller = new SwingController();
					SwingViewBuilder factory = new SwingViewBuilder(controller);
					
					viewerComponentPanel = factory.buildViewerPanel();
					controller.getDocumentViewController().setAnnotationCallback(
							new MyAnnotationCallback(controller.getDocumentViewController()));
					controller.openDocument(tmp.getPath());
				} catch (IOException e) {
					e.printStackTrace();
					viewerComponentPanel = new JPanel();
					viewerComponentPanel.setLayout(new BorderLayout());
					viewerComponentPanel.add(new JLabel("Hubo un error al intentar mostrar el programa."), BorderLayout.CENTER);
				}
			} else {
				viewerComponentPanel = new JPanel();
				viewerComponentPanel.setLayout(new BorderLayout());
				viewerComponentPanel.add(new JLabel("Esta cursada no tiene un programa cargado."), BorderLayout.CENTER);
			}
			setVisible(false);
			remove(PanelCargandoPrograma.getInstance());
			add(viewerComponentPanel, BorderLayout.CENTER);
			setVisible(true);
		}).start();
	}

	public JButton getBtnCargar()
	{
		return btnCargar;
	}

	public JButton getBtnEliminar()
	{
		return btnEliminar;
	}
}
