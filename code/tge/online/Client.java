package tge.online;

import java.io.*;
import java.net.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.BiConsumer;

//il existe des object stream qui peuvent envoyer des objets entier

public class Client {
    private Socket socket;
    private DataInputStream input;
    protected DataOutputStream output;
    protected ConcurrentHashMap<Character,BiConsumer<DataInputStream,Client>> packetReader=new ConcurrentHashMap<Character,BiConsumer<DataInputStream,Client>>();

    private ConcurrentLinkedQueue<byte[]> packets=new ConcurrentLinkedQueue<byte[]>();
    
    
    public void addPacket(byte[] p) {
    	packets.add(p);
    }
    
    private int port;
    private String host;
    
    public Client(String host, int port) {
    	this.port=port;
    	this.host=host;
        
        
    }
    
    public void startClient()throws IOException  {
    	socket = new Socket(host, port);
        System.out.println("Connected to server at " + host + ":" + port);
        output = new DataOutputStream(socket.getOutputStream());
        input = new DataInputStream(socket.getInputStream());
    }

    public void start() {
        // Listen for updates from the server
        new Thread(this::listenForUpdates).start();
        new Thread(this::sendMsg).start();
    }

    private void listenForUpdates() {
        try {
            while (true) {
                // Read float position from the server
	            char requestType = (char)input.read();
	            //System.out.println((byte)requestType+" "+requestType+((char)(byte)requestType));
	            if(packetReader.containsKey(requestType)) {
	              	packetReader.get(requestType).accept(input, this);
	            }
	    		
                //System.out.println("end of request");
            }
        } catch (IOException e) {
            System.out.println("Disconnected from server.");
        }
    }
    
    private void sendMsg() {
        while (true) {
	    	while(!packets.isEmpty()) {
	    		//System.out.println("sending package !!! "+packets.size());
	        	try {
	        		
		    		byte[] p =packets.poll();
			    	output.write(p);
			    	output.flush();
	    		} catch (IOException e) {
		            System.out.println("error sending packet !");
		        }
	    	}
	    	//System.out.println("end of package sending");
        }
    }
}