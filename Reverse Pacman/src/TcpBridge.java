import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
/**
 * Bridge between app and Ghost 
 */
public class TcpBridge {
	ArrayList<connection> loc = new ArrayList<connection>();
	int startport=1000;
	
	public TcpBridge(){
		
	}
	/**
	 * {@link Constructor} of the Class
	 * @param startport int of first Port
	 */
	public TcpBridge(int startport){
		this.startport=startport;
	}
	/**
	 * Opens a new connection for a Ghost
	 * @param br {@link Bridgehandeler} to conroll 
	 * @param name name of the connection
	 */
	public void addPlayer(Bridgehandeler br, String name) throws IOException {
		if(!PacMan.ghostki){
		int port=loc.size()+startport;
		connection temp=new connection(port, br, name);
		br.setcon(temp);
		loc.add(temp);
		System.out.println("[TCP-B] "+name+" has been added to Connection list");
		}
	}
	/**
	 * Shutsdown a Connection and deletes it
	 * @param name of Connection to shut down
	 */
	public void delPlayer(String name) throws IOException{
		connection player = null;
		for (connection connection : loc) {
			if(connection.getname().equals(name)){
				player=connection;
			}
		}
		if(!(player==null)){
		player.shutdown();
		loc.remove(player);
		System.out.println("[TCP-B] "+name+" has been removed");
		}else{
			System.out.println("[TCP-B] "+name+" has not been found");
		}
		
	}
	/**
	 * Accept all incoming connections
	 */
	public boolean ackall() throws IOException{
		for (connection connection : loc) {
			connection.ackconn();
			KIData.cs.connected_player();
			System.out.println("[TCP-B] moving to next Client");
			
		}
		System.out.println("[TCP-B] ALL CLIENTS CONNECTED");
		
		return true;
	}
	/**
	 * Disconnects all clients
	 */
	public boolean disconall() throws IOException{
		for (connection connection : loc) {
			connection.disconnect();
			System.out.println("[TCP-B] moving to next Client");
		}
		System.out.println("[TCP-B] ALL CLIENTS DISCONNECTED");
		return true;
	}
	/**
	 * Removes all Connections
	 */
	public boolean removeall() throws IOException{
		for (connection connection : loc) {
			connection.shutdown();
			System.out.println("[TCP-B] moving to next Client");
		}
		loc.clear();
		System.out.println("[TCP-B] ALL CLIENTS DELETED");
		return true;
	}
	/**
	 *Start to listen for incoming connections
	 */
	public boolean startlisten(){
		for (connection connection : loc) {
			connection.startlistener();
			System.out.println("[TCP-B] moving to next Client");
		}
		System.out.println("[TCP-B] ALL LISTENERS STARTED");
		return true;
	}
}
