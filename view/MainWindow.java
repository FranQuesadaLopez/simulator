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
	//private StatusBar statusBar;
	//private BodiesTabel bodiesTable;
	//private Viewer viewer;
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
	//controlPanel.setPreferredSize(new Dimension(100, 100));
	mainPanel.add(controlPanel, BorderLayout.PAGE_START);
	//statusBar = new StatusBar(_ctrl);
	//statusBar.setPreferredSize();
	//mainPanel.add(statusBar, BorderLayout.PAGE_END);
	//bodiesTable = new BodiesTable(_ctrl);
	//bodiesTable.setPreferredSize();
	//viewer = new Viewer(_ctrl);
	//viewer.setPreferredSize();
	//JPanel bodies_viewer = new JPanel(new BoxLayout(bodiesTable, BoxLayout.Y_AXIS, viewer, BoxLayout.Y_AXIS));
	//mainPanel.add(bodies_viewer, BorderLayout.CENTER);
	
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.pack();
	this.setVisible(true);
	// Completa el método para construir la GUI
	}
	// Añade private/protected methods
}