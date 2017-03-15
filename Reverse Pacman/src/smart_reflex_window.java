import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * Frame that enables advanced options for the Smart Reflex Agent
 */
public class smart_reflex_window extends JFrame {

	private JPanel contentPane;
	public JLabel lblsmartReflexAgent;
	public JSeparator separator;
	public JLabel label;
	public JLabel lblAgent;
	public JLabel lblGhostScareDistance;
	public JSpinner spinner;
	public JButton button;
	public JCheckBox chckbxDrawAsearch;
	public JLabel lblDrawAsearch;

	
	public smart_reflex_window() {
		setTitle("Smart Reflex Agent");
		setBounds(100, 100, 497, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblsmartReflexAgent = new JLabel("\"SMART REFLEX\"");
		lblsmartReflexAgent.setForeground(Color.LIGHT_GRAY);
		lblsmartReflexAgent.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 28));
		lblsmartReflexAgent.setBounds(225, 11, 283, 51);
		contentPane.add(lblsmartReflexAgent);
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(255, 153, 0));
		separator.setBackground(new Color(255, 153, 0));
		separator.setBounds(10, -14, 17, 302);
		contentPane.add(separator);
		
		label = new JLabel("immage");
		label.setIcon(new ImageIcon(smart_reflex_window.class.getResource("/res/smart_reflex_pic.png")));
		label.setBounds(10, 11, 182, 239);
		contentPane.add(label);
		
		lblAgent = new JLabel("AGENT");
		lblAgent.setForeground(Color.LIGHT_GRAY);
		lblAgent.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 28));
		lblAgent.setBounds(338, 49, 96, 51);
		contentPane.add(lblAgent);
		
		lblGhostScareDistance = new JLabel("Ghost Scare Distance");
		lblGhostScareDistance.setToolTipText("Distance which triggers the ghost alert event");
		lblGhostScareDistance.setForeground(new Color(255, 153, 0));
		lblGhostScareDistance.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 16));
		lblGhostScareDistance.setBounds(265, 117, 157, 20);
		contentPane.add(lblGhostScareDistance);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(3, 1, 10, 1));
		spinner.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		spinner.setBounds(432, 115, 39, 23);
		contentPane.add(spinner);
		
		button = new JButton("APPLY");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				KIData.Ghost_Scare_distance=(int) spinner.getValue();
				KIData.draw_asearch=chckbxDrawAsearch.isSelected();
			}
		});
		button.setForeground(Color.GRAY);
		button.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		button.setBounds(382, 227, 89, 23);
		contentPane.add(button);
		
		chckbxDrawAsearch = new JCheckBox();
		chckbxDrawAsearch.setBackground(Color.WHITE);
		chckbxDrawAsearch.setBounds(432, 148, 21, 20);
		
		contentPane.add(chckbxDrawAsearch);
		
		lblDrawAsearch = new JLabel("Draw A*-Search");
		lblDrawAsearch.setToolTipText("Distance which triggers the ghost alert event");
		lblDrawAsearch.setForeground(new Color(255, 153, 0));
		lblDrawAsearch.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 16));
		lblDrawAsearch.setBounds(303, 148, 119, 20);
		contentPane.add(lblDrawAsearch);
	}
}
