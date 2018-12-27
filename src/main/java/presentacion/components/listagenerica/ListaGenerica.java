package presentacion.components.listagenerica;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListaGenerica<T extends Component> extends JPanel implements Iterable<T>
{
	private static final long serialVersionUID = 1329496900245745548L;
	
	private List<T> componentes;
	private List<JLabel> drags;
	private transient ComponentFactory<T> factory;
	private JPanel content;
	
	private transient List<ListChangeListener<T>> listeners;
	private JScrollPane scrollPane;
	
	public ListaGenerica(ComponentFactory<T> componentFactory)
	{
		super();
		listeners = new LinkedList<>();
		setLayout(new BorderLayout());
		componentes = new LinkedList<>();
		drags = new LinkedList<>();
		
		factory = componentFactory;
		content = new JPanel();
		content.setLayout(null);
		scrollPane = new JScrollPane(content);
		add(scrollPane, BorderLayout.CENTER);
		
		addComponentListener(new ComponentAdapter()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				actualizar();
			}
		});
	}
	
	public void addListChangeListener(ListChangeListener<T> listener)
	{
		listeners.add(listener);
	}
	
	public Iterator<T> iterator()
	{
		return componentes.iterator();
	}

	public void addElement(T c) 
	{
		componentes.add(c);
		actualizar();
	}
	
	public void addElement(int index, T c) 
	{
		componentes.add(index, c);
		actualizar();
	}
	
	public T removeElement(int index) 
	{
		T ret = componentes.remove(index);
		actualizar();
		return ret;
	}
	
	public void clear()
	{
		componentes.clear();
		actualizar();
	}
	
	private void actualizar() 
	{
		invalidate();
		content.removeAll();
		drags.clear();
		int y = 0;
		for(int i = 0; i < componentes.size(); i++)
		{
			T c = componentes.get(i);
			agregar(c, y);
			y = y + c.getHeight();
		}
		
		Dimension dim = getDimension(componentes);
		dim.setSize(Integer.max((int) dim.getWidth(), scrollPane.getWidth() - 5), dim.getHeight());
		content.setPreferredSize(dim);

		JButton nuevoComponente = new JButton("+");
		nuevoComponente.setBounds(1, y, (int) (content.getPreferredSize().getWidth()-2), 20);
		nuevoComponente.addActionListener(e -> crear());
		content.add(nuevoComponente);
		
		repaint();
		validate();
		
		for(ListChangeListener<T> l : listeners)
			l.listChanged();
	}
	
	private void agregar(T c, int y) 
	{
		c.setBounds(20, y, getWidth() - 20, (int) c.getPreferredSize().getHeight());
		content.add(c);
		
		JLabel drag;
		URL url = ListaGenerica.class.getResource("/presentacion/components/listagenerica/drag20x20.png");
		if(url!=null)
			drag = new JLabel(new ImageIcon(url));
		else
			drag = new JLabel("...");
		drag.setBounds(0, y, 20, c.getHeight());
		content.add(drag);
		drags.add(drag);
		FDragManager dragManager = new FDragManager(componentes.indexOf(c));
		drag.addMouseListener(dragManager);
		drag.addMouseMotionListener(dragManager);
	}
	
	public void cambiarPosicion(int index, int pos) 
	{
		T c = componentes.remove(index);
		componentes.add(pos, c);
		actualizar();
	}

	public void quitar(int index) 
	{
		componentes.remove(index);
		actualizar();
	}
	
	public void crear() 
	{
		T elem = factory.create();
		if(elem != null)
		{
			componentes.add(elem);
			actualizar();
		}
	}
	
	private static Dimension getDimension(List<? extends Component> components) 
	{
		int height = 0, width = 50;
		for(Component c : components) 
		{
			height = (int) (height + c.getPreferredSize().getHeight());
			width = (int) Math.max(width, c.getPreferredSize().getWidth());
		}
		return new Dimension(width + 20, height + 21);
	}
	
	public List<T> getComponentsList()
	{
		return componentes;
	}
	
	private class FDragManager extends FMouseAdapter
	{
		private int index;
		private JPanel estimatedPosition;

		public FDragManager(int index)
		{
			this.index = index;
			estimatedPosition = new JPanel();
			estimatedPosition.setBackground(Color.RED);
			estimatedPosition.setVisible(false);
			content.add(estimatedPosition, 0);
		}
		
		@Override
		public void mousePressed(MouseEvent arg0)
		{
			mostrarPosicionEstimada(arg0);
		}

		@Override
		public void mouseDragged(MouseEvent arg0)
		{
			mostrarPosicionEstimada(arg0);
		}
		
		private void mostrarPosicionEstimada(MouseEvent arg0) 
		{
			content.invalidate();
			if(!componentContainsEventPoint(arg0, content)) {
				estimatedPosition.setBounds(componentes.get(index).getBounds());
			} else {
				int pos = calcularPos(arg0);
				if(pos > index)
					estimatedPosition.setBounds(0, componentes.get(pos).getY() + componentes.get(pos).getHeight(), content.getWidth(), 2);
				else
					estimatedPosition.setBounds(0, componentes.get(pos).getY(), content.getWidth(), 2);
			}
			estimatedPosition.setVisible(true);
			
			content.repaint();
			content.revalidate();
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
			if(!componentContainsEventPoint(arg0, content)) {
				quitar(index);
			} else {
				cambiarPosicion(index, calcularPos(arg0));
			}
			estimatedPosition.setVisible(false);
		}

		private int calcularPos(MouseEvent e)
		{
			for(int i = 0; i < componentes.size(); i++)
				if(componentContainsEventPoint(e, componentes.get(i), drags.get(i)))
					return i;
			return componentes.size()-1;
		}
		
		private boolean componentContainsEventPoint(MouseEvent e, Component... components) 
		{
			int x = Integer.MAX_VALUE, y = Integer.MAX_VALUE, x2 = Integer.MIN_VALUE, y2 = Integer.MIN_VALUE;
			for(Component c : components)
			{
				x = Math.min(x, (int) c.getLocationOnScreen().getX());
				y = Math.min(y, (int) c.getLocationOnScreen().getY());
				x2 = Math.max(x2, (int) c.getLocationOnScreen().getX() + c.getWidth());
				y2 = Math.max(y2, (int) c.getLocationOnScreen().getY() + c.getHeight());
			}
			
			Rectangle rect = new Rectangle(x, y, x2 - x, y2 -y);
			return rect.contains(e.getLocationOnScreen());
		}
	}
}

