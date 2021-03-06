package com.mabeproductions.trianglemadness;

import java.util.ArrayList;
import java.util.Date;
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
import com.badlogic.gdx.utils.TimeUtils;

public class GameSc implements Screen {

	// Levels
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
	private int rocketDelayTickCount = 200;
	private int randomDirection;
	private int tickCountsCoins;
	private int CoinsDelay = 50;
	private int index = 0;
	private float enemySpeed = -15;
	private int enemyDelay = 350;
	public int Score = 0;
	private int stage = 1;
	private int rocketSpeed = 0;
	private Texture cleaner;
	private int cleanerTicks;
	private int cleanerDelay = 70;

	// HourGlass variables
	private int hourglassDelay = 900;
	private int hourglassTicks;
	private Texture hourglassTexture;

	private int multiDelay = 1600;
	private int multiTicks;

	// Objects
	private ParticleEffect emitter;
	private ParticleEffect coinEmitter;
	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public GameRunner runner;
	private BitmapFont font;
	private Broadcaster broadcaster;
	private Sound stageSound;

	// Collections
	public ArrayList<Enemy> enemies2;
	private Texture[] cointxt = new Texture[7];
	public static ArrayList<Coin> coins = new ArrayList<Coin>();
	public ArrayList<Hourglass> hourglasess;
	public ArrayList<Rocket> rocketList;
	public ArrayList<Cleaner> cleanerList;
	public ArrayList<Multiplier> multiplierList;

	// Textures.
	private Texture background;
	private Texture txt;
	private Texture enemy2;
	private Texture rockettxt;
	private Texture multiplierTexture;
	private boolean isStarted = false;
	public Music music;
	public static Timer t;
	private Texture rightButtonTxt;

	public GameSc(GameRunner runnerClass) {
		t = new Timer("MainGameLoop");

		this.runner = runnerClass;
		setupBackground();

		box = new Box(this);

		rightButtonTxt = GameRunner.assets.get("rightButton.png", Texture.class);

		batch = new SpriteBatch();

		stageSound = GameRunner.assets.get("Sounds/stageSound.wav");
		txt = GameRunner.assets.get("Textures/Enemies/Boxers/Enemy.png");
		enemy2 = GameRunner.assets.get("Textures/Enemies/Enemy2.png");
		rockettxt = GameRunner.assets.get("Textures/Enemies/rocket.png");
		multiplierTexture = GameRunner.assets.get("multiplier.png");
		hourglassTexture = GameRunner.assets.get("clock.png");
		cleaner = GameRunner.assets.get("cleaner.png");


		emitter = GameRunner.rocketEmitter;
		coinEmitter = GameRunner.coinEmitter;

		rocketList = new ArrayList<Rocket>();
		enemies2 = new ArrayList<Enemy>();
		hourglasess = new ArrayList<Hourglass>();
		cleanerList = new ArrayList<Cleaner>();
		multiplierList = new ArrayList<Multiplier>();

		shape = new ShapeRenderer();

		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);

		cointxt[0] = GameRunner.assets.get("Coins/1.png");
		cointxt[1] = GameRunner.assets.get("Coins/2.png");
		cointxt[2] = GameRunner.assets.get("Coins/3.png");
		cointxt[3] = GameRunner.assets.get("Coins/4.png");
		cointxt[4] = GameRunner.assets.get("Coins/5.png");
		cointxt[5] = GameRunner.assets.get("Coins/6.png");
		cointxt[6] = GameRunner.assets.get("Coins/7.png");

