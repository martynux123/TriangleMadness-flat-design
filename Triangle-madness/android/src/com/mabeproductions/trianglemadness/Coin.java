package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Coin {
	
	//Variables
	private int x;
	private int y;
	private int speed;
	private int index=0;
	private int tickCount=0;
	private int rotationIndex;
	private int coinSize= 100;
	
	
	
	//Collections
	private Texture [] txt = new Texture[7];
	
	//Objects
	public Rectangle bounds;
	
	//Booleans
	private boolean debugMode  = false;
	
	public Coin(int x, int y, int speed, Texture[] txt, int index) {		
		this.x = x;		
		this.y = y;
		this.speed = speed;			
		this.txt = txt;
		this.index = index;
		
		bounds = new Rectangle(x, y, coinSize, coinSize );
		
	}
	public int getX(){
		return x;
		}
	public int getY(){
		return y;
		}
		
	public void render(SpriteBatch batch, ShapeRenderer render) {
		batch.begin();
		batch.draw(txt[index], x, y, coinSize, coinSize);
		batch.end();
		
		if(debugMode){
			render.setAutoShapeType(true);
			render.begin(ShapeType.Line);
			render.setColor(Color.RED);
			render.rect(bounds.x, bounds.y, bounds.width, bounds.height);
			render.end();
		
		}
		
	}
	
	
	

	public void update() {
		
		if(tickCount >= 30){
			y-=speed;
			tickCount = 0;
		}
		
		if(rotationIndex>=70){
			index++;			
			rotationIndex = 0;
		}
		
		
		if (index + 1 > 6) {
			index = 0;
		}
		bounds.setPosition(x, y);
		
		rotationIndex++;
		tickCount++;
		
	}
}