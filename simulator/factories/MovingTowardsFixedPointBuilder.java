package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<MovingTowardsFixedPoint>{

	public MovingTowardsFixedPointBuilder() {
		typeTag = "mtfp";
		desc = "movimiento hacia un punto fijo en el espacio";
	}
	
	@Override
	public MovingTowardsFixedPoint createTheInstance(JSONObject info) throws IllegalArgumentException{
		double g;
		Vector2D c;
		if(info.has("g"))
			g = info.getDouble("g");
		else
			g = 9.81;
		if(info.has("c")) {
			if(info.getJSONArray("c").length() != 2)
				throw new IllegalArgumentException();
			c = new Vector2D(info.getJSONArray("c").getDouble(0), info.getJSONArray("c").getDouble(1));
		}
		else
			c = new Vector2D(0, 0);
		return new MovingTowardsFixedPoint(c, g);
	}
	
	public JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("c", "(opcional) centro fijo");
		data.put("g", "(opcional) valor de la gravedad");
		return data;
	}

}
