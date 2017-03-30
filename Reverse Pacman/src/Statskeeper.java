import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import util.Stopwatch;

/**
 * Records Game-Statistics from past games
 */

public class Statskeeper {
	/**
	 * Name of the AI to keep track of
	 */
	public String Name;
	/**
	 * Absolute score of the Ai
	 */
	public int absolutscore;
	/**
	 * Minimal score of the Ai
	 */
	public int min;
	/**
	 * Maximum score of the Ai
	 */
	public int max;
	/**
	 * Average score of the Ai
	 */
	public int avescore;
	/**
	 * Amount of Games of the Ai
	 */
	public int amountofgames;
	/**
	 * Amount of playtime of the Ai
	 */
	public int playtime;
	/**
	 * Average playtime of the Ai
	 */
	public int aveplaytime;
	/**
	 * Amount of wins of the Ai
	 */
	public int win;
	/**
	 * Amount if looses of the Ai
	 */
	public int loose;
	/**
	 * ID of the AI
	 */
	public int aiid;
	/**
	 * Logging Started
	 */
	public boolean logging = false;

	/**
	 * {@link BufferedReader} of Stat File
	 */
	public BufferedReader Br;
	/**
	 * Buffertwriter of Stats File
	 */
	public BufferedWriter bw;
	/**
	 * Stopwatch of Stat session
	 */
	public Stopwatch sp;

	public Statskeeper(int aiid) {

		this.aiid = aiid;
		sp = new Stopwatch(1000);
		try {
			loadstats();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Loads stats from file
	 */
	public void loadstats() throws IOException {
		File fl = null;
		try {
			fl = new File("./stats/" + aiid + ".stats");
			System.out.println(fl.getAbsolutePath());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("TRYING ALTERNATIV");
			fl = new File("C:/Users/Manuel/Desktop/REVERSE PACMAN/Reverse Pacman/src/stats/" + aiid + ".stats");

		}
		try {
			this.Br = new BufferedReader(new FileReader(fl));

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("TRYING ALTERNATIV");
			fl = new File("C:/Users/Manuel/Desktop/REVERSE PACMAN/Reverse Pacman/src/stats/" + aiid + ".stats");
			this.Br = new BufferedReader(new FileReader(fl));

		}

		String readstring = "";
		readstring = Br.readLine();
		if (!(readstring == null)) {
			String[] llll = readstring.split(",");
			this.Name = llll[0];
			this.absolutscore = Integer.decode(llll[1]);
			this.min = Integer.decode(llll[2]);
			this.max = Integer.decode(llll[3]);
			this.avescore = Integer.decode(llll[4]);
			this.amountofgames = Integer.decode(llll[5]);
			this.playtime = Integer.decode(llll[6]);
			this.aveplaytime = Integer.decode(llll[7]);
			this.win = Integer.decode(llll[8]);
			this.loose = Integer.decode(llll[9]);

		}

	}

	/**
	 * Updates Stats
	 * 
	 * @param winloose
	 *            1=win 0=loose
	 * @param score
	 *            score of the game
	 */
	public void update(int winloose, int score) {
		if (winloose == 1) {
			win++;
		} else {
			loose++;
		}
		absolutscore = absolutscore + score;
		amountofgames++;
		avescore = absolutscore / amountofgames;
		if (min == 0 && max == 0) {
			min = score;
			max = score;
		}
		if (score > max)
			max = score;
		if (score < min) {
			min = score;
		}

		playtime = playtime + sp.stop();
		aveplaytime = playtime / amountofgames;
	}

	/**
	 * Starts the logging session
	 */
	public void startlog() {
		logging = true;
		System.out.println("[STAT KEEPER] Logging Stats");
		KIData.updatestatus("KEEPING STATS", Color.yellow);
		sp.start();
	}

	/**
	 * Stops the logging session
	 */
	public void stoplog() {

		sp.stop();
	}

	/**
	 * Saves stats
	 */
	public void savelog() throws IOException {
		if (logging) {
			File fl = null;
			try {
				fl = new File("./stats/" + aiid + ".stats");
				this.bw = new BufferedWriter(new FileWriter(fl, false));
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("TRYING ALTERNATIV");
				fl = new File("C:/Users/Manuel/Desktop/REVERSE PACMAN/Reverse Pacman/src/stats/" + aiid + ".stats");

				this.bw = new BufferedWriter(new FileWriter(fl, false));
			}
			String savestring = toString();
			try {
				bw.write(savestring);
				bw.flush();
				bw.close();
				System.out.println(aiid);
				System.out.println(savestring);
				System.out.println("[Stat Keeper] Stats saved");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			System.out.println("[Stat Keeper] No Stats has been saved");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Name + "," + absolutscore + "," + min + "," + max + "," + avescore + "," + amountofgames + "," + playtime
				+ "," + aveplaytime + "," + win + "," + loose + "," + aiid;
	}

}
