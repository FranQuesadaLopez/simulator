package simulator.model;

import simulator.misc.Vector2D;

public class MassLossingBody extends Body{
	
	private double _lossFactor; // un número (double) entre 0 y 1 que representa el factor de pérdida de masa.
	private double _lossFrequency; // un número positivo (double) que indica el intervalo de tiempo (en segundos) después del cual el objeto pierde masa.
	private double c;

	public MassLossingBody(String id, double m, Vector2D p, Vector2D v, double lossFactor, double lossFrequency) {
		super(id, m, p, v);
		_lossFactor = lossFactor;
		_lossFrequency = lossFrequency;
	}
	
	public void move(double t) {
		super.move(t);
		c += t;
		if(c >= _lossFrequency) {
			c = 0;
			_m *= (1-_lossFactor);
		}
	}

}
