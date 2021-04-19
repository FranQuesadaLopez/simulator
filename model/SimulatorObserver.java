package simulator.model;
<<<<<<< HEAD
import java.util.List;

public interface SimulatorObserver {
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc);
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc);
	public void onBodyAdded(List<Body> bodies, Body b);
	public void onAdvance(List<Body> bodies, double time);
	public void onDeltaTimeChanged(double dt);
	public void onForceLawsChanged(String fLawsDesc);
=======

import java.util.List;

public interface SimulatorObserver {
	public void onRegister(List<Body> bodies, double time, double dt,
			String fLawsDesc);
	
			public void onReset(List<Body> bodies, double time, double dt,
			String fLawsDesc);
			
			public void onBodyAdded(List<Body> bodies, Body b);
			
			public void onAdvance(List<Body> bodies, double time);
			
			public void onDeltaTimeChanged(double dt);
			
			public void onForceLawsChanged(String fLawsDesc);
>>>>>>> 64ec497bf1d59879a1f3fa48f02f29205db619ae
}
