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
	
private int x;
private int y;
private int speed;
private final float scale = 0.6f;
private int tickCount;
private Texture rocket;
private Rectangle bounds;
private ShapeRenderer render;
private final boolean debugRocket=false;
private int rot;
private TextureRegion region;

private ParticleEffect emitter;

	public Rocket(int x, int y, int speed, int degrees, Texture rocket){
		
		this.x=x;
		
		this.y=y;
		
		this.speed=speed;
		
		this.rocket=rocket;
		
		this.rot = degrees;

		bounds = new Rectangle(x + rocket.getHeight()*scale, y+40, rocket.getWidth()*scale, (rocket.getHeight()*scale-150)*scale);
		//render = new ShapeRenderer();
		region = new TextureRegion(rocket);
	
		
		
		emitter = new ParticleEffect();
		emitter.load(Gdx.files.internal("Particles/fire"), Gdx.files.internal(""));
		emitter.scaleEffect(2);
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
		
		System.out.println(getX() + " " + getY() + " | " + Gdx.graphics.getWidth());
		
		batch.begin();
		emitter.setPosition(x+60, y+80);
		emitter.update(Gdx.graphics.getDeltaTime());
		emitter.draw(batch);
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
			bounds.setPosition(x - rocket.getWidth()*scale*scale, y + 60 +rocket.getHeight()*scale - 10);
			
		}
		//TICK count.
		if(tickCount >= 30){
			x+= speed;
			tickCount = 0;
		}
		tickCount++;
		

	}
	
	
	
}
	
	

