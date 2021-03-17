package simulator.model;

import java.util.Iterator;
import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws{
	
	private double G = 6.67 * Math.pow(10, -11);
	
	public NewtonUniversalGravitation(double _G){
		G = _G;
	}
	
	@Override
	public void apply(List<Body> bs) {
		Iterator<Body> it_i = bs.listIterator();
		Iterator<Body> it_j = bs.listIterator();
		while(it_i.hasNext()) {
			Body b1 = it_i.next();
			if(b1.getMass() == 0.0) {
				b1._v = new Vector2D();
				b1._a = new Vector2D();
			}
			while(it_j.hasNext()) {
				Body b2 = it_j.next();
				if(it_i != it_j) 
					b1.addForce(calculateForceVector(b1, b2));	
			}
		}
	}
	
	private Vector2D calculateForceVector(Body b1, Body b2) {
		if(b1._p.distanceTo(b2._p) == 0)
			return new Vector2D();
		double module = G * b1._m * b2._m / b1._p.distanceTo(b2._p);
		return ((b2._p.minus(b1._p)).direction()).scale(module);
	}

}
