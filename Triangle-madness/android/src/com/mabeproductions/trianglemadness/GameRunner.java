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
	public static ParticleEffect snowEmitter;
	public static ParticleEffect fireEmitter;
	public static ParticleEffect waterEmitter;
	public static ParticleEffect jungleEmitter;
	public static ParticleEffect sandEmitter;
	
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
		
		assets.load("Sounds/Backtrack.wav", Sound.class);
		
		assets.load("Level2/cleaner_winter.png", Texture.class);
		assets.load("Level3/cleaner_hell.png", Texture.class);
		assets.load("Level4/cleaner_underwater.png", Texture.class);
		assets.load("Level5/cleaner_Jungle.png", Texture.class);
		assets.load("Level6/cleaner_desert.png", Texture.class);
		
		assets.load("Level2/multiplier_winter.png", Texture.class);
		assets.load("Level3/multiplier_hell.png", Texture.class);
		assets.load("Level4/multiplier_underwater.png", Texture.class);
		assets.load("Level5/multiplier_Jungle.png", Texture.class);
		assets.load("Level6/multiplier_desert.png", Texture.class);
		
		
		
		
		//HourGlass
		assets.load("clock.png", Texture.class);
		assets.load("cleaner.png", Texture.class);
		assets.load("multiplier.png", Texture.class);
		assets.load("Textures/defaultBubble.png", Texture.class);
		
		assets.load("Level2/clock_winter.png", Texture.class);
		assets.load("Level2/FrostLevelChar.png", Texture.class);
		assets.load("Level2/FrostLevelEnemy.png", Texture.class);
		assets.load("Level2/avoidness.png", Texture.class);
		assets.load("Level5/avoidness.png", Texture.class);
		assets.load("Level5/EnemyJungle.png", Texture.class);
		assets.load("Level5/jungleCharacter.png", Texture.class);
		assets.load("Level6/sandyCharacter.png", Texture.class);
		assets.load("leftButton.png", Texture.class);
		assets.load("rightButton.png", Texture.class);
		assets.load("rightButtonRed.png", Texture.class);
		
		assets.load("Level6/desertEnemy.png", Texture.class);
		assets.load("Level6/desertTheme.png", Texture.class);
		
		//LEVEL 3
		assets.load("Level3/FireEnemy.png", Texture.class);
		assets.load("Level3/AvoidnessLava.png", Texture.class);
		assets.load("Level3/fireCharacter.png", Texture.class);
		assets.load("Level3/clock_hell.png", Texture.class);
		
		//LEVEL 4
		assets.load("Level4/wateBackground.png", Texture.class);
		assets.load("Level4/wateEnemy.png", Texture.class);
		assets.load("Level4/waterBubble.png", Texture.class);
		assets.load("Level4/clock_underwater.png", Texture.class);
		
		assets.load("Level5/clock_Jungle.png", Texture.class);
		
		assets.load("Level6/clock_desert.png", Texture.class);
		
		
		assets.load("Sounds/dzinkt.wav", Sound.class);
		assets.load("Sounds/gameMusic.wav", Music.class);
		assets.load("Sounds/gameOver.wav", Sound.class);
		assets.load("Sounds/gameMenu.wav", Music.class);
		assets.load("Sounds/stageSound.wav", Sound.class);
		assets.load("Sounds/FreezeTime.wav", Sound.class);
		assets.load("Sounds/sparkle.wav", Sound.class);
		
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
		
		assets.load("Level5/1.png", Texture.class);
		assets.load("Level5/2.png", Texture.class);
		assets.load("Level5/3.png", Texture.class);
		assets.load("Level5/4.png", Texture.class);
		assets.load("Level5/5.png", Texture.class);
		assets.load("Level5/6.png", Texture.class);
		assets.load("Level5/7.png", Texture.class);
		
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
		
		assets.load("Level2/1.png", Texture.class);
		assets.load("Level2/2.png", Texture.class);
		assets.load("Level2/3.png", Texture.class);
		assets.load("Level2/4.png", Texture.class);
		assets.load("Level2/5.png", Texture.class);
		assets.load("Level2/6.png", Texture.class);
		assets.load("Level2/7.png", Texture.class);
		
		assets.load("Level3/1.png", Texture.class);
		assets.load("Level3/2.png", Texture.class);
		assets.load("Level3/3.png", Texture.class);
		assets.load("Level3/4.png", Texture.class);
		assets.load("Level3/5.png", Texture.class);
		assets.load("Level3/6.png", Texture.class);
		assets.load("Level3/7.png", Texture.class);
		
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
		emitter.load(Gdx.files.internal("Particles/defaultParticle"), Gdx.files.internal("Particles"));
		emitter.scaleEffect(2);
		
		//Rocket particle emitter
		rocketEmitter = new ParticleEffect();
		rocketEmitter.load(Gdx.files.internal("Particles/fire2"), Gdx.files.internal(""));
		rocketEmitter.scaleEffect(2);
	
		//Coin Emitter
		coinEmitter = new ParticleEffect();
		coinEmitter.load(Gdx.files.internal("Particles/coinPart"),Gdx.files.internal(""));
		coinEmitter.scaleEffect(2);
		
		//Bubble Emitter
		snowEmitter = new ParticleEffect();
		snowEmitter.load(Gdx.files.internal("Level2/SnowFlakes.p"),Gdx.files.internal("Level2"));
		snowEmitter.scaleEffect(2);
		
		//Bubble Fire
		fireEmitter = new ParticleEffect();
		fireEmitter.load(Gdx.files.internal("Level3/fireParticle"),Gdx.files.internal("Level3"));
		fireEmitter.scaleEffect(2);
		assets.finishLoading();
		
		waterEmitter = new ParticleEffect();
		waterEmitter.load(Gdx.files.internal("Level4/BubblePart1"),Gdx.files.internal("Level4"));
		waterEmitter.scaleEffect(2);
		assets.finishLoading();
		
		jungleEmitter = new ParticleEffect();
		jungleEmitter.load(Gdx.files.internal("Level5/jungleParticles.p"),Gdx.files.internal("Level5"));
		jungleEmitter.scaleEffect(2);
		
		sandEmitter = new ParticleEffect();
		sandEmitter.load(Gdx.files.internal("Level6/sandParticles"),Gdx.files.internal("Level6"));
		sandEmitter.scaleEffect(2);
		
		assets.finishLoading();
		
		this.setScreen(new GameMenu(this));
	}
	

}

