package presentacion.components.abm;

public interface Editor<T>
{
	public void crear();
	public void modificar(T elem);
	public void eliminar(T elem);
}
