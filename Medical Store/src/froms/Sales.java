package froms;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableCellRenderer;

public class Sales extends JPanel implements ActionListener{
	JPanel panel = new JPanel();
	JButton viewSalesRecordBtn = new JButton("View Sales Record"); 
	JButton saleMedicineBtn = new JButton("Sale Medicine");
	JButton updateStockBtn = new JButton("Update Stock");
	CardLayout cardLayout = new CardLayout();
	public Sales() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		setLayout(null);
		panel.setLayout(cardLayout);
	
		panel.setOpaque(true);
		panel.setBounds(20, 20, 800, 600);
		
		
		
		saleMedicineBtn.setBounds(900, 200, 150, 35);
		saleMedicineBtn.addActionListener(this);
		add(saleMedicineBtn);
		
		viewSalesRecordBtn.setBounds(900, 260, 150, 35);
		viewSalesRecordBtn.addActionListener(this);
		add(viewSalesRecordBtn);
		
		
		
		
		
		add(panel);
		
		
		
	}
	
	/*public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		JFrame fr = new JFrame("Sales");
		fr.add(new Sales());
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fr.setVisible(true);
		
	}*/

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(viewSalesRecordBtn)){
			try {
				panel.add("salesTable", new SalesTable());
				cardLayout.show(panel, "salesTable");
				panel.revalidate();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			//cardLayout.show(panel, "salesTable");
		}
		if(e.getSource().equals(saleMedicineBtn)){
			try {
				panel.add("salesMedicine", new SaleMedicine());
				cardLayout.show(panel,"salesMedicine");
				panel.revalidate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
}

class SalesTable extends JPanel{
	JTable salesTable;
	public SalesTable() throws SQLException, ClassNotFoundException{
		super(null);
		JLabel totalSalesAmountLbl = new JLabel("Total Sales Amount");
		JLabel totalSalesAmount = new JLabel();
		
		
		String[] columns = {"Date", "Medicine Name","Medicine Rate","Quantity","Amount"};
		String[][] data = getSalesTable();
		salesTable = new JTable(data, columns){
			public boolean isCellEditable(int data, int columns){
				return false;
				}
			public Component prepareRenderer(TableCellRenderer r, int data, int columns){
				Component c = super.prepareRenderer(r, data, columns);
				if (data % 2 == 0)
					c.setBackground(Color.WHITE);
				else 
					c.setBackground(Color.LIGHT_GRAY);
				return c; 
				}
			};
			salesTable.setPreferredScrollableViewportSize(new Dimension(700, 550));
			salesTable.setFillsViewportHeight(false);
			JScrollPane jps = new JScrollPane(salesTable);
			jps.setBounds(10, 10, 700, 550);
			add(jps);
			totalSalesAmountLbl.setBounds(350, 580, 200, 30);
			totalSalesAmountLbl.setFont(new Font("Tahoma", Font.BOLD,12));
			add(totalSalesAmountLbl);
			totalSalesAmount.setBounds(600, 580, 100, 30);
			totalSalesAmount.setFont(new Font("Tahoma", Font.BOLD,12));
			add(totalSalesAmount);
			
			Connection con = UpdateStock.getDatabaseConnection();
			PreparedStatement pS= con.prepareStatement("Select Amount from sales_record;");
			ResultSet rs = pS.executeQuery();
			float amount=0;
			while(rs.next()){
				amount=amount+(rs.getFloat(1));
			}
			totalSalesAmount.setText(" Rs  "+amount+"/-");
			
			
	}
	static String[][] getSalesTable() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		PreparedStatement pS = conn.prepareStatement("SELECT * FROM sales_record;");
		ResultSet rS = pS.executeQuery();
		int count=0;
		while(rS.next()){
			count++;
		}
		
		String[][] data = new String[count][5];
		count=0;
		rS.beforeFirst();
		while(rS.next()){
			data[count][0] = rS.getString(1);
			data[count][1] = rS.getString(2);
			data[count][2] = String.valueOf(rS.getFloat(3));
			data[count][3] = String.valueOf(rS.getInt(4));
			data[count][4] = String.valueOf(rS.getFloat(5));
			count++;
		}
		
		return data;
	}
}