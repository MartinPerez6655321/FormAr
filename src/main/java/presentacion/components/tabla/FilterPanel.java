package presentacion.components.tabla;

import java.awt.Dimension;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import presentacion.components.tabla.filterimpl.Filter;
import presentacion.components.tabla.filterimpl.TextFieldFilter;

public class FilterPanel<T> extends JPanel
{
	private static final long serialVersionUID = -527880210814914166L;
	
	private JButton clearButton;
	private List<JLabel> labels;
	private transient List<FilterComponent<T>> filterComponents;

	public FilterPanel(String filterName, FilterComponent<T> filter) 
	{
		List<String> filterNames = new LinkedList<>();
		filterNames.add(filterName);
		List<FilterComponent<T>> filters = new LinkedList<>();
		filters.add(filter);
		
		initialize(filterNames, filters);
	}
	
	public FilterPanel(String[] filterNames, FilterComponent<T>[] filters)
	{
		initialize(Arrays.asList(filterNames), Arrays.asList(filters));
	}
	
	public FilterPanel(List<String> filterNames, List<FilterComponent<T>> filters)
	{
		initialize(filterNames, filters);
	}
	
	private void initialize(List<String> filterNames, List<FilterComponent<T>> filters) 
	{
		clearButton = new JButton("Limpiar filtros");
		clearButton.addActionListener(e -> clearFilters());
		
		if(filterNames.size() != filters.size())
			throw new IndexOutOfBoundsException("The lists are not the same size!");
		
		this.filterComponents = filters;
		this.labels = new LinkedList<>();
		
		for(int i = 0; i < filterNames.size(); i++)
		{
			JLabel lbl = new JLabel(filterNames.get(i));
			
			labels.add(lbl);
			add(lbl);
			add(filterComponents.get(i).getComponent());
		}
		
		add(clearButton);
		setPreferredSize(new Dimension(calculateWidth(), calculateHeight()));
	}
	
	private int calculateHeight() 
	{
		int ret = 0;

		for(FilterComponent<T> c : filterComponents)
			ret = ret + c.getComponent().getPreferredSize().height;
		for(JLabel lbl : labels)
			ret = ret + lbl.getPreferredSize().height;
		ret = ret + clearButton.getPreferredSize().height;
		ret = ret + labels.size() * 10;
		
		return ret;
	}
	
	private int calculateWidth() 
	{
		int ret = 0;

		for(FilterComponent<T> c : filterComponents)
			ret = Integer.max(ret, c.getComponent().getPreferredSize().width);
		
		return ret;
	}
	
	public boolean fits(T elem)
	{
		boolean ret = true;
		for(FilterComponent<T> filter : filterComponents)
			ret = ret && filter.fits(elem);
		return ret;
	}
	
	public List<FilterComponent<T>> getFilterComponents()
	{
		return filterComponents;
	}
	
	public int filtersSize()
	{
		return filterComponents.size();
	}
	
	public void clearFilters() 
	{
		for(FilterComponent<T> fc : filterComponents)
			fc.setFilter(null);
	}
	
	public static <T> FilterPanel<T> stringFilterPanel(String[] filterNames, Transformer<T> filterElements)
	{
		return createFilterPanel(stringFilters(filterNames, filterElements));
	}
	
	public static <T> List<Filter<T>> stringFilters(String[] filterNames, Transformer<T> filterElements)
	{
		List<Filter<T>> ret = new LinkedList<>();

		for(int i = 0; i < filterNames.length; i++)
		{
			final int elementIndex = i; 
			ret.add(new Filter<>(filterNames[i], new TextFieldFilter<>(elem -> new String[] { filterElements.transform(elem)[elementIndex] })));
		}
		
		return ret;
	}
	
	@SafeVarargs
	public static <T> FilterPanel<T> createFilterPanel(Filter<T>... filters)
	{
		return createFilterPanel(Arrays.asList(filters));
	}
	
	@SafeVarargs
	public static <T> FilterPanel<T> createFilterPanel(List<Filter<T>>... filters)
	{
		List<String> filterNamesList = new LinkedList<>();
		List<FilterComponent<T>> filterComponents = new LinkedList<>();
		
		for(List<Filter<T>> filterList : filters)
			for(Filter<T> filter : filterList)
			{
				filterNamesList.add(filter.getLabel());
				filterComponents.add(filter.getFilterComponent());
			}
		
		return new FilterPanel<>(filterNamesList, filterComponents);
	}
}
