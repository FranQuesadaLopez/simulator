package simulator.view;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	
	private static final long serialVersionUID = 1L;
	private StatusBar _statusBar;
	private BodiesTable _bodiesTable;
	private Viewer _viewer;
	Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		mainPanel.setPreferredSize(new Dimension(1000, 600));
		ControlPanel controlPanel = new ControlPanel(_ctrl);
		controlPanel.setPreferredSize(new Dimension(100, 100));
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		_statusBar = new StatusBar(_ctrl);
		_statusBar.setPreferredSize(new Dimension(20,100));
		mainPanel.add(_statusBar, BorderLayout.PAGE_END);
		//_bodiesTable = new BodiesTable(_ctrl);
		//_bodiesTable.setPreferredSize(new Dimension(100, 100));
		_viewer = new Viewer(_ctrl);
		//_viewer.setPreferredSize(new Dimension(100, 100));
		JPanel bodies_viewer = new JPanel();
		bodies_viewer.setLayout(new BoxLayout(bodies_viewer, BoxLayout.Y_AXIS));
		//bodies_viewer.add(_bodiesTable);
		bodies_viewer.add(_viewer);
		mainPanel.add(bodies_viewer, BorderLayout.CENTER);
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	// Completa el método para construir la GUI
	}
	// Añade private/protected methods
}
