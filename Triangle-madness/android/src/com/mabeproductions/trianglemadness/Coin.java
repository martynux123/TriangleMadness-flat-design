package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Coin {

	private int x;
	private int y;
	private int speed;
	private Texture txt;
	
	public Coin(int x, int y, int speed, Texture txt) {

		this.x = x;

		this.y = y;

		this.speed = speed;
		
		this.txt = txt;

	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	
	public void render(ShapeRenderer render, SpriteBatch batch) {
		
		
		
	}
	
	

	public void update() {

	}

}
