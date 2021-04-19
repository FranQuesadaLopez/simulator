package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	
	private double _g;
	private Vector2D _O;
	
	public MovingTowardsFixedPoint(Vector2D origen, double g){
		_g = g;
		_O = new Vector2D(origen);
	}

	@Override
	public void apply(List<Body> bs) {
		
		Iterator<Body> it = bs.listIterator();
		while(it.hasNext()) {
			Body b = it.next();
			b.addForce(_O.minus(b._p.direction().scale(_g * b._m)));
			b._a = b._f.scale(1/b._m);
		}
		
	}
	
	
	@Override
	public String toString() {
		return String.format("Moving towards %s with constant acceleration %f", _O.toString(), _g);
	}
}
