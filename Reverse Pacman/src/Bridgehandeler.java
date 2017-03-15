/**
 * Connection class between {@link connection} and {@link Ghostplayer}
 */
public interface Bridgehandeler {
	/**
	 * Sets connection connection on {@link Ghostplayer}
	 */
public void setcon(connection con);
/**
 * Gets Connection from {@link Ghostplayer}
 * @return {@link connection}
 */
public connection getcon();
/**
 * Sends message from {@link connection} to {@link Ghostplayer}
 */
	public void messagereceived(String message);
}
