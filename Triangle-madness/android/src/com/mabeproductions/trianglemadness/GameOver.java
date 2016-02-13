package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class GameOver implements Screen {

	//Variables
	private int index=0;
	
	//Objects
	public GameRunner runner;
	private SpriteBatch batch;
	private Rectangle r;
	private int Score;
	
	
	//Collections
	private Texture[] over = new Texture[7];
	
	
	// Buttons
	private Skin skinAgain, skinMenu;
	private TextureAtlas atlasAgain;
	private TextButtonStyle styleAgain, styleMenu;
	private TextButton buttonAgain, buttonMenu;
	private Stage stage;
	private FreeTypeFontGenerator scorefontgen;
	private BitmapFont scorefont;
	private int best;
	private TextureRegion background;
	private Sound sound;
	private int total;
	
	public GameOver(GameRunner runner, int Score, TextureRegion background) {
		
		Preferences prefs = Gdx.app.getPreferences("Stats");
		this.background = background;
		this.Score = Score;
		this.runner = runner;
		
		if(prefs.getInteger("Tries", 0) % 4 == 0){
			GameRunner.adcontroller.showInterAd(new Runnable() {
				
				@Override
				public void run() {
					
				}
			});
		}
		
		
		

	}

	@Override
	public void show() {
	
		
		gameOverThread();
		scorefont = GameRunner.ScoreFont;
		
		best = Gdx.app.getPreferences("Stats").getInteger("HighScore", 0);
		total = Gdx.app.getPreferences("Stats").getInteger("TotalScore",0);
		
		
		stage = new Stage();
		
		
		
		Gdx.input.setInputProcessor(stage);
		
		skinAgain = new Skin();
		atlasAgain = GameRunner.assets.get("Textures/GameOver/Buttons/Buttons.pack");
		skinAgain.addRegions(atlasAgain);
		styleAgain = new TextButtonStyle();
		styleAgain.up = skinAgain.getDrawable("Retry_released");
		styleAgain.down = skinAgain.getDrawable("Retry_pressed");
		styleAgain.font = scorefont;
		
		skinMenu = new Skin();
		skinMenu.addRegions(atlasAgain);
		styleMenu = new TextButtonStyle();
		styleMenu.up = skinMenu.getDrawable("Home_released");
		styleMenu.down = skinMenu.getDrawable("Home_pressed");
		styleMenu.font = scorefont;
		
		buttonAgain = new TextButton(" ", styleAgain);
		buttonAgain.setBounds(Gdx.graphics.getWidth()*0.2f, Gdx.graphics.getHeight()*0.38f,Gdx.graphics.getHeight()*0.20f, Gdx.graphics.getHeight()*0.20f);
		
		buttonMenu = new TextButton(" ", styleMenu);
		buttonMenu.setBounds(Gdx.graphics.getWidth()*0.69f, Gdx.graphics.getHeight()*0.38f, Gdx.graphics.getHeight()*0.20f, Gdx.graphics.getHeight()*0.20f);
		
		stage.addActor(buttonAgain);
		stage.addActor(buttonMenu);
		// END
		
		over[0] = GameRunner.assets.get("Textures/GameOver/GameOver0001.png");
		over[1] = GameRunner.assets.get("Textures/GameOver/GameOver0004.png");
		over[2] = GameRunner.assets.get("Textures/GameOver/GameOver0007.png");
		over[3] = GameRunner.assets.get("Textures/GameOver/GameOver0010.png");
		over[4] = GameRunner.assets.get("Textures/GameOver/GameOver0013.png");
		over[5] = GameRunner.assets.get("Textures/GameOver/GameOver0016.png");
		batch = new SpriteBatch();

	}
	
	public void gameOverMusic(){
		if(!GameMenu.isMuted){
			sound.play(1f);
		}		
	}

	public void gameOverThread() {
		index = 0;
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameOver.this) {
					try {
						Thread.sleep(50);

					} catch (InterruptedException e) {

						e.printStackTrace();
					}

					if (index + 1 > 5) {
						index = -1;
					}
					index++;

				}
				
				Thread.currentThread().interrupt();
			}
		},"GameOver anim").start();
	}
	
	public void update(){
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// TO GAMESCREEN
		if (buttonAgain.isPressed()) {

			runner.setScreen(new GameSc(runner));
				
		}
		
		System.out.println(best);

		// TO MENU SCREEN
		if (buttonMenu.isPressed()) {
			runner.setScreen(new GameMenu(runner));
		}

		if (over[index] != null) {
			batch.begin();
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(over[index], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			scorefont.draw(batch, "Best: " + best, Gdx.graphics.getWidth()*0.35f, Gdx.graphics.getHeight()*0.7f);
			scorefont.draw(batch, "Score: " + Score, Gdx.graphics.getWidth()*0.35f, Gdx.graphics.getHeight()*0.52f);
			scorefont.draw(batch, "Total: " + total , Gdx.graphics.getWidth()*0.35f, Gdx.graphics.getHeight()*0.32f);
			batch.end();
		}
		stage.draw();
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
		stage.dispose();
		skinAgain.dispose();
		skinMenu.dispose();
	}

}
