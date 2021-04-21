package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
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
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
		_ctrl.addObserver(this);
		}
	
	//GUI initialization
	private void initGUI() {
		this.setLayout(new BorderLayout());
		JToolBar panel = new JToolBar();
		this.add(panel);
		
		//button to choose bodies files
		JButton files_button = new JButton();
		files_button.setIcon(loadImage("resources/icons/open.png"));
		files_button.setToolTipText("Load bodies file into the editor");
		panel.add(files_button);
		
		
		//button to choose force law
		JButton forcelaws_button = new JButton();
		forcelaws_button.setIcon(loadImage("resources/icons/physics.png"));
		forcelaws_button.setToolTipText("Choose the applied force law");
		panel.add(forcelaws_button);
		
		
		//run button
		JButton run_button = new JButton();
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.setToolTipText("Runs the simulator");
		panel.add(run_button);
		
		
		//stop button
		JButton stop_button = new JButton();
		stop_button.setIcon(loadImage("resources/icons/stop.png"));
		stop_button.setToolTipText("Stops the simulator");
		panel.add(stop_button);
		
		//steps label
		JLabel stepsLabel = new JLabel("Steps: ");
		panel.add(stepsLabel);
		
		//steps input
		JSpinner stepsSpinner = new JSpinner();
		stepsSpinner.setPreferredSize(new Dimension(70, 10));
		panel.add(stepsSpinner);
		
		//delta time label
		JLabel dtimeLabel = new JLabel("Delta-Time: ");
		panel.add(dtimeLabel);
		
		//delta time input
		JTextField dtimevalue = new JTextField();
		panel.add(dtimevalue);
		
		//label used to generate space between components
		JLabel space = new JLabel("                                                                                                                                                                       ");
		panel.add(space);
		
		//exit button
		JButton exit_button = new JButton();
		exit_button.setIcon(loadImage("resources/icons/exit.png"));
		exit_button.setToolTipText("Closes the simulator");
		panel.add(exit_button);
		
		
		
		//files button listener instantiation
		FilesButtonManager files_funct = new FilesButtonManager(_ctrl);
		files_button.addActionListener(files_funct);
		
		//force law button listener instantiation
		ForceLawsButtonManager forcelaws_funct = new ForceLawsButtonManager(this, _ctrl);
		forcelaws_button.addActionListener(forcelaws_funct);
		
		//delta time text field listener instantiation
		DtimeTextFieldManager dListener = new DtimeTextFieldManager(dtimevalue);
		dtimevalue.addActionListener(dListener);
		
		//steps spinner listener instantiation
		StepsSpinnerManager spinner_funct = new StepsSpinnerManager(stepsSpinner);
		stepsSpinner.addChangeListener(spinner_funct);
		
		//run button listener instantiation
		RunButtonManager run_funct = new RunButtonManager(files_button, forcelaws_button, exit_button, _stopped, _ctrl, dListener);
		run_button.addActionListener(run_funct);
		
		//stop button listener instantiation
		StopButtonManager stop_funct = new StopButtonManager(files_button, forcelaws_button, exit_button);
		stop_button.addActionListener(stop_funct);
		
		//exit button listener instantiation
		ExitButtonManager exit_funct = new ExitButtonManager(this);
		exit_button.addActionListener(exit_funct);
		
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
		}
	
	class FilesButtonManager implements ActionListener{
		
		Controller ctrl;
		
		public FilesButtonManager(Controller ctrl) {
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
	
	class ForceLawsButtonManager implements ActionListener{

		ControlPanel ctrlpnl;
		ForceLawsDialog forcelawsDialog;
		Controller ctrl;

		ForceLawsButtonManager(ControlPanel ctrlpnl, Controller ctrl){
			this.ctrlpnl = ctrlpnl;
			this.ctrl = ctrl;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(forcelawsDialog == null) {
				forcelawsDialog = new ForceLawsDialog((Frame) SwingUtilities.getWindowAncestor(ctrlpnl), ctrl);
			}
			forcelawsDialog.open();
		}

	}

	private class DtimeTextFieldManager implements ActionListener {
		
		int dt = 0;
		JTextField dtime;

		public DtimeTextFieldManager(JTextField dtime) {
			this.dtime = dtime;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			dt = Integer.parseInt(dtime.getText());
			
		}
		
		public int getDt() {
			return dt;
		}
		
	}
	
	private class StepsSpinnerManager implements ChangeListener{

		int steps = 0;
		JSpinner spinner;
		
		StepsSpinnerManager(JSpinner spinner){
			this.spinner = spinner;
		}
		@Override
		public void stateChanged(ChangeEvent e) {
			steps = (int) spinner.getValue();
		}
		
		public int getSteps() {
			return steps;
		}
	}
	
	private class RunButtonManager implements ActionListener{
		
		JButton filesButton;
		JButton flButton;
		JButton exitButton;
		boolean stopped;
		Controller ctrl;
		DtimeTextFieldManager dListener;
		
		RunButtonManager(JButton filesButton, JButton flButton, JButton exitButton, boolean stopped, Controller ctrl, DtimeTextFieldManager dListener){
			this.filesButton = filesButton;
			this.flButton = flButton;
			this.exitButton = exitButton;
			this.stopped = stopped;
			this.ctrl = ctrl;
			this.dListener = dListener;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			stopped = false;
			
			ctrl.setDeltaTime(dListener.getDt());
			ctrl.printDT();
			filesButton.setEnabled(false);
			flButton.setEnabled(false);
			exitButton.setEnabled(false);		
		}
		
	}
	
	private class StopButtonManager implements ActionListener{

		JButton filesButton;
		JButton flButton;
		JButton exitButton;
		
		StopButtonManager(JButton filesButton, JButton flButton, JButton exitButton){
			this.filesButton = filesButton;
			this.flButton = flButton;
			this.exitButton = exitButton;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			filesButton.setEnabled(true);
			flButton.setEnabled(true);
			exitButton.setEnabled(true);;
			
		}
		
	}

	class ExitButtonManager implements ActionListener{
		
		ControlPanel ctrlpnl;
		
		
		ExitButtonManager(ControlPanel ctrlpnl){
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