		font = GameRunner.BigScoreFont;

	}

	private void setupBackground() {
		Preferences prefs = Gdx.app.getPreferences("Stats");
		prefs.flush();
		if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_1 && Level == 1) {
			background = GameRunner.assets.get("Textures/avoidness.png");
			music = GameRunner.assets.get("Sounds/gameMusic.mp3");
		}
		if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_2 && Level == 2) {
			music = GameRunner.assets.get("Sounds/gameMusic1.mp3");
			background = GameRunner.assets.get("Level2/avoidness.png");
		}
		if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_3 && Level == 3) {
			music = GameRunner.assets.get("Sounds/gameMusic3.mp3");
			background = GameRunner.assets.get("Level3/AvoidnessLava.png");
		}
		if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_4 && Level == 4) {
			music = GameRunner.assets.get("Sounds/gameMusic4.mp3");
			background = GameRunner.assets.get("Level4/wateBackground.png");
		}
		if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_5 && Level == 5) {
			music = GameRunner.assets.get("Sounds/gameMusic5.mp3");
			background = GameRunner.assets.get("Level5/avoidness.png");
		}
		if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_6 && Level == 6) {
			music = GameRunner.assets.get("Sounds/gameMusic6.mp3");
			background = GameRunner.assets.get("Level6/desertTheme.png");
		}

	}

	public void stageSound() {
		if (GameMenu.isMuted) {
			stageSound.play(0);
		} else
			stageSound.play(1f);

	}

	public void gameMusic() {
		music.setLooping(true);
		music.play();
		music.setVolume(0);
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				if (GameMenu.isMuted) {
					music.setVolume(0);
				} else
					music.setVolume(1f);

				Thread.currentThread().interrupt();
			}

		}, 500);

	}

	@Override
	public void show() {
		Preferences prefs = Gdx.app.getPreferences("Stats");
		prefs.putInteger("Tries", prefs.getInteger("Tries") + 1);
		prefs.flush();

		if (!prefs.getBoolean("isScoreReset")) {
			prefs.putInteger("TotalScore", 0);
			prefs.putBoolean("isScoreReset", true);
			prefs.flush();
		}

		t.schedule(new TimerTask() {

			@Override
			public void run() {

				boolean wasPressed = true;

				while (!isStarted) {
					int touchX = Gdx.input.getX();
					if (Gdx.input.justTouched() && touchX >= Gdx.graphics.getHeight() * 0.277f
							&& touchX <= Gdx.graphics.getHeight() * 1.55f)
						isStarted = true;
					if (touchX >= Gdx.graphics.getHeight() * 1.55f && Gdx.input.justTouched() && wasPressed) {
						wasPressed = false;

						Preferences prefs = Gdx.app.getPreferences("Stats");

						boolean isLevelChanged = false;

						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_2 && Level == 1 && !isLevelChanged) {
							Level = 2;
							isLevelChanged = true;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_3 && Level == 2 && !isLevelChanged) {
							Level = 3;
							isLevelChanged = true;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_4 && Level == 3 && !isLevelChanged) {
							Level = 4;
							isLevelChanged = true;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_5 && Level == 4 && !isLevelChanged) {
							Level = 5;
							isLevelChanged = true;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_6 && Level == 5 && !isLevelChanged) {
							Level = 6;
							isLevelChanged = true;
						}

						rightButtonTxt = GameRunner.assets.get("rightButton.png", Texture.class);

						if (!isLevelChanged) {
							rightButtonTxt = GameRunner.assets.get("rightButtonRed.png", Texture.class);
						}

						if (Level > 6)
							Level = 1;

						setupBackground();
						box = new Box(GameSc.this);

					}
					if (touchX <= Gdx.graphics.getHeight() * 0.277f && Gdx.input.justTouched() && wasPressed) {
						wasPressed = false;

						Preferences prefs = Gdx.app.getPreferences("Stats");

						rightButtonTxt = GameRunner.assets.get("rightButton.png", Texture.class);

						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_1 && Level == 2) {
							Level = 1;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_2 && Level == 3) {
							Level = 2;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_3 && Level == 4) {
							Level = 3;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_4 && Level == 5) {
							Level = 4;
						}
						if (prefs.getInteger("TotalScore") >= GameSc.LEVEL_5 && Level == 6) {
							Level = 5;
						}

						if (Level < 1)
							Level = 6;
						setupBackground();
						box = new Box(GameSc.this);

					}
					if (!Gdx.input.isTouched())
						wasPressed = true;
				}
				gameMusic();

				gameStages();
				enemyFromTopThread();

				t.schedule(new TimerTask() {

					@Override
					public void run() {
						if (runner.getScreen() != GameSc.this) {
							this.cancel();
						}
						update();
					}
				}, 16, 16);

				Thread.currentThread().interrupt();
			}
		}, 100);

	}

	public void gameStages() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this && stage < 5) {

					try {
						stageSound();
						broadcaster = new Broadcaster(0,
								(int) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() * 0.0925f),
								Gdx.graphics.getHeight() * 0.0138f, "STAGE " + stage, GameSc.this);
						Thread.sleep(30000);
						stage++;

					} catch (InterruptedException e) {

						e.printStackTrace();
					}

				}
				
				

				broadcaster = new Broadcaster(0, (int) (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() * 0.0925f),
						Gdx.graphics.getHeight() * 0.0138f, "Freeplay", GameSc.this);

				Thread.currentThread().interrupt();

			}
		}, "Stages").start();
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

		if (!isStarted) {
			batch.draw(GameRunner.assets.get("leftButton.png", Texture.class), 0, 0, Gdx.graphics.getHeight() * 0.1851f,
					Gdx.graphics.getHeight());
			batch.draw(rightButtonTxt, Gdx.graphics.getWidth() - Gdx.graphics.getHeight() * 0.1851f, 0,
					Gdx.graphics.getHeight() * 0.1851f, Gdx.graphics.getHeight());
			GameRunner.BigScoreFont.getData().setScale(Gdx.graphics.getHeight()*0.00075f);
			GameRunner.BigScoreFont.draw(batch, "PRESS TO START", Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.9f);
			GameRunner.BigScoreFont.getData().setScale(Gdx.graphics.getHeight()*0.0013888f);
		}

		font.getData().setScale(Gdx.graphics.getHeight() * 0.000825f);

		font.draw(batch, "" + Score, Gdx.graphics.getWidth() / 2 - (String.valueOf(Score).length() * 30),
				Gdx.graphics.getHeight() / 2 + Gdx.graphics.getHeight() * 0.0462f);

		font.getData().setScale(Gdx.graphics.getHeight() * 0.000525f);
		if (!isStarted) {
			Preferences prefs = Gdx.app.getPreferences("Stats");

			boolean isPrinted = false;

			if (prefs.getInteger("TotalScore") <= GameSc.LEVEL_2 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_1
					&& !isPrinted) {
				if (GameSc.LEVEL_2 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0) {
					font.draw(batch,
							(Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_2)
									+ " TO THE NEXT LEVEL",
							Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() * 0.5777f,
							Gdx.graphics.getHeight() * 0.2777f);
					isPrinted = true;
				}
			}
			if (prefs.getInteger("TotalScore") <= GameSc.LEVEL_3 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_2
					&& !isPrinted) {
				if (GameSc.LEVEL_3 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0) {
					font.draw(batch,
							(Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_3)
									+ " TO THE NEXT LEVEL",
							Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() * 0.5777f,
							Gdx.graphics.getHeight() * 0.2777f);
					isPrinted = true;
				}
			}
			if (prefs.getInteger("TotalScore") <= GameSc.LEVEL_4 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_3
					&& !isPrinted) {
				if (GameSc.LEVEL_4 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0) {
					font.draw(batch,
							(Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_4)
									+ " TO THE NEXT LEVEL",
							Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() * 0.5777f,
							Gdx.graphics.getHeight() * 0.2777f);
					isPrinted = true;
				}
			}
			if (prefs.getInteger("TotalScore") <= GameSc.LEVEL_5 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_4
					&& !isPrinted) {
				if (GameSc.LEVEL_5 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") >= 0) {
					font.draw(batch,
							(Gdx.app.getPreferences("Stats").getInteger("TotalScore") - GameSc.LEVEL_5)
									+ " TO THE NEXT LEVEL",
							Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() * 0.5777f,
							Gdx.graphics.getHeight() * 0.2777f);
					isPrinted = true;
				}
			}
			if (prefs.getInteger("TotalScore") <= GameSc.LEVEL_6 && prefs.getInteger("TotalScore") >= GameSc.LEVEL_5
					&& !isPrinted) {
				if (GameSc.LEVEL_6 - Gdx.app.getPreferences("Stats").getInteger("TotalScore") > 0) {
					font.draw(batch, " MAXIMUM LEVEL", Gdx.graphics.getWidth() / 2 - Gdx.graphics.getHeight() * 0.5777f,
							Gdx.graphics.getHeight() * 0.2777f);
					isPrinted = true;
				}
			}

			font.getData().setScale(Gdx.graphics.getHeight() * 0.0013888f);
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

		if (broadcaster != null)
			broadcaster.render(batch);

		// rendering rocket
		for (int i = 0; i < rocketList.size(); i++) {
			rocketList.get(i).render(batch, shape);
		}

		box.render(batch);

		// ========================================================
		// Rendering hourglass

		for (int i = 0; i < hourglasess.size(); i++) {
			if (hourglasess.get(i) != null) {
				hourglasess.get(i).render(batch, shape);
			}
		}
		// ========================================================

		for (int i = 0; i < cleanerList.size(); i++) {
			if (cleanerList.get(i) != null) {
				cleanerList.get(i).render(batch, shape);
			}
		}

		for (int i = 0; i < multiplierList.size(); i++) {
			if (multiplierList.get(i) != null) {
				multiplierList.get(i).render(batch, shape);
			}
		}

	}

	private int tickCount = 0;
	private int time = 0;

	public void update() {

		if (game_paused)
			return;

		// ==============Tick count/sec====================
		tickCount++;
		Date date = new Date(TimeUtils.millis());

		if ((date.getSeconds() - time) > 1) {
			// System.out.println("Ticks/sec: " + tickCount);
			tickCount = 0;
			time = date.getSeconds();
		}
		// ==============Testing====================

		if (!GameMenu.isMuted && Box.tookHourglass == false) {
			music.setVolume(1f);
		}
		if (!GameMenu.isMuted && Box.tookHourglass == true) {
			music.setVolume(0.3f);
		}

		// Updating coins
		for (int i = 0; i < coins.size(); i++) {
			coins.get(i).update();

			// Removes a coin if it's < 0
			if (coins.get(i).getY() < -Coin.getCoinSize()) {
				coins.remove(coins.get(i));
			}

		}

		box.update();

		// Updating enemies

		for (int i = 0; i < enemies2.size(); i++) {
			if (enemies2.get(i) != null) {
				enemies2.get(i).update();

			}

		}

		randomDirection = MathUtils.random(0, 1);

		// Spawning rockets
		if (randomDirection == 1) {
			if (stage > 1) {
				if (stage == 2)
					rocketSpeed = (int) (Gdx.graphics.getHeight() * 0.015f);
				if (stage == 3)
					rocketSpeed = (int) (Gdx.graphics.getHeight() * 0.025f);
				if (stage == 4)
					rocketSpeed = (int) (Gdx.graphics.getHeight() * 0.03f);

				if (rocketTickCount >= rocketDelayTickCount) {
					rocketDelayTickCount = MathUtils.random(400, 1000);

					spawnRocket(-200, (int) box.getPos().y / 2, rocketSpeed, -90, rockettxt, emitter);
					for (int i = 0; i < rocketList.size(); i++) {
						if (rocketList.get(i).getX() > Gdx.graphics.getWidth()) {
							rocketList.remove(rocketList.get(i));
						}
					}
					rocketTickCount = 0;
				}

				rocketTickCount++;
			}

		}

		if (randomDirection == 0) {

			if (stage > 1) {
				if (stage == 2)
					rocketSpeed = -(int) (Gdx.graphics.getHeight() * 0.015f);
				if (stage == 3)
					rocketSpeed = -(int) (Gdx.graphics.getHeight() * 0.025f);
				if (stage == 4)
					rocketSpeed = -(int) (Gdx.graphics.getHeight() * 0.03f);
				if (rocketTickCount >= rocketDelayTickCount) {
					rocketDelayTickCount = MathUtils.random(400, 1000);
					spawnRocket(Gdx.graphics.getWidth() + 200,
							(int) (box.getPos().y - Gdx.graphics.getHeight() * 0.2777f), rocketSpeed, 90, rockettxt,
							emitter);
					for (int i = 0; i < rocketList.size(); i++) {
						if (rocketList.get(i).getX() < 0) {
							rocketList.remove(rocketList.get(i));
						}
					}
					rocketTickCount = 0;
				}
				rocketTickCount++;

			}

		}

		// Spawning coins
		if (tickCountsCoins >= CoinsDelay) {
			int x = MathUtils.random(Gdx.graphics.getWidth() - Coin.getCoinSize());

			spawnCoin(x, Gdx.graphics.getHeight(), (int) (Gdx.graphics.getHeight() * 0.0166f), cointxt, index,
					coinEmitter);

			// Checking if coin spawned on top of enemy
			tickCountsCoins = 0;
			try {
				for (int i = 0; i < enemies2.size(); i++) {
					if (coins.get(coins.size() - 1).getBounds().overlaps(enemies2.get(i).bounds)) {
						coins.remove(coins.size() - 1);
						tickCountsCoins = CoinsDelay;
					}
				}

			} catch (ArrayIndexOutOfBoundsException e) {
			}

		}
		tickCountsCoins++;

		// ===============================================================================================================
		// REMOVING HOURGLASESS

		// Hourglass spawn

		if (hourglassTicks >= hourglassDelay) {
			float x = MathUtils.random(Gdx.graphics.getWidth() - Hourglass.HourGlassSize);
			spawnHourglass(x, Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.00833f, hourglassTexture);
			hourglassTicks = 0;
			for (int i = 0; i < hourglasess.size(); i++) {
				if (hourglasess.get(i).getY() + Hourglass.HourGlassSize < 0) {
					hourglasess.remove(i);
				}
			}
		}

		hourglassTicks++;

		if (cleanerTicks >= cleanerDelay) {
			float x = MathUtils.random(Gdx.graphics.getWidth() - Hourglass.HourGlassSize);
			spawnTrashbins(x, Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.00833f, cleaner);
			cleanerTicks = 0;
			for (int i = 0; i < cleanerList.size(); i++) {
				if (cleanerList.get(i).getY() + Cleaner.CleanGlassSize < 0) {
					cleanerList.remove(i);
				}
			}
		}

		cleanerTicks++;

		if (multiTicks >= multiDelay) {
			float x = MathUtils.random(Gdx.graphics.getWidth() - Multiplier.multiSize);
			spawnMultiplier(x, Gdx.graphics.getHeight(), Gdx.graphics.getHeight() * 0.00833f, multiplierTexture);
			multiTicks = 0;
			for (int i = 0; i < multiplierList.size(); i++) {
				if (multiplierList.get(i).getY() + Multiplier.multiSize < 0) {
					multiplierList.remove(i);
				}
			}
		}

		multiTicks++;

		// ===============================================================================================================

	}

	// Enemy from top thread
	private void enemyFromTopThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (runner.getScreen() == GameSc.this) {
					if (!game_paused) {

						if (stage == 1) {
							enemySpeed = -Gdx.graphics.getHeight() * 0.0157f;
							enemyDelay = 290;

						}
						if (stage == 2) {
							enemySpeed = -Gdx.graphics.getHeight() * 0.02037f;
							enemyDelay = 230;
						}
						if (stage == 3) {
							enemySpeed = -Gdx.graphics.getHeight() * 0.025f;
							enemyDelay = 170;
						}
						if (stage == 4) {
							enemySpeed = -Gdx.graphics.getHeight() * 0.0296f;
							enemyDelay = 135;
						}
						float x = MathUtils.random(0, Gdx.graphics.getWidth() - Enemy.UNIFORM_WIDTH);
						float y = Gdx.graphics.getHeight() + Enemy.UNIFORM_HEIGHT;
						spawnEnemyFromTop(x, y, Math.round(enemySpeed), enemy2);

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
		CoinsDelay = MathUtils.random(150, 300);

	}

	public void spawnEnemyFromTop(float x, float y, float speed, Texture currentTexture) {
		Enemy enemy = new Enemy(x, y, speed, Enemy.UNIFORM_WIDTH, Enemy.UNIFORM_HEIGHT, currentTexture);
		enemies2.add(enemy);
	}

	public void spawnHourglass(float x, int y, float speed, Texture texture) {
		Hourglass hourglass = new Hourglass(x, y, speed, texture);
		hourglassDelay = MathUtils.random(800, 1500);
		hourglasess.add(hourglass);
	}

	public void spawnTrashbins(float x, int y, float speed, Texture texture) {
		Cleaner cleaner = new Cleaner(x, y, speed, texture);
		cleanerList.add(cleaner);
		cleanerDelay = MathUtils.random(800, 1500);
	}

	public void spawnMultiplier(float x, int y, float speed, Texture texture) {
		Multiplier multi = new Multiplier(x, y, speed, texture);
		multiplierList.add(multi);
		multiDelay = MathUtils.random(800, 1500);
	}

	public void spawnRocket(int x, int y, int speed, int degrees, Texture currentTexture, ParticleEffect emitter) {
		Rocket rocket = new Rocket(x, y, speed, degrees, currentTexture, emitter);
		rocketList.add(rocket);
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

		coins.clear();
		hourglasess.clear();
		rocketList.clear();
		multiplierList.clear();
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