package presentacion.components.tabla.filterimpl;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import org.jdesktop.swingx.autocomplete.ObjectToStringConverter;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import presentacion.components.tabla.FilterComponent;
import presentacion.components.tabla.Getter;
import presentacion.components.tabla.Obtainer;
import presentacion.components.tabla.SingleColumnTransformer;
import presentacion.components.tabla.TablaGenerica;

public class ComboBoxFilter<T, Y> extends JComboBox<Y> implements FilterComponent<T>, InvalidationListener
{
	private static final long serialVersionUID = 3030034274442352078L;
	private transient Obtainer<Y> obtainer; 
	private transient Getter<T, Y> getter;
	private transient SingleColumnTransformer<Y> singleTransformer;
	
	public ComboBoxFilter(Obtainer<Y> obtainer, Getter<T, Y> getter, SingleColumnTransformer<Y> transformer, Observable observable)
	{
		initialize(obtainer, getter, transformer);
		observable.addListener(this);
	}
	
	public ComboBoxFilter(Obtainer<Y> obtainer, Getter<T, Y> getter, Observable observable)
	{
		initialize(obtainer, getter, null);
		observable.addListener(this);
	}
	
	public ComboBoxFilter(Obtainer<Y> obtainer, Getter<T, Y> getter, SingleColumnTransformer<Y> transformer)
	{
		initialize(obtainer, getter, transformer);
	}
	
	public ComboBoxFilter(Obtainer<Y> obtainer, Getter<T, Y> getter)
	{
		initialize(obtainer, getter, null);
	}
	
	private void initialize(Obtainer<Y> obtainer, Getter<T, Y> getter, SingleColumnTransformer<Y> transformer)
	{
		this.obtainer = obtainer;
		this.getter = getter;
		
		if(transformer == null)
			this.singleTransformer = String::valueOf;
		else
			this.singleTransformer = transformer;
		
		setRenderer(new DefaultListCellRenderer()
		{
			private static final long serialVersionUID = -216161604379430873L;

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus)
			{
				if(value != null)
					return super.getListCellRendererComponent(list, singleTransformer.transformSingle((Y)value), index, isSelected, cellHasFocus);
				else
					return super.getListCellRendererComponent(list, " ", index, isSelected, cellHasFocus);
			}
		});
		setPreferredSize(new Dimension(200, 25));
		update();
		
		AutoCompleteDecorator.decorate(this, new ObjectToStringConverter()
		{
			@Override
			public String getPreferredStringForItem(Object arg0)
			{
				return arg0==null? null : singleTransformer.transformSingle((Y)arg0);
			}
		});
	}
	
	@Override
	public boolean fits(T elem)
	{
		return getSelectedItem() == null || (getter.getValue(elem) != null && getter.getValue(elem).equals(getSelectedItem()));
	}

	@Override
	public Component getComponent()
	{
		return this;
	}

	@Override
	public void addListener(TablaGenerica<T> tabla)
	{
		addActionListener(e -> tabla.filterElems());
	}

	@Override
	public void invalidated(Observable observable)
	{
		update();
	}

	private void update()
	{
		Object selected = getSelectedItem();
		
		removeAllItems();
		
		List<Y> elems = obtainer.obtain();
		addItem(null);
		for(Y elem : elems)
			addItem(elem);
		
		if(elems.contains(selected))
			setSelectedIndex(elems.indexOf(selected) + 1);
	}

	@Override
	public void setFilter(Object filter)
	{
		setSelectedItem(filter == null? "" : singleTransformer.transformSingle((Y)filter));
	}
}
