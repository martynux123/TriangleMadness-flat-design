package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

import android.view.textservice.SuggestionsInfo;

public class GameOver implements Screen {

	//Variables
	private int index=0;
	
	//Objects
	public GameRunner runner;
	private SpriteBatch batch;
	private ShapeRenderer render;
	private Rectangle r;
	
	
	//Collections
	private Texture[] over = new Texture[7];
	
	
	// Buttons
	private Skin skinAgain, skinMenu;
	private TextureAtlas atlasAgain;
	private TextButtonStyle styleAgain, styleMenu;
	private TextButton buttonAgain, buttonMenu;
	private Stage stage;
	private BitmapFont font;

	
	public GameOver(GameRunner runner) {
		this.runner = runner;

		

		

		
		
		font = new BitmapFont();
		font.setColor(Color.RED);

		
		
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		skinAgain = new Skin();
		atlasAgain = GameRunner.assets.get("Textures/GameOver/Buttons/Buttons.pack");
		skinAgain.addRegions(atlasAgain);
		styleAgain = new TextButtonStyle();
		styleAgain.up = skinAgain.getDrawable("RetryR");
		styleAgain.down = skinAgain.getDrawable("RetryP");
		styleAgain.font = font;

		skinMenu = new Skin();
		skinMenu.addRegions(atlasAgain);
		styleMenu = new TextButtonStyle();
		styleMenu.up = skinMenu.getDrawable("MenuR");
		styleMenu.down = skinMenu.getDrawable("MenuP");
		styleMenu.font = font;

		buttonAgain = new TextButton(" ", styleAgain);
		buttonAgain.setBounds(Gdx.graphics.getWidth() / 2 + 120, Gdx.graphics.getHeight() / 2 + 50, 600, 200);

		buttonMenu = new TextButton(" ", styleMenu);
		buttonMenu.setBounds(Gdx.graphics.getWidth() / 2 - 590, Gdx.graphics.getHeight() - 740, 600, 200);

		stage.addActor(buttonAgain);
		stage.addActor(buttonMenu);
		// END

		over[0] = GameRunner.assets.get("Textures/GameOver/GameOver0001.png");
		over[1] = GameRunner.assets.get("Textures/GameOver/GameOver0004.png");
		over[2] = GameRunner.assets.get("Textures/GameOver/GameOver0007.png");
		over[3] = GameRunner.assets.get("Textures/GameOver/GameOver0010.png");
		over[4] = GameRunner.assets.get("Textures/GameOver/GameOver0013.png");
		over[5] = GameRunner.assets.get("Textures/GameOver/GameOver0016.png");
		over[6] = GameRunner.assets.get("Textures/GameOver/GameOver0019.png");
		batch = new SpriteBatch();

	}

	@Override
	public void show() {
		gameOverThread();

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

					if (index + 1 > 6) {
						index = -1;
					}
					index++;

				}

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
			Thread.currentThread().interrupt();
				
		}

		// TO MENU SCREEN
		if (buttonMenu.isPressed()) {
			runner.getScreen().dispose();
			runner.setScreen(new GameMenu(runner));
			Thread.currentThread().interrupt();
		}

		if (over[index] != null) {
			batch.begin();
			batch.draw(over[index], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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
		font.dispose();
		
	}

}
