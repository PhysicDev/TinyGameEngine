package tge.online;


import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.BiConsumer;

public abstract class Server{
	

	private static final int MAX_PLAYER=10;
	protected static int FrameLimiterSyncUpdate=30;
	protected static int FrameLimiterLogic=250;
	
    private ServerSocket serverSocket;
    protected final ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private final ExecutorService executor = Executors.newCachedThreadPool();
	
	
	protected HashMap<Character,BiConsumer<DataInputStream,ClientHandler>> packetReader=new HashMap<Character,BiConsumer<DataInputStream,ClientHandler>>();
	
	public int FrameSync() {return FrameLimiterSyncUpdate;}
	public int FrameLogic() {return FrameLimiterLogic;}
	
	protected static long Sframes=0;
	protected static long Lframes=0;
	
	private int port;

    public Server(int port) {
    	this.port=port;
        
        ClientHandler.parent=this;
    }
	
    public void startServer()throws IOException {
    	serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);
    }

	public void start() {
	    executor.submit(() -> {
	        try {
	            while (true) {
	                Socket clientSocket = serverSocket.accept();
	                System.out.println("New client connected!");
	                ClientHandler ch=new ClientHandler(clientSocket);
	                if(clients.size()>=MAX_PLAYER) {
	                	clientSocket.close();
	                }else {
	                	clients.add(ch);
	                	executor.submit(ch);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    });
	}
	 
	 
	public void StartLoop() {
		System.out.println("starting loop");
		GameLoop gameloop=new GameLoop();
		gameloop.parent=this;
		gameloop.start();
		
		new Thread(this::syncLoop).start();
		
	}
	
	public void syncLoop() {
		while(true) {
			long time=System.currentTimeMillis();
			syncSignal();
			try {
				long waitTime=(long) (1000d/(double)FrameLimiterSyncUpdate-System.currentTimeMillis()+time);
				if(waitTime>0)
					Thread.sleep(waitTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class GameLoop extends Thread {
		 long timeNano=System.nanoTime();
		 Server parent;
		 public void run() {
			  while(true) {
				long time=System.currentTimeMillis();
				loop();
				try {
					long waitTime=(long) (1000d/(double)FrameLimiterLogic-System.currentTimeMillis()+time);
					if(waitTime>0)
						Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			  }
		 }
		
		public int loop() {
			return (parent.LogicLoop());
		}
	}
	
	public int LogicLoop() {
		Lframes++;
		return 1;//success
	}
	
	public int syncSignal(){
		Sframes++;
		return 1;//success
	}

	public static int getFrameLimiterSyncUpdate() {
		return FrameLimiterSyncUpdate;
	}

	public static void setFrameLimiterSyncUpdate(int FrameLimiterSyncUpdate) {
		Server.FrameLimiterSyncUpdate = FrameLimiterSyncUpdate;
	}

	public static int getFrameLimiterLogic() {
		return FrameLimiterLogic;
	}

	public static void setFrameLimiterLogic(int frameLimiterLogic) {
		FrameLimiterLogic = frameLimiterLogic;
	}
	
	public void broadcast(byte[] p ) {
		for(ClientHandler ch : clients)
			ch.packets.add(p.clone());
	}
	
	public void disconect(ClientHandler clientHandler) {
		System.out.println("disconnect");
		clients.remove(clientHandler);
	}
	
	public void connect(ClientHandler clientHandler) {
	}
	
	
}
