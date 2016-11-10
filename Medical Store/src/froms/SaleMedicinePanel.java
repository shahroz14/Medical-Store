package froms;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class SaleMedicinePanel extends JPanel {
	private JTextField textField;
	private JTextField amountTF;
	private JComboBox comboBox;
	private JLabel lblRate;
	private JSpinner spinner;
	private JTable table;
	MyTableModel tableModel;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public SaleMedicinePanel() throws ClassNotFoundException, SQLException {
		setLayout(null);
		
		JLabel lblSaleMedicine = new JLabel("Sale Medicine");
		lblSaleMedicine.setForeground(SystemColor.textHighlight);
		lblSaleMedicine.setFont(new Font("Verdana", Font.BOLD, 13));
		lblSaleMedicine.setBounds(60, 11, 111, 23);
		add(lblSaleMedicine);
		
		JLabel lblCustomerName = new JLabel("Customer Name");
		lblCustomerName.setForeground(new Color(0, 0, 0));
		lblCustomerName.setBackground(SystemColor.desktop);
		lblCustomerName.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblCustomerName.setBounds(60, 70, 111, 14);
		add(lblCustomerName);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		textField.setBounds(209, 63, 166, 25);
		add(textField);
		textField.setColumns(10);
		
		JLabel lblMedicineName = new JLabel("Medicine Name");
		lblMedicineName.setForeground(new Color(0, 0, 0));
		lblMedicineName.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblMedicineName.setBounds(60, 110, 111, 14);
		add(lblMedicineName);
		
		comboBox = new AutoCompleteComboBox(SaleMedicine.getMedicineNames());
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
		comboBox.setBounds(209, 105, 166, 23);
		comboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Medicine med = (Medicine)comboBox.getSelectedItem();
				lblRate.setText("@ "+med.rate);
				amountTF.setText("Rs "+(Integer)spinner.getValue()*med.rate);
			}
		});
		add(comboBox);
		
		JLabel lblQty = new JLabel("Qty.");
		lblQty.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblQty.setBounds(444, 63, 37, 23);
		add(lblQty);
		
		spinner = new JSpinner();
		spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//int qty = (Integer)spinner.getValue();
				float rate = ((Medicine)comboBox.getSelectedItem()).rate;
				amountTF.setText("Rs "+(Integer)spinner.getValue()*rate);
			}
		});
		spinner.setBounds(479, 65, 46, 23);
		add(spinner);
		
		lblRate = new JLabel("@");
		lblRate.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblRate.setBounds(444, 108, 82, 18);
		add(lblRate);
		
		JLabel lblAmount = new JLabel("Amount");
		lblAmount.setFont(new Font("Verdana", Font.PLAIN, 12));
		lblAmount.setBounds(425, 152, 55, 15);
		add(lblAmount);
		
		amountTF = new JTextField();
		amountTF.setFont(new Font("Verdana", Font.BOLD, 11));
		amountTF.setEditable(false);
		amountTF.setBounds(501, 143, 75, 34);
		add(amountTF);
		amountTF.setColumns(10);
		
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(60, 266, 446, 206);
		add(scrollPane_1);
		
		tableModel = new MyTableModel();
		table = new JTable(tableModel);
		scrollPane_1.setViewportView(table);
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, SystemColor.activeCaption, null, null, null));
		table.setFont(new Font("Verdana", Font.PLAIN, 12));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Verdana", Font.PLAIN, 12));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Medicine med = (Medicine)comboBox.getSelectedItem();
				Integer sno = tableModel.getRowCount()+1;
				String[] data = {sno.toString(),med.medName.toString(),};
			}
		});
		btnAdd.setBounds(337, 203, 89, 34);
		add(btnAdd);

	}
	
	
}


class MyTableModel implements TableModel{
	private String[] columnNames = {"S No.",
									"Product Name",
									"Quantity",
									"Rate",
									"Amount"};
	String[][] data = {};
	
	
	public int getColumnCount() {
		return columnNames.length;
		
	}

	public int getRowCount() {
		return data.length;
	}

	public Object getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public String getColumnName(int col){
		return columnNames[col];
	}

	public void addTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public Class getColumnClass(int c) {
		return columnNames[c].getClass();
	}
	
	public void setData(String[][] data){ 
        this.data = data;
	}

	public boolean isCellEditable(int arg0, int arg1) {
		
		return true;
	}

	public void removeTableModelListener(TableModelListener arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setValueAt(Object value, int row, int col) {
		data[row][col]=(String) value; 
		
	}
	
}
