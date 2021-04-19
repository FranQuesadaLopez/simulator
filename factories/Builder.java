package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	
	String typeTag;
	String desc;
	
	public T createInstance(JSONObject info) {
		if(info.getString("type").equals(typeTag))
			return createTheInstance(info.getJSONObject("data"));
		else
			return null;
	}
	public JSONObject getBuilderInfo() {
		JSONObject builderinfo = new JSONObject();
		builderinfo.put("type", typeTag);
		builderinfo.put("data", createData());
		builderinfo.put("desc", desc);
		return builderinfo;
	}
	JSONObject createData() {
		return new JSONObject();
	}
	public abstract T createTheInstance(JSONObject info);
}
