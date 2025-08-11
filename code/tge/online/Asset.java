package tge.online;

public class Asset {
	
	public Asset(Streamable a){
		asset=a;
		last_update=System.nanoTime();
	}
	public int id;
	public Streamable asset;
	public long last_update;
	public boolean autoUpdate=true;//true : auto update every sync cycle // false : update on change
	public boolean update=true;//true : update once then reset to false // false : don't update
}
