package observer;

import java.util.LinkedList;
import java.util.List;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

public class AbstractObservable implements Observable
{
	private List<InvalidationListener> listeners = new LinkedList<>();

	protected void invalidateObservers()
	{
		for(InvalidationListener listener : listeners)
		{
			listener.invalidated(this);
		}
	}
	
	@Override
	public void addListener(InvalidationListener listener)
	{
		listeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener)
	{
		listeners.remove(listener);
	}
}
