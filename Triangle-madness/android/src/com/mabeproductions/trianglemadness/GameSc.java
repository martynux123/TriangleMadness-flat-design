package com.mabeproductions.trianglemadness;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class GameSc implements Screen {

	// Booleans
	public boolean game_paused = false;

	// Variables
	private int rocketTickCount = 0;
	private int rocketDelayTickCount = 15000;
	private int randomDirection;
	private int tickCountsCoins;
	private int CoinsDelay = 900;
	private int index = 0;
	private float enemySpeed = -15;
	private int enemyDelay = 350;
	private int tickCountEnemySpeed = 0;
	public int Score = 0;
	private int stage = 1;
	private int rocketSpeed = 0;
	
	//Music variables

	
	
	// Objects
	private ParticleEffect emitter;
	private ParticleEffect coinEmitter;
	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public Rocket rocket;
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public GameRunner runner;
	private BitmapFont font;
	private Broadcaster broadcaster;
	private Sound stageSound;
	
	
	
	
	// Collections
	public ArrayList<Enemy> enemies2;
	private Texture[] cointxt = new Texture[7];

	// Textures.
	private Texture background;
	private Texture txt;
	private Texture enemy2;
	private Texture rockettxt;
	private boolean isStarted = false;
	public Music music;
	private Timer t;

	
	

	
	
	public GameSc(GameRunner runnerClass) {
		
		box = new Box(this);
		this.runner = runnerClass;

		
	
		
				
		music = GameRunner.assets.get("Sounds/gameMusic.wav");

		batch = new SpriteBatch();
		
		stageSound = GameRunner.assets.get("Sounds/stageSound.wav");
		background = GameRunner.assets.get("Textures/avoidness.png");
		txt = GameRunner.assets.get("Textures/Enemies/Boxers/Enemy.png");
		enemy2 = GameRunner.assets.get("Textures/Enemies/Enemy2.png");
		rockettxt = GameRunner.assets.get("Textures/Enemies/rocket.png");
		
		t = new Timer();

		emitter = GameRunner.rocketEmitter;
		coinEmitter = GameRunner.coinEmitter;


		enemies2 = new ArrayList<Enemy>();
		shape = new ShapeRenderer();

		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		spawnRocket(Gdx.graphics.getWidth() + 200, (int) box.getPos().y, (int) (Gdx.graphics.getHeight()*0.0138f), -90, rockettxt, emitter);


		cointxt[0] = GameRunner.assets.get("Coins/1.png");
		cointxt[1] = GameRunner.assets.get("Coins/2.png");
		cointxt[2] = GameRunner.assets.get("Coins/3.png");
		cointxt[3] = GameRunner.assets.get("Coins/4.png");
		cointxt[4] = GameRunner.assets.get("Coins/5.png");
		cointxt[5] = GameRunner.assets.get("Coins/6.png");
		cointxt[6] = GameRunner.assets.get("Coins/7.png");

		font = GameRunner.BigScoreFont;
		
	}
	
	public void stageSound(){
		if(GameMenu.isMuted){
			stageSound.play(0);
		}else stageSound.play(1f);
		
	}
	
	public void gameMusic(){
		music.setLooping(true);
		music.play();
		music.setVolume(0);
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				if(GameMenu.isMuted){
					music.setVolume(0);
				}else music.setVolume(1f);
			}
		}, 500);
			
	}

	@Override
	public void show() {
		
		
		Preferences prefs = Gdx.app.getPreferences("Stats");
		prefs.putInteger("Tries", prefs.getInteger("Tries") + 1);
		prefs.flush();
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				broadcaster = new Broadcaster((int) (Gdx.graphics.getHeight()*0.1203f), (int) (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.0925f), 0, "PRESS TO START", GameSc.this);
				while(!isStarted){
					if(Gdx.input.justTouched())
						isStarted = true;
				}
				gameMusic();
					
				gameStages();
				enemyFromTopThread();
				
				
				// ================== Update thread ================================
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
				//==========================================//
				
				
			}
		}, 100);

	}

	public void gameStages() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this & stage < 5) {
					
					try {
						stageSound();
						broadcaster = new Broadcaster(0, (int) (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.0925f), Gdx.graphics.getHeight()*0.0138f, "STAGE " + stage, GameSc.this);
						Thread.sleep(30000);
						stage++;
						
					} catch (InterruptedException e) {

						e.printStackTrace();
					}

				}
				
				broadcaster = new Broadcaster(0, (int) (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.0925f), Gdx.graphics.getHeight()*0.0138f, "Freeplay", GameSc.this);
				
				Thread.currentThread().interrupt();

			}
		}).start();
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
		
		
		font.getData().setScale(Gdx.graphics.getHeight()*0.000925f);
		
		font.draw(batch, "" + Score, Gdx.graphics.getWidth() / 2 - (String.valueOf(Score).length() * 30),
				Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight()*0.0462f);

		if(!isStarted){
			
			
			if(Gdx.app.getPreferences("Stats").getInteger("Tries") > 1){
				font.draw(batch, Gdx.app.getPreferences("Stats").getInteger("Tries") + " TRIES", Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()*0.2777f , Gdx.graphics.getHeight()*0.2777f);
			}else{
				font.draw(batch, Gdx.app.getPreferences("Stats").getInteger("Tries") + " TRY", Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()*0.2777f, Gdx.graphics.getHeight()*0.2777f);
			}
			font.getData().setScale(Gdx.graphics.getHeight()*0.0013888f);
		}
		batch.end();

		// Rendering enemies
		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.get(i).render(batch, shape);
		}

		// rendering coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).render(batch, shape);

		}
		if(broadcaster != null)
			broadcaster.render(batch);

		// rendering rocket
		rocket.render(batch, shape);
		box.render(batch);

	}

	public void update() {

		if (game_paused)
			return;

		randomDirection = MathUtils.random(0, 1);

		// Updating coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).update();

			// Removes a coin if it's < 0
			if (coins.get(i).getY() < -Coin.getCoinSize()) {
				coins.remove(coins.get(i));

			}

		}

		box.update();
		rocket.update();

		// Updating enemies
		for (int i = 0; i < enemies2.size(); i++) {
			if (enemies2.get(i) != null) {
				enemies2.get(i).update();

			}

		}

		// Spawning rockets
		if (randomDirection == 0) {
			if(stage == 2)
				rocketSpeed = (int) (Gdx.graphics.getHeight()*0.0231f);
			if(stage == 3)
				rocketSpeed =(int) (Gdx.graphics.getHeight()*0.0324f) ;
			if(stage == 4)
				rocketSpeed = (int) (Gdx.graphics.getHeight()*0.0416f);
				
			
			if (rocketTickCount >= rocketDelayTickCount) {
				rocketDelayTickCount = MathUtils.random(4000, 9000);

				spawnRocket(-200, (int) box.getPos().y / 2, rocketSpeed, -90, rockettxt, emitter);
				rocketTickCount = 0;
			}
			if(stage > 1)
				rocketTickCount++;

		}

		if (randomDirection == 1) {
			if(stage == 2)
				rocketSpeed = -(int) (Gdx.graphics.getHeight()*0.0231f);
			if(stage == 3)
				rocketSpeed = -(int) (Gdx.graphics.getHeight()*0.0324f);
			if(stage == 4)
				rocketSpeed = -(int) (Gdx.graphics.getHeight()*0.0416f);
			if (rocketTickCount >= rocketDelayTickCount) {
				rocketDelayTickCount = MathUtils.random(4000, 5000);

				spawnRocket(Gdx.graphics.getWidth() + 200, (int) (box.getPos().y - Gdx.graphics.getHeight()*0.2777f), rocketSpeed, 90, rockettxt, emitter);
				rocketTickCount = 0;
			}
			if(stage>1){
				rocketTickCount++;
				
			}

		}

		// Spawning coins
		if (tickCountsCoins >= CoinsDelay) {
			int x = MathUtils.random(Gdx.graphics.getWidth() - Coin.getCoinSize());
			
			spawnCoin(x, Gdx.graphics.getHeight(), (int) (Gdx.graphics.getHeight()*0.0166f), cointxt, index, coinEmitter);
			
			//Checking if coin spawned on top of enemy
			for (int i = 0; i < enemies2.size(); i++) {
				if(coins.get(coins.size()-1).getBounds().overlaps(enemies2.get(i).bounds)){
					coins.remove(coins.size()-1);
				}else{
					tickCountsCoins = 0;
				}
			}
			
		}
		tickCountsCoins++;

		

	}

	// Enemy from top thread
	private void enemyFromTopThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this) {
					if (!game_paused) {

						System.out.println(enemySpeed);
						if(stage == 1){
							enemySpeed = - Gdx.graphics.getHeight()*0.0157f;
							enemyDelay = 290;
							
						}
						if(stage == 2){
							enemySpeed = -Gdx.graphics.getHeight()*0.02037f;
							enemyDelay = 230;
						}
						if(stage == 3){
							enemySpeed = -Gdx.graphics.getHeight()*0.025f;
							enemyDelay = 170;
						}
						if(stage == 4){
							enemySpeed = -Gdx.graphics.getHeight()*0.0296f;
							enemyDelay = 135;
						}
						float x = MathUtils.random(0, Gdx.graphics.getWidth() - Enemy.UNIFORM_WIDTH);
						float y = Gdx.graphics.getHeight() + Enemy.UNIFORM_HEIGHT;
						spawnEnemyFromTop(x, y,Math.round( enemySpeed), enemy2);

						for (int i = 0; i < enemies2.size(); i++) {
							if (enemies2.get(i).getY() + Enemy.UNIFORM_HEIGHT < 0) {
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
		coins.add(new Coin(x, y, speed, txt, index, effect));

	}

	public void spawnEnemyFromTop(float x, float y, float  speed, Texture currentTexture) {
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
		
		
		try {
			int x = test.getInteger("HighScore");
			if (x < Score)
				test.putInteger("HighScore", Score);
		} catch (Exception e) {
			test.putInteger("HighScore", Score);
		}

		test.putInteger("TotalScore", Gdx.app.getPreferences("Stats").getInteger("TotalScore") + Score);
		
		test.flush();
	}

	@Override
	public void hide() {

	}

}