package presentacion.components.tabla.filterimpl;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import presentacion.components.tabla.FilterComponent;
import presentacion.components.tabla.TablaGenerica;

public class BetweenFilter<T> extends JPanel implements FilterComponent<T>
{
	private static final long serialVersionUID = -5804200212374561264L;
	private NumberTransformer<T> transformer;
	
	private JSpinner spnDesde;
	private JSpinner spnHasta;
	
	public BetweenFilter(NumberTransformer<T> numberTransformer)
	{
		transformer = numberTransformer;
		
		JLabel lblDesde = new JLabel("Desde");
		spnDesde = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnDesde.setPreferredSize(new Dimension(50, 25));
		JLabel lblHasta = new JLabel("Hasta");
		spnHasta = new JSpinner(new SpinnerNumberModel(100, 1, null, 1));
		spnHasta.setPreferredSize(new Dimension(50, 25));

		add(lblDesde);
		add(spnDesde);
		add(lblHasta);
		add(spnHasta);
	}
	
	@Override
	public boolean fits(T elem)
	{
		return transformer.getNumber(elem) <= (Integer)spnHasta.getValue() && transformer.getNumber(elem) >= (Integer)spnDesde.getValue();
	}
	
	@Override
	public Component getComponent()
	{
		return this;
	}
	
	@Override
	public void addListener(TablaGenerica<T> tabla)
	{
		spnDesde.addChangeListener(e -> tabla.filterElems());
		spnHasta.addChangeListener(e -> tabla.filterElems());
	}

	@Override
	public void setFilter(Object filter)
	{
		if(filter == null)
		{
			spnDesde.setValue(0);
			spnHasta.setValue(100);
		}
	}
}
