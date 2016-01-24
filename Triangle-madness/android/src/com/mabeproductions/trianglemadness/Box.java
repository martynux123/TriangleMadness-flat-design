package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Box {

	// Settings
	private boolean debugMode = false;
	private float size = 0.3f;
	
	// Variables
	private int touchX;
	private int touchY;
	private GameSc g;
	private Texture box;
	private Vector2 pos;
	public Rectangle bounds;
	private boolean isTouched;
	private ShapeRenderer shape;
	private boolean shouldScreenChange = false;
	

	
	public Box(GameSc g) {
		this.g = g;
		box = new Texture(Gdx.files.internal("Textures/box.png"));
		size = box.getWidth() * size;
		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size / 2, Gdx.graphics.getHeight() / 2 - size / 2);
		bounds = new Rectangle(pos.x, pos.y, size, size);
		
		shape = new ShapeRenderer();
	}

	public void update() {

		// Getting touch coords
		touchX = Gdx.input.getX();
		touchY = Gdx.input.getY() + ((Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 2);

		// Checking if box is touched and setting isTouched to true
		if (Gdx.input.justTouched() && bounds.contains(touchX, touchY))
			isTouched = true;

		// Moving box the touchX and touchY if it's touched
		if (isTouched) {

			if (Gdx.input.isTouched()) {

				pos.x = touchX - size / 2;
				pos.y = touchY - size / 2;
				bounds.setPosition(pos.x, pos.y);

			} else {
				isTouched = false;
			}

		}

		// Out Of Bounds
		if (pos.x < 0) {
			pos.x = 0;
		}
		
		if (pos.x > Gdx.graphics.getWidth() - size) {
			pos.x = Gdx.graphics.getWidth() - size;
		}
		
		if (pos.y < 0) {
			pos.y = 0;
		}
		
		if (pos.y > Gdx.graphics.getHeight() - size) {
			pos.y = Gdx.graphics.getHeight() - size;
		}
		
		try{
			for (int i = 0; i < g.snakes.size(); i++) {
				for (int y = 0; y < g.snakes.get(i).snake.size(); y++) {
					
					if (bounds.contains(g.snakes.get(i).snake.get(y).x, g.snakes.get(i).snake.get(y).y) && !shouldScreenChange) {
						shouldScreenChange = true;
						}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void render(SpriteBatch batch) {
		
		
		if(shouldScreenChange){
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
		batch.draw(box, pos.x, pos.y, size, size);
		batch.end();
	}

}
