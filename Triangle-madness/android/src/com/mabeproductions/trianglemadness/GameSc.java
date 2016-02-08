package com.mabeproductions.trianglemadness;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;


public class GameSc implements Screen {
	
	
	//Levels
	public static final int LEVEL_1 = 0;
	public static final int LEVEL_2 = 1000;
	public static final int LEVEL_3 = 2500;
	public static final int LEVEL_4 = 4000;
	public static final int LEVEL_5 = 5500;
	public static final int LEVEL_6 = 7000;
	
	public static int Level = 1;
	

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
	
	
	//HourGlass variables
	private int hourglassDelay = 6000;
	private int hourglassTicks;
	private Texture hourglassTexture;

	
	
	// Objects
	private ParticleEffect emitter;
	private ParticleEffect coinEmitter;
	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public Rocket rocket;
	public GameRunner runner;
	private BitmapFont font;
	private Broadcaster broadcaster;
	private Sound stageSound;
	

	// Collections
	public ArrayList<Enemy> enemies2;
	private Texture[] cointxt = new Texture[7];
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public ArrayList<Hourglass> hourglasess;

	// Textures.
	private Texture background;
	private Texture txt;
	private Texture enemy2;
	private Texture rockettxt;
	private boolean isStarted = false;
	public Music music;
	private Timer t;
	private Texture rightButtonTxt;
	
	public GameSc(GameRunner runnerClass) {
		
		this.runner = runnerClass;
		setupBackground();
		
		box = new Box(this);
		
		rightButtonTxt = GameRunner.assets.get("rightButton.png", Texture.class);
		music = GameRunner.assets.get("Sounds/gameMusic.wav");

		batch = new SpriteBatch();
		
		stageSound = GameRunner.assets.get("Sounds/stageSound.wav");
		txt = GameRunner.assets.get("Textures/Enemies/Boxers/Enemy.png");
		enemy2 = GameRunner.assets.get("Textures/Enemies/Enemy2.png");
		rockettxt = GameRunner.assets.get("Textures/Enemies/rocket.png");
		hourglassTexture = GameRunner.assets.get("hourglass.png");
		
		
		t = new Timer();

		emitter = GameRunner.rocketEmitter;
		coinEmitter = GameRunner.coinEmitter;
		

		enemies2 = new ArrayList<Enemy>();
		hourglasess = new ArrayList<Hourglass>();
		
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
	
	private void setupBackground(){
		Preferences prefs = Gdx.app.getPreferences("Stats");
		prefs.flush();
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_1 && Level == 1){
			background = GameRunner.assets.get("Textures/avoidness.png");
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_2 && Level == 2){
			background = GameRunner.assets.get("Level2/avoidness.png" );
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_3 && Level == 3){
			background = GameRunner.assets.get("Level3/AvoidnessLava.png");
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_4 && Level == 4){
			background = GameRunner.assets.get("Level4/wateBackground.png");
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_5 && Level == 5){
			background = GameRunner.assets.get("Level5/avoidness.png");
		}
		if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_6 && Level == 6){
			background = GameRunner.assets.get("Level6/desertTheme.png");
		}
		
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
		
		if(!prefs.getBoolean("isScoreReset")){
			prefs.putInteger("TotalScore", 0);
			prefs.putBoolean("isScoreReset", true);
			prefs.flush();
		}
		
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				boolean wasPressed = true;
				broadcaster = new Broadcaster((int) (Gdx.graphics.getHeight()*0.1203f), (int) (Gdx.graphics.getHeight()-Gdx.graphics.getHeight()*0.0925f), 0, "PRESS TO START", GameSc.this);
				while(!isStarted){
					int touchX = Gdx.input.getX();
					if(Gdx.input.justTouched() && touchX >= Gdx.graphics.getHeight()*0.277f && touchX <= Gdx.graphics.getHeight()*1.55f)
						isStarted = true;
					if(touchX >= Gdx.graphics.getHeight()*1.55f && Gdx.input.justTouched() && wasPressed){
						wasPressed = false;
						
						Preferences prefs = Gdx.app.getPreferences("Stats");
						
						boolean isLevelChanged = false;
						
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_2 && Level == 1 && !isLevelChanged){
							Level = 2;
							isLevelChanged = true;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_3 && Level == 2 && !isLevelChanged){
							Level = 3;
							isLevelChanged = true;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_4 && Level == 3 && !isLevelChanged){
							Level = 4;
							isLevelChanged = true;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_5 && Level == 4 && !isLevelChanged){
							Level = 5;
							isLevelChanged = true;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_6 && Level == 5 && !isLevelChanged){
							Level = 6;
							isLevelChanged = true;
						}
						
						rightButtonTxt = GameRunner.assets.get("rightButton.png", Texture.class);
						
						if(!isLevelChanged){
							rightButtonTxt = GameRunner.assets.get("rightButtonRed.png", Texture.class);
						}
						
						
						if(Level > 6) Level = 1;
							
						setupBackground();
						box = new Box(GameSc.this);
						
					}
					if(touchX <= Gdx.graphics.getHeight()*0.277f && Gdx.input.justTouched() && wasPressed){
						wasPressed = false;
						
						Preferences prefs = Gdx.app.getPreferences("Stats");
						
						rightButtonTxt = GameRunner.assets.get("rightButton.png", Texture.class);
						
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_1 && Level == 2){
							Level = 1;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_2 && Level == 3){
							Level = 2;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_3 && Level == 4){
							Level = 3;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_4 && Level == 5){
							Level = 4;
						}
						if(prefs.getInteger("TotalScore") >= GameSc.LEVEL_5 && Level == 6){
							Level = 5;
						}
						
