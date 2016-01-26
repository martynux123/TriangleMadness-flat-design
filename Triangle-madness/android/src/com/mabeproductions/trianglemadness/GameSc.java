package com.mabeproductions.trianglemadness;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameSc implements Screen {

	public boolean game_paused = false;
	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public ArrayList<Enemy> enemies;
	public ArrayList<Enemy> enemies2;
	// Created Texture.
	private Texture background;
	public GameRunner runner;
	Texture txt;
	Texture enemy2;


	public GameSc(GameRunner runner) {
		this.runner = runner;
		

		background = new Texture(Gdx.files.internal("Textures/background.png"));
		batch = new SpriteBatch();

		box = new Box(this);

		txt = new Texture(Gdx.files.internal("Textures/Enemies/Boxers/Enemy.png"));

		enemy2 = new Texture(Gdx.files.internal("Textures/Enemies/Enemy2.png"));

		enemies = new ArrayList<Enemy>();
		enemies2 = new ArrayList<Enemy>();
		shape = new ShapeRenderer();

		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						// Sleeping a thread for thread delays to be alike
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					update();
				}

			}
		}).start();

		
		
						enemyThread();
						enemyFromTopThread();
											
				

	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override

	public void render(float dt) {
		if(game_paused)
			return;

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Drawing an image.
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		box.render(batch);


		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(batch, shape);
		}

		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.get(i).render(batch, shape);
		}
	}

	public void update() {
		
		if(game_paused)
			return;

		box.update();

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i) != null) {

				enemies.get(i).update();
			}
		}

		for (int i = 0; i < enemies2.size(); i++) {
			if (enemies2.get(i) != null) {
				enemies2.get(i).update();

			}
		}

	}

	private void enemyThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					
					if(!game_paused){
					
					int x = MathUtils.random(Enemy.UNIFORM_WIDTH, Gdx.graphics.getWidth() - Enemy.UNIFORM_WIDTH);
					int y = -Enemy.UNIFORM_HEIGHT;
					// int speed = MathUtils.random(10, 30);
					int speed = 15;
					int delay = MathUtils.random(300, 500);

					try {
						spawnEnemy(x, y, speed, txt);
						for (int i = 0; i < enemies.size(); i++) {
							if (enemies.get(i).getY() > Gdx.graphics.getHeight()) {
								enemies.remove(i);
							}

						}

						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					}
				}

			}
		}).start();

	}

	private void enemyFromTopThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					
					if(!game_paused){
						
					
					int x = MathUtils.random(Enemy.UNIFORM_WIDTH, Gdx.graphics.getWidth() - Enemy.UNIFORM_WIDTH);
					int y = Gdx.graphics.getHeight() + Enemy.UNIFORM_HEIGHT;
					int speed = -13;
					int delay = MathUtils.random(800, 900);
					try {
						spawnEnemyFromTop(x, y, speed, txt);
						for (int i = 0; i < enemies2.size(); i++) {
							if (enemies2.get(i).getY() < 0) {
								enemies2.remove(i);
							}

						}

						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					}

				}

			}
		}).start();
	}


	public void spawnEnemy(int x, int y, int speed, Texture currentTexture) {
		Enemy enemy = new Enemy(x, y, speed, Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT, txt);
		enemies.add(enemy);

	}

	public void spawnEnemyFromTop(int x, int y, int speed, Texture currentTexture) {
		Enemy enemy = new Enemy(x, y, speed, Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT, enemy2);
		enemies2.add(enemy);

	}

	@Override
	public void pause() {
		game_paused = true;
	}

	@Override
	public void resume() {
		game_paused = false;
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