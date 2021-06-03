package simulator.factories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONObject;

public class BuilderBasedFactory <T> implements Factory<T>{
	
	List<Builder<T>> _builders;
	List<JSONObject> info;
	List<JSONObject> _factoryElements;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		_builders = builders;
		info = new ArrayList<JSONObject>();
		Iterator<Builder<T>> it = _builders.listIterator();
		while(it.hasNext())
			info.add(it.next().getBuilderInfo());
	}

	@Override
	public T createInstance(JSONObject info) {
		ListIterator<Builder<T>> it = _builders.listIterator();
		while(it.hasNext()) {
			if(it.next().createInstance(info) != null) {
				return it.previous().createInstance(info);		
			}
		}
		throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());
		//return null;//lanzar excepción
	}

	@Override
	public List<JSONObject> getInfo() {	
		_factoryElements = Collections.unmodifiableList(info);
		return _factoryElements;
	}

}
