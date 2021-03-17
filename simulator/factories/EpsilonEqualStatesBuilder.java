package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator>{

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
