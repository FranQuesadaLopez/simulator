package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class BodiesTable extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable bodies_table;
	private BodiesTableModel bodies_model;
	private Controller ctrl;

	BodiesTable(Controller ctrl) {
		this.ctrl = ctrl;
		initGUI();
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				"Bodies",
				TitledBorder.LEFT, TitledBorder.TOP));
		this.add(new JScrollPane(bodies_table, 
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
	}
	
	private void initGUI() {
		bodies_model = new BodiesTableModel(ctrl);
		bodies_table = new JTable(bodies_model);
	}
	
	public class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {

		private static final long serialVersionUID = 1L;
		private List<Body> _bodies;
		private String[] _header = { "Id", "Mass", "Position", "Velocity", "Force" };
		
		BodiesTableModel(Controller ctrl) {
			_bodies = new ArrayList<>();
			ctrl.addObserver(this);
		}
		
		@Override
		public int getRowCount() {
			return _bodies.size();
			
		}
		
		@Override
		public int getColumnCount() {	
			return _header.length;
		}
		
		@Override
		public String getColumnName(int column) {
			return _header[column];
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Object s = null;
			switch (columnIndex) {
			case 0:
				s = _bodies.get(rowIndex).getId();
				break;
			case 1:
				s = _bodies.get(rowIndex).getMass();
				break;
			case 2:
				s = _bodies.get(rowIndex).getPosition();
				break;
			case 3:
				s = _bodies.get(rowIndex).getVelocity();
			case 4:
				s = _bodies.get(rowIndex).getForce();
			}
			return s;
		}
		
		// SimulatorObserver methods
		
				/*En los métodos como observador, cuando cambia el estado
			(por ejemplo en onAdvance, onRegister, onBodyAdded y
			onReset) es necesario en primer lugar actualizar el valor del
			campo _bodies y después llamar a
			fireTableStructureChanged() para notificar a la
			correspondiente JTable que hay cambios en la tabla (y por lo
			tanto es necesario redibujarla). Haz estas actualizaciones con
			invokeLater. */

		@Override
		public void onRegister(List<Body> bodies, double time, double dt, String fLawsDesc) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					_bodies = bodies;
					fireTableStructureChanged();
				}
			});
		}

		@Override
		public void onReset(List<Body> bodies, double time, double dt, String fLawsDesc) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					_bodies.clear();
					fireTableStructureChanged();
				}
			});
			
		}

		@Override
		public void onBodyAdded(List<Body> bodies, Body b) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					_bodies.add(b);
					fireTableStructureChanged();
				}
			});
			
		}

		@Override
		public void onAdvance(List<Body> bodies, double time) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					_bodies = bodies;
					fireTableStructureChanged();
				}
			});

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
}
