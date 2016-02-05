package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader.ParticleEffectParameter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class GameRunner extends Game{
	
	public static AssetManager assets;
	public static ParticleEffectParameter param;
	public static ParticleEffectLoader loader;
	public static ParticleEffect emitter;
	public static ParticleEffect rocketEmitter;
	public static ParticleEffect coinEmitter;
	public static BitmapFont ScoreFont;
	public static BitmapFont BigScoreFont;
	public static BitmapFont PlayFont;
	public static AdController adcontroller;
	
	public GameRunner(AdController adcontroller) {
		this.adcontroller = adcontroller;
	}
	
	@Override
	public void create() {
		assets = new AssetManager();
		
		FreeTypeFontGenerator scorefontgen = new FreeTypeFontGenerator(Gdx.files.internal("Font.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int) (Gdx.graphics.getHeight()*0.092);
		ScoreFont = scorefontgen.generateFont(parameter);
 		
		FreeTypeFontParameter parameterbig = new FreeTypeFontParameter();
		parameterbig.size = 200;
		BigScoreFont = scorefontgen.generateFont(parameterbig);
		BigScoreFont.getData().setScale(Gdx.graphics.getHeight()*0.0013888f);
		
		FreeTypeFontParameter parametersmaller = new FreeTypeFontParameter();
		parametersmaller.size=(int) (Gdx.graphics.getHeight()*0.11f);
		PlayFont = scorefontgen.generateFont(parametersmaller);
	
		scorefontgen.dispose();
		
		
		
		
		
		assets.load("Textures/Blue_ball.png", Texture.class);
		
		assets.load("Sounds/dzinkt.wav", Sound.class);
		assets.load("Sounds/gameMusic.wav", Music.class);
		assets.load("Sounds/gameOver.wav", Sound.class);
		assets.load("Sounds/gameMenu.wav", Music.class);
		assets.load("Sounds/stageSound.wav", Sound.class);
		
		//GameOver
		assets.load("Textures/GameOver/GameOver0001.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0004.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0007.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0010.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0013.png", Texture.class);
		assets.load("Textures/GameOver/GameOver0016.png", Texture.class);
		
		
		assets.load("Textures/GameOver/Buttons/Buttons.pack", TextureAtlas.class);
		assets.load("Textures/GameOver/Buttons/Buttons.pack", TextureAtlas.class);
		assets.load("Textures/Menu/rateUs.pack", TextureAtlas.class);
		assets.load("Textures/Menu/facebook.pack", TextureAtlas.class);
		
		assets.load("Textures/Menu/1.png", Texture.class);
		assets.load("Textures/Menu/2.png", Texture.class);
		assets.load("Textures/Menu/3.png", Texture.class);
		assets.load("Textures/Menu/4.png", Texture.class);
		assets.load("Textures/Menu/5.png", Texture.class);
		assets.load("Textures/Menu/6.png", Texture.class);
		assets.load("Textures/Menu/7.png", Texture.class);
		assets.load("Textures/Menu/8.png", Texture.class);
		assets.load("Textures/Menu/9.png", Texture.class);
		assets.load("Textures/Menu/10.png", Texture.class);
		
		assets.load("MuteButton/soundOn.png", Texture.class);
		assets.load("MuteButton/soundOff.png", Texture.class);
		
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
		
		assets.load("Circle/Circle10001.png", Texture.class);
		assets.load("Circle/Circle10003.png", Texture.class);
		assets.load("Circle/Circle10005.png", Texture.class);
		assets.load("Circle/Circle10007.png", Texture.class);
		assets.load("Circle/Circle10009.png", Texture.class);
		assets.load("Circle/Circle10011.png", Texture.class);
		assets.load("Circle/Circle10013.png", Texture.class);
		assets.load("Circle/Circle10015.png", Texture.class);
		
		
		
	
		
		
	
		//Box particle emitter
		emitter = new ParticleEffect();
		emitter.load(Gdx.files.internal("Particles/BubblePart1"), Gdx.files.internal(""));
		emitter.scaleEffect(2);
		
		//Rocket particle emitter
		rocketEmitter = new ParticleEffect();
		rocketEmitter.load(Gdx.files.internal("Particles/fire2"), Gdx.files.internal(""));
		rocketEmitter.scaleEffect(2);
	
		//Coin Emitter
		coinEmitter = new ParticleEffect();
		coinEmitter.load(Gdx.files.internal("Particles/coinPart"),Gdx.files.internal(""));
		coinEmitter.scaleEffect(2);
		
		assets.finishLoading();
		
		this.setScreen(new GameMenu(this));
	}
	

}

