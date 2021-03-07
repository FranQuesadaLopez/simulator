package simulator.model;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	double dt;
	ForceLaws FL;
	double t;
	List<Body> bodies;
	
	public PhysicsSimulator(String _dt, ForceLaws _fl) throws IllegalArgumentException{
		try {
			Double.parseDouble(_dt);
		}
		catch(NumberFormatException nfe) {
			throw new IllegalArgumentException();
		}
		dt = Double.parseDouble(_dt);
		if(_fl == null)
			throw new IllegalArgumentException();
		FL = _fl;
		t = 0.0;
	}
	
	public void advance() {
		Iterator<Body> it = bodies.listIterator();
		while(it.hasNext()) {
			Body b = it.next();
			b.resetForce();
		}
		FL.apply(bodies);
		while(it.hasNext()) {
			Body b = it.next();
			b.move(dt);
			t += dt;
		}
	}
	
	public void addBody(Body b) throws IllegalArgumentException{
		Iterator<Body> it = bodies.listIterator();
		while(it.hasNext()) {
			Body body = it.next();
			if(body._id == b._id)
				throw new IllegalArgumentException();
		}
		bodies.add(b);
	}
	
	public JSONObject getState() {
		Iterator<Body> it = bodies.listIterator();
		JSONArray b_states = new JSONArray();
		while(it.hasNext()) {
			Body b = it.next();
			b_states.put(b.getState());
		}
		JSONObject state = new JSONObject();
		state.put("time", t);
		state.put("bodies", b_states);
		return state;
	}
	
	public String toString() {
		return getState().toString();
	}
}
