package com.mabeproductions.trianglemadness;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Game implements ApplicationListener {

	
	private OrthographicCamera cam;
	private SpriteBatch batch;
	public Box box;
	public Snake snake;
	
	//Created Texture.
	private Texture background;
	
	@Override
	public void create() {
		
		//initialize
		background = new Texture(Gdx.files.internal("Textures/background.png"));
		box = new Box(this);
		batch = new SpriteBatch();
		snake = new Snake(45, new Vector2(100, 100), 120, 8);
		 Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		 
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						update();
					}
					
				}
			}).start();
			
			
	}

	@Override
	public void resize(int width, int height) {

		
	}
	
	

	@Override
	public void render() {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		//Drawing an image.
		batch.begin();
		batch.draw(background, 0,0 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		box.render(batch);
		snake.render();
		
	}
	public void update(){
		box.update();
		snake.update();
		
	}
	
	
	

	@Override
	public void pause() {

		
	}

	@Override
	public void resume() {

		
	}

	@Override
	public void dispose() {
		
		
	}
	
	
	

}