package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.control.Controller;

public class ForceLawsDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller ctrl;

	public ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, "Force Laws Selection", true);
		this.ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		JPanel helpPanel = new JPanel(new FlowLayout());
		mainPanel.add(helpPanel);
		JLabel helpMsg_1 = new JLabel("Select a force law and provide values for the parameters in the value column default values are used for (parameters with no value)");
		helpPanel.add(helpMsg_1, FlowLayout.LEFT);
		
		DefaultComboBoxModel<String> forcesBoxModel = new DefaultComboBoxModel<String>();
		List<JSONObject> fl_desc = ctrl.getForceLawsInfo();
		for(JSONObject fl: fl_desc) {
			forcesBoxModel.addElement(fl.getString("desc"));
		}
		JComboBox<String> forcesBox = new JComboBox<>(forcesBoxModel);
		
		ForceLawsTableModel _dataTableModel = new ForceLawsTableModel(forcesBox);
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;

			// we override prepareRenderer to resized rows to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		
		ComboBoxManager cbm = new ComboBoxManager(_dataTableModel);
		forcesBox.addActionListener(cbm);
		
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(tabelScroll);
		
		JPanel forcesPanel = new JPanel(new FlowLayout());
		mainPanel.add(forcesPanel);		
		JLabel forceOptions = new JLabel("Force Law: ");
		forceOptions.setAlignmentX(CENTER_ALIGNMENT);
		forcesPanel.add(forceOptions);
		
		
		forcesPanel.add(forcesBox);
		
		forcesBox.setPreferredSize(new Dimension(200, 20));
		
		JPanel ok_cancel_optionsPanel = new JPanel(new FlowLayout());
		mainPanel.add(ok_cancel_optionsPanel);
		JButton ok = new JButton("OK");
		ok_cancel_optionsPanel.add(ok);
		JLabel space = new JLabel("          ");
		ok_cancel_optionsPanel.add(space);
		JButton cancel = new JButton("Cancel");
		ok_cancel_optionsPanel.add(cancel);
		
		
		
		setPreferredSize(new Dimension(800, 500));
		pack();
		setVisible(false);
	}
	
	private class ComboBoxManager implements ActionListener{
		
		ForceLawsTableModel datatable;
		
		ComboBoxManager(ForceLawsTableModel datatable){
			this.datatable = datatable;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			datatable.writeData();
			
		}
		
	}
	
	private class ForceLawsTableModel extends AbstractTableModel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private String[] _header = { "Force", "Value", "Description" };
		String[][] _data;
		JComboBox<String> forcesBox;

		ForceLawsTableModel(JComboBox<String> forcesBox) {
			this.forcesBox = forcesBox;
			_data = new String[5][3];
			clear();
			writeData();
		}
		
		public void writeData() {
			clear();
			JSONObject option = null;
			for(JSONObject fl: ctrl.getForceLawsInfo()) {
				if(fl.getString("desc").equals(forcesBox.getSelectedItem())) {
					option = fl;
					break;
				}
			}
			JSONObject data = option.getJSONObject("data");
			if(option.getString("type").equals("nlug")) {	
				_data[0][2] = data.getString("G");
			}
			else {
				if(option.getString("type").equals("mtfp")) {
					_data[0][2] = data.getString("c");
					_data[1][2] = data.getString("g");
				}
			}
		}

		public void clear() {
			for (int i = 0; i < 5; i++)
				for (int j = 0; j < 3; j++)
					_data[i][j] = "";
			fireTableStructureChanged();
		}

		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		@Override
		public int getRowCount() {
			return _data.length;
		}

		@Override
		public int getColumnCount() {
			return _header.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == 1)
				return true;
			else
				return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return _data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			_data[rowIndex][columnIndex] = o.toString();
		}

		// Method getData() returns a String corresponding to a JSON structure
		// with column 1 as keys and column 2 as values.

		// This method return the coIt is important to build it as a string, if
		// we create a corresponding JSONObject and use put(key,value), all values
		// will be added as string. This also means that if users want to add a
		// string value they should add the quotes as well as part of the
		// value (2nd column).
		//
		public String getData() {
			StringBuilder s = new StringBuilder();
			s.append('{');
			for (int i = 0; i < _data.length; i++) {
				if (!_data[i][0].isEmpty() && !_data[i][1].isEmpty()) {
					s.append('"');
					s.append(_data[i][0]);
					s.append('"');
					s.append(':');
					s.append(_data[i][1]);
					s.append(',');
				}
			}

			if (s.length() > 1)
				s.deleteCharAt(s.length() - 1);
			s.append('}');

			return s.toString();
		}
	}
	
	public void open() {
		
		
		
		setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);
		setVisible(true);
	}
	
}
