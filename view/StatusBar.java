package simulator.view;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {
	
	private JLabel _currTime;
	private JLabel _currLaws;
	private JLabel _numOfBodies;
	
	private double _time;
	private int _nB;
	private String _fd;
	
	
	StatusBar(Controller ctrl) {
		initGUI();
		ctrl.addObserver(this);
	} 
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder( BorderFactory.createBevelBorder( 1 ));
	
		_currTime = new JLabel(String.format("Time: %f", _time));
		this.add(_currTime);
		
		_numOfBodies = new JLabel(String.format("Bodies: %d", _nB));
		this.add(_numOfBodies);
		
		_currLaws = new JLabel(String.format("Laws: %s", _fd));
		this.add(_currLaws);
	}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_time = time;
		_nB = bodies.size();
		_fd = fLawsDesc;
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		_time = time;
		_nB = bodies.size();
		_fd = fLawsDesc;
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		_nB = bodies.size();
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		_time = time;
	}

	@Override
	public void onDeltaTimeChanged(double dt) {}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		_fd = fLawsDesc;
	}
}
