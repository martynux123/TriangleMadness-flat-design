package com.mabeproductions.trianglemadness;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
	private int CoinsDelay=900;
	private int index=0;
	private int enemySpeed = -15;
	private int enemyDelay = 350;
	private int tickCountEnemySpeed = 0;
	public int Score = 0;

	
	//Objects
	private ParticleEffect emitter;
	private ParticleEffect coinEmitter;
	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public Rocket rocket;
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public GameRunner runner;
	private BitmapFont font;
	
	//Collections
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
		
		background = GameRunner.assets.get("Textures/avoidness.png");
		txt = GameRunner.assets.get("Textures/Enemies/Boxers/Enemy.png");
		enemy2 = GameRunner.assets.get("Textures/Enemies/Enemy2.png");
		rockettxt = GameRunner.assets.get("Textures/Enemies/rocket.png");
		
		
		emitter = GameRunner.rocketEmitter;
		coinEmitter = GameRunner.coinEmitter;
		
		box = new Box(this);

		enemies2 = new ArrayList<Enemy>();
		shape = new ShapeRenderer();

		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		spawnRocket(
				Gdx.graphics.getWidth() + 100, (int) box.getPos().y, 15, -90, rockettxt, emitter);


		enemyFromTopThread();

		cointxt[0] = GameRunner.assets.get("Coins/1.png");
		cointxt[1] = GameRunner.assets.get("Coins/2.png");
		cointxt[2] = GameRunner.assets.get("Coins/3.png");
		cointxt[3] = GameRunner.assets.get("Coins/4.png");
		cointxt[4] = GameRunner.assets.get("Coins/5.png");
		cointxt[5] = GameRunner.assets.get("Coins/6.png");
		cointxt[6] = GameRunner.assets.get("Coins/7.png");
		
		font = GameRunner.BigScoreFont;
	}

	@Override
	public void show() {
		
		//Update thread
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
		
		font.draw(batch, "" + Score, Gdx.graphics.getWidth()/2- (String.valueOf(Score).length() * 20), Gdx.graphics.getHeight()/2+50);
		
		batch.end();
		
		//Rendering enemies
		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.get(i).render(batch, shape);
		}
	
		//rendering coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).render(batch, shape);
			
		}
		
		//rendering rocket
		rocket.render(batch, shape);
		box.render(batch);
		
	}

	public void update() {

		if (game_paused)
			return;
		
		randomDirection = MathUtils.random(0, 1);

		//Updating coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).update();
			
			//Removes a coin if it's < 0 
			if(coins.get(i).getY()<0){
				coins.remove(coins.get(i));
				
			}
			
		}
		
		box.update();
		rocket.update();

		
		//Updating enemies
		for (int i = 0; i < enemies2.size(); i++) {
			if (enemies2.get(i) != null) {
				enemies2.get(i).update();

			}
			
		}
		
		
		
		//Spawning rockets
		if (randomDirection == 0) {
			if (rocketTickCount >= rocketDelayTickCount) {
				rocketDelayTickCount = MathUtils.random(4000, 9000);

				spawnRocket(-100, (int) box.getPos().y/2, 20, -90, rockettxt, emitter);
				rocketTickCount = 0;
			}
			rocketTickCount++;

		}

		if (randomDirection == 1) {
			if (rocketTickCount >= rocketDelayTickCount) {
				rocketDelayTickCount = MathUtils.random(4000, 5000);

				spawnRocket(Gdx.graphics.getWidth() + 100, (int) box.getPos().y-300, -20, 90, rockettxt, emitter);
				rocketTickCount = 0;
			}
			rocketTickCount++;

		}
		
		
		//Spawning coins
		if(tickCountsCoins>=CoinsDelay){
			int x = MathUtils.random(Gdx.graphics.getWidth());
			spawnCoin(x, Gdx.graphics.getHeight(), 18, cointxt, index, coinEmitter);
			tickCountsCoins=0;
		}
		tickCountsCoins++;
		
		
		//Generating random speed and delay for enemies
		if(tickCountEnemySpeed >= 5000){
			enemySpeed -= 1;;
			enemyDelay -= 15;
			tickCountEnemySpeed = 0;
			
		}
		tickCountEnemySpeed++;
		
		
	
	}
	
	//Enemy from top thread
	private void enemyFromTopThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this) {

					if (!game_paused) {

						int x = MathUtils.random(0, Gdx.graphics.getWidth() - Enemy.UNIFORM_WIDTH);
						int y = Gdx.graphics.getHeight() + Enemy.UNIFORM_HEIGHT;
							spawnEnemyFromTop(x, y, enemySpeed, enemy2);
							for (int i = 0; i < enemies2.size(); i++) {
								if (enemies2.get(i).getY() < 0) {
									enemies2.remove(i);
								}

							}

							try {
							Thread.sleep(enemyDelay);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

				}
				Thread.currentThread().interrupt();
			}
		}, "EnemyFromTopThread").start();
	}

	
	
	public void spawnCoin(int x, int y, int speed, Texture[] txt, int index, ParticleEffect effect) {
		coins.add(new Coin(x,y,speed,txt,index, effect));
		
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
		box.dispose();
		rocket.dispose();
		
		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.get(i).dispose();
		}
		
		coins.clear();
		
		Preferences test = Gdx.app.getPreferences("Stats");
		
		try{
			int x = test.getInteger("HighScore");			
			if(x < Score)
				test.putInteger("HighScore", Score);
		}catch(Exception e){
			test.putInteger("HighScore", Score);
		}
		
		
		test.putInteger("TotalMoney", Gdx.app.getPreferences("Stats").getInteger("TotalMoney") + Score);
		test.flush();
	}

	@Override
	public void hide() {

	}

}