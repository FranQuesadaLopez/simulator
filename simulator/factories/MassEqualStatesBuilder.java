package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator>{

	public MassEqualStatesBuilder() {
		typeTag = "masseq";
		desc = "comparador de estados por masas";
	}
	
	@Override
	public MassEqualStates createTheInstance(JSONObject info) {
		return new MassEqualStates();
	}

}
