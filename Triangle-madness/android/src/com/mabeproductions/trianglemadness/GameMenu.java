package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import android.graphics.drawable.shapes.Shape;

public class GameMenu implements Screen {
	
	
	
	public boolean isFinger = true;
	public Rectangle playBtn;
	public GameRunner runner;
	private int index;
	private SpriteBatch batch;
	private Texture[] bg = new Texture[7]; 
	private TextureAtlas btn;
	private int angleMid = 0;
	private int angleOut = 0;
	private Texture play;
	
	private BitmapFont font;
	
	//GameSc screen object(later initialized in constructor).
//	private GameSc GameScreen;
	private boolean gameScreenSwitch;
	
	
	public GameMenu(GameRunner runner){
		
		//See Line: 142 - WORKS
//		GameScreen = new GameSc(runner);
		

		
		
		this.runner = runner;
		batch = new SpriteBatch();
		
		bg[0] = new Texture(Gdx.files.internal("Textures/Menu/1.png"));
		bg[1] = new Texture(Gdx.files.internal("Textures/Menu/2.png"));
		bg[2] = new Texture(Gdx.files.internal("Textures/Menu/3.png"));
		bg[3] = new Texture(Gdx.files.internal("Textures/Menu/4.png"));
		bg[4] = new Texture(Gdx.files.internal("Textures/Menu/5.png"));
		bg[5] = new Texture(Gdx.files.internal("Textures/Menu/6.png"));
		bg[6] = new Texture(Gdx.files.internal("Textures/Menu/7.png"));	
		
		play = new Texture(Gdx.files.internal("Textures/Menu/play.png"));		
		btn = new TextureAtlas(Gdx.files.internal("Textures/Menu/circle.pack"));
		playBtn = new Rectangle(Gdx.graphics.getWidth()/2-btn.findRegion("Middle_circle").getRegionWidth()/2,
				Gdx.graphics.getHeight()/2-btn.findRegion("Middle_circle").getRegionHeight()/2,
				btn.findRegion("Middle_circle").getRegionWidth(),btn.findRegion("Middle_circle").getRegionHeight());
		
		menuThread();
	}
	public void menuThread(){
		
		index = 0;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}	
						
					
					
					
					if(index+1>6){
						index = -1;
					}
					index++;
					angleMid++;
					angleOut--;
				}
				
			}
		}).start();
	}
	
	
	
	
	@Override
	public void show() {

		
	
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(bg[index], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//batch.setColor(new Color(243f, 156f, 18f,1f));
		batch.draw(btn.findRegion("Middle_circle"),Gdx.graphics.getWidth()/2-btn.findRegion("Middle_circle").getRegionWidth()
				, Gdx.graphics.getHeight()/2-btn.findRegion("Middle_circle").getRegionHeight(),
				btn.findRegion("Middle_circle").getRegionWidth(), btn.findRegion("Middle_circle").getRegionHeight()
				, 400, 400, 1, 1, angleMid);
		//batch.setColor(new Color(52, 152, 219,1f));
		batch.draw(btn.findRegion("Outter_circle"),Gdx.graphics.getWidth()/2-btn.findRegion("Outter_circle").getRegionWidth()
				, Gdx.graphics.getHeight()/2-btn.findRegion("Outter_circle").getRegionHeight(),
				btn.findRegion("Outter_circle").getRegionWidth(), btn.findRegion("Outter_circle").getRegionHeight()
				, 400, 400, 1, 1, angleOut);
		
		
		batch.draw(play, Gdx.graphics.getWidth()/2-play.getWidth()/2-50,Gdx.graphics.getHeight()/2-play.getHeight()/2-30, 200,100); 
		
		batch.end();
		
		/*
		r.begin(ShapeType.Line);
		r.setColor(Color.BLACK);
		r.rect(playBtn.x, playBtn.y, playBtn.width, playBtn.height);
		r.end();
		*/
		
		if(playBtn.contains(Gdx.input.getX(), Gdx.input.getY()) && Gdx.input.justTouched()){
			gameScreenSwitch=true;
		
		}
		if(gameScreenSwitch){
			GameSc currentGameSc = runner.pausedGameScreen;
			currentGameSc.resume();
			runner.setScreen(currentGameSc);
			gameScreenSwitch=false;
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
		runner.getScreen().dispose();
		btn.dispose();
		batch.dispose();
		
	}


}
