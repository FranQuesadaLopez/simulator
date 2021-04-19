package simulator.control;

import simulator.model.*;
import simulator.factories.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import java.util.List;

import org.json.*;

public class Controller {
	
	private PhysicsSimulator _ps;
	private Factory<Body> _b;
	private Factory<ForceLaws> _fl;
	
	public Controller (PhysicsSimulator ps, Factory<Body> b, Factory<ForceLaws> fl) {
		this._ps = ps;
		this._b = b;
		this._fl = fl;
	}
	
	public void reset() {
		this._ps.reset();
	}

	public void loadBodies(InputStream in) throws IllegalArgumentException{
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		for(int i = 0; i < jsonInput.getJSONArray("bodies").length(); ++i) {
			_ps.addBody(_b.createInstance(jsonInput.getJSONArray("bodies").getJSONObject(i)));
		}
	}
	
	public void setDeltaTime(double dt) {
		this._ps.setDeltaTime(dt);
	}
	
	public void setForceLaws(JSONObject info) {
		this._ps.setForceLaws(_fl.createInstance(info));
	}
	
	public List<JSONObject>getForceLawsInfo(){
		return this._fl.getInfo();
	}
	
	public void addObserver(SimulatorObserver o) {
		this._ps.addObserver(o);
	}
	
	//TODO 
	//Ejecuta n pasos del simulador sin escribir nada en consola.
	//O pasarle un OutputStream que no escriba
	public void run (int n, OutputStream out, InputStream expOut, StateComparator cmp)
		throws DiferentStatesException {
		
		JSONObject jo = null;
		JSONArray ja = null;
		boolean expOutNotNull = false;
		
		if(expOut != null) {
			 jo = new JSONObject (new JSONTokener(expOut));
			 ja = jo.getJSONArray("states");
			 expOutNotNull = true;
		}
		
		PrintStream p = new PrintStream(out);
		p.println("{");
		p.println("\"states\": [");
		p.println(_ps.toString());
		_ps.advance();
		
		for (int i = 1; i <= n; ++i) {
			p.println("," + _ps.toString());
			
			if(expOutNotNull && !cmp.equal(ja.getJSONObject(i), _ps.getState())) {
				String msg = new String(
						"Simulation step: " + String.valueOf(i) + " = "
						+ "The states \n"
						+ ja.getJSONObject(i).toString()
						+ "and\n"
						+ _ps.toString()
						+ "are diferent\n"
				);
				throw new DiferentStatesException(msg);
			}
			_ps.advance();
		}

		p.println("]");
		p.println("}");

	}

}