package presentacion.components.tabla;

import java.awt.Component;

public interface FilterComponent<T>
{
	public boolean fits(T elem);
	public Component getComponent();
	public void addListener(TablaGenerica<T> tabla);
	public void setFilter(Object filter);
}
