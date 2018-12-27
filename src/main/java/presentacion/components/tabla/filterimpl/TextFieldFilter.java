package presentacion.components.tabla.filterimpl;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import presentacion.components.tabla.FilterComponent;
import presentacion.components.tabla.TablaGenerica;
import presentacion.components.tabla.Transformer;

public class TextFieldFilter<T> extends JTextField implements FilterComponent<T>
{
	private static final long serialVersionUID = -7859160127492394988L;
	private transient Transformer<T> transformer;
	
	public TextFieldFilter(Transformer<T> transformer)
	{
		this.transformer = transformer;
		setPreferredSize(new Dimension(200, 25));
	}
	
	@Override
	public boolean fits(T elem)
	{
		String text = getText().trim().toLowerCase();
		if(text.isEmpty())
			return true;
		else
		{
			boolean ret = false;
			String[] transformed = transformer.transform(elem);
			for(String s : transformed)
			{
				if(s != null)
					ret = ret || s.toLowerCase().contains(text);
			}
			return ret;
		}
	}

	@Override
	public Component getComponent()
	{
		return this;
	}

	@Override
	public void addListener(TablaGenerica<T> tabla)
	{
		getDocument().addDocumentListener(new DocumentListener()
		{
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				tabla.filterElems();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				tabla.filterElems();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e)
			{
				tabla.filterElems();
			}
		});
	}

	@Override
	public void setFilter(Object filter)
	{
		setText(filter == null? "" : String.valueOf(filter));
	}
}
