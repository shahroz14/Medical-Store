package froms;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;




public class UpdateStock extends JPanel implements ActionListener, KeyListener {
	JComboBox medNameCombo;
	JTextField rateTF;
	JLabel amountLabel;
	JTextField qtyTF;
	JTextField amountTF;
	JButton update;
	
	public UpdateStock() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException {
		super();
		
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		
		//Misc.
		Font label = new Font("Verdana", Font.PLAIN, 13);
		Font tF = new Font("Calibri", Font.PLAIN, 13);
		Font head = new Font("Verdana", Font.BOLD, 15);
		Insets headGap = new Insets(30, 10, 10, 10);
		Insets body = new Insets(10,10,10,10);
		
		JLabel heading = new JLabel("Update Medicine");
		heading.setFont(head);
		g=AddNewMedicine.constraints(g, 0, 0, GridBagConstraints.REMAINDER, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(heading,g);
		
		JLabel medNameLabel = new JLabel("Medicine Name :");
		medNameLabel.setFont(label);
		g=AddNewMedicine.constraints(g, 0, 1, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		g.insets = headGap;
		add(medNameLabel,g);
		
		//Medicine[] medNames = UpdateStock.getMedicineNames();
		medNameCombo = new AutoCompleteComboBox(UpdateStock.getMedicineNames());
		medNameCombo.setFont(label);
		g=AddNewMedicine.constraints(g, 1, 1, 1, 1, GridBagConstraints.WEST, 0, 60, 0);
		add(medNameCombo,g);
		medNameCombo.addActionListener(this);
		
		JLabel rateLabel = new JLabel("Rate :");
		rateLabel.setFont(label);
		g.insets=body;
		g=AddNewMedicine.constraints(g, 0, 2, 1, 1, GridBagConstraints.WEST, 0, 20, 0);
		add(rateLabel,g);
		
		
		rateTF = new JTextField(6);
		rateTF.setFont(tF);
		g.insets=body;
		g=AddNewMedicine.constraints(g, 1, 2, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		add(rateTF,g);
		rateTF.addActionListener(this);
		
		JLabel qtyLabel = new JLabel("Quantity :");
		qtyLabel.setFont(label);
		g.insets=body;
		g=AddNewMedicine.constraints(g, 0, 3, 1, 1, GridBagConstraints.WEST, 0, 20, 0);
		add(qtyLabel,g);
		
		qtyTF = new JTextField(6);
		qtyTF.setFont(tF);
		g.insets=body;
		g=AddNewMedicine.constraints(g, 1, 3, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		add(qtyTF,g);
		qtyTF.addKeyListener(this);
		qtyTF.addActionListener(this);
		
		
		amountLabel = new JLabel("Amount :");
		amountLabel.setFont(label);
		g=AddNewMedicine.constraints(g, 0, 4, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		add(amountLabel,g);
		
		amountTF = new JTextField(7);
		amountTF.setFont(tF);
		g=AddNewMedicine.constraints(g, 1, 4, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		amountTF.setEditable(false);
		add(amountTF,g);
		
		update = new JButton("Update");
		update.setFont(label);
		g.insets = headGap;
		g=AddNewMedicine.constraints(g, 1, 5, 1, 1, GridBagConstraints.EAST, 0, 0, 0);
		add(update,g);
		update.addActionListener(this);
		
		
		
		
		
		setVisible(true);
		setSize(800, 600);
		
	}
	static public Medicine[] getMedicineNames() throws ClassNotFoundException, SQLException {
		Connection conn = getDatabaseConnection();
		PreparedStatement pS = conn.prepareStatement("select Med_Name, Med_Rate, Stock_Available, Amount from medicine_stock;");
		ResultSet rS = pS.executeQuery();
		int count =0;
		while(rS.next()){
			count++;
		}
		Medicine medNames[] = new Medicine[count];
		count=0;
		rS.beforeFirst();
		while(rS.next()){
			medNames[count] = new Medicine();
			medNames[count].medName = rS.getString(1);
			medNames[count].rate = rS.getFloat(2);
			medNames[count].quantity = rS.getInt(3);
			medNames[count].amount = rS.getFloat(4);
			Calendar date = Calendar.getInstance();
			Date time = date.getTime();
			SimpleDateFormat datePrint = new SimpleDateFormat("dd/MM/yyyy");
			medNames[count].date = datePrint.format(time);
			count++;
		}
		rS.close();
		conn.close();
		return medNames;
	}
	
	static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		return conn;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(medNameCombo)){
			try{
				Medicine med = (Medicine)medNameCombo.getSelectedItem();
				try {
					med = updateComboBox(med);
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				rateTF.setText(""+med.rate);
				qtyTF.setText(""+med.quantity);
				float amount = (Float.parseFloat(rateTF.getText()))*(Integer.parseInt(qtyTF.getText()));
				amountTF.setText(""+amount);
			}
			catch (ClassCastException e1) {
				JOptionPane.showMessageDialog(this, "Medicine not found!", "Caution", JOptionPane.ERROR_MESSAGE, null);
				e1.printStackTrace();
			}
		}
		
		if(e.getSource().equals(qtyTF)||e.getSource().equals(rateTF)){
			float amount = (Float.parseFloat(rateTF.getText()))*(Integer.parseInt(qtyTF.getText()));
			amountTF.setText(""+amount);
		}
		
		if(e.getSource().equals(update)){
			
			try {
				updateMedicine(medNameCombo.getSelectedItem());
				
				JOptionPane.showMessageDialog(this, "Update Successfully", "Done", JOptionPane.INFORMATION_MESSAGE,null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
		}
		
		
	}
	private Medicine updateComboBox(Medicine med) throws SQLException, ClassNotFoundException {
		Connection conn = getDatabaseConnection();
		PreparedStatement pS = conn.prepareStatement("select Med_Name, Med_Rate, Stock_Available, Amount from medicine_stock WHERE Med_Name=?;");
		pS.setString(1, med.medName);
		ResultSet rS = pS.executeQuery();
		rS.next();
		med.rate = rS.getFloat(2);
		med.quantity = rS.getInt(3);
		med.amount = rS.getFloat(4);
		
		
		
		
		return med;
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(qtyTF)){
			char c = e.getKeyChar();
			if(!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))){
				e.setKeyChar('\0');
			}
		}
		
	}
	
	private void updateMedicine(Object selectedItem) throws SQLException, ClassNotFoundException {
		
		Medicine med = (Medicine)selectedItem;
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		PreparedStatement pS = conn.prepareStatement("UPDATE medicine_stock SET Stock_Available=?, Amount=?, Med_Rate=? WHERE Med_Name=?;");
		float rate = Float.parseFloat(rateTF.getText());
		int qty = Integer.parseInt(qtyTF.getText());
		float amt = rate*qty;
		pS.setInt(1, qty);
		pS.setFloat(2, amt);
		pS.setFloat(3, rate);
		pS.setString(4, med.medName);
		
		pS.executeUpdate();
		pS.close();
		conn.close();
		
	}
	
}

class Medicine{
	String date;
	String medName;
	Float rate;
	Integer quantity;
	Float amount;
	public String toString() {
		return this.medName;
	}
}
