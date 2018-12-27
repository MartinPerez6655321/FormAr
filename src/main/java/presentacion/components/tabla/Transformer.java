package presentacion.components.tabla;

public interface Transformer<T>
{
	public String[] transform(T elem);
}