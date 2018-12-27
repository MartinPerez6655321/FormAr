package presentacion.components.tabla;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import presentacion.components.Modal;
import presentacion.components.PanelHideAndShow;
import presentacion.components.tabla.filterimpl.TextFieldFilter;

public class TablaGenerica<T> extends JPanel implements InvalidationListener
{
	private static final long serialVersionUID = -1692593511892138367L;
	
	private transient Transformer<T> transformer;
	private transient Obtainer<T> obtainer;
	private JScrollPane scrollPaneFilter;
	private FilterPanel<T> filter;
	
	private transient List<T> elems;
	private transient List<T> filteredElems;
	private transient T selectedElem;
	private transient List<T> selectedElems;
	
	private String[] columnNames;
	private JTable table;

	private transient List<SelectionListener<T>> listeners;
	private transient List<ListSelectionListener<T>> listListeners;
	private transient List<DoubleClickListener> doubleClickListeners;

	private PanelHideAndShow hasPanel;


	public static <Y> Y seleccionSimpleFiltro(List<Y> options, String title, String column, SingleColumnTransformer<Y> transformer)
	{
		return seleccionSimple(options, title, column, transformer, new FilterPanel<>(column, new TextFieldFilter<>(toCommonTransformer(transformer))));
	}
	
	public static <Y> Y seleccionSimple(List<Y> options, String title, String column, SingleColumnTransformer<Y> transformer)
	{
		return seleccionSimple(options, title, column, transformer, null);
	}
	
	public static <Y> Y seleccionSimple(List<Y> options, String title, String column, SingleColumnTransformer<Y> transformer, FilterPanel<Y> filterPanel)
	{
		TablaGenerica<Y> tabla = new TablaGenerica<>(new String[] {column}, toCommonTransformer(transformer), () -> options, filterPanel);
		Modal dialogo = new Modal(tabla, 0, 0, 500, 500);
		dialogo.setTitle(title);
		tabla.addSelectionListener(selectedItem -> dialogo.dispose());
		dialogo.setVisible(true);
		return tabla.getSelectedElem();
	}
	
	private static <Y> Transformer<Y> toCommonTransformer(SingleColumnTransformer<Y> transformer)
	{
		return elem -> new String[] {transformer.transformSingle(elem)};
	}
	
	public TablaGenerica(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer, FilterPanel<T> filterPanel, Observable observable)
	{
		initialize(columnNames, transformer, obtainer, filterPanel);
	}
	
	public TablaGenerica(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer, Observable observable)
	{
		initialize(columnNames, transformer, obtainer, null);
	}
	
	public TablaGenerica(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer, FilterPanel<T> filterPanel)
	{
		initialize(columnNames, transformer, obtainer, filterPanel);
	}
	
	public TablaGenerica(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer)
	{
		initialize(columnNames, transformer, obtainer, null);
	}
	
	private void initialize(String[] columnNames, Transformer<T> transformer, Obtainer<T> obtainer, FilterPanel<T> filterPanel) 
	{
		this.listeners = new LinkedList<>();
		this.listListeners = new LinkedList<>();
		this.doubleClickListeners = new LinkedList<>();
		this.selectedElems = new LinkedList<>();
		
		this.transformer = transformer;
		this.obtainer = obtainer;
		this.columnNames = columnNames;
		
		table = new JTable();
		table.setBackground(Color.darkGray);
		table.setForeground(Color.WHITE);

		table.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseClicked(MouseEvent e)
			{
				if(e.getClickCount()%2 == 0)
					notifyDoubleClickListeners();
			}
		});
		
		table.getTableHeader().setReorderingAllowed(false);
		
		setElems(obtainer.obtain());
		
		scrollPaneFilter = new JScrollPane();
		hasPanel = new PanelHideAndShow(scrollPaneFilter, BorderLayout.WEST);
		
		setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);
		add(scrollPane, BorderLayout.CENTER);
		add(hasPanel, BorderLayout.EAST);
		
		setFilterPanel(filterPanel);
	}
	
	public void setFilterPanel(FilterPanel<T> filterPanel)
	{
		filter = filterPanel;
		hasPanel.setVisible(filter!=null);
		scrollPaneFilter.setViewportView(filterPanel);
		
		if(filter!=null)
			for(FilterComponent<T> filterComponent : filter.getFilterComponents())
				filterComponent.addListener(this);
	}
	
	@Override
	public void invalidated(Observable observable)
	{
		setElems(obtainer.obtain());
	}
	
	public List<T> getElems() 
	{
		return elems;
	}
	
	public void setElems(List<T> elems)
	{
		this.elems = elems;
		filterElems();
	}
	
	public void filterElems()
	{
		filteredElems = new LinkedList<>();
		if(filter == null)
		{
			filteredElems.addAll(elems);
		} else {
			for(T elem : elems)
				if(filter.fits(elem))
					filteredElems.add(elem);
		}
		fillTable();
	}

	private void fillTable()
	{
		int index = filteredElems.indexOf(selectedElem);
		
		Object[][] data = new Object[filteredElems.size()][columnNames.length];
		
		for(int row = 0; row < filteredElems.size(); row++)
		{
			Object[] transformed = transformer.transform(filteredElems.get(row));
			
			if(columnNames.length != transformed.length)
				throw new IndexOutOfBoundsException("The transformer yields a " + transformed.length + " sized array, while the columnNames provided has a lenght of " + columnNames.length);
			
			for(int col = 0; col < columnNames.length; col++)
			{
				data[row][col] = transformed[col];
			}
		}
		
		table.setModel(new DefaultTableModel(data, columnNames) 
		{
			private static final long serialVersionUID = -3145800686074789415L;
			@Override
			public boolean isCellEditable(int row, int column){return false;}
		});
		
		//Si el seleccionado todavía está en la tabla, lo selecciono nuevamente
		if(index!=-1)
			table.addRowSelectionInterval(index, index);

		table.getSelectionModel().addListSelectionListener( 
				e -> {
					if(table.getSelectedRow() != -1)
						setSelectedElem(filteredElems.get(table.getSelectedRow()));
					else
						setSelectedElem(null);
					
					setSelectedList(table.getSelectedRows());
					});
	}

	public T getSelectedElem()
	{
		return selectedElem;
	}

	public List<T> getSelectedElems()
	{
		return selectedElems;
	}

	public void setSelectedElem(T selectedElem)
	{
		this.selectedElem = selectedElem;
		notifySelectionListeners();
	}
	
	public void setSelectedList(int... is) 
	{
		selectedElems.clear();
		for(int i : is)
			selectedElems.add(filteredElems.get(i));
		notifyListSelectionListeners();
	}
	
	private void notifySelectionListeners()
	{
		for(SelectionListener<T> listener : listeners)
			listener.selectionChanged(getSelectedElem());
	}
	
	private void notifyListSelectionListeners()
	{
		for(ListSelectionListener<T> listener : listListeners)
			listener.selectionChanged(getSelectedElems());
	}
	
	private void notifyDoubleClickListeners() 
	{
		for(DoubleClickListener listener : doubleClickListeners)
			listener.notifyDoubleClick();
	}
	
	public void addSelectionListener(SelectionListener<T> listener)
	{
		listeners.add(listener);
	}
	
	public void addListSelectionListener(ListSelectionListener<T> listener) 
	{
		listListeners.add(listener);
	}
	
	public void addDoubleClickListener(DoubleClickListener listener) 
	{
		doubleClickListeners.add(listener);
	}
	
	public JTable getTable()
	{
		return table;
	}
	
	public FilterPanel<T> getFilterPanel()
	{
		return filter;
	}
}
