package presentacion.components.tabla.filterimpl;

import presentacion.components.tabla.FilterComponent;

public class Filter<T>
{
	private String label;
	private FilterComponent<T> filterComponent;
	
	public Filter(String label, FilterComponent<T> filter)
	{
		this.setLabel(label);
		this.setFilterComponent(filter);
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public FilterComponent<T> getFilterComponent()
	{
		return filterComponent;
	}

	public void setFilterComponent(FilterComponent<T> filter)
	{
		this.filterComponent = filter;
	}
}
