package tge.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ClientHandler extends Thread {
    private Socket socket;
    private DataInputStream input;
    public DataOutputStream output;
    public static Server parent;
    
    public ConcurrentLinkedQueue<byte[]> packets=new ConcurrentLinkedQueue<byte[]>();
    
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            parent.connect(this);
            while (true) {
            	//taking care of input
            	if(input.available()!=0) {
            		char requestType = (char)input.read();
                    if(parent.packetReader.containsKey(requestType))
                    	parent.packetReader.get(requestType).accept(input, this);
            	}
            	
            	//sending responses
            	while(!packets.isEmpty()) {
            		output.write(packets.poll());
            		output.flush();
            	}
            }
        } catch (IOException e) {
        	//disconection managment
        	parent.disconect(this);
        }
    }
}
