package assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class ButtonAssets {
	
	// buttons	
	public static Texture startButtonActive;
	public static Sprite startButtonActiveSprite;
	
	public static Texture startButtonInactive;
	public static Sprite startButtonInactiveSprite;
	
	public static Texture exitButtonActive;
	public static Sprite exitButtonActiveSprite;
	
	public static Texture exitButtonInactive;
	public static Sprite exitButtonInactiveSprite;
	
	public static Texture creditsButtonActive;
	public static Sprite creditsButtonActiveSprite;
	
	public static Texture creditsButtonInactive;
	public static Sprite creditsButtonInactiveSprite;
	
	public static Texture menuButtonActive;
	public static Sprite menuButtonActiveSprite;
	
	public static Texture menuButtonInactive;
	public static Sprite menuButtonInactiveSprite;
	
	public static Texture retryButtonActive;
	public static Sprite retryButtonActiveSprite;
	
	public static Texture retryButtonInactive;
	public static Sprite retryButtonInactiveSprite;	
	
	// background 
	public static Texture menuBG;
	public static Texture creditsBG;
	public static Texture gameOverBG;
	
	public static void menuBGLoad() {
		menuBG = new Texture(Gdx.files.internal("bg/menuBG2.png"));
	}
	
	public static void menuButtonLoad() {
		
		// Menu Buttons
		
		// start
		startButtonActive = new Texture(Gdx.files.internal("buttons/startButtonActive.png"));
		startButtonActiveSprite = new Sprite(startButtonActive);
		
		startButtonInactive = new Texture(Gdx.files.internal("buttons/startButtonInactive.png"));
		startButtonInactiveSprite = new Sprite(startButtonInactive);
		
		// credits
		creditsButtonActive = new Texture(Gdx.files.internal("buttons/creditsButtonActive.png"));
		creditsButtonActiveSprite = new Sprite(creditsButtonActive);

		creditsButtonInactive = new Texture(Gdx.files.internal("buttons/creditsButtonInactive.png"));
		creditsButtonInactiveSprite = new Sprite(creditsButtonInactive);		
		
		//exit
		exitButtonActive = new Texture(Gdx.files.internal("buttons/exitButtonActive.png"));
		exitButtonActiveSprite = new Sprite(exitButtonActive);

		exitButtonInactive = new Texture(Gdx.files.internal("buttons/exitButtonInactive.png"));
		exitButtonInactiveSprite = new Sprite(exitButtonInactive);
	}
	
	public static void creditsBG() {
		creditsBG = new Texture(Gdx.files.internal("bg/creditsBG.png"));
	}
	
	public static void creditsButtonLoad() {
		// menu
		menuButtonActive = new Texture(Gdx.files.internal("buttons/menuButtonActive.png"));
		menuButtonActiveSprite = new Sprite(menuButtonActive);

		menuButtonInactive = new Texture(Gdx.files.internal("buttons/menuButtonInactive.png"));
		menuButtonInactiveSprite = new Sprite(menuButtonInactive);

	}
	
	public static void gameOverBG() {
		gameOverBG = new Texture(Gdx.files.internal("bg/gameOverBG.png"));
	}
	
	public static void gameOverButtonLoad() {
		// retry
		retryButtonActive = new Texture(Gdx.files.internal("buttons/retryButtonActive.png"));
		retryButtonActiveSprite = new Sprite(retryButtonActive);

		retryButtonInactive = new Texture(Gdx.files.internal("buttons/retryButtonInactive.png"));
		retryButtonInactiveSprite = new Sprite(retryButtonInactive);
		
		// menu
		menuButtonActive = new Texture(Gdx.files.internal("buttons/menuButtonActive.png"));
		menuButtonActiveSprite = new Sprite(menuButtonActive);

		menuButtonInactive = new Texture(Gdx.files.internal("buttons/menuButtonInactive.png"));
		menuButtonInactiveSprite = new Sprite(menuButtonInactive);
				
		//exit
		exitButtonActive = new Texture(Gdx.files.internal("buttons/exitButtonActive.png"));
		exitButtonActiveSprite = new Sprite(exitButtonActive);

		exitButtonInactive = new Texture(Gdx.files.internal("buttons/exitButtonInactive.png"));
		exitButtonInactiveSprite = new Sprite(exitButtonInactive);

	}
	





}
