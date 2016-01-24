package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

public class GameRunner extends Game {
	
	
public	boolean menu = true;
public	boolean play = true;
	public GameRunner(){	
		
		
	}

	@Override
	public void create() {
		
		menu();
		
		
	}

	public void play(){
		this.setScreen(new GameSc(this));
		play = true;
	}
	public void menu(){
		
	
		this.setScreen(new GameMenu(this));
		menu = true;
	}
	public void gameOver(){
		
		this.setScreen(new GameOver(this));
		
	}
}

