package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.Particle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader.ParticleEffectLoadParameter;

public class GameRunner extends Game{
	
	public static AssetManager assets;
	public static ParticleEffectParameter param;
	public static ParticleEffectLoader loader;
	public static ParticleEffect emitter;
	public static ParticleEffect rocketEmitter;
	public static ParticleEffect coinEmitter;
	
	@Override
	public void create() {
		
		assets = new AssetManager();
		
		
		assets.load("Textures/Blue_ball.png", Texture.class);
		
		
		
		//GameOver
		assets.load("Textures/GameOver/GameOver0001.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0004.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0007.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0010.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0013.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0016.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0019.png", Texture.class);
		
		
		
		assets.load("Textures/GameOver/Buttons/Buttons.pack", TextureAtlas.class);
		assets.load("Textures/GameOver/Buttons/Buttons.pack", TextureAtlas.class);
		
		
		assets.load("Textures/Menu/1.png", Texture.class);
		assets.load("Textures/Menu/2.png", Texture.class);
		assets.load("Textures/Menu/3.png", Texture.class);
		assets.load("Textures/Menu/4.png", Texture.class);
		assets.load("Textures/Menu/5.png", Texture.class);
		assets.load("Textures/Menu/6.png", Texture.class);
		assets.load("Textures/Menu/7.png", Texture.class);
		
		assets.load("Textures/Menu/play.png", Texture.class);		
		assets.load("Textures/Menu/circle.pack", TextureAtlas.class);
		
		assets.load("Textures/avoidness.png", Texture.class);
		assets.load("Textures/Enemies/Boxers/Enemy.png", Texture.class);
		assets.load("Textures/Enemies/Enemy2.png", Texture.class);
		assets.load("Textures/Enemies/rocket.png", Texture.class);
		
		
		assets.load("Coins/1.png", Texture.class);
		assets.load("Coins/2.png", Texture.class);
		assets.load("Coins/3.png", Texture.class);
		assets.load("Coins/4.png", Texture.class);
		assets.load("Coins/5.png", Texture.class);
		assets.load("Coins/6.png", Texture.class);
		assets.load("Coins/7.png", Texture.class);
		
		
	
		//Box particle emitter
		emitter = new ParticleEffect();
		emitter.load(Gdx.files.internal("Particles/space"), Gdx.files.internal(""));
		emitter.scaleEffect(2);
		
		//Rocket particle emitter
		rocketEmitter = new ParticleEffect();
		rocketEmitter.load(Gdx.files.internal("Particles/fire2"), Gdx.files.internal(""));
		rocketEmitter.scaleEffect(2);
	
		//Coin Emitter
		coinEmitter = new ParticleEffect();
		coinEmitter.load(Gdx.files.internal("Particles/yellowPart1"),Gdx.files.internal(""));
		coinEmitter.scaleEffect(2);
		
		assets.finishLoading();
		
		this.setScreen(new GameMenu(this));
	}
	

}

