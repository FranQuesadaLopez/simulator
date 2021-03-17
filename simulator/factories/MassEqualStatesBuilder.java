package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;

public class MassEqualStatesBuilder extends Builder<MassEqualStates>{

	public MassEqualStatesBuilder() {
		typeTag = "masseq";
		desc = "comparador de estados por masas";
	}
	
	@Override
	public MassEqualStates createTheInstance(JSONObject info) {
		return new MassEqualStates();
	}

}
