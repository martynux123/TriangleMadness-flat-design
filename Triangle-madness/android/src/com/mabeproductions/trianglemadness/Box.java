package com.mabeproductions.trianglemadness;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Box {

	// Settings
	private final boolean debugMode = false;
	private float size = 0.6f;

	// Variables
	private int touchX;
	private int touchY;
	private GameSc g;
	private Texture ball;
	public static Vector2 pos;
	public Rectangle bounds;
	private boolean isTouched;
	private ShapeRenderer shape;
	public boolean shouldScreenChange;
	private boolean vibrate;
	private float dt;
	private GameOver over;
	private ParticleEffect emitter;
	private int coinIndex=0;
	
	public static boolean coinParticle = false;
	
	public Box(GameSc g) {

		this.g = g;

		
		over = new GameOver(g.runner);

		ball = GameRunner.assets.get("Textures/Blue_ball.png");

		size = ball.getWidth() * size;

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size / 2, Gdx.graphics.getHeight() / 2 - size / 2);

		bounds = new Rectangle(pos.x, pos.y, size, size);

		shape = new ShapeRenderer();

		this.emitter = GameRunner.emitter;
		
		

	}

	public Vector2 getPos() {
		return pos;
	}

	public void update() {
		
		//Collecting coins!
		for (int i = 0; i < g.coins.size(); i++) {
		
			
			if(g.coins.get(i).getBounds().overlaps(bounds)){
				coinParticle=true;	
				System.out.println(Box.coinParticle);
				g.coins.remove(i);		
	
			}
			if(coinParticle){
				coinIndex++;
				if(coinIndex>=500){
					
					coinIndex=0;
					
					coinParticle=false;
				}
			}
			
		}
		
		System.out.println(coinIndex);
		// Getting touch coords
		touchX = Gdx.input.getX();
		touchY = Gdx.input.getY() + ((Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 2);

		// Moving the touchX and touchY if it's touched

		if (Gdx.input.justTouched() && bounds.contains(touchX, touchY)) {
			isTouched = true;
		}

		if (pos.y > Gdx.graphics.getHeight()) {
			shouldScreenChange = true;
		}

		if (!Gdx.input.isTouched())
			isTouched = false;

		if (isTouched) {
			pos.x = touchX - size / 2;
			pos.y = touchY - (size / 2) + size + 20;

			bounds.setPosition(pos.x, pos.y);
		}

		try {

			for (int i = 0; i < g.enemies.size(); i++) {

				if (bounds.overlaps(g.enemies.get(i).bounds) && !shouldScreenChange) {
					shouldScreenChange = true;
					vibrate = true;
					if (vibrate) {

						Gdx.input.vibrate(200);
						vibrate = false;
					}
				}
			}

			for (int i = 0; i < g.enemies2.size(); i++) {

				if (bounds.overlaps(g.enemies2.get(i).bounds) && !shouldScreenChange) {
					shouldScreenChange = true;
					vibrate = true;
					if (vibrate) {
						Gdx.input.vibrate(200);
						vibrate = false;
					}
				}
			}

			if (bounds.overlaps(g.rocket.getBounds())) {

				shouldScreenChange = true;
				// vibrate=true;
				if (vibrate) {
					Gdx.input.vibrate(200);
					vibrate = false;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		
	}

	public void render(SpriteBatch batch) {

		//Changing the screen
		if (shouldScreenChange) {
			g.runner.getScreen().dispose();
			g.runner.setScreen(new GameOver(g.runner));
			shouldScreenChange = false;
	
			
		}
		

		// Only drawing bounds if debug mode is on
		if (debugMode) {
			shape.setAutoShapeType(true);
			shape.begin();
			shape.setColor(new Color(Color.RED));
			shape.rect(bounds.x, bounds.y, bounds.getWidth(), bounds.getHeight());
			shape.end();

		}

		// Drawing a box

		batch.begin();
		emitter.setPosition(pos.x + size / 2 + 10, pos.y + size / 2 + 10);
		emitter.update(Gdx.graphics.getDeltaTime());
		emitter.draw(batch);
		batch.draw(GameRunner.assets.get("Textures/Blue_ball.png", Texture.class), pos.x, pos.y, size, size);
		batch.end();

	}
	
	public void dispose(){
		shape.dispose();
	}

}
