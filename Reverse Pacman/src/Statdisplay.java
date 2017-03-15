import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 * Displays Stats  
 */
public class Statdisplay extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	JTable table;

	

	/**
	 * Create the frame.
	 */
	public Statdisplay() {
		setTitle("Statistic Screen");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 994, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		table = new JTable();
try {
	loadstats();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setLocation(0, 0);
		scrollPane.setSize(985, 361);
		contentPane.add(scrollPane);
	}
/**
 * Loads Stat's from previous Pac-Man Games
 */
	public void loadstats() throws IOException {
		String[][] rowData = new String[6][11];

		String[] columnNames = { "Ai Name", "Absolute Score", "Min", "Max", "Average Score", "Games",
				"Absolute Playtime", "Average Playtime", "Wins", "Looses" , "ID AI"};
		for (int aiid = 1; aiid <= 6; aiid++) {

			File fl = null;
			BufferedReader Br = null;
			try {
				fl=new File("./stats/"+aiid+".stats");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("TRYING ALTERNATIV");
				fl = new File("C:/Users/Manuel/Desktop/REVERSE PACMAN/Reverse Pacman/src/stats/" + aiid + ".stats");

			}
			try {
				Br = new BufferedReader(new FileReader(fl));

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("TRYING ALTERNATIV");
				fl = new File("C:/Users/Manuel/Desktop/REVERSE PACMAN/Reverse Pacman/src/stats/" + aiid + ".stats");
				Br = new BufferedReader(new FileReader(fl));

			}

			String readstring = "";
			readstring = Br.readLine();
			if (!(readstring == null)) {
				String[] llll = readstring.split(",");
			
				rowData[aiid - 1] = llll;
				
			}
		}
		this.table=new JTable(rowData,columnNames);
	}
	
	

}
