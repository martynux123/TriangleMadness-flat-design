package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Broadcaster {

	
	private int x;
	private int y;
	private float speed;
	private String text;
	private BitmapFont BigScoreFont;
	private GameRunner runner;
	
	private GameSc g;
	public Broadcaster(int x, int y, float speed, String text, GameSc g){
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.text = text;
		
		BigScoreFont = GameRunner.BigScoreFont;
		broadcastThread();
		
	}
	
	public void render(SpriteBatch batch){
		batch.begin();
		BigScoreFont.draw(batch, text, x, y);
		batch.end();
		
	}
	public void update(){
		
		
	}
	public void broadcastThread(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(x<Gdx.graphics.getWidth()/2-700){
					try {
						Thread.sleep(10);
						x+=speed;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				while(x<Gdx.graphics.getWidth()/2-200){
					try {
						Thread.sleep(30);
						x+=speed;
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
	
				}
				while(x<Gdx.graphics.getWidth() + 50){
					try {
						Thread.sleep(10);
						x+=speed;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Thread.currentThread().interrupt();
				
				
			}
		}, "BroadCaster").start();
		
	}
	
	
}
