package froms;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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

import javax.swing.ComboBoxEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicComboBoxEditor;
import javax.swing.plaf.metal.MetalComboBoxEditor;


public class SaleMedicine extends JPanel implements ActionListener, KeyListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JComboBox medNameCombo;
	JButton sale;
	JButton cancel;
	JLabel amountLabel;
	JTextField qtyTF;
	JTextField amountTF;
	JLabel rate;
	
	public SaleMedicine() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException{
		super();
		setLayout(new GridBagLayout());
		GridBagConstraints g = new GridBagConstraints();
		
		
		//Misc.
		Font label = new Font("Verdana", Font.PLAIN, 13);
		Font tF = new Font("Calibri", Font.PLAIN, 13);
		Font head = new Font("Verdana", Font.BOLD, 15);
		Insets headGap = new Insets(30, 10, 10, 10);
		Insets body = new Insets(10,10,10,10);
		//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		
		
		JLabel saleHeading = new JLabel("Sale");
		saleHeading.setFont(head);
		g.insets=headGap;
		g=SaleMedicine.constraints(g, 0, 0, GridBagConstraints.REMAINDER, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(saleHeading,g);
		
		JLabel medNameLabel = new JLabel("Medicine Name :");
		medNameLabel.setFont(label);
		g.insets=body;
		g=SaleMedicine.constraints(g, 0, 1, 1, 1, GridBagConstraints.WEST, 0, 20, 0);
		add(medNameLabel,g);
		
		Medicine[] medNames = getMedicineNames();
		medNameCombo = new AutoCompleteComboBox(medNames);
		medNameCombo.setFont(label);
		g=SaleMedicine.constraints(g, 1, 1, 1, 1, GridBagConstraints.WEST, 0, 60, 0);
		add(medNameCombo,g);
		medNameCombo.addActionListener(this);
		medNameCombo.addKeyListener(this);
		
		JLabel rateLabel = new JLabel("Rate :");
		rateLabel.setFont(label);
		g.insets=body;
		g=SaleMedicine.constraints(g, 0, 2, 1, 1, GridBagConstraints.WEST, 0, 20, 0);
		add(rateLabel,g);
		
		rate = new JLabel("");
		rate.setFont(label);
		g.insets=body;
		g=SaleMedicine.constraints(g, 1, 2, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		add(rate,g);
		
		JLabel qtyLabel = new JLabel("Quantity :");
		qtyLabel.setFont(label);
		g.insets=body;
		g=SaleMedicine.constraints(g, 0, 3, 1, 1, GridBagConstraints.WEST, 0, 20, 0);
		add(qtyLabel,g);
		
		qtyTF = new JTextField(6);
		qtyTF.setFont(label);
		g.insets=body;
		g=SaleMedicine.constraints(g, 1, 3, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		qtyTF.addKeyListener(this);
		qtyTF.addActionListener(this);
		add(qtyTF,g);
		
		
		amountLabel = new JLabel("Amount :");
		amountLabel.setFont(label);
		g=SaleMedicine.constraints(g, 0, 4, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		add(amountLabel,g);
		
		amountTF = new JTextField(7);
		amountTF.setFont(tF);
		g=SaleMedicine.constraints(g, 1, 4, 1, 1, GridBagConstraints.WEST, 0, 0, 0);
		amountTF.setEditable(false);
		add(amountTF,g);
		
		sale = new JButton("Sale");
		sale.setFont(label);
		g.insets = headGap;
		g=SaleMedicine.constraints(g, 1, 5, 1, 1, GridBagConstraints.EAST, 0, 0, 0);
		sale.addActionListener(this);
		add(sale,g);
		
		
		
		
		
		
	}
	
	static public Medicine[] getMedicineNames() throws ClassNotFoundException, SQLException {
		Connection conn = getDatabaseConnection();
		PreparedStatement pS = conn.prepareStatement("select Med_Name, Med_Rate from medicine_stock;");
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

	private static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		return conn;
	}

	public static GridBagConstraints constraints(GridBagConstraints g, int x, int y, int width, int height, int anchor, int fill, int padx, int pady)
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
	
	/*public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException {
		JFrame fr = new JFrame();
		fr.add(new SaleMedicine());
		fr.setVisible(true);
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}*/


	public void keyPressed(KeyEvent e) {
		
	}
		

	public void keyReleased(KeyEvent arg0) {
		
		
	}

	public void keyTyped(KeyEvent e) {
		if(e.getSource().equals(qtyTF)){
			char c = e.getKeyChar();
			if(!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))){
				e.setKeyChar('\0');
			}
		}
		
	}

	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(medNameCombo)||e.getSource().equals(qtyTF)){
			try{
			Medicine med = (Medicine)medNameCombo.getSelectedItem();
			rate.setText("Rs "+med.rate+"/- per unit");
			if(!(qtyTF.getText().equals(""))){
				float amountValue = med.rate*Integer.parseInt(qtyTF.getText());
				amountTF.setText(String.valueOf(amountValue));
			}
			}
			catch(ClassCastException e1){
				JOptionPane.showMessageDialog(this, "Medicine not found!", "Caution", JOptionPane.ERROR_MESSAGE, null);
				e1.printStackTrace();
			}
			
		}
		
		if(e.getSource().equals(sale)){
			Medicine med = (Medicine)medNameCombo.getSelectedItem();
			rate.setText("Rs "+med.rate+"/- per unit");
			if(!(qtyTF.getText().equals(""))){
				float amountValue = med.rate*Integer.parseInt(qtyTF.getText());
				amountTF.setText(String.valueOf(amountValue));
			}
			else
				JOptionPane.showMessageDialog(this, "Enter Quantity", "Caution", JOptionPane.ERROR_MESSAGE,null);
			try {
				int stock = checkStock((Medicine)medNameCombo.getSelectedItem());
				stock=stock-Integer.parseInt(qtyTF.getText());
				if(stock>=0){
					update(medNameCombo.getSelectedItem());
					updateSaleRecord(medNameCombo.getSelectedItem());
					JOptionPane.showMessageDialog(this, "Stock Remaining:	"+stock, "Message", JOptionPane.INFORMATION_MESSAGE,null);
				
					qtyTF.setText("");
					amountTF.setText("");
					rate.setText("");
				}
				else{
					JOptionPane.showMessageDialog(this, "Stock Insufficient", "Caution", JOptionPane.WARNING_MESSAGE,null);
				}
				
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	private void updateSaleRecord(Object object) throws ClassNotFoundException, SQLException {
		Medicine medicine = (Medicine)object;
		Connection conn = getDatabaseConnection();
		PreparedStatement pS;
		try {
			pS = conn.prepareStatement("CREATE TABLE Sales_Record(Date VARCHAR(10), Med_Name VARCHAR(30),Med_Rate DECIMAL(9,2), Quantity INT, Amount Numeric(12,2));");
			pS.executeUpdate();
			addSaleData(medicine);
		
		} catch (SQLException e) {
			addSaleData(medicine);
		}
		
	}

	private void addSaleData(Medicine medicine) throws SQLException, ClassNotFoundException {
		Connection conn = getDatabaseConnection();
		PreparedStatement pS = conn.prepareStatement("INSERT INTO Sales_Record VALUES(?,?,?,?,?)");
		medicine.quantity = Integer.parseInt(qtyTF.getText());
		medicine.amount = Float.parseFloat(amountTF.getText());
		pS.setString(1, medicine.date);
		pS.setString(2, medicine.medName);
		pS.setFloat(3, medicine.rate);
		pS.setInt(4, medicine.quantity);
		pS.setFloat(5, medicine.amount);
		pS.executeUpdate();
		conn.close();
		pS.close();
		
	}

	private int checkStock(Medicine medicine) throws NumberFormatException, SQLException, ClassNotFoundException {
		Connection conn = getDatabaseConnection();
		PreparedStatement pS = conn.prepareStatement("SELECT Stock_Available FROM medicine_stock WHERE Med_Name=? AND Med_Rate=?;");
		pS.setString(1, medicine.medName);
		pS.setFloat(2, medicine.rate);
		ResultSet rS = pS.executeQuery();
		rS.next();
		return rS.getInt(1);
	}

	private void update(Object medicine) throws SQLException, ClassNotFoundException {
		Medicine med = (Medicine)medicine;
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		PreparedStatement pS1 = conn.prepareStatement("SELECT Stock_Available, Amount from medicine_stock WHERE Med_Name=? AND Med_Rate=?;");
		pS1.setString(1, med.medName);
		pS1.setFloat(2, med.rate);
		ResultSet rS = pS1.executeQuery();
		rS.next();
		int stock = rS.getInt(1);
		float amt = rS.getFloat(2);
		stock = stock-Integer.parseInt(qtyTF.getText());
		amt = amt-Float.parseFloat(amountTF.getText());
		PreparedStatement pS = conn.prepareStatement("UPDATE medicine_stock SET Stock_Available=?, Amount=? WHERE Med_Name=? AND Med_Rate=?;");
		pS.setInt(1, stock);
		pS.setFloat(2, amt);
		pS.setString(3, med.medName);
		pS.setFloat(4, med.rate);
		pS.executeUpdate();
		pS.close();
		pS1.close();
		conn.close();
	}
	
	static private Medicine[] searchedMedicineNames(String medicineName) throws ClassNotFoundException, SQLException{
		Medicine [] medNames = getMedicineNames();
		medicineName = medicineName.toLowerCase();
		int count=0;
		for(int i=0;i<medNames.length;i++){
			if(medNames[i].medName.toLowerCase().contains(medicineName))
				count++;
		}
		Medicine [] searchedMedNames = new Medicine[count];
		count=0;
		for(int i=0;i<medNames.length;i++){
			
			if(medNames[i].medName.toLowerCase().contains(medicineName)){
				searchedMedNames[count] = medNames[i];
				count++;
			}
		}
		return searchedMedNames;
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		System.out.println(SaleMedicine.searchedMedicineNames("c")[2].medName);
	}

}
class AutoCompleteComboBox extends JComboBox
{
   public int caretPos=0;
   public JTextField inputField=null;
   public AutoCompleteComboBox(final Object elements[]) {
      super(elements);
      setEditor(new MetalComboBoxEditor());
      setEditable(true);
   }
  
   public void setSelectedIndex(int index) {
      super.setSelectedIndex(index);
  
      inputField.setText(getItemAt(index).toString());
      inputField.setSelectionEnd(caretPos + inputField.getText().length());
      inputField.moveCaretPosition(caretPos );
   }
   
  
   public void setEditor(ComboBoxEditor editor) {
      super.setEditor(editor);
      if (editor.getEditorComponent() instanceof JTextField) {
         inputField = (JTextField) editor.getEditorComponent();
         
         inputField.addKeyListener(new KeyAdapter() {
            public void keyReleased( KeyEvent ev ) {
               char key=ev.getKeyChar();
               if (! (Character.isLetterOrDigit(key) || Character.isSpaceChar(key) )) return;
  
               caretPos=inputField.getCaretPosition();
               String text="";
               try {
                  text=inputField.getText(0, caretPos);
               }
               catch (javax.swing.text.BadLocationException e) {
                  e.printStackTrace();
               }
  
               for (int i=0; i<getItemCount(); i++) {
                  String element = getItemAt(i).toString();
                  if (element.toLowerCase().startsWith(text.toLowerCase())) {
                     setSelectedIndex(i);
                     return;
                  }
               }
            }
         });
         inputField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent evt) {
				inputField.setCaretPosition(inputField.getText().length());
			}
		});
      }
   }
}

