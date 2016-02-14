package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
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
	public  static float HourGlassSize = Gdx.graphics.getHeight()*0.12f;
	private Rectangle hourBounds;
	private static Sound sound;
	
	public Hourglass(float x, int y, float speed, Texture texture){
		
switch (GameSc.Level) {
		
		case 1:
			this.texture = texture;
			break;
		case 2:
			this.texture = GameRunner.assets.get("Level2/clock_winter.png");
			break;
		case 3:
			this.texture = GameRunner.assets.get("Level3/clock_hell.png");
			break;
		case 4:
			this.texture = GameRunner.assets.get("Level4/clock_underwater.png");
			break;
		case 5:
			this.texture = GameRunner.assets.get("Level5/clock_Jungle.png");
			break;
		case 6:
			this.texture = GameRunner.assets.get("Level6/clock_desert.png");
			break;
		default:
			break;
			
		}
		
		this.x = x;
		this.y = y;
		this.speed= speed;
		
		sound = GameRunner.assets.get("Sounds/FreezeTime.wav");
		hourBounds = new Rectangle(x, y, HourGlassSize, HourGlassSize);
	}

		public static void hourglassSound(){
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
	
}
