import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.ChangedCharSetException;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Window.Type;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
/**
 * Mainmenu
 */
public class Mainmenu extends JFrame {

	private JPanel contentPane;
	private JTextField txtLevel;
	private PacMan pm;
	boolean wait=true;
	public String level;
	public int Player;
	public Sounds sn;
	public PacGrid pg;
	public Debugmenu db;
	public KIoption ko;
	public boolean Ghostki;
	public JButton btnKiOptionen;
	private JSeparator separator;
	private JLabel image_ai;
	private JLabel lblReversePacman;
	private JRadioButton rdbtnDoItYour;
	private JRadioButton rdbtnAicontroll;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblOr;
	/**
	 * Mainmenue of the Game
	 */
	public Mainmenu() throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		setResizable(false);
		setBackground(new Color(255, 204, 0));
		setFont(new Font("Segoe UI Light", Font.PLAIN, 12));
		sn=new Sounds();
		sn.menusound.loop(Clip.LOOP_CONTINUOUSLY);
		setTitle("Reverse PAC-MAN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1186, 688);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		db=new Debugmenu();
		ko=new KIoption();
		KIData.addupdater(ko);
		
		JLabel lblLevel = new JLabel("Level name");
		lblLevel.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 19));
		lblLevel.setBounds(720, 435, 110, 23);
	
		contentPane.add(lblLevel);
		
		JSpinner spinner = new JSpinner();
		spinner.setToolTipText("<html>Number of ghosts:<br><br>\r\n\r\nWhich should compete against Pac-Man</html>\r\n");
		spinner.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 19));
		spinner.setModel(new SpinnerNumberModel(1, 0, 4, 1));
		spinner.setBounds(902, 347, 39, 29);
		spinner.setValue(2);
		contentPane.add(spinner);
		
		JLabel lblPlayers = new JLabel("Number of ghosts");
		lblPlayers.setToolTipText("<html>Number of ghosts:<br><br>\r\n\r\nWhich should compete against Pac-Man</html>\r\n");
		lblPlayers.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 19));
		lblPlayers.setBounds(710, 351, 185, 25);
		contentPane.add(lblPlayers);
		
		txtLevel = new JTextField();
		txtLevel.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		txtLevel.setText("level1");
		txtLevel.setToolTipText("Level name in here");
		txtLevel.setBounds(902, 434, 167, 23);
		contentPane.add(txtLevel);
		txtLevel.setColumns(10);
		JButton btnNewButton = new JButton("P L A Y");
		btnNewButton.setToolTipText("GUESS WHAT THIS BUTTON WILL DO");
		btnNewButton.setForeground(Color.GRAY);
		btnNewButton.setBackground(new Color(255, 204, 0));
		
		btnNewButton.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 25));
		btnNewButton.setBounds(780, 564, 174, 37);
		contentPane.add(btnNewButton);
		
		JCheckBox chckbxAwesomeMusic = new JCheckBox("Awesome music");
		chckbxAwesomeMusic.setToolTipText("Its a surprise");
		chckbxAwesomeMusic.setForeground(Color.LIGHT_GRAY);
		chckbxAwesomeMusic.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 19));
		chckbxAwesomeMusic.setBackground(Color.WHITE);
		chckbxAwesomeMusic.setBounds(710, 470, 185, 23);
		contentPane.add(chckbxAwesomeMusic);
		
		JButton btnDebug = new JButton("DEBUG");
		btnDebug.setToolTipText("<html>Debugger:<br><br>\r\n\r\nSome more options mainly for developer</html>");
		btnDebug.setBackground(Color.LIGHT_GRAY);
		btnDebug.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		btnDebug.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(db.isVisible()){
					db.setVisible(false);
				}else{
					db.setVisible(true);
				}
			}
		});
		btnDebug.setBounds(969, 564, 112, 37);
		contentPane.add(btnDebug);
		
		btnKiOptionen = new JButton("AI-COCKPIT");
		btnKiOptionen.setToolTipText("<html>AI - Cockpit:<br><br> \r\n\r\nSelect the kind of agent for pacman and some specific options as well as browse the Actions of the agents</html>");
		btnKiOptionen.setBackground(Color.LIGHT_GRAY);
		btnKiOptionen.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		btnKiOptionen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ko.setVisible(true);
			}
		});
		btnKiOptionen.setBounds(646, 564, 119, 37);
		contentPane.add(btnKiOptionen);
		
		separator = new JSeparator();
		separator.setToolTipText("ICH BIN EINE LINIE");
		separator.setForeground(new Color(255, 204, 0));
		separator.setBackground(new Color(255, 204, 0));
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(554, 0, 15, 632);
		contentPane.add(separator);
		
		image_ai = new JLabel("");
		image_ai.setToolTipText("ICH BIN EIN BILD");
		image_ai.setIcon(new ImageIcon(Mainmenu.class.getResource("/res/robo_with_pac_1.png")));
		image_ai.setBounds(60, 91, 420, 367);
		contentPane.add(image_ai);
		
		lblReversePacman = new JLabel("");
		lblReversePacman.setIcon(new ImageIcon(Mainmenu.class.getResource("/res/pac-man-schriftzug_2.png")));
		lblReversePacman.setFont(new Font("PacFont Good", Font.PLAIN, 16));
		lblReversePacman.setBounds(50, 474, 427, 43);
		contentPane.add(lblReversePacman);
		
		JLabel lblWaasig = new JLabel("!!!! REMEMBER !!!!");
		lblWaasig.setForeground(new Color(255, 204, 0));
		lblWaasig.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 22));
		lblWaasig.setBounds(753, 40, 219, 20);
		contentPane.add(lblWaasig);
		
		JLabel lblInfoText = new JLabel("<html>Pac-Man is controlled by an Artificial Intelligence<br>witch can be chosen in the \"AI-Cockpit\" menue</html>");
		lblInfoText.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		lblInfoText.setBounds(660, 52, 421, 100);
		contentPane.add(lblInfoText);
		
		JLabel lblGhostControllText = new JLabel("YOU can choose Ghost-Control");
		lblGhostControllText.setToolTipText("<html>Artificial Intelligence (AI):<br><br>\r\n\r\nLet ghosts beeing controlled by a plain AI to concentrate on observation of Pac-Man's AI</html>\r\n");
		lblGhostControllText.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 22));
		lblGhostControllText.setBounds(685, 225, 385, 73);
		contentPane.add(lblGhostControllText);
		
		rdbtnDoItYour = new JRadioButton("Do it your self");
		rdbtnDoItYour.setToolTipText("<html>\r\nDo it yourself:<br><br>\r\n\r\nControlling a ghost<br>\r\nvia mobile app</html>\r\n");
		rdbtnDoItYour.setForeground(new Color(255, 204, 0));
		rdbtnDoItYour.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		rdbtnDoItYour.setBackground(Color.WHITE);
		buttonGroup.add(rdbtnDoItYour);
		rdbtnDoItYour.setBounds(672, 281, 165, 29);
		contentPane.add(rdbtnDoItYour);
		
		rdbtnAicontroll = new JRadioButton("AI-Control");
		rdbtnAicontroll.setToolTipText("<html>Artificial Intelligence (AI):<br><br>\r\n\r\nLet ghosts beeing controlled by a plain AI to concentrate on observation of Pac-Man's AI</html>\r\n");
		rdbtnAicontroll.setForeground(new Color(255, 204, 0));
		rdbtnAicontroll.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		rdbtnAicontroll.setBackground(Color.WHITE);
		rdbtnAicontroll.setSelected(true);
		buttonGroup.add(rdbtnAicontroll);
		rdbtnAicontroll.setBounds(893, 281, 155, 29);
		contentPane.add(rdbtnAicontroll);
		
		lblOr = new JLabel("OR");
		lblOr.setFont(new Font("Microsoft JhengHei Light", Font.BOLD, 18));
		lblOr.setBounds(848, 285, 39, 20);
		contentPane.add(lblOr);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setToolTipText("STELL DIR VOR .... ICH AUCH");
		separator_1.setBackground(new Color(255, 204, 0));
		separator_1.setForeground(new Color(255, 204, 0));
		separator_1.setBounds(554, 392, 626, 10);
		contentPane.add(separator_1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pg=new PacGrid("level/"+txtLevel.getText());
				Player=(int) spinner.getValue();
				if(Player==0){
					PacMan.ghostdumm=true;
				}
				Ghostki=rdbtnAicontroll.isSelected();
				try {
					sn.setgamemusic(chckbxAwesomeMusic.isSelected());
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setVisible(false);
				wait=false;
			}
		});
		
	}
	@Deprecated
	public ArrayList<String> loadlvllist(String path,JList list){
		try {
			ArrayList<String> as=new ArrayList<String>();
			String lvpath=path+".txt";
		DefaultListModel<String> lm=new DefaultListModel<String>();
			InputStream is=getClass().getResourceAsStream(lvpath);
			
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			
			String stemp=br.readLine();
			while(!(stemp==null)){
				as.add(stemp);
				lm.addElement(stemp);
				stemp=br.readLine();
				
			}
			
			list=new JList(lm);
		
		       
			return as;
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return null;
	}
}
