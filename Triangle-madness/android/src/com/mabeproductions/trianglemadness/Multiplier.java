package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;



public class Multiplier {

	private boolean debugMode = false;
	private float x;
	private int y;
	private float speed;
	private Texture texture;
	private int tickCount;
	public  static float multiSize = Gdx.graphics.getHeight()*0.12f;
	private Rectangle multiBounds;
	private static Sound sound;
	
	public Multiplier(float x, int y, float speed, Texture texture){
		this.x = x;
		this.y = y;
		this.speed= speed;
		this.texture=texture;
		sound = GameRunner.assets.get("Sounds/sparkle.wav");
		
        multiBounds = new Rectangle(x, y, multiSize, multiSize);
	}

		public static void multiSound(){
			if(GameMenu.isMuted){			
				sound.play(0);
			}else sound.play(2f);
			
		}
	
	
	public float getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	public Rectangle multiBounds(){
		return multiBounds;
	}
	
	public void render(SpriteBatch batch, ShapeRenderer render){
		if(tickCount>=2){
			y-=speed;
			tickCount =0;
		}
		multiBounds.setPosition(x, y);
		tickCount++;
	
		batch.begin();
		batch.draw(texture, x, y, multiSize,multiSize);
		batch.end();
		
		if(debugMode){
			render.setAutoShapeType(true);

			render.begin();
			render.set(ShapeType.Line);
			render.setColor(Color.BROWN);
			render.rect(x, y, multiSize, multiSize);
			render.end();
			
		}

	}
	
}

