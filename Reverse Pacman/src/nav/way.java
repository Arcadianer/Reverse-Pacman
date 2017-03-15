package nav;
/**
 * A possible way to walk with a length of the path and the endpoint
 */
public class way {
	/**
	 * Endpoint of the path
	 */
	public node endpoint;
	/**
	 * Lenghth of the path
	 */
	public int length;

	public way(node endpoint, int length) {
		this.endpoint = endpoint;
		this.length = Math.abs(length);
	}
	public String toString(){
		return endpoint.name;
	}

}
