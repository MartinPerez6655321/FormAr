package presentacion.components.listagenerica;

import java.awt.Component;

public interface ComponentFactory<T extends Component>
{
	public T create();
}
