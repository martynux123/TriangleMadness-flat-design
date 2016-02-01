package com.mabeproductions.trianglemadness;

import java.util.Timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;

public class GameMenu implements Screen {
	
	//Booleans
	public boolean isFinger = true;
	private boolean gameScreenSwitch;
	
	//Variables
	private int index;
	private int angleMid = 0;
	private int angleOut = 0;
	private BitmapFont playFont;
	
	//Objects
	public Rectangle playBtn;
	public GameRunner runner;
	private SpriteBatch batch;
	private Music music;
	private Timer t;
	//Collections
	private Texture[] bg = new Texture[10]; 
	
	//Textures
	private TextureAtlas btn;
	private Texture play;
	
	public GameMenu(GameRunner runner){
		this.runner = runner;
		music = GameRunner.assets.get("Sounds/gameMenu.wav");
		GameRunner.adcontroller.showAd();
		batch = new SpriteBatch();
		playFont = GameRunner.PlayFont;
		bg[0] = GameRunner.assets.get("Textures/Menu/1.png");
		bg[1] = GameRunner.assets.get("Textures/Menu/2.png");
		bg[2] = GameRunner.assets.get("Textures/Menu/3.png");
		bg[3] = GameRunner.assets.get("Textures/Menu/4.png");
		bg[4] = GameRunner.assets.get("Textures/Menu/5.png");
		bg[5] = GameRunner.assets.get("Textures/Menu/6.png");
		bg[6] = GameRunner.assets.get("Textures/Menu/7.png");
		bg[7] = GameRunner.assets.get("Textures/Menu/8.png");
		bg[8] = GameRunner.assets.get("Textures/Menu/9.png");
		bg[9] = GameRunner.assets.get("Textures/Menu/10.png");
		
		play = GameRunner.assets.get("Textures/Menu/play.png");		
		btn = GameRunner.assets.get("Textures/Menu/circle.pack");
		
		playBtn = new Rectangle(Gdx.graphics.getWidth()*0.42f, Gdx.graphics.getHeight()*0.46f, 200, 200);
		
		menuThread();
		
			
		
	}
	
	
	
	
	public void menuMusic(){
		
	
		music.setLooping(true);
		music.play();
		music.setVolume(0);
		music.setVolume(1);

		
	}
	
	public void menuThread(){
		
		index = 0;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(runner.getScreen() == GameMenu.this){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}	
						
					
					
					
					if(index+1>9){
						index = -1;
					}
					index++;
					angleMid++;
					angleOut--;
				}
				Thread.currentThread().interrupt();
			}
		}, "menuThread").start();
	}
	
	
	
	
	@Override
	public void show() {
		
		menuMusic();
		Preferences prefs = Gdx.app.getPreferences("Stats");
		prefs.putInteger("Tries", 0);
		prefs.flush();
		
	
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(bg[index], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
		batch.draw(btn.findRegion("Middle_circle"),Gdx.graphics.getWidth()/2-btn.findRegion("Middle_circle").getRegionWidth()
				, Gdx.graphics.getHeight()/2-btn.findRegion("Middle_circle").getRegionHeight() - 100,
				btn.findRegion("Middle_circle").getRegionWidth(), btn.findRegion("Middle_circle").getRegionHeight()
				, 400, 400, 1.5f, 1.5f, angleMid);
		
		batch.draw(btn.findRegion("Outter_circle"),Gdx.graphics.getWidth()/2-btn.findRegion("Outter_circle").getRegionWidth()
				, Gdx.graphics.getHeight()/2-btn.findRegion("Outter_circle").getRegionHeight() - 100,
				btn.findRegion("Outter_circle").getRegionWidth(), btn.findRegion("Outter_circle").getRegionHeight()
				, 400, 400, 1.5f, 1.5f, angleOut);
		
		
		playFont.draw(batch, "PLAY", Gdx.graphics.getWidth()*0.42f, Gdx.graphics.getHeight()*0.46f);
		
		
		batch.end();
		
		
		
		
		
		if(playBtn.contains(Gdx.input.getX(), Gdx.input.getY()) && Gdx.input.justTouched()){
			gameScreenSwitch=true;
		
		}
		if(gameScreenSwitch){
			GameRunner.adcontroller.hideAd();
			music.stop();
			runner.setScreen(new GameSc(runner));
			this.dispose();
			gameScreenSwitch = false;
			
		}
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}