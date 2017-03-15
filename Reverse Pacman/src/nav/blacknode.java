package nav;
/**
 *  Blacklist Node
 */
public class blacknode {

	private int count = 1;
	private node thenode;

	public blacknode(node thenode) {
		this.thenode = thenode;
	}

	public void add() {
		count++;
	}

	public node getnode() {
		return thenode;
	}

	public int getcount() {
		return count;
	}

	public String toString() {
		String temp="("+thenode.name+"|"+count+")";
		return temp;
	}

}
