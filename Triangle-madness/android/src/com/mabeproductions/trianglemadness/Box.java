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
	private boolean debugMode = true;
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
	
	//GameOver screen object(later initialized in constructor).
	private GameOver gameOver;

	
	public Box(GameSc g) {
		
		
		
		this.g = g;

		ball = new Texture(Gdx.files.internal("Textures/Blue_ball.png"));

		size = ball.getWidth() * size;

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size / 2, Gdx.graphics.getHeight() / 2 - size / 2);

		bounds = new Rectangle(pos.x, pos.y, size, size);

		shape = new ShapeRenderer();

		
		//DOESN"T WORK : See Line: 125
		//gameOver = new GameOver(g.runner);
		
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
			
			g.runner.setScreen(new GameOver(g.runner)/*gameOver*/);
			
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
		
		 batch.begin(); batch.draw(ball, pos.x, pos.y, size, size);
		 batch.end();
		 
	}

}
