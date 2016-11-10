package froms;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteMedicine extends JPanel implements ActionListener {

	JLabel deleteHeaderLbl = new JLabel("Delete Medicine");
	JLabel selectLbl = new JLabel("Select Medicne");
	JComboBox medCombo = new AutoCompleteComboBox(UpdateStock.getMedicineNames());
	JButton deleteBtn = new JButton("Delete");
	
	public DeleteMedicine() throws ClassNotFoundException, SQLException {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		GridBagConstraints g = new GridBagConstraints();
		
		deleteHeaderLbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		AddNewMedicine.constraints(g, 0, 0, GridBagConstraints.REMAINDER, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(deleteHeaderLbl, g);
		
		
		selectLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		g.insets = new Insets(70, 15, 10, 15);
		AddNewMedicine.constraints(g, 0, 1, 1, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(selectLbl, g);
		
		
		AddNewMedicine.constraints(g, 1, 1, 1, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(medCombo,g);
		
		AddNewMedicine.constraints(g, 0, 2, GridBagConstraints.REMAINDER, 1, GridBagConstraints.CENTER, 0, 0, 0);
		g.insets = new Insets(25, 5, 5, 5);
		deleteBtn.addActionListener(this);
		add(deleteBtn,g);
		
		

	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		JFrame fr = new JFrame();
		fr.add(new DeleteMedicine());
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fr.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Medicine med = (Medicine)medCombo.getSelectedItem();
		try {
			Connection con = UpdateStock.getDatabaseConnection();
			PreparedStatement pS = con.prepareStatement("DELETE FROM medicine_stock WHERE Med_Name=?;");
			pS.setString(1, med.medName);
			pS.executeUpdate();
			JOptionPane.showMessageDialog(this, "Deleted Successfully", "Done", JOptionPane.INFORMATION_MESSAGE, null);
			
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}

}