						if(Level < 1)
							Level = 6;
						setupBackground();
						box = new Box(GameSc.this);
						
					}
					if(!Gdx.input.isTouched())
						wasPressed = true;
				}
				gameMusic();
					
				gameStages();
				enemyFromTopThread();
				
				
		
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
		
		
		if(!isStarted){
			batch.draw(GameRunner.assets.get("leftButton.png", Texture.class), 0, 0, Gdx.graphics.getHeight()*0.1851f, Gdx.graphics.getHeight());
			batch.draw(rightButtonTxt, Gdx.graphics.getWidth() - Gdx.graphics.getHeight()*0.1851f, 0, Gdx.graphics.getHeight()*0.1851f, Gdx.graphics.getHeight());
		}
		
		
		font.getData().setScale(Gdx.graphics.getHeight()*0.000825f);
		
		font.draw(batch, "" + Score, Gdx.graphics.getWidth() / 2 - (String.valueOf(Score).length() * 30),
				Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight()*0.0462f);

		font.getData().setScale(Gdx.graphics.getHeight()*0.000525f);
		if(!isStarted){
			Preferences prefs = Gdx.app.getPreferences("Stats");
			
			boolean isPrinted = false;
			
			if(prefs.getInteger("TotalScore") <= GameSc.LEVEL_2 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_1 && !isPrinted){
				if( GameSc.LEVEL_2 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0){
						font.draw(batch, (Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_2) + " TO THE NEXT LEVEL", Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()*0.5777f , Gdx.graphics.getHeight()*0.2777f);
						isPrinted = true;
				}
			}
			if(prefs.getInteger("TotalScore") <= GameSc.LEVEL_3 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_2  && !isPrinted){
				if(GameSc.LEVEL_3 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0){
					font.draw(batch, (Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_3) + " TO THE NEXT LEVEL", Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()*0.5777f , Gdx.graphics.getHeight()*0.2777f);
					isPrinted = true;
				}
			}
			if(prefs.getInteger("TotalScore") <= GameSc.LEVEL_4 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_3  && !isPrinted){
				if(GameSc.LEVEL_4 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0){
					font.draw(batch, (Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_4) + " TO THE NEXT LEVEL", Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()*0.5777f , Gdx.graphics.getHeight()*0.2777f);
				isPrinted = true;
			}
			}
			if(prefs.getInteger("TotalScore") <= GameSc.LEVEL_5 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_4  && !isPrinted){
				if(GameSc.LEVEL_5 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0){
					font.draw(batch, (Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_5) + " TO THE NEXT LEVEL", Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()*0.5777f , Gdx.graphics.getHeight()*0.2777f);
				isPrinted = true;
			}
			}
			if(prefs.getInteger("TotalScore") <= GameSc.LEVEL_6 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_5  && !isPrinted){
				if( GameSc.LEVEL_6 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") > 0){
					font.draw(batch," MAXIMUM LEVEL", Gdx.graphics.getWidth()/2 - Gdx.graphics.getHeight()*0.5777f , Gdx.graphics.getHeight()*0.2777f);
				isPrinted = true;
			}
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
		
		
		//========================================================
		//Rendering hourglass
				for(int i = 0; i<hourglasess.size();i++){
					if(hourglasess.get(i)!=null){
						hourglasess.get(i).render(batch, shape);						
					}
				}
				//========================================================
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
				rocketDelayTickCount = MathUtils.random(4000, 5000);

	
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
			try{
			for (int i = 0; i < enemies2.size(); i++) {
				if(coins.get(coins.size()-1).getBounds().overlaps(enemies2.get(i).bounds)){
					coins.remove(coins.size()-1);
				}else{
					tickCountsCoins = 0;
				}
			}
			
			}catch(ArrayIndexOutOfBoundsException e){
			}
			
		}
		tickCountsCoins++;
		
		
		//===============================================================================================================
		//REMOVING HOURGLASESS
		for(int i = 0; i<hourglasess.size(); i++){
			if(hourglasess.get(i).getY()+Hourglass.HourGlassSize<0){
				hourglasess.remove(i);
			}
		}

		//Hourglass spawn
		if(hourglassTicks>=hourglassDelay){
			float x = MathUtils.random(Gdx.graphics.getWidth()-Hourglass.HourGlassSize);
			spawnHourglass(x, Gdx.graphics.getHeight(),Gdx.graphics.getHeight()*0.00833f,hourglassTexture );
			hourglassTicks=0;
			try{
				
				for(int i =0; i<enemies2.size(); i++){
					if(hourglasess.get(hourglasess.size()-1).hourBounds().overlaps(enemies2.get(i).bounds)){
						hourglasess.remove(hourglasess.size()-1);
						hourglassTicks=0;
			}
			}
			}catch(Exception e){	
			}
			}
			
		hourglassTicks++;
		//===============================================================================================================

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
	//===============================================================================================================
	public void spawnHourglass(float x, int y, float speed, Texture texture){
		Hourglass hourglass = new Hourglass(x,y,speed,texture);
		hourglasess.add(hourglass);		
	}
	//===============================================================================================================

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
		
		hourglasess.clear();

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