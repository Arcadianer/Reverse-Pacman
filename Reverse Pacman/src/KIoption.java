import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ch.aplu.jgamegrid.Location.CompassDirection;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Choice;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSeparator;

/**
 * AI Option Frame Is used to set different AI parameters, start , stop or step
 * the current game. Further more can it be used to set different AI Algorithms
 * to use for the current game.
 */
public class KIoption extends JFrame implements kidataupdater {

	private JPanel contentPane;
	public JButton btnNewButton;
	public JLabel lblNewLabel;
	public JComboBox comboBox;
	public JLabel lblUtillity;
	public JLabel lblScore;
	public JLabel arrow;
	public JButton btnPause;
	public minmaxwindow minmax;
	public smart_reflex_window smartreflexwindow;
	public JSpinner spinner;
	public JLabel lblSimspeed;
	public JButton btnStep;
	public JLabel pill;
	public JLabel lblPacmanPic;
	public JSeparator separator;
	public JSeparator separator_1;
	public JSeparator separator_2;
	public JLabel lblAiStatus;
	public JLabel lblDirection;
	public JLabel lblPowerpode;
	public JButton statsbutton;
	public JLabel lblArtificialIntelligenceCockpit;
	public JLabel lblCurrentAi;

	/**
	 * Create the frame.
	 */
	public KIoption() {
		setResizable(false);
		setTitle("ARTIFICIAL INTELLIGENCE COCKPIT");
		setBounds(100, 100, 771, 614);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		minmax = new minmaxwindow();
		btnNewButton = new JButton("APPLY");
		btnNewButton.setForeground(new Color(255, 102, 0));
		btnNewButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));

		btnNewButton.setBounds(46, 253, 147, 36);
		contentPane.add(btnNewButton);

		lblNewLabel = new JLabel("NOT RUNNING");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 24));
		lblNewLabel.setBounds(283, 169, 302, 56);
		contentPane.add(lblNewLabel);

		comboBox = new JComboBox();
		comboBox.setForeground(Color.GRAY);
		comboBox.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 13));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"SMART_REFLEX_AGENT", "MINIMAX", "RANDOM", "SIMPLE_FIND_PILLS", "CRAZY_FIND_PILLS", "AVOID_GHOST", "MINIMAX_DYNAMIC"}));
		comboBox.setSelectedIndex(1);

		comboBox.setBounds(46, 450, 147, 20);
		contentPane.add(comboBox);

		lblUtillity = new JLabel("utillity : 000");
		lblUtillity.setForeground(Color.GRAY);
		lblUtillity.setHorizontalAlignment(SwingConstants.LEFT);
		lblUtillity.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblUtillity.setBounds(390, 365, 286, 56);
		contentPane.add(lblUtillity);

		lblScore = new JLabel("score : 000");
		lblScore.setForeground(Color.GRAY);
		lblScore.setHorizontalAlignment(SwingConstants.LEFT);
		lblScore.setFont(new Font("Segoe UI Light", Font.PLAIN, 14));
		lblScore.setBounds(390, 391, 128, 79);
		contentPane.add(lblScore);

		arrow = new JLabel("");
		arrow.setIcon(new ImageIcon(KIoption.class.getResource("/res/Arrowup.png")));
		arrow.setBounds(562, 146, 79, 79);
		contentPane.add(arrow);

		btnPause = new JButton("Pause");
		btnPause.setToolTipText("Freezees the game / Continues the game");
		btnPause.setForeground(Color.GRAY);
		btnPause.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnPause.getText().equals("Pause")) {
					KIData.gm.musicstop();
					KIData.gm.pause();
					btnPause.setText("Run");
					btnStep.setEnabled(true);

				} else {
					btnPause.setText("Pause");
					KIData.gm.musicplay();
					KIData.gm.resume();
					btnStep.setEnabled(false);
				}
			}
		});
		btnPause.setBounds(46, 354, 120, 34);
		contentPane.add(btnPause);

		spinner = new JSpinner();
		spinner.setToolTipText("Delay time between ticks in ms");
		spinner.setForeground(Color.GRAY);
		spinner.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		spinner.setModel(new SpinnerNumberModel(60, 60, 10000, 1));
		spinner.setBounds(119, 498, 74, 20);
		contentPane.add(spinner);
		spinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				try {
					KIData.gm.setgamespeed((int) spinner.getValue());
				
				} catch (Exception e2) {
					// TODO: handle exception
				}
				KIData.simspeed=(int) spinner.getValue();
			}
		});
		lblSimspeed = new JLabel("Simspeed :");
		lblSimspeed.setToolTipText("Delay time between ticks in ms");
		lblSimspeed.setForeground(Color.GRAY);
		lblSimspeed.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		lblSimspeed.setBounds(46, 497, 89, 23);
		contentPane.add(lblSimspeed);

		btnStep = new JButton("Step");
		btnStep.setToolTipText("Triggers game tick");
		btnStep.setFont(new Font("Microsoft YaHei Light", Font.PLAIN, 16));
		btnStep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KIData.gm.dostep();
				System.out.println("STEEEEP");
			}
		});
		btnStep.setEnabled(false);
		btnStep.setBounds(46, 399, 120, 34);
		contentPane.add(btnStep);

		pill = new JLabel("");
		pill.setEnabled(false);
		pill.setIcon(new ImageIcon(KIoption.class.getResource("/res/rote-pille.png")));
		pill.setBounds(651, 146, 79, 79);
		contentPane.add(pill);

		lblPacmanPic = new JLabel("PACMAN PIC");
		lblPacmanPic.setIcon(new ImageIcon(KIoption.class.getResource("/res/aicock.png")));
		lblPacmanPic.setBounds(20, 20, 200, 200);
		contentPane.add(lblPacmanPic);

		separator = new JSeparator();
		separator.setForeground(new Color(255, 204, 0));
		separator.setBackground(new Color(255, 204, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(262, 11, 46, 652);
		contentPane.add(separator);

		separator_1 = new JSeparator();
		separator_1.setForeground(Color.LIGHT_GRAY);
		separator_1.setBackground(Color.LIGHT_GRAY);
		separator_1.setBounds(309, 231, 430, 2);
		contentPane.add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setForeground(Color.LIGHT_GRAY);
		separator_2.setBackground(Color.LIGHT_GRAY);
		separator_2.setBounds(309, 236, 430, 2);
		contentPane.add(separator_2);

		lblAiStatus = new JLabel("AI Status");
		lblAiStatus.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		lblAiStatus.setForeground(Color.GRAY);
		lblAiStatus.setBounds(389, 242, 89, 14);
		contentPane.add(lblAiStatus);

		lblDirection = new JLabel("Direction");
		lblDirection.setForeground(Color.GRAY);
		lblDirection.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		lblDirection.setBounds(572, 242, 89, 14);
		contentPane.add(lblDirection);

		lblPowerpode = new JLabel("Powermode");
		lblPowerpode.setForeground(Color.GRAY);
		lblPowerpode.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		lblPowerpode.setBounds(661, 239, 89, 20);
		contentPane.add(lblPowerpode);

		statsbutton = new JButton("Statistics");
		statsbutton.setToolTipText("Shows statistics about the different agents");
		statsbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Statdisplay st = new Statdisplay();
				st.setVisible(true);
				st.setDefaultCloseOperation(HIDE_ON_CLOSE);
			}
		});
		statsbutton.setForeground(Color.GRAY);
		statsbutton.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		statsbutton.setBounds(46, 307, 120, 36);
		contentPane.add(statsbutton);

		lblArtificialIntelligenceCockpit = new JLabel("ARTIFICIAL INTELLIGENCE COCKPIT");
		lblArtificialIntelligenceCockpit.setForeground(Color.LIGHT_GRAY);
		lblArtificialIntelligenceCockpit.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 22));
		lblArtificialIntelligenceCockpit.setBounds(327, 20, 388, 69);
		contentPane.add(lblArtificialIntelligenceCockpit);
		
		lblCurrentAi = new JLabel("Current AI : Minimax");
		lblCurrentAi.setForeground(Color.RED);
		lblCurrentAi.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 17));
		lblCurrentAi.setBounds(390, 321, 365, 33);
		contentPane.add(lblCurrentAi);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(comboBox.getSelectedIndex() + 1);
				switch (comboBox.getSelectedIndex() + 1) {
				case 1:
					KIData.KI_select = 4;
					lblCurrentAi.setText("Current AI : Smart Reflex Agent");
					break;
				case 2:
					KIData.KI_select = 5;
					lblCurrentAi.setText("Current AI : Minimax");
					break;
				case 3:
					KIData.KI_select = 6;
					lblCurrentAi.setText("Current AI : Random");
					break;
				case 4:
					KIData.KI_select = 0;
					lblCurrentAi.setText("Current AI : Simple Find Pills");
					break;
				case 5:
					KIData.KI_select = 1;
					lblCurrentAi.setText("Current AI : Crazy Find Pills");
					break;
				case 6:
					KIData.KI_select = 3;
					lblCurrentAi.setText("Current AI : Ghost Avoid");
					break;

				case 7:
					KIData.KI_select = 7;
					lblCurrentAi.setText("Current AI : Minimax Dynamic");
					break;

				}
				
				if(!(smartreflexwindow==null))
					smartreflexwindow.setVisible(false);
				smartreflexwindow=new smart_reflex_window();
				if(!(minmax==null))
					minmax.setVisible(false);
				minmax = new minmaxwindow();
				if (KIData.KI_select == 5 || KIData.KI_select==7) {
					
					minmax.setVisible(true);
				} else {

					minmax.setVisible(false);
				}
				if (KIData.KI_select == 4) {
					
					smartreflexwindow.setVisible(true);
				} else {

					smartreflexwindow.setVisible(false);
				}
			}
		});
	}

	/**
	 * 
	 * @see kidataupdater#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (KIData.running) {
			lblNewLabel.setText(KIData.KISTATUS);
			lblNewLabel.setForeground(KIData.messagecolore);
		} else {
			lblNewLabel.setText("NOT RUNNING");
			lblNewLabel.setForeground(Color.red);
		}

		lblUtillity.setText("utillity : " + substringdata(KIData.utillity + ""));
		lblScore.setText("score : " + KIData.Score );
		if (KIData.pacpower) {
			pill.setEnabled(true);
		} else {
			pill.setEnabled(false);
		}
		switch (KIData.cdd) {
		case NORTH:

			arrow.setIcon(new ImageIcon(KIoption.class.getResource("/res/Arrowup.png")));
			break;
		case SOUTH:
			arrow.setIcon(new ImageIcon(KIoption.class.getResource("/res/Arrowdown.png")));

			break;
		case EAST:
			arrow.setIcon(new ImageIcon(KIoption.class.getResource("/res/Arrowright.png")));

			break;
		case WEST:
			arrow.setIcon(new ImageIcon(KIoption.class.getResource("/res/Arrowleft.png")));

			break;

		}
	}

	/**
	 * Substrings the data
	 */
	public String substringdata(String input) {

		int pointpos = 0;
		pointpos = input.indexOf('.');

		String ret = input.substring(0, pointpos + 2);

		return ret;

	}
}
