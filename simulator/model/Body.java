package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public class Body {
	
	String _id;
	double _m;
	Vector2D _f;
	Vector2D _v;
	Vector2D _p;
	Vector2D _a;
	
	public Body(String id, double m, Vector2D p, Vector2D v){
		_id = id;
		_m = m;
		_f = new Vector2D();
		_v = new Vector2D(v);
		_p = new Vector2D(p);
	}
	
	public String getId() {
		return _id;
	}
	
	public Vector2D getVelocity() {
		return _v;// devuelve el vector de velocidad.
	}
	
	public Vector2D getForce() {
		return _f;// devuelve el vector de fuerza.
	}
	
	public Vector2D getPosition() {
		return _p;
	}
	
	public double getMass(){
		return _m;// devuelve la masa del cuerpo.
	}
	void addForce(Vector2D f) {
		_f = _f.plus(f);
		//añade la fuerza f al vector de fuerza del cuerpo (usando el método plus de la clase Vector2D).
	}
	
	void resetForce() {
		_f = new Vector2D();
	}
	
	void move(double t) {
		if (_m == 0)
			_a = new Vector2D();
		else 
			_a = new Vector2D(_f.scale(1/_m));
		_p = _p.plus(_v.scale(t).plus(_a.scale(1/2).scale(t*t)));
		_v = _v.plus(_a.scale(t));
	}
	
	public JSONObject getState() {
		JSONObject state = new JSONObject();
		state.put("id", _id);
		state.put("m", _m);
		state.put("p", _p);
		state.put("v", _v);
		state.put("f", _f);
		return state;
		//devuelve la siguiente información del cuerpo en formato JSON (como JSONObject)
	}
	
	public String toString() {
		return getState().toString();
	}

}
