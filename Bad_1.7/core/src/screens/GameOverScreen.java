package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import assets.ButtonAssets;
import audio.Sfx;
import bad.game.Bad;
import tools.Buttons;
import tools.GameCamera;

public class GameOverScreen implements Screen {

	Bad bad;
	SpriteBatch batch;
	BitmapFont menuFont;
	
	GameCamera camera;
	
	Buttons retryButtonActive;
	Buttons retryButtonInactive;

	Buttons menuButtonActive;
	Buttons menuButtonInactive;
	
	Buttons exitButtonActive;
	Buttons exitButtonInactive;
	
	public GameOverScreen(Bad bad) {
		this.bad = bad;
		
		batch = new SpriteBatch();
		camera = new GameCamera(Bad.WIDTH, Bad.HEIGHT);
		
		menuFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
		
		ButtonAssets.gameOverBG();
		ButtonAssets.gameOverButtonLoad();
		
		retryButtonActive = new Buttons(ButtonAssets.retryButtonActiveSprite);  
		retryButtonInactive = new Buttons(ButtonAssets.retryButtonInactiveSprite);  

		menuButtonActive = new Buttons(ButtonAssets.menuButtonActiveSprite);
		menuButtonInactive = new Buttons(ButtonAssets.menuButtonInactiveSprite);
		
		exitButtonActive = new Buttons(ButtonAssets.exitButtonActiveSprite);
		exitButtonInactive = new Buttons(ButtonAssets.exitButtonInactiveSprite);
		
		
	}
	
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

		batch.begin();
		
		batch.draw(ButtonAssets.gameOverBG, 0, 0);
			
		buttonCollision();
	
		
		/*GlyphLayout playButttonLayout = new GlyphLayout(menuFont, "Retry", Color.WHITE, Bad.WIDTH - 50, Align.left, true);
		menuFont.draw(batch, playButttonLayout, Bad.WIDTH / 2 - playButttonLayout.width / 2, Bad.HEIGHT / 2 - playButttonLayout.height);
		
		GlyphLayout exitButttonLayout = new GlyphLayout(menuFont, "Exit", Color.WHITE, Bad.WIDTH - 50, Align.left, true);
		menuFont.draw(batch, exitButttonLayout, Bad.WIDTH / 2 - exitButttonLayout.width / 2, Bad.HEIGHT / 2 - exitButttonLayout.height - 100);
		*/
		
		
		batch.end();
	}
	
	public void buttonCollision() {

		// retry button
		if(		camera.getInputInGameWorld().x >= retryButtonActive.bounds.x + menuButtonActive.xDistance&& 
				camera.getInputInGameWorld().x <= retryButtonActive.bounds.x + menuButtonActive.xDistance + retryButtonActive.width &&
				camera.getInputInGameWorld().y >= retryButtonActive.bounds.y &&
				camera.getInputInGameWorld().y <= retryButtonActive.bounds.y + retryButtonActive.height) {
			batch.draw(retryButtonActive.sprite, retryButtonActive.bounds.x + menuButtonActive.xDistance, retryButtonActive.bounds.y);
			// Go to game screen
			if(Gdx.input.justTouched()) {
				Sfx.playClickSound();
				this.dispose();
				bad.setScreen(new MainGameScreen(bad));
			}
		
		}
		else {
			batch.draw(retryButtonInactive.sprite, retryButtonActive.bounds.x + menuButtonActive.xDistance, retryButtonActive.bounds.y);
		}
		
		// menu
		if(		camera.getInputInGameWorld().x >= menuButtonActive.bounds.x + menuButtonActive.xDistance && 
				camera.getInputInGameWorld().x <= menuButtonActive.bounds.x + menuButtonActive.xDistance + menuButtonActive.width &&
				camera.getInputInGameWorld().y >= menuButtonActive.bounds.y + menuButtonActive.yDistance &&
				camera.getInputInGameWorld().y <= menuButtonActive.bounds.y + menuButtonActive.yDistance + menuButtonActive.height) {
			batch.draw(menuButtonActive.sprite, menuButtonActive.bounds.x + menuButtonActive.xDistance, menuButtonActive.bounds.y - menuButtonActive.yDistance);
			// Go back to main menu
			if(Gdx.input.justTouched()) {
				Sfx.playClickSound();
				this.dispose();
				bad.setScreen(new MainMenuScreen(bad));
			}
		
		}
		else {
			batch.draw(menuButtonInactive.sprite, menuButtonInactive.bounds.x + menuButtonActive.xDistance, menuButtonInactive.bounds.y - menuButtonActive.yDistance);
		}
		
		// exit button
		if(		camera.getInputInGameWorld().x >= exitButtonActive.bounds.x + menuButtonActive.xDistance && 
				camera.getInputInGameWorld().x <= exitButtonActive.bounds.x + menuButtonActive.xDistance + exitButtonActive.width &&
				camera.getInputInGameWorld().y >= exitButtonActive.bounds.y + exitButtonActive.yDistance * 2 &&
				camera.getInputInGameWorld().y <= exitButtonActive.bounds.y + exitButtonActive.yDistance * 2 + exitButtonActive.height ) {
			batch.draw(exitButtonActive.sprite, exitButtonActive.bounds.x + menuButtonActive.xDistance, exitButtonActive.bounds.y - exitButtonActive.yDistance * 2);
			// Close game
			if(Gdx.input.justTouched()) {
				Gdx.app.exit();
				}
				
		}
		else {
			batch.draw(exitButtonInactive.sprite, exitButtonActive.bounds.x + menuButtonActive.xDistance, exitButtonActive.bounds.y - exitButtonActive.yDistance * 2);
		}	
		
	}
	
	@Override
	public void resize(int width, int height) {
		camera.update(Bad.WIDTH, Bad.HEIGHT);		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
