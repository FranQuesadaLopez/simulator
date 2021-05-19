package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Vector2D;

public class NoForce implements ForceLaws{

	@Override
	public void apply(List<Body> bs) {
		Iterator<Body> it = bs.listIterator();
		while(it.hasNext()) {
			Body b = it.next();
			b._a = new Vector2D();
		}
	}
	
	@Override
	public String toString() {
		return "No Force";
	}
	
}
