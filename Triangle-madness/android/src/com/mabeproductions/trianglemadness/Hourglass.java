package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;



public class Hourglass {

	private boolean debugMode = false;
	private float x;
	private int y;
	private float speed;
	private Texture texture;
	private int tickCount;
	public  static float HourGlassSize = Gdx.graphics.getHeight()*0.078703f;
	private Rectangle hourBounds;
	
	
	public Hourglass(float x, int y, float speed, Texture texture){
		this.x = x;
		this.y = y;
		this.speed= speed;
		this.texture=texture;
		
		hourBounds = new Rectangle(x, y, HourGlassSize, HourGlassSize);
	}
	
	public float getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public Rectangle hourBounds(){
		return hourBounds;
	}
	
	public void render(SpriteBatch batch, ShapeRenderer render){
		if(tickCount>=2){
			y-=speed;
			tickCount =0;
		}
		hourBounds.setPosition(x, y);
		tickCount++;
	
		batch.begin();
		batch.draw(texture, x, y, HourGlassSize,HourGlassSize);
		batch.end();
		
		if(debugMode){
			render.setAutoShapeType(true);

			render.begin();
			render.set(ShapeType.Line);
			render.setColor(Color.BROWN);
			render.rect(x, y, HourGlassSize, HourGlassSize);
			render.end();
			
		}

	}
	public void update(){
		
		
	}
	public void onTouch(){
		
	}
	
	
}
