package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	double dt;
	ForceLaws FL;
	double t;
	List<Body> bodies;
	
	public PhysicsSimulator(double _dt, ForceLaws _fl) throws IllegalArgumentException{
		if(_dt < 0)
			throw new IllegalArgumentException();
		else
			dt = _dt;
		if(_fl == null)
			throw new IllegalArgumentException();
		FL = _fl;
		t = 0.0;
		bodies = new ArrayList<>();
	}
	
	public void advance() {
		Iterator<Body> it = bodies.listIterator();
		while(it.hasNext()) {
			Body b = it.next();
			b.resetForce();
		}
		FL.apply(bodies);
		Iterator<Body> it_ = bodies.listIterator();
		while(it_.hasNext()) {
			Body b = it_.next();
			b.move(dt);
		}
		t += dt;
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
