package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		JToolBar panel = new JToolBar();
		this.add(panel);
		
		
		JButton files_button = new JButton();
		files_button.setIcon(loadImage("resources/icons/open.png"));
		files_button.setToolTipText("Load bodies file into the editor");
		panel.add(files_button);
		GestorBotonArchivos files_funct = new GestorBotonArchivos(_ctrl);
		files_button.addActionListener(files_funct);
		
		
		JButton forcelaws_button = new JButton();
		forcelaws_button.setIcon(loadImage("resources/icons/physics.png"));
		forcelaws_button.setToolTipText("Choose the applied force law");
		panel.add(forcelaws_button);
		GestorBotonLeyesFuerza forcelaws_funct = new GestorBotonLeyesFuerza();
		forcelaws_button.addActionListener(forcelaws_funct);
		
		
		JButton run_button = new JButton();
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.setToolTipText("Runs the simulator");
		panel.add(run_button);
		
		
		JButton stop_button = new JButton();
		stop_button.setIcon(loadImage("resources/icons/stop.png"));
		stop_button.setToolTipText("Stops the simulator");
		panel.add(stop_button);
		
		
		JLabel stepsLabel = new JLabel("Steps: ");
		panel.add(stepsLabel);
		
		
		JSpinner stepsSpinner = new JSpinner();
		stepsSpinner.setPreferredSize(new Dimension(70, 10));
		panel.add(stepsSpinner);
		
		
		JLabel dtimeLabel = new JLabel("Delta-Time: ");
		panel.add(dtimeLabel);
		
		
		JTextField dtimevalue = new JTextField();
		panel.add(dtimevalue);
		
		
		JLabel space = new JLabel("                                                                                                                                                                       ");
		panel.add(space);
		
		
		JButton exit_button = new JButton();
		exit_button.setIcon(loadImage("resources/icons/exit.png"));
		exit_button.setToolTipText("Closes the simulator");
		panel.add(exit_button);
		GestorBotonSalida exit_funct = new GestorBotonSalida(this);
		exit_button.addActionListener(exit_funct);
		// TODO Auto-generated method stub
		
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
		}
	
	class GestorBotonArchivos implements ActionListener{
		
		Controller ctrl;
		
		public GestorBotonArchivos(Controller ctrl) {
			this.ctrl = ctrl;
		}
		
		@Override
		public void actionPerformed(ActionEvent e){
			JFileChooser filechooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "JSON");
			filechooser.setFileFilter(filter);
			int returnVal = filechooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					ctrl.loadBodies(new FileInputStream(new File(filechooser.getSelectedFile().getPath())));
				} 
				catch(Exception e1) {	
				}
			}
		}

	}
	
	class GestorBotonLeyesFuerza implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	class GestorBotonSalida implements ActionListener{
		
		ControlPanel ctrlpnl;
		
		
		GestorBotonSalida(ControlPanel ctrlpnl){
			this.ctrlpnl = ctrlpnl;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
				Object[] options = {"Yes", "No"};
				int n = JOptionPane.showOptionDialog(ctrlpnl, "Are you sure?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
						options, options[1]);
				if(n == 0)
					System.exit(0);	
		}
		
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
