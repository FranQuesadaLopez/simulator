package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;

public class EpsilonEqualStatesBuilder extends Builder<EpsilonEqualStates>{

	public EpsilonEqualStatesBuilder() {
		typeTag = "epseq";
		desc = "comparador de estados con modulo epsilon";
	}
	
	@Override
	public EpsilonEqualStates createTheInstance(JSONObject info) {
		double eps;
		if(info.has("eps"))
			eps = info.getDouble("eps");
		else
			eps = 0.0;
		return new EpsilonEqualStates(eps);
	}
	
	JSONObject createData() {
		JSONObject data = new JSONObject();
		data.put("eps", "(opcional) valor de epsilon");
		return data;
	}

}
