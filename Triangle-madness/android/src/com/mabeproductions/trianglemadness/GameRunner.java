package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Game;

public class GameRunner extends Game{
	
	public GameMenu GameMen;
	public GameOver over;
	
	@Override
	public void create() {
		GameMen = new GameMenu(this);
		over = new GameOver(this);
		this.setScreen(GameMen);
	}
	

}

