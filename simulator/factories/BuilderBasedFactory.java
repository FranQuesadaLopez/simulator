package simulator.factories;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.json.JSONObject;

public class BuilderBasedFactory <T> implements Factory<T>{
	
	List<Builder<T>> _builders;
	List<JSONObject> info;
	
	public BuilderBasedFactory(List<Builder<T>> builders) {
		_builders = builders;
		info = new ArrayList<JSONObject>();
	}

	@Override
	public T createInstance(JSONObject info) {
		ListIterator<Builder<T>> it = _builders.listIterator();
		while(it.hasNext()) {
			if(it.next().createInstance(info) != null) {
				return it.previous().createInstance(info);
			}
		}
		return null;
	}

	@Override
	public List<JSONObject> getInfo() {		
		Iterator<Builder<T>> it = _builders.listIterator();
		while(it.hasNext())
			info.add(it.next().getBuilderInfo());
		return info;
	}

}
