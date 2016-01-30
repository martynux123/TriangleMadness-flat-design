package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Rocket {
	
	//Variables
	private int x;
	private int y;
	private int speed;
	private final float scale = 0.6f;
	private int tickCount;
	private int rot;
	
	//Textures
	private Texture rocket;
	private TextureRegion region;
	private ParticleEffect emitter;
	
	
	//Booleans
	private final boolean debugRocket=true;
	
	
	//Objects
	private Rectangle bounds;
	
	
	

	public Rocket(int x, int y, int speed, int degrees, Texture rocket, ParticleEffect emitter){
		
		this.emitter=emitter;		
		this.x=x;		
		this.y=y;		
		this.speed=speed;		
		this.rot = degrees;
		this.rocket=rocket;	
		
		bounds = new Rectangle(x + rocket.getHeight()*scale, y+40, rocket.getWidth()*scale, (rocket.getHeight()*scale-150)*scale);
		
		region = new TextureRegion(rocket);

		
	}

	public Rectangle getBounds(){
		
		return bounds;
		
	}
	
	
	public int getX(){
	
		return x;
	}
	
	public int getY(){
		
		return y;
	}
	
	
	
	public void render(SpriteBatch batch, ShapeRenderer render){
		
		
		
		batch.begin();
		
		if(rot==-90){
			emitter.setPosition(x+60, y+100);
			emitter.update(Gdx.graphics.getDeltaTime());
			emitter.draw(batch);
		}
		if(rot==90){
			emitter.setPosition(x-55, y+400);
			emitter.update(Gdx.graphics.getDeltaTime());
			emitter.draw(batch);
		}
		batch.draw(region, x, y + rocket.getHeight()/2, 0,0, rocket.getWidth()*scale, rocket.getHeight()*scale, 1, 1, rot);
		batch.end();
		
		if(debugRocket){
			render.begin(ShapeType.Line);			
			render.setAutoShapeType(true);
			render.setColor(Color.RED);
			render.rect(bounds.x , bounds.y , bounds.getWidth(), bounds.getHeight());
			render.end();
		}
		
	}
	
	public void update() {
		 
		if(rot==-90){
			bounds.setPosition(x-10, y + 60);
			
		}
		
		if(rot==90){
			bounds.setPosition(x - rocket.getWidth()*scale*scale-100, y + 60 +rocket.getHeight()*scale - 10);
			
		}
		//TICK count.
		if(tickCount >= 30){
			x+= speed;
			tickCount = 0;
		}
		tickCount++;
		

	}
	
	public void dispose(){
	}
	
	
	
}
	
	

