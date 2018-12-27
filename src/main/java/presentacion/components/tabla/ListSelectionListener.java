package presentacion.components.tabla;

import java.util.List;

public interface ListSelectionListener<T>
{
	public void selectionChanged(List<T> selectedElements);
}
