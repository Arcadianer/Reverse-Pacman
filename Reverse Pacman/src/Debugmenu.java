import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Window.Type;
import javax.swing.ImageIcon;
/**
 * Enables different options for development
 */
public class Debugmenu extends JFrame {

	private JPanel contentPane;
	public static boolean[] debugoptions = new boolean[5];
	private boolean debug = false;
	public JSeparator separator;
	public JLabel lblNewLabel_1;

	
	/**
	 * Opens the Debugmenu
	 */

	public Debugmenu() {
		setResizable(false);
		setType(Type.UTILITY);
		setForeground(Color.WHITE);
		setTitle("Debug Options");
		setBounds(100, 100, 319, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("AI DEBUGGING");
		lblNewLabel.setForeground(Color.LIGHT_GRAY);
		lblNewLabel.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
		lblNewLabel.setBounds(69, 82, 185, 35);
		contentPane.add(lblNewLabel);

		JCheckBox chckbxShowNode = new JCheckBox("Show node");
		chckbxShowNode.setToolTipText("Display nodes and their ids");
		chckbxShowNode.setBackground(Color.WHITE);
		chckbxShowNode.setForeground(new Color(255, 153, 0));
		chckbxShowNode.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 12));
		chckbxShowNode.setEnabled(false);
		chckbxShowNode.setBounds(31, 149, 97, 23);
		contentPane.add(chckbxShowNode);

		JCheckBox chckbxIdleGhost = new JCheckBox("Idle Ghost");
		chckbxIdleGhost.setBackground(Color.WHITE);
		chckbxIdleGhost.setForeground(new Color(255, 153, 0));
		chckbxIdleGhost.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 12));
		chckbxIdleGhost.setEnabled(false);
		chckbxIdleGhost.setBounds(31, 225, 97, 23);
		chckbxIdleGhost.setVisible(false);
		contentPane.add(chckbxIdleGhost);

		JCheckBox chckbxShowNodeConnection = new JCheckBox("Show node connection");
		chckbxShowNodeConnection.setToolTipText("Display way connections between nodes");
		chckbxShowNodeConnection.setBackground(Color.WHITE);
		chckbxShowNodeConnection.setForeground(new Color(255, 153, 0));
		chckbxShowNodeConnection.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 12));
		chckbxShowNodeConnection.setEnabled(false);
		chckbxShowNodeConnection.setBounds(31, 175, 166, 23);
		contentPane.add(chckbxShowNodeConnection);
		JCheckBox chckbxDebug = new JCheckBox("Debug");
		chckbxDebug.setToolTipText("Enables debug mode");
		chckbxDebug.setBackground(Color.WHITE);
		chckbxDebug.setForeground(new Color(255, 153, 0));
		chckbxDebug.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 12));
		chckbxDebug.setBounds(31, 123, 97, 23);
		
		contentPane.add(chckbxDebug);
		JCheckBox chckbxcord = new JCheckBox("Draw cords");
		chckbxcord.setToolTipText("Drawes coordinates of maze");
		chckbxcord.setEnabled(false);
		chckbxcord.setBackground(Color.WHITE);
		chckbxcord.setForeground(new Color(255, 153, 0));
		chckbxcord.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 12));
		chckbxcord.setBounds(31, 201, 97, 23);
		contentPane.add(chckbxcord);
		chckbxDebug.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!debug) {
					chckbxIdleGhost.setEnabled(true);
					chckbxShowNode.setEnabled(true);
					chckbxShowNodeConnection.setEnabled(true);
					chckbxcord.setEnabled(true);
					debug=true;
				} else {
					chckbxIdleGhost.setEnabled(false);
					chckbxShowNode.setEnabled(false);
					chckbxShowNodeConnection.setEnabled(false);
					chckbxcord.setEnabled(false);
					debug=false;
				}

			}
		});
		JButton btnApply = new JButton("APPLY");
		btnApply.setToolTipText("");
		btnApply.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		btnApply.setForeground(new Color(255, 153, 0));
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PacMan.debug=chckbxDebug.isSelected();
				PacMan.ghostdumm=chckbxIdleGhost.isSelected();
				PacMan.drawconnection=chckbxShowNodeConnection.isSelected();
				PacMan.drawnode=chckbxShowNode.isSelected();
				PacMan.drawcords=chckbxcord.isSelected();
				setVisible(false);
			}
		});
		btnApply.setBounds(165, 217, 104, 33);
		contentPane.add(btnApply);
		
		separator = new JSeparator();
		separator.setForeground(new Color(255, 204, 0));
		separator.setBackground(Color.ORANGE);
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(5, 0, 7, 271);
		contentPane.add(separator);
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setIcon(new ImageIcon(Debugmenu.class.getResource("/res/debug1.png")));
		lblNewLabel_1.setBounds(23, 0, 160, 71);
		contentPane.add(lblNewLabel_1);

		
	}
}
