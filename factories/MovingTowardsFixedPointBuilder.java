package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws>{

	public MovingTowardsFixedPointBuilder() {
		typeTag = "mtfp";
		desc = "Moving towards a fixed point";
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
		data.put("c", "the point towards wich bodies move"
				    + "(a json of numbers, e.g. [100.0, 50.0])");
		data.put("g", "the lenght of the acceleration vector (a number)");
		return data;
	}

}
