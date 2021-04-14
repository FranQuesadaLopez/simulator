package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws{
	
	private double g;
	private Vector2D O;
	
	public MovingTowardsFixedPoint(Vector2D origen, double _g){
		g = _g;
		O = new Vector2D(origen);
	}

	@Override
	public void apply(List<Body> bs) {
		
		Iterator<Body> it = bs.listIterator();
		while(it.hasNext()) {
			Body b = it.next();
			b.addForce(O.minus(b._p.direction().scale(g * b._m)));
			b._a = b._f.scale(1/b._m);
		}
		
	}

}
