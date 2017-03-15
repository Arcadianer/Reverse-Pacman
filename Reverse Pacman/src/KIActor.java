
// PacActor.java
// Used for PacMan

import ch.aplu.jgamegrid.*;
import ch.aplu.jgamegrid.Location.CompassDirection;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import block.block;
import block.nothing;

import java.awt.*;

/**
 * Pacman Actor controlled by AI
 * @author Manuel Plonski
 * 
 */
 
public class KIActor extends Actor implements aiinterface{
	private static final int nbSprites = 4;
	private int idSprite = 0;
	public int nbPills = 0;
	public static boolean getpower = false;
	public KI ki;
	public CompassDirection lastmove;
	public int pacwalked;
	public int PPills = 0;
/**
 * Constructor for KIActor
 */
	public KIActor() {

		super(true, "sprites/pacpix.gif", nbSprites); // Rotatable
		this.ki = new KI(this, KIData.KI_select, PacMan.navi);
		lastmove = CompassDirection.NORTH;
		pacwalked = 0;
	}
	/**
	 * Constructor for KIActor
	 */
	public KIActor(KI ki) {
		super(true, "sprites/pacpix.gif", nbSprites); // Rotatable
		this.ki = ki;
		lastmove = CompassDirection.NORTH;
		pacwalked = 0;
	}
	/**
	 * @see ch.aplu.jgamegrid.Actor#move(int)
	 */
@Override
	public void move(int keyCode) {
		if (isRemoved()) // Already removed
			return;
		Location next = getLocation();
		eatPill(next);
		switch (keyCode) {
		case 0:
			next = getLocation().getNeighbourLocation(Location.WEST);
			setDirection(Location.WEST);
			lastmove = CompassDirection.WEST;
			break;
		case 1:
			next = getLocation().getNeighbourLocation(Location.NORTH);
			setDirection(Location.NORTH);
			lastmove = CompassDirection.NORTH;
			break;
		case 2:
			next = getLocation().getNeighbourLocation(Location.EAST);
			setDirection(Location.EAST);
			lastmove = CompassDirection.EAST;
			break;
		case 3:
			next = getLocation().getNeighbourLocation(Location.SOUTH);
			setDirection(Location.SOUTH);
			lastmove = CompassDirection.SOUTH;
			break;
		}
		if (next != null && canMove(next)) {
			setLocation(next);

		}
		pacwalked++;
	}

	public void act() {
		ki.act();
		if (ki.warningghost) {
			show(4);
		} else {
			show(idSprite);
		}
		idSprite++;
		if (idSprite == nbSprites)
			idSprite = 0;

	}

	private boolean canMove(Location location) {
		block temp = PacGrid.maze[location.y][location.x];
		if (temp.blockmovment) {
			return false;
		} else {
			return true;
		}

	}

	public void eatPill(Location location) {
		Color c = getBackground().getColor(location);
		block temp = PacGrid.maze[location.y][location.x];
		if (temp.id.equals(".")) {
			try {
				Sounds.plop();
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
			nbPills++;

			new nothing().draw(location, getBackground());
			PacGrid.maze[location.y][location.x] = new nothing();

		} else if (temp.id.equals("p")) {
			try {
				Sounds.plop();
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
			nbPills++;
			PPills++;

			System.out.println("Number Powerpills " + PPills);
			Thread tr = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					KIActor.getpower = true;
					KIData.pacpower = true;
					KIData.update();
					System.out.println("PACPOWER 10 SECONDS");
					int temp = Sounds.gamemusic.getFramePosition();
					if (!PacMan.nomusic) {

						Sounds.gamemusic.stop();
						Sounds.ppsound.setFramePosition(0);
						Sounds.ppsound.start();
					}
					try {
						for (int a = 0; a <= 9; a++) {
							if (KIData.statmode) {
								Thread.sleep(500);
							} else {
								Thread.sleep(1000);
							}
							KIActor.getpower = true;
							KIData.pacpower = true;
							KIData.update();
						}

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (!Sounds.ppsound.isRunning()) {
						Sounds.gamemusic.setFramePosition(temp);
						if (!PacMan.nomusic)
							Sounds.gamemusic.loop(Clip.LOOP_CONTINUOUSLY);
						KIActor.getpower = false;
						KIData.pacpower = false;
						KIData.update();
					}
					System.out.println("PACPOWER OVER");

				}

			});
			tr.start();

			new nothing().draw(location, getBackground());
			PacGrid.maze[location.y][location.x] = new nothing();

		}
		gameGrid.setTitle("# SCORE: " + (nbPills + PPills));
		KIData.Score = nbPills + PPills;
		KIData.update();
	}

	/**
	 * @see aiinterface#up()
	 */
	@Override
	public void up() {
		// TODO Auto-generated method stub
		move(1);
		KIData.cdd = CompassDirection.NORTH;
		KIData.update();

	}

	/**
	 * 
	 * @see aiinterface#down()
	 */
	@Override
	public void down() {
		// TODO Auto-generated method stub
		move(3);
		KIData.cdd = CompassDirection.SOUTH;
		KIData.update();
	}

	/**
	 * 
	 * @see aiinterface#left()
	 */
	@Override
	public void left() {
		// TODO Auto-generated method stub
		move(0);
		KIData.cdd = CompassDirection.WEST;
		KIData.update();
	}

	/**
	 * 
	 * @see aiinterface#right()
	 */
	@Override
	public void right() {
		// TODO Auto-generated method stub
		move(2);
		KIData.cdd = CompassDirection.EAST;
		KIData.update();
	}

	/**
	 * 
	 * @see aiinterface#getpower()
	 */
	@Override
	public boolean getpower() {
		// TODO Auto-generated method stub
		return getpower;
	}

	/**
	 * 
	 * @see aiinterface#getpills()
	 */
	@Override
	public int getpills() {
		// TODO Auto-generated method stub
		return nbPills;
	}

	/**
	 * 
	 * @see aiinterface#getlastmove()
	 */
	@Override
	public CompassDirection getlastmove() {
		// TODO Auto-generated method stub
		return lastmove;
	}

	/**
	 * 
	 * @see aiinterface#getwalked()
	 */
	@Override
	public int getwalked() {
		// TODO Auto-generated method stub
		return pacwalked;
	}

	/**
	 * 
	 * @see aiinterface#getPpills()
	 */
	@Override
	public int getPpills() {
		// TODO Auto-generated method stub
		return PPills;
	}
}
