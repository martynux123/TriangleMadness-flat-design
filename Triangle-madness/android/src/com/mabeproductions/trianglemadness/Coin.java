package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
		this.index = index;
		this.effect = effect;
		
		Preferences prefs = Gdx.app.getPreferences("Stats");
		
		switch (GameSc.Level) {
			
		case 1:
			this.txt = txt;
			break;
		case 2:
			this.txt[0] = GameRunner.assets.get("Level2/1.png");
			this.txt[1] = GameRunner.assets.get("Level2/2.png");
			this.txt[2] = GameRunner.assets.get("Level2/3.png");
			this.txt[3] = GameRunner.assets.get("Level2/4.png");
			this.txt[4] = GameRunner.assets.get("Level2/5.png");
			this.txt[5] = GameRunner.assets.get("Level2/6.png");
			this.txt[6] = GameRunner.assets.get("Level2/7.png");
			break;
		case 3:
			this.txt[0] = GameRunner.assets.get("Level3/1.png");
			this.txt[1] = GameRunner.assets.get("Level3/2.png");
			this.txt[2] = GameRunner.assets.get("Level3/3.png");
			this.txt[3] = GameRunner.assets.get("Level3/4.png");
			this.txt[4] = GameRunner.assets.get("Level3/5.png");
			this.txt[5] = GameRunner.assets.get("Level3/6.png");
			this.txt[6] = GameRunner.assets.get("Level3/7.png");
			break;
		case 4:
			this.txt[0] =  GameRunner.assets.get("Level5/1.png");
			this.txt[1] =  GameRunner.assets.get("Level5/2.png");
			this.txt[2] =  GameRunner.assets.get("Level5/3.png");
			this.txt[3] =  GameRunner.assets.get("Level5/4.png");
			this.txt[4] =  GameRunner.assets.get("Level5/5.png");
			this.txt[5] =  GameRunner.assets.get("Level5/6.png");
			this.txt[6] =  GameRunner.assets.get("Level5/7.png");
			break;
			
		default:
			this.txt=txt;
			break;
			
		}


		
		
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