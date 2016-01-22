package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Box {
	
	//Settings
	private boolean debugMode = false;
	private float size = 0.3f;
	
	//Variables
	private Game g;
	private Texture box;
	private Vector2 pos;
	public Rectangle bounds;
	private boolean isTouched;

	public Box(Game g) {
		this.g = g;
		box = new Texture(Gdx.files.internal("Textures/box.png"));
		size = box.getWidth() * size;
		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size / 2, Gdx.graphics.getHeight() / 2 - size / 2);
		bounds = new Rectangle(pos.x, pos.y, size, size);
	}

	public void update() {
		
		//Getting touch coords
		int touchX = Gdx.input.getX();
		int touchY = Gdx.input.getY() + ((Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 2);
		
		//Checking if box is touched and setting isTouched to true
		if (Gdx.input.justTouched() && bounds.contains(touchX, touchY))
			isTouched = true;

		
		//Moving box the touchX and touchY if it's touched
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

	}

	public void render(SpriteBatch batch) {
		//Only drawing bounds if debug mode is on
		if(debugMode){
			ShapeRenderer shape = new ShapeRenderer();
			shape.setAutoShapeType(true);
			shape.begin();
			shape.setColor(new Color(Color.RED));
			shape.rect(bounds.x, bounds.y, bounds.getWidth(), bounds.getHeight());
			shape.end();			
		}
		
		//Drawing a box
		batch.begin();
		batch.draw(box, pos.x, pos.y, size, size);
		batch.end();

	}

}
