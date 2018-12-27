package presentacion.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionListener;

public class ListaMultiple<T> extends JPanel
{
	private static final long serialVersionUID = -6607129564557005737L;
	private transient List<T> elems;
	private boolean elegirMultiples;
	private boolean filtradoActivo;
	private DefaultListModel<T> elegidosListModel;
	private DefaultListModel<T> noElegidosListModel;
	private DefaultListModel<T> elegidosListModelFilter;
	private DefaultListModel<T> noElegidosListModelFilter;

	private static final String ACCION_ELEGIR = "accion_elegir";
	private static final String ACCION_DESELEGIR = "accion_deselegir";
	private static final String ACCION_FILTRAR = "accion_filtrar";
	private static final String ACCION_QUITAR_FILTRO = "accion_desfiltrar";
	private JTextField txtFilter;
	private JList<T> listElegidos;
	private JList<T> listNoElegidos;
	private JPanel filterPanel;
	
	public ListaMultiple(List<T> elems, boolean elegirMultiples, boolean filtroVisible)
	{
		this.elems = new LinkedList<>(elems);
		this.elegirMultiples = elegirMultiples;
		inicializarComponentes(filtroVisible);
	}
	
	private void inicializarComponentes(boolean filtroVisible)
	{
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		setLayout(new BorderLayout(0, 0));
		add(center, BorderLayout.CENTER);
		JScrollPane scrollPaneElegidos = new JScrollPane();
		center.add(scrollPaneElegidos);
		listElegidos = new JList<>();
		scrollPaneElegidos.setViewportView(listElegidos);
		elegidosListModel = new DefaultListModel<>();
		listElegidos.setModel(elegidosListModel);
		noElegidosListModel = new DefaultListModel<>();
		for(T t : elems)
			noElegidosListModel.addElement(t);
		elegidosListModelFilter = new DefaultListModel<>();
		noElegidosListModelFilter = new DefaultListModel<>();
		
		JPanel botonera = new JPanel();
		botonera.setMaximumSize(new Dimension(50, 70));
		center.add(botonera);

		JScrollPane scrollPaneNoElegidos = new JScrollPane();
		center.add(scrollPaneNoElegidos);
		listNoElegidos = new JList<>();
		scrollPaneNoElegidos.setViewportView(listNoElegidos);
		listNoElegidos.setModel(noElegidosListModel);
		botonera.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(50, 100));
		panel.setMinimumSize(new Dimension(50, 100));
		panel.setPreferredSize(new Dimension(50, 100));
		botonera.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		filterPanel = new JPanel();
		filterPanel.setVisible(filtroVisible);
		add(filterPanel, BorderLayout.NORTH);
		
		txtFilter = new JTextField();
		filterPanel.add(txtFilter);
		txtFilter.setColumns(10);
		
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				int parte = (e.getComponent().getWidth() - botonera.getWidth()) / 2; 
				scrollPaneElegidos.setPreferredSize(new Dimension(parte, e.getComponent().getHeight() - filterPanel.getHeight()));
				scrollPaneNoElegidos.setPreferredSize(new Dimension(parte, e.getComponent().getHeight() - filterPanel.getHeight()));
			}
		});
		
		BotonesController btnController = new BotonesController();
		
		JButton btnElegir = new JButton("<");
		JButton btnDeselegir = new JButton(">");
		JButton btnFiltrar = new JButton("Filtrar");
		JButton btnQuitarFiltro = new JButton("Quitar filtro");
		
		panel.add(btnElegir);
		panel.add(btnDeselegir);
		filterPanel.add(btnFiltrar);
		filterPanel.add(btnQuitarFiltro);
		
		btnElegir.setActionCommand(ACCION_ELEGIR);
		btnDeselegir.setActionCommand(ACCION_DESELEGIR);
		btnFiltrar.setActionCommand(ACCION_FILTRAR);
		btnQuitarFiltro.setActionCommand(ACCION_QUITAR_FILTRO);
		
		btnElegir.addActionListener(btnController);
		btnDeselegir.addActionListener(btnController);
		btnFiltrar.addActionListener(btnController);
		btnQuitarFiltro.addActionListener(btnController);

		listNoElegidos.addMouseListener(new DblClickController(ACCION_ELEGIR));
		listElegidos.addMouseListener(new DblClickController(ACCION_DESELEGIR));
	}
	
	public List<T> getAllElems()
	{
		return new LinkedList<>(elems);
	}
	
	private class DblClickController extends MouseAdapter
	{
		private String cmd;
		DblClickController(String cmd)
		{
			this.cmd = cmd;
		}
		
		@Override
		public void mouseClicked(MouseEvent event) 
		{
			if(event.getClickCount() % 2 == 0 && event.getButton() == MouseEvent.BUTTON1)
				accion(cmd);
		}
	}
	
	private class BotonesController implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			accion(arg0.getActionCommand());
		}
	}
	
	private void accion(String cmd) 
	{
		if(cmd.equals(ACCION_ELEGIR))
		{
			accionElegir();
		}
		if(cmd.equals(ACCION_DESELEGIR))
		{
			accionDeselegir();
		}
		if(cmd.equals(ACCION_FILTRAR))
		{
			filtrar(txtFilter.getText());
		}
		if(cmd.equals(ACCION_QUITAR_FILTRO))
		{
			quitarFiltro();
		}
	}
	
	private void accionElegir()
	{
		for(T t : listNoElegidos.getSelectedValuesList())
		{
			elegidosListModel.addElement(t);
			if(!elegirMultiples) noElegidosListModel.removeElement(t);
			if(filtradoActivo)
			{
				elegidosListModelFilter.addElement(t);
				if(!elegirMultiples) noElegidosListModelFilter.removeElement(t);
			}
		}
	}
	
	private void accionDeselegir()
	{
		for(T t : listElegidos.getSelectedValuesList())
		{
			if(!elegirMultiples) noElegidosListModel.addElement(t);
			elegidosListModel.removeElement(t);
			if(filtradoActivo)
			{
				if(!elegirMultiples) noElegidosListModelFilter.addElement(t);
				elegidosListModelFilter.removeElement(t);
			}
		}
	}
	
	public void filtrar(String filtro)
	{
		filtradoActivo = true;
		elegidosListModelFilter.clear();
		noElegidosListModelFilter.clear();

		for(int i = 0; i < elegidosListModel.size(); i++)
			if(elegidosListModel.get(i).toString().contains(filtro))
				elegidosListModelFilter.addElement(elegidosListModel.get(i));
		for(int i = 0; i < noElegidosListModel.size(); i++)
			if(noElegidosListModel.get(i).toString().contains(filtro))
				noElegidosListModelFilter.addElement(noElegidosListModel.get(i));
		
		listElegidos.setModel(elegidosListModelFilter);
		listNoElegidos.setModel(noElegidosListModelFilter);
	}
	
	public void quitarFiltro()
	{
		filtradoActivo = false;
		listElegidos.setModel(elegidosListModel);
		listNoElegidos.setModel(noElegidosListModel);
	}
	
	public List<T> getSelectedValues()
	{
		List<T> ret = new LinkedList<>();
		for(int i = 0; i < elegidosListModel.size(); i++)
			ret.add(elegidosListModel.get(i));
		return ret;
	}
	
	public void filtroVisible(boolean visible)
	{
		filterPanel.setVisible(visible);
	}
	
	public void addRightListSelectionListener(ListSelectionListener listener)
	{
		listNoElegidos.addListSelectionListener(listener);
	}
	
	public void addLeftListSelectionListener(ListSelectionListener listener)
	{
		listElegidos.addListSelectionListener(listener);
	}
}
