package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Game;

public class GameRunner extends Game{

	@Override
	public void create() {
		this.setScreen(new GameMenu(this));
	}
	

}

