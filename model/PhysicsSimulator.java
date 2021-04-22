package simulator.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	double dt;
	public ForceLaws FL;
	double t;
	List<Body> bodies;
	List<SimulatorObserver> observers;
	
	public PhysicsSimulator(double _dt, ForceLaws _fl) throws IllegalArgumentException{
		t = 0.0;
		bodies = new ArrayList<>();
		observers = new ArrayList<>();
		setDeltaTime(_dt);
		setForceLaws(_fl);
		reset();
	}
	
	public void reset () {
		bodies.clear();
		
		for (SimulatorObserver o : observers)
			o.onReset(bodies, t, dt, FL.toString());
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
		
		for (SimulatorObserver o : observers)
			o.onAdvance(bodies, t);
	}
	
	public void addBody(Body b) 
			throws IllegalArgumentException{
		Iterator<Body> it = bodies.listIterator();
		while(it.hasNext()) {
			Body body = it.next();
			if(body.equals(b))
				throw new IllegalArgumentException();
		}
		bodies.add(b);
		
		for (SimulatorObserver o : observers)
			o.onBodyAdded(bodies, b);
	}
	
	public void addObserver(SimulatorObserver o) 
			throws IllegalArgumentException{
		Iterator<SimulatorObserver> it = observers.listIterator();
		while(it.hasNext()) {
			SimulatorObserver so = it.next();
			if(so.equals(o))
				throw new IllegalArgumentException();
		}
		observers.add(o);
		
		o.onRegister(bodies, t, dt, FL.toString());
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
	
	public void setDeltaTime(double _dt) {
		
		if(_dt <= 0)
			throw new IllegalArgumentException();
		
		dt = _dt;
		
		for (SimulatorObserver o : observers)
			o.onDeltaTimeChanged(dt);
	}
	
	public void setForceLaws(ForceLaws forceLaws)
			throws IllegalArgumentException{
		if(forceLaws == null)
			throw new IllegalArgumentException();
		
		FL = forceLaws;
		
		for (SimulatorObserver o : observers)
			o.onForceLawsChanged(FL.toString());
	}
	
	public String toString() {
		return getState().toString();
	}
}
