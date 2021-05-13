package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

import org.json.JSONException;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ControlPanel extends JPanel implements SimulatorObserver{
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private boolean _stopped;

	private JButton files_button;
	private JButton forcelaws_button;
	private JButton run_button;
	private JButton stop_button;
	private JButton exit_button;
	private JLabel stepsLabel;
	private JSpinner stepsSpinner;
	private JLabel dtimeLabel;
	private JTextField dtimeTextField;
	private JLabel space;
	private int steps;
	private double dt;
	private Frame parent;

	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		dt = 0;
		parent = (Frame) SwingUtilities.getWindowAncestor(this);
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
		files_button = new JButton();
		files_button.setIcon(loadImage("resources/icons/open.png"));
		files_button.setToolTipText("Load bodies file into the editor");
		panel.add(files_button);
		
		
		//button to choose force law
		forcelaws_button = new JButton();
		forcelaws_button.setIcon(loadImage("resources/icons/physics.png"));
		forcelaws_button.setToolTipText("Choose the applied force law");
		panel.add(forcelaws_button);
		
		
		//run button
		run_button = new JButton();
		run_button.setIcon(loadImage("resources/icons/run.png"));
		run_button.setToolTipText("Runs the simulator");
		panel.add(run_button);
		
		
		//stop button
		stop_button = new JButton();
		stop_button.setIcon(loadImage("resources/icons/stop.png"));
		stop_button.setToolTipText("Stops the simulator");
		panel.add(stop_button);
		
		//steps label
		stepsLabel = new JLabel("Steps: ");
		panel.add(stepsLabel);
		
		//steps input
		stepsSpinner = new JSpinner();
		stepsSpinner.setPreferredSize(new Dimension(70, 10));
		panel.add(stepsSpinner);
		
		//delta time label
		dtimeLabel = new JLabel("Delta-Time: ");
		panel.add(dtimeLabel);
		
		//delta time input
		dtimeTextField = new JTextField();
		dtimeTextField.setText(Double.toString(dt));
		panel.add(dtimeTextField);
		//label used to generate space between components
		space = new JLabel(String.format("%50s", " "));
		panel.add(space);
		
		//exit button
		exit_button = new JButton();
		exit_button.setIcon(loadImage("resources/icons/exit.png"));
		exit_button.setToolTipText("Closes the simulator");
		panel.add(exit_button);
		
		//error messages
		
		
		//files button listener instantiation
		FilesButtonManager files_funct = new FilesButtonManager();
		files_button.addActionListener(files_funct);
		
		//force law button listener instantiation
		ForceLawsButtonManager forcelaws_funct = new ForceLawsButtonManager(this);
		forcelaws_button.addActionListener(forcelaws_funct);
		
		//delta time text field listener instantiation
		DtimeTextFieldManager dListener = new DtimeTextFieldManager(this);
		dtimeTextField.addActionListener(dListener);
		
		//steps spinner listener instantiation
		StepsSpinnerManager spinner_funct = new StepsSpinnerManager();
		stepsSpinner.addChangeListener(spinner_funct);
		
		//run button listener instantiation
		RunButtonManager run_funct = new RunButtonManager(this);
		run_button.addActionListener(run_funct);
		
		//stop button listener instantiation
		StopButtonManager stop_funct = new StopButtonManager();
		stop_button.addActionListener(stop_funct);
		
		//exit button listener instantiation
		ExitButtonManager exit_funct = new ExitButtonManager(this);
		exit_button.addActionListener(exit_funct);
		
	}
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
		}
	
	class FilesButtonManager implements ActionListener{	
		
		
		
		@Override
		public void actionPerformed(ActionEvent e){
			JFileChooser filechooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON", "JSON");
			filechooser.setFileFilter(filter);
			int returnVal = filechooser.showOpenDialog(getParent());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				try {
					_ctrl.loadBodies(new FileInputStream(new File(filechooser.getSelectedFile().getPath())));
				} 
				catch(Exception e1) {
					JOptionPane.showMessageDialog(parent, "El archivo seleccionado no es valido", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	class ForceLawsButtonManager implements ActionListener{

		ControlPanel ctrlpnl;
		ForceLawsDialog forcelawsDialog;

		ForceLawsButtonManager(ControlPanel ctrlpnl){
			this.ctrlpnl = ctrlpnl;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(forcelawsDialog == null) {
				try {
				forcelawsDialog = new ForceLawsDialog((Frame) SwingUtilities.getWindowAncestor(ctrlpnl), _ctrl);
				}
				catch (Exception ex){
					JOptionPane.showMessageDialog(ctrlpnl, "Force law couldn't be built. Check the values and write them down again. Press 'h' for help.", "Error", JOptionPane.ERROR_MESSAGE);

				}
			}
			forcelawsDialog.open();
		}

	}

	private class DtimeTextFieldManager implements ActionListener{
		
		ControlPanel ctrlpnl;
		DtimeTextFieldManager(ControlPanel ctrlpnl){
			this.ctrlpnl = ctrlpnl;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
			dt = Double.parseDouble(dtimeTextField.getText());	
			}
			catch(NumberFormatException ex) {
				JOptionPane.showMessageDialog(ctrlpnl, "Delta Time must be a number", "Error", JOptionPane.ERROR_MESSAGE);

			}
		}	
	}
	
	private class StepsSpinnerManager implements ChangeListener{
		
		@Override
		public void stateChanged(ChangeEvent e) {
			steps = (int) stepsSpinner.getValue();
		}
	}
	
	private class RunButtonManager implements ActionListener{
		
		ControlPanel ctrlpnl;
		
		RunButtonManager(ControlPanel ctrlpnl){
			this.ctrlpnl = ctrlpnl;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			_stopped = false;	
			try {
				_ctrl.setDeltaTime(dt);
			}
			catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(ctrlpnl, "Delta Time must be a number", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch(IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(ctrlpnl, "Delta Time must be higher than 0", "Error", JOptionPane.ERROR_MESSAGE);
				_stopped = true;
			}
			
			files_button.setEnabled(false);
			forcelaws_button.setEnabled(false);
			exit_button.setEnabled(false);
			stepsSpinner.setEnabled(false);
			dtimeTextField.setEnabled(false);
			run_button.setEnabled(false);
			if(steps < 0) {
				JOptionPane.showMessageDialog(ctrlpnl, "Steps must be higher or equal to 0", "Error", JOptionPane.ERROR_MESSAGE);
				_stopped = true;
			}
			run_sim(steps);
		}
		
	}
	
	private class StopButtonManager implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			files_button.setEnabled(true);
			forcelaws_button.setEnabled(true);
			exit_button.setEnabled(true);
			stepsSpinner.setEnabled(true);
			dtimeTextField.setEnabled(true);
			run_button.setEnabled(true);
			_stopped = true;
			
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
	
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(0, new OutputStream() {
					@Override
					public void write(int b) throws IOException {};
				}
				, null, null);
			} catch (Exception e) {
				// Muestra el error con una ventana JOptionPane
				files_button.setEnabled(true);
				forcelaws_button.setEnabled(true);
				exit_button.setEnabled(true);
				stepsSpinner.setEnabled(true);
				dtimeTextField.setEnabled(true);
				run_button.setEnabled(true);
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater( new Runnable() {
				@Override
				public void run() {
					run_sim(n-1);
				} });
		} else {
			_stopped = true;
			files_button.setEnabled(true);
			forcelaws_button.setEnabled(true);
			exit_button.setEnabled(true);
			stepsSpinner.setEnabled(true);
			dtimeTextField.setEnabled(true);
			run_button.setEnabled(true);
		}
	}


	@Override
	public void onRegister(List<Body> bodies, double time, double _dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dtimeTextField.setText(Double.toString(_dt));
				dt = _dt;
			}
		});
		
	}

	@Override
	public void onReset(List<Body> bodies, double time, double _dt, String fLawsDesc) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dtimeTextField.setText(Double.toString(_dt));
				dt = _dt;
			}
		});
		
		
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
	public void onDeltaTimeChanged(double _dt) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				dtimeTextField.setText(Double.toString(_dt));
				dt = _dt;
			}
		});
		
	}

	@Override
	public void onForceLawsChanged(String fLawsDesc) {
		// TODO Auto-generated method stub
		
	}

}
