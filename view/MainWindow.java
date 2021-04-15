package simulator.view;


import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 1L;
	Controller _ctrl;
	public MainWindow(Controller ctrl) {
	super("Physics Simulator");
	_ctrl = ctrl;
	initGUI();
	}
	private void initGUI() {
	JPanel mainPanel = new JPanel(new BorderLayout());
	setContentPane(mainPanel);
	
	// Completa el método para construir la GUI
	}
	// Añade private/protected methods
}
