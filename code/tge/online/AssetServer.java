package tge.online;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import agario.Player;

public class AssetServer extends Server {
	
	public ConcurrentHashMap<Integer,Asset> assets=new ConcurrentHashMap<Integer,Asset>();
	
	public static char UpdateWord='U';
	public static char CreateWord='C';
	public static char RemoveWord='R';
	public static char EndWord='=';

	public AssetServer(int port) throws IOException {
		super(port);
	}

	public byte[] update(Asset a) throws IOException{
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(byteWriter);
		writer.write(a.asset.identifier());
		writer.writeInt(a.id);
		a.asset.updatePacket(writer);
		return byteWriter.toByteArray();
	}
	
	@Override
	public int syncSignal() {
		super.syncSignal();
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(byteWriter);
		try {
			writer.write(AssetServer.UpdateWord);
			for(Asset a:assets.values())
				if(a.autoUpdate || a.update) {
					a.update=false;
					writer.write(a.asset.identifier());
					writer.writeInt(a.id);
					a.asset.updatePacket(writer);
				}
			writer.write(EndWord);
			byte[] packet=byteWriter.toByteArray();
			this.broadcast(packet);
		}catch(IOException e) {}
		return 1;
	}
	/**
	public byte[] updatePacket(Iterable<Asset> assets) {
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(byteWriter);
		writer.write(AssetServer.UpdateWord);
		for(Asset a:assets) {
			if(a.autoUpdate || a.update) {
				a.update=false;
				writer.write(a.asset.identifier());
				writer.writeInt(a.id);
				a.asset.updatePacket(writer);
			}
		}
		return null;
	}**/

	public byte[] createPack() {
		return createPack(assets.values());
	}
	public byte[] createPack(Asset a) {
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(byteWriter);
		try {
			writer.write(CreateWord);
			writer.write(a.asset.identifier());
			writer.writeInt(a.id);
			a.asset.createPacket(writer);
			writer.write(EndWord);
		} catch (IOException e) {e.printStackTrace();}
		return byteWriter.toByteArray();
	}
	
	public byte[] createPack(Iterable<Asset> as){
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(byteWriter);
		
		try {
			writer.write(CreateWord);
			for(Asset a:as) {
				writer.write(a.asset.identifier());
				writer.writeInt(a.id);
				a.asset.createPacket(writer);
			}
			writer.write(EndWord);
		} catch (IOException e) {e.printStackTrace();}
		return byteWriter.toByteArray();
	}
	
	public byte[] removePack(Asset a) {
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(byteWriter);
		try {
			writer.write(CreateWord);
			writer.write(a.asset.identifier());
			writer.writeInt(a.id);
			writer.write(EndWord);
		} catch (IOException e) {e.printStackTrace();}
		return byteWriter.toByteArray();
	}
	
	public byte[] removePack(Iterable<Asset> as){
		ByteArrayOutputStream byteWriter = new ByteArrayOutputStream();
		DataOutputStream writer = new DataOutputStream(byteWriter);
		
		try {
			writer.write(RemoveWord);
			for(Asset a:as) {
				writer.write(a.asset.identifier());
				writer.writeInt(a.id);
			}
			writer.write(EndWord);
		} catch (IOException e) {}
		return byteWriter.toByteArray();
	}

}
