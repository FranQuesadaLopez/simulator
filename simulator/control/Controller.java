package simulator.control;

import simulator.model.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.*;

import simulator.factories.*;

public class Controller {
	
	private PhysicsSimulator _ps;
	private Factory<Body> _b;
	
	public Controller (PhysicsSimulator ps, Factory<Body> b) {
		this._ps = ps;
		this._b = b;
	}
	
	public void loadBodies(InputStream in) {
		
		JSONObject jsonInput = new JSONObject(new JSONTokener(in));
		
		//TODO Crear cada cuerpo con la factoría
		
		//TODO Añadir el cuerpo al PysiscSimulator con addBody
	}
	
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
		
		for (int i = 0; i < n; ++i) {
			_ps.advance();
			p.println(_ps.toString());
			
			if(expOutNotNull && !cmp.equal(ja.getJSONObject(i), _ps.getState())) {
				String msg = new String(
						//Not sure how to write this
						"Simulation step: " + String.valueOf(i) + " = "
						+ "The states \n"
						+ ja.getJSONObject(i).toString()
						+ "and\n"
						+ _ps.getState().toString()
						+ "are diferent\n"
				);
				throw new DiferentStatesException(msg);
			}
		}

		p.println("]");
		p.println("}");
		
	}
	
}