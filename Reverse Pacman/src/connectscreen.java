import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;

/**
 * Screen for connection help
 * (Only for visualization purpose)
 */
public class connectscreen extends JFrame {

	private JPanel contentPane;
	public JSeparator separator;
	public JLabel lblReadyToConnect;
	public JLabel lblIp;
	public JLabel lblNewLabel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_no;
	public JLabel label_no_1;
	public JLabel label_no_2;
	public JLabel label_no_3;
	public int playerconnect=1;

/**
 * Constructor for the screen
 */
	public connectscreen(int players) throws UnknownHostException {
		setTitle("READY TO CONNECT");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 150);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(255, 153, 0));
		separator.setBackground(new Color(255, 153, 0));
		separator.setBounds(10, -15, 17, 302);
		contentPane.add(separator);
		
		lblReadyToConnect = new JLabel("READY TO CONNECT");
		lblReadyToConnect.setToolTipText("");
		lblReadyToConnect.setForeground(new Color(255, 153, 0));
		lblReadyToConnect.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 16));
		lblReadyToConnect.setBounds(37, 11, 171, 20);
		contentPane.add(lblReadyToConnect);
		
		lblIp = new JLabel("IP Adress: "+Inet4Address.getLocalHost().getHostAddress().toString());
		lblIp.setToolTipText("");
		lblIp.setForeground(Color.LIGHT_GRAY);
		lblIp.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		lblIp.setBounds(160, 42, 211, 20);
		contentPane.add(lblIp);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/ghost_0.gif")));
		lblNewLabel.setBounds(160, 73, 17, 31);
		contentPane.add(lblNewLabel);
		lblNewLabel.setVisible(false);
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/ghost_1.gif")));
		label.setBounds(210, 73, 17, 31);
		contentPane.add(label);
		label.setVisible(false);
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/ghost_2.gif")));
		label_1.setBounds(260, 73, 17, 31);
		contentPane.add(label_1);
		label_1.setVisible(false);
		
		label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/ghost_3.gif")));
		label_2.setBounds(310, 73, 17, 31);
		contentPane.add(label_2);
		label_2.setVisible(false);
		
		label_no = new JLabel("");
		label_no.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/gray.gif")));
		label_no.setBounds(160, 73, 17, 31);
		contentPane.add(label_no);
		
		label_no_1 = new JLabel("");
		label_no_1.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/gray.gif")));
		label_no_1.setBounds(210, 73, 17, 31);
		contentPane.add(label_no_1);
		
		
		label_no_2 = new JLabel("");
		label_no_2.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/gray.gif")));
		label_no_2.setBounds(260, 73, 17, 31);
		contentPane.add(label_no_2);
		
		
		label_no_3 = new JLabel("");
		label_no_3.setIcon(new ImageIcon(connectscreen.class.getResource("/sprites/gray.gif")));
		label_no_3.setBounds(310, 73, 17, 31);
		contentPane.add(label_no_3);
		shownotconnected(players);
	
	}
	/**
	 * Sets ghost visible
	 */
	public void shownotconnected(int player){
		if(player<4)
			label_no_3.setVisible(false);
		if(player<3)
			label_no_2.setVisible(false);
		if(player<2)
			label_no_1.setVisible(false);
		
	}
	
	/**
	 * Switches ghost to connected
	 */
	public void connected_player(){
		switch (playerconnect) {
		case 1:
			lblNewLabel.setVisible(true);
			label_no.setVisible(false);
			break;
		case 2:
			label.setVisible(true);
			label_no_1.setVisible(false);
			break;
		case 3:
			label_1.setVisible(true);
			label_no_2.setVisible(false);
			break;
		case 4:
			label_2.setVisible(true);
			label_no_3.setVisible(false);
			break;

		
		}
		playerconnect++;
		
	}
	

}
