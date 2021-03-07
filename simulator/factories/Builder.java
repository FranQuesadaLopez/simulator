package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	
	String typeTag;
	String desc;
	public T createInstance(JSONObject info) {
		
	}
}
