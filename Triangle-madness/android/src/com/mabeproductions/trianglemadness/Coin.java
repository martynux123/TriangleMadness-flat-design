package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Coin {

	// Variables
	private int x;
	private int y;
	private int speed;
	private int index = 0;
	private int tickCount = 0;
	private int rotationIndex;
	private static int coinSize = (int) (Gdx.graphics.getHeight()*0.0925f);
	

	// Collections
	private Texture[] txt = new Texture[7];

	// Objects
	private Rectangle bounds;
	private ParticleEffect effect;
	private Sound sound;

	// Booleans
	private boolean debugMode = false;

	public Coin(int x, int y, int speed, Texture[] txt, int index, ParticleEffect effect) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.txt = txt;
		this.index = index;
		this.effect = effect;
		
		bounds = new Rectangle(x, y, getCoinSize(), getCoinSize());
		sound = GameRunner.assets.get("Sounds/dzinkt.wav");

	}

	public Rectangle getBounds() {
		return bounds;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void render(SpriteBatch batch, ShapeRenderer render) {
		batch.begin();
		batch.draw(txt[index], x, y, Coin.getCoinSize(), Coin.getCoinSize());
		batch.end();

		if (debugMode) {
			render.setAutoShapeType(true);
			render.begin(ShapeType.Line);
			render.setColor(Color.RED);
			render.rect(bounds.x, bounds.y, bounds.width, bounds.height);
			render.end();

		}

	}
	
	public void onAquire(){
		if(GameMenu.isMuted){			
			sound.play(0);
		}else sound.play(0.05f);
		
	}

	public void update() {

		if (tickCount >= 30) {
			y -= speed;
			tickCount = 0;
		}

		if (rotationIndex >= 70) {
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

	public static int getCoinSize() {
		return coinSize;
	}
}