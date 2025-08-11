package tge.online;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tge.Updatable;

public interface Streamable extends Updatable{

	public char identifier();
	public void updatePacket(DataOutputStream out)throws IOException;
	public void createPacket(DataOutputStream out)throws IOException;
	public void readUpdate(DataInputStream in)throws IOException;
	public Streamable create(DataInputStream in)throws IOException;
}
