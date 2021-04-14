package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;

public class BasicBodyBuilder extends Builder<Body>{
	
	public BasicBodyBuilder() {
		typeTag = "basic";
		desc = "basic body";
	}

	@Override
	public Body createTheInstance(JSONObject info) throws IllegalArgumentException{	
		if(info.getJSONArray("v").length() != 2 || info.getJSONArray("p").length() != 2)
			throw new IllegalArgumentException();
		Vector2D v = new Vector2D(info.getJSONArray("v").getDouble(0), info.getJSONArray("v").getDouble(1));
		Vector2D p = new Vector2D(info.getJSONArray("p").getDouble(0), info.getJSONArray("p").getDouble(1));	
		return new Body(info.getString("id"), info.getDouble("m"), p, v);
	}
	
	JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "identificador del cuerpo");
		data.put("p", "vector posicion");
		data.put("v", "vector velocidad");
		data.put("m", "masa del cuerpo");
		return data;
	}
}
