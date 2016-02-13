package com.mabeproductions.trianglemadness;

import java.util.Timer;
import java.util.TimerTask;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Box {

	// Settings
	private final boolean debugMode = false;
	private float size = Gdx.graphics.getHeight()*0.00056f;

	// Variables
	private int touchX;
	private int touchY;
	private GameSc g;
	private Texture ball;
	public static Vector2 pos;
	public Rectangle bounds;
	private boolean isTouched;
	private ShapeRenderer shape;
	public boolean shouldScreenChange;
	private boolean vibrate;
	private ParticleEffect emitter;
	private int coinIndex=0;
	private TextureRegion screenshot;
	private Sound sound;
	private Rectangle touchBounds;
	public static boolean tookHourglass = false;
	private int totalScore;
	private Timer t;
	public  boolean isCoin=false;
	
	

	public Box(GameSc g) {

		this.g = g;
		
		
		//Levels
		Preferences prefs = Gdx.app.getPreferences("Stats");
		totalScore = prefs.getInteger("TotalScore");
		prefs.flush();
		
		switch (GameSc.Level) {
		
		case 1:
			
			ball = GameRunner.assets.get("Textures/defaultBubble.png");
			this.emitter = GameRunner.emitter;
			break;
		case 2:
			ball = GameRunner.assets.get("Level2/FrostLevelChar.png");
			this.emitter = GameRunner.snowEmitter;
			break;
		case 3:
			ball = GameRunner.assets.get("Level3/fireCharacter.png");
			this.emitter = GameRunner.fireEmitter;
			break;
		case 4:
			ball = GameRunner.assets.get("Level4/waterBubble.png");
			this.emitter = GameRunner.waterEmitter;
			break;
		case 5:
			ball = GameRunner.assets.get("Level5/jungleCharacter.png");
			this.emitter = GameRunner.jungleEmitter;
			break;
		case 6:
			ball = GameRunner.assets.get("Level6/sandyCharacter.png");
			this.emitter = GameRunner.sandEmitter;
			break;
		default:
			break;
			
		}
		

		
		sound = GameRunner.assets.get("Sounds/gameOver.wav");
		size = ball.getWidth() * size;

		pos = new Vector2(Gdx.graphics.getWidth() / 2 - size / 2, Gdx.graphics.getHeight() / 2 - size / 2);

		t = new Timer();
		
		bounds = new Rectangle(pos.x, pos.y, size-Gdx.graphics.getHeight()*0.0185f, size-Gdx.graphics.getHeight()*0.0185f);
		touchBounds = new Rectangle(pos.x - Gdx.graphics.getHeight()*0.025f, pos.y - Gdx.graphics.getHeight()*0.025f, Gdx.graphics.getHeight()*0.15f, Gdx.graphics.getHeight()*0.15f);

		shape = new ShapeRenderer();

	}
	public void gameOverMusic(){
		if(!GameMenu.isMuted){
			sound.play(0.08f);			
		}
	
	
	}

	public Vector2 getPos() {
		return pos;
	}

	public void update() {
		
		if (Gdx.input.isTouched() && touchBounds.contains(touchX, touchY)) {
			isTouched = true;
		}
		
	
		
		touchBounds.setPosition(pos.x - 27f, pos.y - 27f);
		
		//Collecting coins!
		for (int i = 0; i < GameSc.coins.size(); i++) {
		

			    
						
		
		
			if(GameSc.coins.get(i).getBounds().overlaps(bounds)){		
				if(tookHourglass==false){
					g.Score++;		
					
				}
				if(tookHourglass==true){
					g.Score+=3;
					

					
				}
				g.coins.get(i).onAquire();
				g.coins.remove(i);
			}
			
		}
		
		// Getting touch coords
		touchX = Gdx.input.getX();
		touchY = Gdx.input.getY() + ((Gdx.graphics.getHeight() / 2 - Gdx.input.getY()) * 2);

		// Moving the touchX and touchY if it's touched
		if (pos.y > Gdx.graphics.getHeight()) {
			shouldScreenChange = true;
		}

		if (!Gdx.input.isTouched())
			isTouched = false;

		if (isTouched) {
			pos.x = touchX - size / 2;
			pos.y = touchY - (size / 2) + size + Gdx.graphics.getHeight()*0.0185f;

			bounds.setPosition(pos.x+Gdx.graphics.getHeight()*0.0092f, pos.y+Gdx.graphics.getHeight()*0.0092f);
		}

		try {


			for (int i = 0; i < g.enemies2.size(); i++) {

				if (bounds.overlaps(g.enemies2.get(i).bounds) && !shouldScreenChange) {
					shouldScreenChange = true;
					
					vibrate = true;
					if (vibrate) {
						Gdx.input.vibrate(200);
						vibrate = false;
					}
				}
			}

			for(int i=0; i<g.rocketList.size(); i++){
				if(bounds.overlaps(g.rocketList.get(i).getBounds())){
					shouldScreenChange = true;
					// vibrate=true;
					if (vibrate) {
						Gdx.input.vibrate(200);
						vibrate = false;
					}
					
				}
			}
			
			

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		//Picking up HOURGLASSES
		//========================================================
		
		for(int i = 0; i<g.hourglasess.size();i++){
			if(g.hourglasess.get(i).hourBounds().overlaps(bounds)){
				g.hourglasess.remove(i);
				Hourglass.hourglassSound();	
				tookHourglass=true;
			   
				t.schedule(new TimerTask() {
					
					@Override
					public void run() {
						tookHourglass=false;
					}
				}, 4000);
				
			}
		}
		//============================================
		
		for(int i = 0; i<g.cleanerList.size();i++){
			if(g.cleanerList.get(i).cleanerBounds().overlaps(bounds)){
				g.cleanerList.remove(i);
				g.enemies2.clear();
			}
		}
	}

	public void render(SpriteBatch batch) {

		

		// Only drawing bounds if debug mode is on
		if (debugMode) {
			shape.setAutoShapeType(true);
			shape.begin();
			shape.setColor(new Color(Color.RED));
			shape.rect(bounds.x, bounds.y, bounds.getWidth(), bounds.getHeight());
			shape.setColor(Color.GREEN);
			shape.rect(touchBounds.x, touchBounds.y, touchBounds.getWidth(), touchBounds.getHeight());
			shape.end();

		}

		// Drawing a box

		batch.begin();
		emitter.setPosition(pos.x + size / 2 + Gdx.graphics.getHeight()*0.00925f, pos.y + size / 2 + Gdx.graphics.getHeight()*0.00925f);
		emitter.update(Gdx.graphics.getDeltaTime());
		emitter.draw(batch);
		
			
		batch.draw(ball, pos.x, pos.y, size, size);
		batch.end();

		//Changing the screen
		if (shouldScreenChange) {
			gameOverMusic();
			g.music.stop();
			g.music = GameRunner.assets.get("Sounds/gameMusic.wav");
			g.music.setPosition(0);
			batch.setColor(0,0,0,0.7f);
			screenshot = ScreenUtils.getFrameBufferTexture();
			g.runner.getScreen().dispose();
			g.runner.setScreen(new GameOver(g.runner, g.Score, screenshot));
			shouldScreenChange = false;
		}
	}
	
	public void dispose(){
		shape.dispose();
	}

}
