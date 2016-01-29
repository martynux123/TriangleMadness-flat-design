package com.mabeproductions.trianglemadness;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class GameSc implements Screen {

	//Booleans
	public boolean game_paused = false;

	
	//Variables
	private int rocketTickCount = 0;
	private int rocketDelayTickCount = 15000;
	private int randomDirection;
	private int tickCountsCoins;
	private int CoinsDelay=9000;
	private int index=0;


	
	//Objects
	private ParticleEffect emitter;
	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public Rocket rocket;
	public Coin coin; /*TO-DO: Reikia coinams arraylisto*/
	public GameRunner runner;
	
	//Collections
	public ArrayList<Enemy> enemies;
	public ArrayList<Enemy> enemies2;
	private Texture[] cointxt = new Texture[7];

	
	//Textures.
	private Texture background;
	private Texture txt;
	private Texture enemy2;
	private Texture rockettxt;
	
	
	
	
	
	
	

	public GameSc(GameRunner runnerClass) {
		this.runner = runnerClass;

		int firstX = MathUtils.random(Gdx.graphics.getWidth());
		
		batch = new SpriteBatch();

		coin = new Coin(firstX ,Gdx.graphics.getHeight(),4,cointxt,index);
		
		background = GameRunner.assets.get("Textures/background.png");
		txt = GameRunner.assets.get("Textures/Enemies/Boxers/Enemy.png");
		enemy2 = GameRunner.assets.get("Textures/Enemies/Enemy2.png");
		rockettxt = GameRunner.assets.get("Textures/Enemies/rocket.png");
		
		
		
		emitter = GameRunner.rocketEmitter;

		box = new Box(this);

		enemies = new ArrayList<Enemy>();
		enemies2 = new ArrayList<Enemy>();
		shape = new ShapeRenderer();

		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		spawnRocket(
				Gdx.graphics.getWidth() + 100 /*
												 * kad pirma raketa nesimatytu,
												 * atspawninam ja uz ekrano ribu
												 */, (int) box.getPos().y, 15, -90, rockettxt, emitter);


		enemyThread();
		enemyFromTopThread();

		cointxt[0] = GameRunner.assets.get("Coins/1.png");
		cointxt[1] = GameRunner.assets.get("Coins/2.png");
		cointxt[2] = GameRunner.assets.get("Coins/3.png");
		cointxt[3] = GameRunner.assets.get("Coins/4.png");
		cointxt[4] = GameRunner.assets.get("Coins/5.png");
		cointxt[5] = GameRunner.assets.get("Coins/6.png");
		cointxt[6] = GameRunner.assets.get("Coins/7.png");
	}

	@Override
	public void show() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this) {
					try {
						// Sleeping a thread for thread delays to be alike
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					update();
				}
				
				Thread.currentThread().interrupt();
				
			}
		}, "Update").start();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override

	public void render(float dt) {
		if (game_paused)
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
	
		
		
		rocket.render(batch, shape);
		coin.render(batch, shape);
	}

	public void update() {

		if (game_paused)
			return;
		randomDirection = MathUtils.random(0, 1);
		//over.update();

		coin.update();
		box.update();
		rocket.update();

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
		
		
		

		if (randomDirection == 0) {
			if (rocketTickCount >= rocketDelayTickCount) {
				rocketDelayTickCount = MathUtils.random(4000, 5000);

				spawnRocket(-100, (int) box.getPos().y, 15, -90, rockettxt, emitter);
				rocketTickCount = 0;
			}
			rocketTickCount++;

		}

		if (randomDirection == 1) {
			if (rocketTickCount >= rocketDelayTickCount) {
				rocketDelayTickCount = MathUtils.random(4000, 5000);

				spawnRocket(Gdx.graphics.getWidth() + 100, (int) box.getPos().y/2, -15, 90, rockettxt, emitter);
				rocketTickCount = 0;
			}
			rocketTickCount++;

		}

		if(tickCountsCoins>=CoinsDelay){
			int x = MathUtils.random(Gdx.graphics.getWidth());
			spawnCoin(x, Gdx.graphics.getHeight(), 4,cointxt, index);
			
			tickCountsCoins=0;
		}
		tickCountsCoins++;
		
		
		
		
		
		
	
	}

	private void enemyThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this) {

					if (!game_paused) {

						int x = MathUtils.random(Enemy.UNIFORM_WIDTH, Gdx.graphics.getWidth()-Enemy.UNIFORM_WIDTH);
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
				Thread.currentThread().interrupt();

			}
		}, "EnemyThread").start();

	}

	private void enemyFromTopThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this) {

					if (!game_paused) {

						int x = MathUtils.random(Enemy.UNIFORM_WIDTH, Gdx.graphics.getWidth());
						int y = Gdx.graphics.getHeight() + Enemy.UNIFORM_HEIGHT;
						int speed = -13;
						int delay = MathUtils.random(800, 900);
						try {
							spawnEnemyFromTop(x, y, speed, enemy2);
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
				Thread.currentThread().interrupt();
			}
		}, "EnemyFromTopThread").start();
	}

	public void spawnCoin(int x, int y, int speed, Texture[] txt, int index) {
		coin = new Coin(x,y,speed,txt,index);
		
	}
	public void spawnEnemy(int x, int y, int speed, Texture currentTexture) {
		Enemy enemy = new Enemy(x, y, speed, Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT, currentTexture);
		enemies.add(enemy);

	}

	public void spawnEnemyFromTop(int x, int y, int speed, Texture currentTexture) {
		Enemy enemy = new Enemy(x, y, speed, Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT, currentTexture);
		enemies2.add(enemy);

	}

	public void spawnRocket(int x, int y, int speed, int degrees, Texture currentTexture, ParticleEffect emitter) {
		rocket = new Rocket(x, y, speed, degrees, currentTexture, emitter);
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
		shape.dispose();
		batch.dispose();
		emitter.dispose();
		box.dispose();
		rocket.dispose();
		
		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.get(i).dispose();
		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).dispose();
		}
	}

	@Override
	public void hide() {

	}

}