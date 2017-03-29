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
public class End_Game extends JFrame {

	private JPanel contentPane;
	public JSeparator separator;
	public JLabel lblReadyToConnect;
	public int playerconnect=1;

/**
 * Constructor for the screen
 */
	public End_Game(boolean pacmanwin) throws UnknownHostException {
		setTitle("END OF GAME");
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
		
		lblReadyToConnect = new JLabel("GHOSTS WIN");
		lblReadyToConnect.setToolTipText("");
		lblReadyToConnect.setForeground(Color.RED);
		lblReadyToConnect.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 32));
		lblReadyToConnect.setBounds(37, 11, 424, 89);
		contentPane.add(lblReadyToConnect);
		 if(pacmanwin){
			 lblReadyToConnect.setText("PACMAN WINS");
			 lblReadyToConnect.setForeground(Color.GREEN);
		 }
	
	}
	/**
	 * Sets ghost visible
	 */

	
	/**
	 * Switches ghost to connected
	 */

	

}
