package presentacion.vista;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import dto.NotificacionDTO;
import presentacion.components.PanelHideAndShow;
import presentacion.vista.subcomponentes.NotificacionComponent;

public class PanelNotificaciones extends JPanel
{
	private static final long serialVersionUID = -6459594935689612127L;
	
	private PanelHideAndShow content;
	private JScrollPane scrollPane;
	private JList<NotificacionDTO> listNotificacion;
	
	
	public PanelNotificaciones() 
	{
		setLayout(new BorderLayout());
		
		this.listNotificacion = new JList<>();
		this.listNotificacion.setCellRenderer(NotificacionComponent::new);
		
		scrollPane = new JScrollPane();
		content = new PanelHideAndShow(scrollPane, BorderLayout.EAST);
		content.setHidden(true);
		this.add(content, BorderLayout.CENTER);
		scrollPane.setViewportView(listNotificacion);
	}
	
	public void llenarPanel(List<NotificacionDTO> notificacion)
	{
		this.listNotificacion.setModel(new DefaultListModel<NotificacionDTO>()
		{
			private static final long serialVersionUID = 4468419293189953534L;
			
			List<NotificacionDTO> internalList =notificacion;
			@Override
			public int getSize()
			{
				return internalList.size();
			}

			@Override
			public NotificacionDTO getElementAt(int index)
			{
				return internalList.get(index);
			}
		});
		
		content.setButtonText("(" + filtarNoVistos(notificacion) + ")");
	}

	private String filtarNoVistos(List<NotificacionDTO> notificacion) {
		List<NotificacionDTO> list=new ArrayList<NotificacionDTO>();
		for (NotificacionDTO notificacionDTO : notificacion) {
			if (!notificacionDTO.getVisto())
				list.add(notificacionDTO);
		}
		return String.valueOf(list.size());
	}

	public JList<NotificacionDTO> getListNotificacion() {
		return listNotificacion;
	}

	public void setListNotificacion(JList<NotificacionDTO> listNotificacion) {
		this.listNotificacion = listNotificacion;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}



	
}
