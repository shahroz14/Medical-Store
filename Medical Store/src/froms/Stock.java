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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableCellRenderer;

public class Stock extends JPanel implements ActionListener{
	
	static JPanel panel = new JPanel();
	AddNewMedicine addMedicine;
	JButton viewStockTableBtn = new JButton("View Stock Table"); 
	JButton addNewMedicineBtn = new JButton("Add New Medicine");
	JButton updateStockBtn = new JButton("Update Stock");
	JButton deleteMedicineBtn = new JButton("Delete Medicine");
	static CardLayout cardLayout = new CardLayout();
	public Stock() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		setLayout(null);
		
		
		panel.setLayout(cardLayout);
		panel.setOpaque(false);
		//panel.setBackground(Color.BLACK);
		panel.setBounds(20, 20, 800, 600);
		
		
		
		add(panel);
		
		
		viewStockTableBtn.setBounds(900, 200, 150, 35);
		viewStockTableBtn.addActionListener(this);
		add(viewStockTableBtn);
		
		addNewMedicineBtn.setBounds(900, 260, 150, 35);
		addNewMedicineBtn.addActionListener(this);
		add(addNewMedicineBtn);
		
		updateStockBtn.setBounds(900, 320, 150, 35);
		updateStockBtn.addActionListener(this);
		add(updateStockBtn);
		
		deleteMedicineBtn.setBounds(900, 380, 150, 35);
		deleteMedicineBtn.addActionListener(this);
		add(deleteMedicineBtn);
		
		
		
	}
	
	
	
	
	/*public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
		JFrame jf = new JFrame("View Stock");
		jf.add(new Stock());
		jf.setSize(800, 500);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/




	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(viewStockTableBtn)){
			try {
				panel.add("viewStockTable", new ViewStockTable());
				cardLayout.show(panel,"viewStockTable");
				panel.revalidate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource().equals(addNewMedicineBtn)){
			try {
				addMedicine = new AddNewMedicine();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			}
			panel.add("addNewMedicine", addMedicine);
			System.out.println("yeah");
			cardLayout.show(panel, "addNewMedicine");
			panel.revalidate();
		}
		if(e.getSource().equals(updateStockBtn)){
			try {
				panel.add("updateStock", new UpdateStock());
				cardLayout.show(panel, "updateStock");
				panel.revalidate();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		
		if(e.getSource().equals(deleteMedicineBtn)){
			try {
				panel.add("delete",new DeleteMedicine());
				cardLayout.show(panel, "delete");
				panel.revalidate();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
class ViewStockTable extends JPanel{
	JTable stockTable;
	JLabel stockAmountLbl = new JLabel("Stock Amount");
	JLabel stockAmount = new JLabel();
	
	public ViewStockTable() throws SQLException, ClassNotFoundException {
		super(null);
		String[] columns = {"Medicine Name","Medicine Rate","Stock Available","Amount"};
		String[][] data = getStockTable();
		setOpaque(false);
		stockTable = new JTable(data, columns){
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
			stockTable.setPreferredScrollableViewportSize(new Dimension(750, 550));
			//stockTable.setFillsViewportHeight(false);
			JScrollPane jps = new JScrollPane(stockTable);
			jps.setBounds(5, 5, 700, 550);
			add(jps);
			
			stockAmountLbl.setBounds(400, 570, 200, 30);
			stockAmountLbl.setFont(new Font("Tahoma",Font.BOLD,12));
			add(stockAmountLbl);
			
			stockAmount.setBounds(550,570,100,30);
			stockAmount.setFont(new Font("Tahoma",Font.BOLD,12));
			add(stockAmount);
			
			Medicine med[] = UpdateStock.getMedicineNames();
			int i=0;
			float amount =0;
			while(i<med.length){
				amount=amount+med[i].amount;
				i++;
			}
			stockAmount.setText("Rs  "+amount+"/-");
	}
	
	private String[][] getStockTable() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/Medical_Store_Management_System?user=root");
		PreparedStatement pS = conn.prepareStatement("SELECT * FROM Medicine_Stock;");
		ResultSet rS = pS.executeQuery();
		int count=0;
		while(rS.next()){
			count++;
		}
		
		String[][] data = new String[count][4];
		count=0;
		rS.beforeFirst();
		while(rS.next()){
			
			data[count][0] = rS.getString(1);
			data[count][1] = String.valueOf(rS.getFloat(2));
			data[count][2] = String.valueOf(rS.getInt(3));
			data[count][3] = String.valueOf(rS.getFloat(4));
			count++;
		}
		
		return data;
	}
}

