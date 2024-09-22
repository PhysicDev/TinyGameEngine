package tge.tileset;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import tge.Updatable;

public class Grid<E> implements Updatable,Iterable<E>{

	private double posX;
	private double posY;

	Random R=new Random(12345);
	
	protected ArrayList<GridRenderer<E>> renderers=new ArrayList<GridRenderer<E>>();
	
	public void addGridRenderer(GridRenderer<E> gridRender) {
		renderers.add(gridRender);
	}
	
	public void resetRenderer() {
		renderers=new ArrayList<GridRenderer<E>>();
	}
	
	public int getRendererAmount() {
		return renderers.size();
	}
	
	
	protected int sizeX;
	protected int sizeY;
	private double scale;
	private int Zpos;
	
	protected ArrayList<E> data=new ArrayList<E>();

	public double getScale() {
		return scale;}
	public void setScale(double scale) {
		this.scale = scale;}
	public int getSizeY() {
		return sizeY;}
	public int getSizeX() {
		return sizeX;}
	

	public void fill(E val) {
		for(int i=0;i<sizeX*sizeY;i++)
			data.set(i, val);
	}
	
	public double getPosX() {
		return posX;
	}
	public void setPosX(double posX) {
		this.posX = posX;
	}
	public double getPosY() {
		return posY;
	}
	public void setPosY(double posY) {
		this.posY = posY;
	}
	public Grid(int X,int Y,double size){

		sizeX=X;
		sizeY=Y;
		scale=size;
		for(int i=0;i<sizeX*sizeY;i++)
			data.add(null);
	}
	
	public Grid(int X,int Y){
		this(X,Y,16);
	}
	
	public Grid() {
		this(10,10,16);
	}
	
	public Grid(Grid<E> g) {
		sizeX=g.sizeX;
		sizeY=g.sizeY;
		for(int i=0;i<sizeX*sizeY;i++)
			data.add(null);
		Collections.copy(data,g.data);
		scale=g.scale;
		Zpos=g.Zpos+1;
		renderers=new ArrayList<GridRenderer<E>>(g.renderers);
	}
	
	
	
	public int getSize() {
		return sizeX*sizeY;
	}
	
	public E get(int id) {
		return data.get(id);}
	public void set(int id,E el) {
		data.set(id,el);}
	
	public E get(int X,int Y) {
		return data.get(get1D(X,Y));}
	public void set(int X,int Y,E el) {
		data.set(get1D(X,Y),el);}
	
	public int get1D(int x,int y) {
		return y*sizeX+x;}
	protected int get2DX(int id){
		return id%sizeX;}
	protected int get2DY(int id){
		return id/sizeX;}

	@Override
	public double Zindex() {
		return Zpos;}

	@Override
	public void update() {}

	@Override
	public void draw(Graphics g) {
		for(GridRenderer<E> gr:renderers)
			gr.draw(this, g);
	}

	@Override
	public Iterator<E> iterator() {
		return data.iterator();
	}

}
