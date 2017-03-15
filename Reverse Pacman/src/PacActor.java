// PacActor.java
// Used for PacMan

import ch.aplu.jgamegrid.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import block.block;
import block.nothing;

import java.awt.*;
/**
 * Pacman Actor
 */
@Deprecated

public class PacActor extends Actor implements GGKeyRepeatListener
{
  private static final int nbSprites = 4;
  private int idSprite = 0;
 public int nbPills = 0;
  public static boolean getpower=false;
/**
 * Class Constructor
 */
  public PacActor()
  {
    super(true, "sprites/pacpix.gif", nbSprites);  // Rotatable
  }
/**
 * @see ch.aplu.jgamegrid.GGKeyRepeatListener#keyRepeated(int)
 */
  public void keyRepeated(int keyCode)
  {
    if (isRemoved())  // Already removed
      return;
    Location next = null;
    switch (keyCode)
    {
      case KeyEvent.VK_LEFT:
        next = getLocation().getNeighbourLocation(Location.WEST);
        setDirection(Location.WEST);
        break;
      case KeyEvent.VK_UP:
        next = getLocation().getNeighbourLocation(Location.NORTH);
        setDirection(Location.NORTH);
        break;
      case KeyEvent.VK_RIGHT:
        next = getLocation().getNeighbourLocation(Location.EAST);
        setDirection(Location.EAST);
        break;
      case KeyEvent.VK_DOWN:
        next = getLocation().getNeighbourLocation(Location.SOUTH);
        setDirection(Location.SOUTH);
        break;
    }
    if (next != null && canMove(next))
    {
      setLocation(next);
      eatPill(next);
    }
  }
/**
 * @see ch.aplu.jgamegrid.Actor#act()
 */
  public void act()
  {
    show(idSprite);
    idSprite++;
    if (idSprite == nbSprites)
      idSprite = 0;
  }

  private boolean canMove(Location location)
  {
	  block temp=PacGrid.maze[location.y][location.x];
		if(temp.blockmovment){
			return false;
		}else{
			return true;
		}
		
	  
  }
/**
 * Eats pill at Location
 * @param location Location of pill to eat
 */
  private void eatPill(Location location)
  {
    Color c = getBackground().getColor(location);
    block temp=PacGrid.maze[location.y][location.x];
    if (temp.id.equals("."))
    {
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
      PacGrid.maze[location.y][location.x]=new nothing();
      
      gameGrid.setTitle("# Eaten Pills: " + nbPills);
    }else if (temp.id.equals("p")){
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
      Thread tr =new Thread(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			PacActor.getpower=true;
			int temp=Sounds.gamemusic.getFramePosition();
			Sounds.gamemusic.stop();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Sounds.gamemusic.setFramePosition(temp);
			Sounds.gamemusic.loop(Clip.LOOP_CONTINUOUSLY);
			PacActor.getpower=false;
		}
		
	});
      tr.start();
      new nothing().draw(location, getBackground());
      PacGrid.maze[location.y][location.x]=new nothing();
      
      gameGrid.setTitle("# Eaten Pills: " + nbPills);
    }
    
  }
}
