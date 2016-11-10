package froms;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;


public class AddNewMedicine extends JPanel implements ActionListener, KeyListener, MouseListener{
	JButton addMedicineBtn = new JButton("Add New Medicine");
	JTextField medNameTF = new JTextField(25);
	JTextField qtyTF = new JTextField(3);
	JTextField rateTF = new JTextField(3);
	JTextField amountTF = new JTextField(5);
	JLabel medNamePropmtLabel;

	public AddNewMedicine() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		setLayout(new GridBagLayout());
		setOpaque(false);
		GridBagConstraints g = new GridBagConstraints();
		
		
		Font label = new Font("Verdana", Font.PLAIN, 13);
		Font tF = new Font("Calibri", Font.PLAIN, 13);
		Font head = new Font("Verdana", Font.BOLD, 15);
		Insets headGap = new Insets(30, 10, 10, 10);
		Insets body = new Insets(10,10,10,10);
		
		JLabel heading = new JLabel("Add Medicine Details");
		heading.setFont(head);
		g=AddNewMedicine.constraints(g, 0, 0, GridBagConstraints.REMAINDER, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(heading,g);
		
		JLabel medNameLabel = new JLabel("Medicine Name :");
		medNameLabel.setFont(label);
		g=AddNewMedicine.constraints(g, 0, 1, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		g.insets = headGap;
		add(medNameLabel,g);
		
		medNameTF.setFont(tF);
		g=AddNewMedicine.constraints(g, 1, 1, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		add(medNameTF,g);
		medNameTF.addMouseListener(this);
		
		medNamePropmtLabel = new JLabel();
		medNamePropmtLabel.setFont(label);
		g=AddNewMedicine.constraints(g, 2, 1, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		add(medNamePropmtLabel);
		
		JLabel rateLabel = new JLabel("Rate :");
		rateLabel.setFont(label);
		g=AddNewMedicine.constraints(g, 0, 2, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		g.insets = body;
		add(rateLabel,g);
		
		medNameTF.setFont(tF);
		g=AddNewMedicine.constraints(g, 1, 2, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		add(rateTF,g);
		rateTF.addKeyListener(this);
		
		JLabel qtyLabel = new JLabel("Quantity :");
		qtyLabel.setFont(label);
		g=AddNewMedicine.constraints(g, 0, 3, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		add(qtyLabel,g);
		
		qtyTF.setFont(tF);
		g=AddNewMedicine.constraints(g, 1, 3, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		add(qtyTF,g);
		qtyTF.addKeyListener(this);
		qtyTF.addActionListener(this);
		
		JLabel amountLabel = new JLabel("Total Amount :");
		amountLabel.setFont(label);
		g=AddNewMedicine.constraints(g, 0, 4, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		add(amountLabel,g);
		
		amountTF.setFont(tF);
		amountTF.setEditable(false);
		amountTF.setText("Rs");
		g=AddNewMedicine.constraints(g, 1, 4, 1, 1, GridBagConstraints.WEST, 0, 10, 0);
		add(amountTF,g);
		
		addMedicineBtn.setFont(label);
		g=AddNewMedicine.constraints(g, 1, 5, 1, 1, GridBagConstraints.EAST, 0, 10, 5);
		g.insets = headGap;
		add(addMedicineBtn, g);
		addMedicineBtn.addActionListener(this);
			
	}
	
	static GridBagConstraints constraints(GridBagConstraints g, int x, int y, int width, int height, int anchor, int fill, int padx, int pady)
	{
		g.gridx=x;
		g.gridy=y;
		g.gridwidth=width;
		g.gridheight=height;
		g.anchor=anchor;
		g.fill=fill;
		g.ipadx=padx;
		g.ipady=pady;
		return g;
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(addMedicineBtn)){
			if(medNameTF.getText().equals("")||rateTF.getText().equals("")||qtyTF.getText().equals("")){
				JOptionPane.showMessageDialog(this, "Enter all fields", "Caution", JOptionPane.ERROR_MESSAGE, null);
			}
			else{
				try {
					Medicine[] medNames = UpdateStock.getMedicineNames();
					int i=0;
				l1:	while(i!=(medNames.length)){
						if(medNames[i].medName.equals(medNameTF.getText())){
							break l1;
						}
						i++;
					}
					System.out.println(i);
					if(i==medNames.length){
						addData();
						JOptionPane.showMessageDialog(this, "Sucessfully Done", "Done", JOptionPane.INFORMATION_MESSAGE, null);
					}
					else{
						JOptionPane.showMessageDialog(this, "Medicine with this name already exist", "Caution", JOptionPane.WARNING_MESSAGE, null);
					}
					
					
				} catch (ClassNotFoundException e1) {
					//new Message().msgLabel.setText("Connection not established");
					e1.printStackTrace();
				} catch (SQLException e1) {
					//new Message().msgLabel.setText("Table not Found");
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource().equals(qtyTF)){
			float amount = Float.parseFloat(rateTF.getText())*(Integer.parseInt(qtyTF.getText()));
			amountTF.setText(""+amount);
		}
			
	}

	private void addData() throws ClassNotFoundException, SQLException {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
			conn.createStatement().executeUpdate("create table medicine_stock(Med_Name VARCHAR(30),Med_Rate DECIMAL(9,2), Stock_Available INT, Amount Numeric(12,2));");
			System.out.println("Add Data");
			addDataWhenDataNotMatched();
		}
		catch(SQLException e){
			if(check(medNameTF.getText(),rateTF.getText())){
				addDataWhenDataMatched();
			}
			else{
				addDataWhenDataNotMatched();
			}
		
		}
	}
		
	private void addDataWhenDataMatched() throws SQLException, ClassNotFoundException {
		
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
			PreparedStatement pS1 = conn.prepareStatement("SELECT Stock_Available, Amount FROM medicine_stock WHERE Med_Name=? AND Med_Rate=?;");
			pS1.setString(1, medNameTF.getText());
			pS1.setFloat(2, Float.parseFloat(rateTF.getText()));
			ResultSet stock = pS1.executeQuery();
			stock.next();
			int val = stock.getInt(1)+Integer.parseInt(qtyTF.getText());
			float amount = Integer.parseInt(qtyTF.getText())*Float.parseFloat(rateTF.getText());
			amountTF.setText(""+amount);
			amount = stock.getFloat(2)+amount;
			PreparedStatement pS2 = conn.prepareStatement("update medicine_stock set Stock_Available=?, Amount=? where Med_Name=? AND Med_Rate=?; ");
			pS2.setInt(1, val);
			pS2.setFloat(2, amount);
			pS2.setString(3, medNameTF.getText());
			pS2.setFloat(4, Float.parseFloat(rateTF.getText()));
			pS2.executeUpdate();
			conn.close();
			stock.close();
			medNameTF.setText("");
			rateTF.setText("");
			qtyTF.setText("");
			
		
	}


	private void addDataWhenDataNotMatched() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		PreparedStatement pS = conn.prepareStatement("insert into medicine_stock values(?,?,?,?)");
		float amount = Float.parseFloat(rateTF.getText())*Integer.parseInt(qtyTF.getText());
		amountTF.setText(""+amount);
		pS.setString(1, medNameTF.getText());
		pS.setFloat(2, Float.parseFloat(rateTF.getText()));
		pS.setInt(3, Integer.parseInt(qtyTF.getText()));
		pS.setFloat(4, Float.parseFloat(amountTF.getText()));
		pS.executeUpdate();
		medNameTF.setText("");
		rateTF.setText("");
		qtyTF.setText("");
	}

	private boolean check(String medName, String rate) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		PreparedStatement pS = conn.prepareStatement("select Med_Name, Med_Rate from medicine_stock;");
		ResultSet rS = pS.executeQuery();
		float rt = Float.parseFloat(rate);
		while(rS.next()){
			if(rS.getString(1).equals(medName)&&(rS.getFloat(2)==rt)){
				return true;
			}
		}
		return false;	
	}

	public void keyPressed(KeyEvent e) {
		
		
	}

	public void keyReleased(KeyEvent e) {
		
		
	}

	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(qtyTF)){
			char c = e.getKeyChar();
			if(!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))){
				e.setKeyChar('\0');
			}
		}
		else if(e.getSource().equals(rateTF)){
			char c = e.getKeyChar();
			if(!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE || (c == KeyEvent.VK_PERIOD)))){
				e.setKeyChar('\0');
			}
			char text[];
	        int count = 0; 
	        text = rateTF.getText().toCharArray();
	        for(int i = 0 ; i< text.length ; i++){
	            if(text[i] == '.'){
	                count++;
	            }
	        }
	        if(count>=1 && e.getKeyChar() == '.'){
	            e.consume();
	        }
		}
	}


	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		if(arg0.getSource().equals(medNameTF)){
			if(medNameTF.getText().equals("")){
				medNameTF.setToolTipText("Medicine Name can't be left blank");
			}
		}
	}

	public void mouseExited(MouseEvent arg0) {
		medNameTF.setToolTipText("");
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}

