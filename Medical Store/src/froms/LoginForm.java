package froms;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LoginForm extends JFrame implements ActionListener {

	JLabel username;
	static JTextField usernameTF;
	JLabel password;
	static JPasswordField passwordTF;
	JButton loginBtn;
	static String pwd = getPasswordFromFile();
	static Container con;
	static CardLayout card;
	static JPanel homePanel;
	MainPage mainpage;

	public LoginForm() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		super("Medical Store Management System");

		Font labelFont = new Font("Tahoma", Font.BOLD, 13);
		Font textFieldFont = new Font("Tahoma", Font.PLAIN, 13);
		card = new CardLayout();
		setLayout(card);
		UIManager
				.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		ImageIcon pic = new ImageIcon(getClass().getResource(
				"/pictures/redwhite.png"));
		Image im = pic.getImage();
		setIconImage(im);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon img = new ImageIcon(getClass().getResource(
				"/pictures/medicine.jpg"));
		Image imag = img.getImage();
		imag = imag.getScaledInstance(1368, 768, Image.SCALE_SMOOTH);
		img.setImage(imag);
		JLabel bgimage = new JLabel(img);
		bgimage.setLayout(null);
		bgimage.setBounds(0, 0, 1366, 768);

		JPanel panelForHomepage = new JPanel();
		panelForHomepage.setOpaque(false);
		panelForHomepage.setLayout(null);
		panelForHomepage.setBounds(20, 250, 300, 200);

		username = new JLabel("Username");
		username.setBounds(10, 10, 70, 20);
		username.setFont(labelFont);
		username.setForeground(Color.WHITE);
		panelForHomepage.add(username);

		usernameTF = new JTextField("Enter Username");
		usernameTF.setBounds(120, 40, 150, 27);
		usernameTF.setFont(textFieldFont);
		usernameTF.setForeground(Color.LIGHT_GRAY);
		usernameTF.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (usernameTF.getText().equals("Enter Username"))
					usernameTF.setText("");
			}

			public void focusLost(FocusEvent arg0) {
				if (usernameTF.getText().equals("")) {
					usernameTF.setForeground(Color.LIGHT_GRAY);
					usernameTF.setText("Enter Username");
				}
			};
		});
		usernameTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent arg0) {
				usernameTF.setForeground(Color.BLACK);
			}
		});
		panelForHomepage.add(usernameTF);

		password = new JLabel("Password");
		password.setBounds(10, 80, 70, 20);
		password.setFont(labelFont);
		password.setForeground(Color.WHITE);
		panelForHomepage.add(password);

		passwordTF = new JPasswordField("Enter Password");
		passwordTF.setBounds(120, 110, 150, 27);
		passwordTF.setFont(textFieldFont);
		passwordTF.setForeground(Color.LIGHT_GRAY);
		passwordTF.setEchoChar((char) 0);
		passwordTF.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				passwordTF.setText("");
				// passwordTF.setEchoChar('\u25CF');
			}

			public void focusLost(FocusEvent arg0) {
				String pwd = new String(passwordTF.getPassword());
				if (pwd.equals("")) {
					passwordTF.setEchoChar((char) 0);
					passwordTF.setForeground(Color.LIGHT_GRAY);
					passwordTF.setText("Enter Password");
				}
			};
		});
		passwordTF.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				passwordTF.setForeground(Color.BLACK);
				passwordTF.setEchoChar('\u25CF');
			}
		});
		panelForHomepage.add(passwordTF);

		loginBtn = new JButton("Login");
		loginBtn.setBounds(110, 170, 70, 30);
		loginBtn.setFont(labelFont);
		loginBtn.setBackground(Color.BLACK);
		loginBtn.setForeground(Color.WHITE);
		loginBtn.addActionListener(this);
		panelForHomepage.add(loginBtn);

		bgimage.add(panelForHomepage);
		homePanel = new JPanel();
		homePanel.setLayout(null);
		homePanel.setBounds(0, 0, 1366, 768);
		homePanel.add(bgimage);

		con = getContentPane();
		con.add(homePanel, "page1");

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}

	static String getPasswordFromFile() {
		String pass = "";
		try {
			int ch;
			File f1 = new File("pwd.txt");
			if(f1.exists()==false){
				f1.createNewFile();
				FileWriter fw = new FileWriter(f1);
				fw.write("shahroz14");
				fw.flush();
				fw.close();
			}
			FileReader fr = new FileReader(f1);
			while ((ch = fr.read()) != -1) {
				pass = pass + (char) ch;
			}
			//System.out.println(" red "+pass);
			LoginForm.pwd = pass;
			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pass;
	}

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		new LoginForm();

	}

	public void actionPerformed(ActionEvent e) {
		String password = new String(passwordTF.getPassword());
		if (password.equals(pwd)) {
			if (usernameTF.getText().equals("")
					|| usernameTF.getText().equals("Enter Username")) {
				JOptionPane.showMessageDialog(this, "Enter Username",
						"Message", JOptionPane.WARNING_MESSAGE, null);
			} else {
				try {
					mainpage = new MainPage();
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
				add(mainpage, "first");
				card.show(con, "first");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Incorrect Password",
					"Authentication Error", JOptionPane.ERROR_MESSAGE, null);

		}
	}

}
