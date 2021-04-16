package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private boolean _stopped;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		_stopped = true;
		initGUI();
		//_ctrl.addObserver(this);
		}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		this.add(panel, BorderLayout.WEST);
		JButton files_button = new JButton();
		files_button.setIcon(loadImage("resources/icons/open.png"));
		panel.add(files_button, BorderLayout.EAST);
		JButton forcelaws_button = new JButton();
		forcelaws_button.setIcon(loadImage("resources/icons/physics.png"));
		panel.add(forcelaws_button);
		JButton run_button = new JButton();
		run_button.setIcon(loadImage("resources/icons/run.png"));
		panel.add(run_button);
		JButton stop_button = new JButton();
		stop_button.setIcon(loadImage("resources/icons/stop.png"));
		panel.add(stop_button);
		JLabel stepsLabel = new JLabel("Steps: ");
		panel.add(stepsLabel);
		JSpinner stepsSpinner = new JSpinner();
		stepsSpinner.setPreferredSize(new Dimension(70, 27));
		panel.add(stepsSpinner);
		// TODO Auto-generated method stub
		
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
		}

	@Override
	public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBodyAdded(List<Body> bodies, Body b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvance(List<Body> bodies, double time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
