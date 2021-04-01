package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	
	private double G;
	
	public NewtonUniversalGravitation(double _G){
		G = _G;
	}
	
	@Override
	public void apply(List<Body> bs) {
		Iterator<Body> it_i = bs.listIterator();
		while(it_i.hasNext()) {
			Body b1 = it_i.next();
			if(b1.getMass() == 0.0) {
				b1._v = new Vector2D();
				b1._a = new Vector2D();
			}
			else {
				Iterator<Body> it_j = bs.listIterator();
				while(it_j.hasNext()) {
					Body b2 = it_j.next();
					if(b1._id != b2._id) 
						b1.addForce(calculateForceVector(b1, b2));	
				}
				b1._a = b1._f.scale(1/b1._m);
			}
		}
	}
	
	private Vector2D calculateForceVector(Body b1, Body b2) {
		if(b2._p.distanceTo(b1._p) == 0)
			return new Vector2D();
		double module = G * b1._m * b2._m / (b2._p.distanceTo(b1._p) * b2._p.distanceTo(b1._p));
		return ((b2._p.minus(b1._p)).direction()).scale(module);
	}

}
