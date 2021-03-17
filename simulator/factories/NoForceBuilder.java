package simulator.factories;

import org.json.JSONObject;

import simulator.model.NoForce;

public class NoForceBuilder extends Builder<NoForce>{

	public NoForceBuilder() {
		typeTag = "nf";
		desc = "no se aplica ninguna fuerza";
	}
	
	@Override
	public NoForce createTheInstance(JSONObject info) {
		return new NoForce();
	}
}
