package com.mabeproductions.trianglemadness;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Enemy {

	private final boolean debugMode = false;

	private GameSc g;
	private int x = 100;
	private int y = 100;
	private int speed;
	public static final int UNIFORM_WIDTH = 120;
	public static final int UNIFORM_HEIGHT = 120;
	private int width = 60;
	private int height = 60;
	public Rectangle bounds;
	private int tickCount = 0;
	
	private Texture currentTexture;

	public Enemy(int x, int y, int speed, int width, int height, Texture currentTexture) {
		this.currentTexture = currentTexture;
		this.x = x;
		this.width = width;
		this.y = y;
		this.height = height;
		this.speed = speed;
		bounds = new Rectangle(x, y, width, height);
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void render(SpriteBatch batch, ShapeRenderer shape) {

		bounds.set(x, y, width, height);

		//Drawing enemy
		batch.begin();
		batch.draw(currentTexture, x, y, Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT);
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
		
	}

	public void update() {
		//Moving every 30 ticks for optimization purposes
		if(tickCount >= 30){
			y += speed;
			tickCount = 0;
		}
		tickCount++;

	}
}
