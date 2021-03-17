package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLossingBody;

public class MassLosingBodyBuilder extends Builder<Body>{
	
	public MassLosingBodyBuilder() {
		typeTag = "mlb";
		desc = "mass losing body";
	}

	@Override
	public MassLossingBody createTheInstance(JSONObject info) throws IllegalArgumentException{
		if(info.getJSONArray("v").length() != 2 || info.getJSONArray("p").length() != 2)
			throw new IllegalArgumentException();
		Vector2D v = new Vector2D(info.getJSONArray("v").getDouble(0), info.getJSONArray("v").getDouble(1));
		Vector2D p = new Vector2D(info.getJSONArray("p").getDouble(0), info.getJSONArray("p").getDouble(1));
		return new MassLossingBody(info.getString("id"), info.getDouble("m"), p, v, info.getDouble("factor"), info.getDouble("freq"));
	}
	
	JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("id", "identificador del cuerpo");
		data.put("p", "vector posicion");
		data.put("v", "vector velocidad");
		data.put("m", "masa del cuerpo");
		data.put("freq", "(opcional) frecuencia de perdida de masa");
		data.put("factor", "(opcional) factor de perdida de masa");
		return data;
	}
		
}
