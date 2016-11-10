package froms;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChangePassword extends JPanel {
	private JPasswordField currentPwdTF;
	private JPasswordField newPwdTF;

	/**
	 * Create the panel.
	 */
	public ChangePassword() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		
		GridBagConstraints g = new GridBagConstraints();
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		AddNewMedicine.constraints(g, 0, 0, GridBagConstraints.REMAINDER, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(lblChangePassword, g);
		
		JLabel lblCurrentPassword = new JLabel("Current Password :");
		lblCurrentPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		AddNewMedicine.constraints(g, 0, 1, 1, 1, GridBagConstraints.CENTER, 0, 0, 0);
		g.insets = new Insets(30, 10, 10, 10);
		add(lblCurrentPassword, g);
		
		currentPwdTF = new JPasswordField(20);
		AddNewMedicine.constraints(g, 1, 1, 1, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(currentPwdTF,g);
		
		JLabel lblNewPassword = new JLabel("New Password :");
		lblNewPassword.setFont(new Font("Tahoma", Font.PLAIN, 12));
		g.insets = new Insets(15, 10, 30, 10);
		AddNewMedicine.constraints(g, 0, 2, 1, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(lblNewPassword, g);
		
		newPwdTF = new JPasswordField(20);
		AddNewMedicine.constraints(g, 1, 2, 1, 1, GridBagConstraints.CENTER, 0, 0, 0);
		add(newPwdTF, g);
		
		JButton changePwdBtn = new JButton("Change Password");
		changePwdBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String str = String.valueOf(currentPwdTF.getPassword());
				
				if(str.equals(LoginForm.pwd)){
					try {
						FileOutputStream f = new FileOutputStream("pwd.txt");
						char[] pass = newPwdTF.getPassword();
						for(int i=0;i<pass.length;i++){
							f.write(pass[i]);
						}
						f.flush();
						LoginForm.pwd = String.valueOf(pass);
						JOptionPane.showMessageDialog(ChangePassword.this, "Password changed successfully", "Done", JOptionPane.INFORMATION_MESSAGE, null);
						f.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				}
				else{
					JOptionPane.showMessageDialog(ChangePassword.this, "Incorrect Password", "Caution", JOptionPane.ERROR_MESSAGE, null);
				}
			}
		});
		
		
		AddNewMedicine.constraints(g, 0, 3, GridBagConstraints.REMAINDER, 0, GridBagConstraints.CENTER, 0, 0, 0);
		add(changePwdBtn, g);

	}
	
	public static void main(String[] args) {
		JFrame fr = new JFrame();
		fr.add(new ChangePassword());
		
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fr.setVisible(true);
	}

}
