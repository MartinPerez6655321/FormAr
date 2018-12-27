package presentacion.components.tabla;

import java.util.List;

public interface Obtainer<T>
{
	public List<T> obtain();
}