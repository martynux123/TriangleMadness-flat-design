package com.mabeproductions.trianglemadness;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

public class GameMenu implements Screen {
	
	//Booleans
	public boolean isFinger = true;
	private boolean gameScreenSwitch;
	private boolean debugMode = false;
	private boolean facebookOn = true;
	private boolean rateUsOn = true;
	
	//Variables
	private int index;
	private int angleMid = 0;
	private int angleOut = 0;
	private BitmapFont playFont;
	private int circleIndex;


	public Texture[] muteButton = new Texture[2];
	private Rectangle muteButtonRect;
	private int muteButtonIndex=0;
	
	public static boolean isMuted = false;
	
	//Objects
	public Rectangle playBtn;
	public GameRunner runner;
	private SpriteBatch batch;
	private Music music;
	private ShapeRenderer render;
	private Preferences prefs;
	//Collections
	private Texture[] bg = new Texture[10]; 
	private Texture[] circle = new Texture[8];
	//Textures
	private TextureAtlas btn;
	private Texture play;
	private int muteIndex;
	private TextureAtlas atlasRate;
	private TextButtonStyle styleRate;
	private TextButton rateUs;
	private TextureAtlas atlasAgain;
	private Timer t;
	
	private BitmapFont rateFont;
	private Stage stage;
	private TextButtonStyle fbStyle;
	private TextureAtlas fbAtlas;
	private TextButton fbButton;
	
	
	public GameMenu(GameRunner runner){
		this.runner = runner;
		music = GameRunner.assets.get("Sounds/gameMenu.wav");
		
		//Delaying the ad for it to work propertly
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				GameRunner.adcontroller.showAd();
				Thread.currentThread().interrupt();
			}
		}, 200);
		
		t = new Timer();
		
		batch = new SpriteBatch();
		
		
		playFont = GameRunner.PlayFont;
		rateFont = new BitmapFont();
		rateFont.setColor(Color.RED);
		stage = new Stage();
		
		Skin skinRate = new Skin();
		atlasRate = GameRunner.assets.get("Textures/Menu/rateUs.pack");
		skinRate.addRegions(atlasRate);
		styleRate = new TextButtonStyle();
		styleRate.up = skinRate.getDrawable("Rate_us");
		styleRate.down = skinRate.getDrawable("Rate_us");
		styleRate.font = rateFont;
		
		rateUs = new TextButton(" ", styleRate);
		rateUs.setBounds(Gdx.graphics.getHeight()*1.1296f, Gdx.graphics.getHeight()*0.11111f, Gdx.graphics.getHeight()*0.185185f, Gdx.graphics.getHeight()*0.185185f);
		
		
		Skin fbskin = new Skin();
		fbAtlas = GameRunner.assets.get("Textures/Menu/facebook.pack");
		fbskin.addRegions(fbAtlas);
		fbStyle = new TextButtonStyle();
		fbStyle.up = fbskin.getDrawable("facebook");
		fbStyle.down = fbskin.getDrawable("facebook");
		fbStyle.font = rateFont;
		
		fbButton = new TextButton(" ", fbStyle);
		fbButton.setBounds(Gdx.graphics.getHeight()*0.4444f,Gdx.graphics.getHeight()*0.07407f, Gdx.graphics.getHeight()*0.2129f, Gdx.graphics.getHeight()*0.2129f);
		
		bg[0] = GameRunner.assets.get("Textures/Menu/1.png");
		bg[1] = GameRunner.assets.get("Textures/Menu/2.png");
		bg[2] = GameRunner.assets.get("Textures/Menu/3.png");
		bg[3] = 
				GameRunner.assets.get("Textures/Menu/4.png");
		bg[4] = GameRunner.assets.get("Textures/Menu/5.png");
		bg[5] = GameRunner.assets.get("Textures/Menu/6.png");
		bg[6] = GameRunner.assets.get("Textures/Menu/7.png");
		bg[7] = GameRunner.assets.get("Textures/Menu/8.png");
		bg[8] = GameRunner.assets.get("Textures/Menu/9.png");
		bg[9] = GameRunner.assets.get("Textures/Menu/10.png");
		

		muteButton[0] = GameRunner.assets.get("MuteButton/soundOn.png");
		muteButton[1] = GameRunner.assets.get("MuteButton/soundOff.png");
		
		muteButtonRect= new Rectangle(Gdx.graphics.getWidth()*0.85f, Gdx.graphics.getHeight()*0.8f, Gdx.graphics.getHeight()*0.16f,  Gdx.graphics.getHeight()*0.16f);
		
		circle[0] = GameRunner.assets.get("Circle/Circle10001.png", Texture.class);
		circle[1] =GameRunner.assets.get("Circle/Circle10003.png", Texture.class);
		circle[2] =GameRunner.assets.get("Circle/Circle10005.png", Texture.class);
		circle[3] =GameRunner.assets.get("Circle/Circle10007.png", Texture.class);
		circle[4] =GameRunner.assets.get("Circle/Circle10009.png", Texture.class);
		circle[5] =GameRunner.assets.get("Circle/Circle10011.png", Texture.class);
		circle[6] =GameRunner.assets.get("Circle/Circle10013.png", Texture.class);
		circle[7] =GameRunner.assets.get("Circle/Circle10015.png", Texture.class);
		

		playBtn = new Rectangle(Gdx.graphics.getWidth()*0.45f, Gdx.graphics.getHeight()*0.3f, Gdx.graphics.getHeight()*0.2f, Gdx.graphics.getHeight()*0.170f);
		
		render = new ShapeRenderer();
		
		
		
		menuThread();
		stage.addActor(rateUs);
		stage.addActor(fbButton);
	}
	
	
	
	
	public void menuMusic(){
		

		
		music.setLooping(true);
		
		music.setVolume(0);
		
		if(prefs.getInteger("MuteButton") % 2 == 0){
			music.play();
			music.setVolume(1f);
		}

	}
	
	public void menuThread(){
		circleIndex = 0;
		index = 0;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(runner.getScreen() == GameMenu.this){
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}	
						
					if(circleIndex+1>7){
						circleIndex=-1;
					}
					
					
					if(index+1>9){
						index = -1;
					}
					index++;
					circleIndex++;
				}
				Thread.currentThread().interrupt();
			}
		}, "menuThread").start();
		
	}
	
		
	
	
	
	
	
	@Override
	public void show() {
		
		
		 Gdx.input.setInputProcessor(stage);
		 prefs = Gdx.app.getPreferences("Stats");
		 prefs.putInteger("Tries", 0);
		 prefs.flush();
		 menuMusic();
		
		
		

		 
	
	}

	@Override
	public void render(float delta) {
		
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(rateUs.isPressed()&&rateUsOn){
			Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.mabeproductions.trianglemadness");
			rateUsOn=false;
		}
		
		if(fbButton.isPressed() && facebookOn){
			Gdx.net.openURI("https://www.facebook.com/MaBeProductions/?skip_nax_wizard=true");
			facebookOn=false;
		}
		
		batch.begin();
		batch.draw(bg[index], 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(circle[circleIndex], Gdx.graphics.getHeight()*0.601f, Gdx.graphics.getHeight()*0.0925f, Gdx.graphics.getHeight()*0.5555f, Gdx.graphics.getHeight()*0.5555f);
		batch.draw(muteButton[prefs.getInteger("MuteButton")], Gdx.graphics.getWidth()*0.85f, Gdx.graphics.getHeight()*0.8f, Gdx.graphics.getHeight()*0.16f,  Gdx.graphics.getHeight()*0.16f);

		playFont.draw(batch, "PLAY", Gdx.graphics.getWidth()*0.42f, Gdx.graphics.getHeight()*0.42f);
		
		
		batch.end();
		
		if(debugMode){
			
			render.begin(ShapeType.Line);
			render.setAutoShapeType(true);
			render.rect(playBtn.x, playBtn.y, playBtn.width, playBtn.height);
			render.rect(muteButtonRect.x, muteButtonRect.y, muteButtonRect.width, muteButtonRect.height);
			render.end();
			
		}
		
		
	int	touchX = Gdx.input.getX();
	int	touchY = Gdx.input.getY() + ((Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 2);
		
		if(playBtn.contains(touchX, touchY )&& Gdx.input.justTouched()){
			gameScreenSwitch=true;
		}
		
		
		if(muteButtonRect.contains(touchX, touchY )&& Gdx.input.justTouched()){
			muteButtonIndex++;
			if(muteButtonIndex>1){
				muteButtonIndex = 0;
			}
			if(muteButtonIndex%2==0){
				isMuted=false;
				music.setVolume(1f);
				music.play();
			}else{
				isMuted = true;
				music.setVolume(0f);
				music.pause();
			}
			
			prefs.putInteger("MuteButton",muteButtonIndex);
			prefs.flush();
	
		
		}
		
		if(gameScreenSwitch){
			GameRunner.adcontroller.hideAd();
			music.stop();
			runner.setScreen(new GameSc(runner));
			this.dispose();
			gameScreenSwitch = false;
			
		}
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
		batch.dispose();
	}
}