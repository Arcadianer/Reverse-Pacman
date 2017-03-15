package block;

import java.awt.Image;
import java.nio.file.Path;

import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.Location;
import scala.collection.immutable.RedBlack.BlackTree;
/**
 * Standart Block in the the Game-Grid block world.
 * It is parented to all the blocks you see on the Game-Grid itself.
 * 
 * @author Manuel Plonski
 * @version 1.2
 */
public abstract class block {
	/**
	 * Name of the Block
	 */
	public String name;
	/**
	 * ID of the Block which identifies it in a Level file
	 * 
	 */
	public String id;
	
	/**
	 * The Path to the image file of the block in the res package
	 * 
	 */
	public String path;
	/**
	 * Blocks movement of an actor to this block
	 */
	public boolean blockmovment=false;
	
	/**
	 * Constructor of an block object
	 * @param name  the name of a block
	 * @param id  the ID of a block
	 * @param path  the image path of the block
	 */
	public block(String name,String id,String path){
		this.name=name;
		this.id=id;
		this.path=path;
		
	}
	/**
	 * Constructor of an {@link block} object
	 * @param name  the name of a {@link block}
	 * @param id  the ID of a {@link block}
	 * @param path  the image path of the {@link block}
	 * @param blockmovement  blocks movement on this {@link block}
	 */
	public block(String name,String id,String path,boolean blockmovement){
		this.name=name;
		this.id=id;
		this.path=path;
		this.blockmovment=blockmovement;
		
	}
	/**
	 * Constructor of an {@link block} object
	 * @param name  the name of a {@link block}
	 * @param id  the ID of a {@link block}
	 */
	public block(String name,String id){
		this.name=name;
		this.id=id;
		
		
	}
	/**
	 *@return Name of the {@link block}
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name of the {@link block}
	 * @param name  Name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 *@return ID of the {@link block}
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Sets ID of the {@link block}
	 * @param id  ID to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 *@return Path to the {@link Image} of the {@link block}
	 */
	public String getPath() {
		return path;
	}
	
	/**
	 * Sets Path to the {@link Image} of an {@link block}
	 * @param path ID to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	
	/**
	 * Draws the {@link Image} of the block on the Game-Grid.  
	 * This abstract method is describes a block should be drawn on the grid itself.
	 * The {@link GGBackground} object "bd" can be used to draw the desired shape or
	 * image to the {@link Location} "lc".
	 * This has to be set in order to draw an Level on the grid.
	 * @param lc Location to draw in the Grid 
	 * @param bd {@link GGBackground} Object to draw
	 */
	public abstract void draw(Location lc,GGBackground bd);
	@Override
	public abstract block clone();
}
