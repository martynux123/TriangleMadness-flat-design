package com.mabeproductions.trianglemadness;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameSc implements Screen {

	
	
	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public ArrayList<Snake> snakes;
	public ArrayList<Enemy> enemies;
	//Created Texture.
	private Texture background;
	public GameRunner runner;
	Texture txt;
	public boolean isFinger = true;
	
	public GameSc(GameRunner runner, boolean isFinger) {
		this.runner = runner;
		this.isFinger = isFinger;
		
		background = new Texture(Gdx.files.internal("Textures/background.png"));
		batch = new SpriteBatch();
		
		
		
		
		box = new Box(this);
		
		txt = new Texture(Gdx.files.internal("Textures/Enemies/Boxers/Enemy.png"));
		
		snakes = new ArrayList<Snake>();
		enemies = new ArrayList<Enemy>();
	
		shape = new ShapeRenderer();
		
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					update();
				}
				
			}
		}).start();
		
		snakeThread();
		enemyThread();
	}
	
	@Override
	public void show() {

		
		//initialize
		
	}
	
	

	@Override
	public void resize(int width, int height) {

		
		
	}
	
	

	@Override
	
	public void render(float dt) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Drawing an image.
		batch.begin();
		batch.draw(background, 0,0 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		box.render(batch);
				for (int i = 0; i < snakes.size(); i++) {
					
					snakes.get(i).render(shape);
				}
				
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(batch);
		}

	}
	public void update(){
		
		box.update();
		
		for (int i = 0; i < snakes.size(); i++) {
			snakes.get(i).update();
		}
		
		for (int i = 0; i < snakes.size(); i++) {
			
			if(!(snakes.get(i).isAlive)){
				snakes.remove(i);
				System.out.println(snakes.size());
			}
			
			
		}
		
	}
	
	private void snakeThread(){
		new Thread(new Runnable() {
		Random r = new Random();
		//float[]anglem = {30,40,50,60,70,80,90,100};	
			
		@Override
		public void run() {
			while(true){
				int x = r.nextInt(Gdx.graphics.getWidth()-50);
				int y = r.nextInt(Gdx.graphics.getHeight()-50);
				int delay = r.nextInt((6000-2000)+1)+2000;
				int speed = MathUtils.random(50, 150);
				float angle = (float) r.nextInt((110-30)+1)+30;
				int length = MathUtils.random(15, 25);
				try {
					spawnSnake(x, y, angle, length, speed);
					//System.out.println(delay);
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
	}).start();
	
	}
	
	private void enemyThread(){
		new Thread(new Runnable() {
		
			@Override
			public void run() {
				while(true){
					int x = MathUtils.random(Enemy.UNIFORM_WIDTH, Gdx.graphics.getWidth()-Enemy.UNIFORM_WIDTH);
					int y = 0;
//					int speed = MathUtils.random(10, 30);
					int speed = 15;
					int delay = MathUtils.random(400, 600);
					
					
					try {
						spawnEnemy(x, y, speed, txt);
							for (int i = 0; i < enemies.size(); i++) {
								if(enemies.get(i).getY()<0){
									enemies.remove(i);
								}
								
							}
						
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				
			}
		}).start();
		
	}
	
	
	public void spawnSnake(int x, int y, float angle, int length, int speed){
		Snake snake = new Snake(angle,new Vector2(x,y),speed,length);
		snakes.add(snake);
		
	}
	
	public void spawnEnemy(int x, int y, int speed, Texture currentTexture){
		Enemy enemy = new Enemy(x , y , speed, Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT, txt);
		enemies.add(enemy);
		
	}
	
	

	@Override
	public void pause() {

		
	}

	@Override
	public void resume() {

		
	}

	@Override
	public void dispose() {
		runner.getScreen().dispose();
		shape.dispose();
		batch.dispose();
			
		
		
	}



	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	

}