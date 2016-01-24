package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class GameOver implements Screen{
	
	public GameRunner runner;
	private Texture[] over= new Texture[7];
	private int index;	
	private SpriteBatch batch;
	private ShapeRenderer render;
	private Rectangle r;
	
	
	public GameOver(GameRunner runner){
		this.runner = runner;
		gameOverThread();
		//Gdx.app.exit();		
		over[0] = new Texture(Gdx.files.internal("Textures/GameOver/GameOver0001.png"));
		over[1] = new Texture(Gdx.files.internal("Textures/GameOver/GameOver0004.png"));
		over[2] = new Texture(Gdx.files.internal("Textures/GameOver/GameOver0007.png"));
		over[3] = new Texture(Gdx.files.internal("Textures/GameOver/GameOver0010.png"));
		over[4] = new Texture(Gdx.files.internal("Textures/GameOver/GameOver0013.png"));
		over[5] = new Texture(Gdx.files.internal("Textures/GameOver/GameOver0016.png"));
		over[6] = new Texture(Gdx.files.internal("Textures/GameOver/GameOver0019.png"));
		
		batch = new SpriteBatch();
		
	}
	
	@Override
	public void show() {
		
		
		
	
		
	}
	
	public void gameOverThread(){
		index = 0;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try {
						Thread.sleep(100);
						
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}	
						
					
					
					
					if(index+1>6){
						index = -1;
					}
					index++;
					
				}
				
			}
		}).start();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(over[index]!=null){
		batch.begin();
		batch.draw(over[index],0,0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		
		}
		
	
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
