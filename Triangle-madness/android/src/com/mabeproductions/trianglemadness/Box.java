package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import android.content.Context;
import android.media.AudioManager;

public class Box {

	// Settings
	private final boolean debugMode = false;
	private float size = 0.6f;

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
	private boolean vibrate;
	
	//GameOver screen object(later initialized in constructor).

	private ParticleEffect emitter;

	
	
	public Box(GameSc g) {
	
		
		
		this.g = g;

		ball = new Texture(Gdx.files.internal("Textures/Blue_ball.png"));

		size = ball.getWidth() * size;

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size / 2, Gdx.graphics.getHeight() / 2 - size / 2);

		bounds = new Rectangle(pos.x, pos.y, size, size);

		shape = new ShapeRenderer();

		emitter = new ParticleEffect();
		emitter.load(Gdx.files.internal("Particles/BubblePart1"), Gdx.files.internal(""));
		emitter.scaleEffect(2);
		
	}

	public Vector2 getPos() {
		return pos;
	}

	public void update() {

		// Getting touch coords
		touchX = Gdx.input.getX();
		touchY = Gdx.input.getY() + ((Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 2);
		
		
		// Moving the touchX and touchY if it's touched
		
			
		if(Gdx.input.justTouched() && bounds.contains(touchX, touchY)){
			isTouched = true;
		}
		
		
		if(pos.y > Gdx.graphics.getHeight()){
			shouldScreenChange= true;
		}
		
		
		
		
	if(!Gdx.input.isTouched())
		isTouched = false;
		
				
				if(isTouched){
				pos.x = touchX - size/2;
				pos.y = touchY - (size/2)+size+20;
			
				bounds.setPosition(pos.x, pos.y);
				}
				
		try{
			
			
			for (int i = 0; i < g.enemies.size(); i++) {
					
					if (bounds.overlaps(g.enemies.get(i).bounds) && !shouldScreenChange) {
						shouldScreenChange = true;
						vibrate=true;
						if(vibrate){
							
							Gdx.input.vibrate(200);
							vibrate=false;
						}
					}
			}
			
			for (int i = 0; i < g.enemies2.size(); i++) {
				
				if (bounds.overlaps(g.enemies2.get(i).bounds) && !shouldScreenChange) {
					shouldScreenChange = true;
					vibrate=true;
					if(vibrate){
						Gdx.input.vibrate(200);
						vibrate=false;
					}
				}
			}
			
			
			if(bounds.overlaps(g.rocket.getBounds())){
		
				shouldScreenChange = true;
//				vibrate=true;
				if(vibrate){
					Gdx.input.vibrate(200);
					vibrate=false;
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
