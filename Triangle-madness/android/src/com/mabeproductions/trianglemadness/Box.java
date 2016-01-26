package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Box {

	// Settings
	private boolean debugMode = false;
	private float size = 0.4f;

	// Variables
	private int touchX;
	private int touchY;
	private GameSc g;
	private Texture ball;
	private Vector2 pos;
	public Rectangle bounds;
	private boolean isTouched;
	private ShapeRenderer shape;
	private boolean shouldScreenChange;
	
	//GameOver screen object(later initialized in constructor).
	private GameOver gameOver;
	private ParticleEffect emitter;
	private AssetManager mngr;
	
	public Box(GameSc g) {
		mngr = new AssetManager();
		
		
		this.g = g;

		ball = new Texture(Gdx.files.internal("Textures/Blue_ball.png"));

		size = ball.getWidth() * size;

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size / 2, Gdx.graphics.getHeight() / 2 - size / 2);

		bounds = new Rectangle(pos.x, pos.y, size, size);

		shape = new ShapeRenderer();

		emitter = new ParticleEffect();
		emitter.load(Gdx.files.internal("Particles/BubblePart"), Gdx.files.internal(""));
		emitter.scaleEffect(2);
		
	}

	public void update() {

		// Getting touch coords
		touchX = Gdx.input.getX();
		touchY = Gdx.input.getY() + ((Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 2);
		
		
		// Moving the touchX and touchY if it's touched
		
			
		if(Gdx.input.justTouched() && bounds.contains(touchX, touchY)){
			isTouched = true;
		}
		
		
		
	if(!Gdx.input.isTouched())
		isTouched = false;
	
				
				if(isTouched){
				pos.x = touchX - size/2;
				pos.y = touchY - size/2;
			
				bounds.setPosition(pos.x, pos.y);
				}
				
		try{
			
			
			for (int i = 0; i < g.enemies.size(); i++) {
					
					if (bounds.overlaps(g.enemies.get(i).bounds) && !shouldScreenChange) {
						shouldScreenChange = true;
					}
			}
			
			for (int i = 0; i < g.enemies2.size(); i++) {
				
				if (bounds.overlaps(g.enemies2.get(i).bounds) && !shouldScreenChange) {
					shouldScreenChange = true;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void render(SpriteBatch batch) {

		if (shouldScreenChange) {
			
			g.runner.setScreen(g.runner.over);
			
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
		 emitter.setPosition(pos.x + size/2, pos.y + size/2);
		 emitter.update(Gdx.graphics.getDeltaTime());
		 emitter.draw(batch);
		 batch.draw(ball, pos.x, pos.y, size, size);
		 batch.end();
		 
	}

}
