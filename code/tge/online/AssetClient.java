package tge.online;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

import agario.GameLogic;


public class AssetClient extends Client {

	public static char UpdateWord='U';
	public static char CreateWord='C';
	public static char RemoveWord='R';
	public static char EndWord='=';
	public boolean createOnUpdate=false;//set to true to allow udpate packet to instantiate new Assets

	public ConcurrentHashMap<Integer,Asset> assets=new ConcurrentHashMap<Integer,Asset>();
	protected static HashMap<Character,Streamable> charSet=new HashMap<Character,Streamable>();//no very good but don't really have the choice
	
	public AssetClient(String host, int port) throws IOException {
		super(host, port);
		BiConsumer<DataInputStream,Client> up = (DataInputStream in,Client c) -> {
        	try {
				while(true) {
					char nextObj=(char)in.read();
					if(nextObj==EndWord)
						break;
					int id=in.readInt();
					Class<?> type=null;
					if(charSet.containsKey(nextObj))
						type=charSet.get(nextObj).getClass();
					else {
						continue;
					}
					Asset a=null;
					if(!assets.containsKey(id) || !type.isInstance(assets.get(id).asset)) {
						if(createOnUpdate)
							for (Constructor<?> constructor : type.getDeclaredConstructors()) {
				                if (constructor.getParameterCount() == 0) {
				                    Streamable obj=null;
									try {
										obj = (Streamable)constructor.newInstance(new Object[]{});
									} catch (Exception e) {}
				                    a=new Asset(obj);
				                    a.id=id;
				                    assets.put(id, a);
				                    break;
				                }
							}
						else{
							continue;
						}
					}else
						a=assets.get(id);
					a.asset.readUpdate(in);
				}
						
			} catch (IOException e){}
        };
        this.packetReader.put(AssetClient.UpdateWord, up);
        
        BiConsumer<DataInputStream,Client> no = (DataInputStream in,Client c) -> {
			try {
				System.out.println("create packet");
				while(true) {
        			char nextObj=(char)in.read();
        			if(nextObj==EndWord)
        				break;
					int id=in.readInt();
					Streamable type=charSet.get(nextObj);
					if(type==null) {
						continue;
					}
					Streamable newObject=type.create(in);
					Asset a=new Asset(newObject);
					a.id=id;
					assets.put(id, a);
				}
						
			} catch (IOException e){}
        };
        
        this.packetReader.put(AssetClient.CreateWord, no);
        
        BiConsumer<DataInputStream,Client> de = (DataInputStream in,Client c) -> {
			System.out.println("get remove command");
        	try {
        		while(true) {
        			char dat=(char)in.read();//just needed to check the identifier;
        			if(dat==EndWord)
        				break;
    				int id=in.readInt();
    				assets.remove(id);
        			
        		}
			} catch (IOException e) {
				e.printStackTrace();
			}
        };
        
        this.packetReader.put(AssetClient.RemoveWord, de);
	}

}
