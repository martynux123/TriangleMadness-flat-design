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

	private SpriteBatch batch;
	public Box box;
	private ShapeRenderer shape;
	public ArrayList<Snake> snakes;
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

		snakes = new ArrayList<Snake>();
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

		//Turetu pagal ideja pradeti po 3sek kiekviena karta kai, paleidzia screena, bet tai daro tik tada, kai spaudi try again, o jeigu is menu spaudi play, kazkodel neveikia. 
		//Jauciu reik kokio booleano. Kad timeri pradetu skaiciuoti nuo tada, kai ijungtas GameScreenas. 
		
		//Siaip istikruju problema yra tame, kad kai mes esam MenuScreene, jau pradeda veikti gameScreenas, taip neturetu buti, bet kazkodel taip yra.
		//Pabandyk kai isijungsi appa iskart paspausti play ir palauk koki 3-4sek. kai paspausi play, jau bus pradeje spawnintis snakes, boxai ir t.t
		//Manau cia tiesiog irgi ez, bet durnas eroras. :)
		Timer t = new Timer();
				t.schedule(new TimerTask() {
					
					@Override
					public void run() {
						
						snakeThread();
						enemyThread();
						enemyFromTopThread();
											
					}
				}, 3000);

	}

	@Override
	public void show() {

		// initialize

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override

	public void render(float dt) {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Drawing an image.
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
		box.render(batch);

		for (int i = 0; i < snakes.size(); i++) {

			snakes.get(i).render(shape);
		}

		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).render(batch, shape);
		}

		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.get(i).render(batch, shape);
		}
	}

	public void update() {

		box.update();

		for (int i = 0; i < snakes.size(); i++) {
			snakes.get(i).update();

		}

		for (int i = 0; i < snakes.size(); i++) {

			if (!(snakes.get(i).isAlive)) {
				snakes.remove(i);
			}

		}

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

	private void snakeThread() {

		new Thread(new Runnable() {
			Random r = new Random();
			// float[]anglem = {30,40,50,60,70,80,90,100};

			@Override
			public void run() {
				while (true) {
					System.out.println(snakes.size());
					int x = r.nextInt(Gdx.graphics.getWidth() - 50);
					int y = r.nextInt(Gdx.graphics.getHeight() - 900);
					int delay = r.nextInt((6000 - 2000) + 1) + 2000;
					int speed = MathUtils.random(30, 70);
					float angle = MathUtils.random(0, 360);
					int length = MathUtils.random(15, 25);
					try {
						Thread.sleep(delay);
						if (angle >= 135 && angle <= 225) {
							spawnSnake(x, Gdx.graphics.getHeight(), angle, length, speed);
						}
						if (angle <= 45 && angle >= 315) {
							spawnSnake(x, 0, angle, length, speed);
						}
						if (angle >= 225 && angle <= 315) {
							spawnSnake(Gdx.graphics.getWidth(), y, angle, length, speed);

						}
						if (angle <= 45 && angle <= 135) {
							spawnSnake(0, y, angle, length, speed);

						}
						System.out.println(angle);
						// System.out.println(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}
		}).start();

	}

	private void enemyThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
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
		}).start();

	}

	private void enemyFromTopThread() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
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
		}).start();

	}

	public void spawnSnake(int x, int y, float angle, int length, int speed) {
		Snake snake = new Snake(angle, new Vector2(x, y), speed, length);
		snakes.add(snake);

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