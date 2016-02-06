package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {

	
	//Booleans
	private final boolean debugMode = false;

	//Objects
	private GameSc g;
	public Rectangle bounds;
	
	//Textures
	private Texture currentTexture;
	
	
	//Variables
	private float x = 100;
	private float y = 100;
	private float speed;
	public static final int UNIFORM_WIDTH = (int) (Gdx.graphics.getHeight()*0.111f);
	public static final int UNIFORM_HEIGHT = (int) (Gdx.graphics.getHeight()*0.111f);
	private int width = 60;
	private int height = 60;
	private int tickCount = 0;
	
	
	

	public Enemy(float x, float y, float speed, int width, int height, Texture currentTexture) {
		
		Preferences prefs = Gdx.app.getPreferences("Stats");
		
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_1){
			this.currentTexture = currentTexture;
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_2){
			this.currentTexture = GameRunner.assets.get("Level2/FrostLevelEnemy.png");
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_3){
			this.currentTexture = GameRunner.assets.get("Level3/FireEnemy.png");
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_4){
			this.currentTexture = GameRunner.assets.get("Level4/wateEnemy.png");
		}
		
		
		this.x = x;
		this.width = width;
		this.y = y;
		this.height = height;
		this.speed = speed;
		bounds = new Rectangle(x, y, width, height);
	}

	public float getY() {
		return y;
	}

	public float getX() {
		return x;
	}

	public void render(SpriteBatch batch, ShapeRenderer shape) {
		//System.out.println(x + " " + y);
		if(tickCount >= 2){
			y += speed;
			tickCount = 0;
		}
		
		bounds.set(x, y, width, height);

		//Drawing enemy
		batch.begin();
		batch.draw(currentTexture, Math.round(x), Math.round(y), Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT);
		batch.end();
		
		//Debugging
		if (debugMode) {
			shape.begin();
			shape.setAutoShapeType(true);
			shape.set(ShapeType.Line);
			shape.setColor(Color.RED);
			shape.rect(bounds.x, bounds.y, bounds.getWidth(), bounds.getHeight());
			shape.end();
		}
		
		//Moving every 2 ticks
		tickCount++;
	}

	public void update() {

	}
	
	public void dispose(){
	}
}
