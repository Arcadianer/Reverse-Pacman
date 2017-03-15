import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *  Class responsible for connection between App and Ghost
 *  @author Manuel Plonski
 *  
 */
public class connection {
	/**
	 * Name of the connection 
	 */
	String name;
	 
	ServerSocket sc = null;
	/**
	 * Interface that transmit message to Ghost
	 * @see Bridgehandeler
	 */
	Bridgehandeler ghost;
	Socket conn;
	BufferedReader br;
	Thread listenthread;
	String ipString;
/**
 * Constructor of the connection class.
 * @param port Port of the Serversocket
 * @param reverence {@link Bridgehandeler} from the {@link Ghostplayer}
 * @param name Name of the Connection
 */
	public connection(int port, Bridgehandeler reverence, String name) throws IOException {
		
		sc = new ServerSocket();
		sc.setReuseAddress(true);
		sc.bind(new InetSocketAddress(port));
		this.ghost = reverence;
		this.name = name;
		listenthread = new Thread(new Runnable() {

			@Override
			public void run() {
				String temp = "";
				while (!conn.isClosed()) {

					try {
						temp = br.readLine();
						// System.out.println(temp);
						reverence.messagereceived(temp);
					} catch (IOException e) {

						try {
							reconnect();
						} catch (IOException e1) {
							// TODO Auto-generated catch block

						}
					} catch (NullPointerException e) {

					}
				}

			}
		});
	}
/**
 * Accept incomming connection
 * @return Returns true if successful
 */
	public boolean ackconn() throws IOException {
		System.out.println("[" + name + "] " + "Waiting for connection on " + sc.getLocalPort());
		conn = sc.accept();
		InputStreamReader isr = new InputStreamReader(conn.getInputStream());
		br = new BufferedReader(isr);
		ipString = conn.getInetAddress().getHostAddress().toString();
		System.out.println("[" + name + "] " + "Connection established on " + sc.getLocalPort() + "\n IP: " + ipString);
		sc.close();
		sc=null;
		return true;

	}
	/**
	 * Start listen to Messages from Connection
	 * @return Returns true if successful
	 */
	public boolean startlistener() {
		listenthread.start();

		return true;

	}
	/**
	 * Stops Listener
	 * @return Returns true if successful
	 */
	public boolean stoplistener() {
		listenthread.interrupt();
		return true;
	}
	/**
	 * Disconnects Client
	 * @return Returns true if successful
	 */
	public boolean disconnect() throws IOException {
		if (!conn.isClosed()) {
			listenthread.interrupt();
			br = null;
			conn.close();
			System.out.println("[" + name + "] " + "Connection closed from " + ipString + " on " + sc.getLocalPort());
			return true;
		} else {
			System.out.println("[" + name + "] " + "Can't not disconnect!\n NO CONNECTION ");
			return false;
		}

	}
	/**
	 * Reconnects Client
	 * @return Returns true if successful
	 */
	public boolean reconnect() throws IOException {
		System.out.println("[" + name + "] " + "TRYING RECONNECT");
		if (disconnect()) {
			ackconn();
			System.out.println("[" + name + "] " + "RECONNECTION FROM " + ipString + " ON " + sc.getLocalPort());
			return true;
		} else {
			return false;
		}

	}
	/**
	 * Shutsdown the connection savely
	 * @return Returns true if successful
	 */
	public boolean shutdown() throws IOException {
		disconnect();
		sc.close();
		sc.setReuseAddress(true);
		listenthread = null;
		br = null;
		conn = null;
		sc = null;

		return true;
	}
	
	/**
	 * Get the name of the node
	 * @return name of the node
	 */
	public String getname() {
		return name;
	}

}
