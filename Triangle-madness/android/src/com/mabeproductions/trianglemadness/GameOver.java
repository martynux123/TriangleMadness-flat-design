package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
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
	private ShapeRenderer render;
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
		this.background = background;
		this.Score = Score;
		scorefont = GameRunner.ScoreFont;
		
		best = Gdx.app.getPreferences("Stats").getInteger("HighScore", 0);
		total = Gdx.app.getPreferences("Stats").getInteger("TotalScore",0);
		
		this.runner = runner;
		
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
		buttonAgain.setBounds(200, Gdx.graphics.getHeight()/2-buttonAgain.getHeight()/2-70,300, 300);

		buttonMenu = new TextButton(" ", styleMenu);
		buttonMenu.setBounds(1400, Gdx.graphics.getHeight()/2-buttonMenu.getHeight()/2-70, 300, 300);

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

	@Override
	public void show() {
	
		gameOverThread();

	}
	
	public void gameOverMusic(){
		sound.play(0.2f);
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
			runner.getScreen().dispose();
			runner.setScreen(new GameSc(runner));
				
		}
		
		System.out.println(best);

		// TO MENU SCREEN
		if (buttonMenu.isPressed()) {
			runner.getScreen().dispose();
			runner.setScreen(new GameMenu(runner));
		}

		if (over[index] != null) {
			batch.begin();
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(over[index], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			scorefont.draw(batch, "Best: " + best, 730, 760);
			scorefont.draw(batch, "Score: " + Score, 730, 560);
			scorefont.draw(batch, "Total: " + total , 680, 350);
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
