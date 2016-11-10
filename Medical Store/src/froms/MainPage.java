package froms;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EtchedBorder;


public class MainPage extends JPanel implements ActionListener{
	JButton medicineBtn;
	JButton salesBtn;
	JButton logoutBtn;
	JButton changePasswordBtn;
	JPanel displayPanel = new JPanel();
	JLabel username = new JLabel("SHAHROZ SALEEM");
	//JLabel username = new JLabel(LoginForm.usernameTF.getText());
	JLabel loggedIn  = new JLabel("Logged in as:"); 
	JPanel headerPanel = new JPanel(); 
	CardLayout cardLayout = new CardLayout();
	
	
	public MainPage() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException {
		super(null);
		LookAndFeelInfo[] info = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo lookAndFeelInfo : info) {
			System.out.println(lookAndFeelInfo.getClassName());
		}
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		//add(new JButton("Oak"));
		//setBounds(0, 0, 1366, 768);
		Color panelColor = new Color(200, 189, 200);
		JPanel secBtn = new JPanel();
		secBtn.setLayout(null);
		secBtn.setBounds(0, 100, 200, 660);
		secBtn.setBackground(Color.RED);
		
		loggedIn.setBounds(20,80,150,20);
		loggedIn.setFont(new Font("Tahoma", Font.BOLD, 13));
		loggedIn.setForeground(Color.WHITE);
		secBtn.add(loggedIn);
		
		username.setBounds(20, 110, 150, 20);
		username.setFont(new Font("Tahoma", Font.BOLD, 13));
		username.setForeground(Color.WHITE);
		secBtn.add(username);
		
		
		medicineBtn = new JButton("Medicines");
		medicineBtn.setBounds(28, 200, 150, 30);
		medicineBtn.addActionListener(this);
		secBtn.add(medicineBtn);
		
		
		salesBtn = new JButton("Sales");
		salesBtn.setBounds(28, 250, 150, 30);
		salesBtn.addActionListener(this);
		secBtn.add(salesBtn);
		
		
		
		
		changePasswordBtn = new JButton("Change Password");
		changePasswordBtn.setBounds(28, 450, 150, 30);
		changePasswordBtn.addActionListener(this);
		secBtn.add(changePasswordBtn);
		
		logoutBtn = new JButton("Logout");
		logoutBtn.setBounds(28, 500, 150, 30);
		secBtn.add(logoutBtn);
		logoutBtn.addActionListener(this);
		
		
		headerPanel.setBounds(0, 0, 1366, 100);
		headerPanel.setLayout(null);
		headerPanel.setOpaque(false);
		JLabel storeName = new JLabel("Medical Store Management System");
		storeName.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
		storeName.setForeground(Color.WHITE);
		storeName.setBounds(450, 20, 600, 60);
		headerPanel.add(storeName);
		//headerPanel.setBackground(Color.ORANGE);
		ImageIcon img = new ImageIcon(getClass().getResource(
		"/pictures/glare.jpg"));
		Image imag = img.getImage();
		imag = imag.getScaledInstance(1366, 568, Image.SCALE_SMOOTH);
		img.setImage(imag);
		JLabel bgimage = new JLabel(img);
		bgimage.setBounds(0, 0, 1366, 150);
		headerPanel.add(bgimage);
		add(headerPanel);
		
		displayPanel.setBounds(202, 100, 1180, 660);
		displayPanel.setLayout(cardLayout);
		displayPanel.setBorder(new EtchedBorder());
		displayPanel.setBackground(panelColor.darker());
		add(displayPanel);
		add(secBtn);
		
		
		
		
		
		
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, SQLException {
		JFrame fr = new JFrame();
		Container con = fr.getContentPane();
		con.add(new MainPage());
		fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
		fr.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(medicineBtn)){
			try {
				displayPanel.add("stockTable", new Stock());
				cardLayout.show(displayPanel, "stockTable");
				displayPanel.revalidate();
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
		if(e.getSource().equals(salesBtn)){
			try {
				displayPanel.add("sales", new Sales());
				cardLayout.show(displayPanel, "sales");
				displayPanel.revalidate();
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
		
		if(e.getSource().equals(logoutBtn)){
			LoginForm.card.show(LoginForm.con,"page1" );
			LoginForm.passwordTF.setText("");
			LoginForm.usernameTF.setText("");
			LoginForm.pwd=LoginForm.getPasswordFromFile();
		}
		
		if(e.getSource().equals(changePasswordBtn)){
			displayPanel.add("changepwd", new ChangePassword());
			cardLayout.show(displayPanel, "changepwd");
			displayPanel.revalidate();
		}
	}

}

