package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import assets.ButtonAssets;
import audio.Sfx;
import bad.game.Bad;
import tools.Buttons;
import tools.GameCamera;

public class CreditsScreen implements Screen {

	Bad bad;
	SpriteBatch batch;
	GameCamera camera;
	
	Buttons menuButtonActive;
	Buttons menuButtonInactive;
	
	public CreditsScreen(Bad bad) {

		this.bad = bad;
		
		batch = new SpriteBatch();
		camera = new GameCamera(Bad.WIDTH, Bad.HEIGHT);
		
		ButtonAssets.creditsBG();
		ButtonAssets.creditsButtonLoad();
		Sfx.screensLoadAudio();
		
		menuButtonActive = new Buttons(ButtonAssets.menuButtonActiveSprite);
		menuButtonInactive = new Buttons(ButtonAssets.menuButtonInactiveSprite);
	
	}
	
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		
		goBackToMenu();

		batch.begin();
		
		batch.draw(ButtonAssets.creditsBG, 0, 0);
		buttonCollision();
		
		batch.end();
	}

	public void buttonCollision(){
		// menu button
		if(		camera.getInputInGameWorld().x >= menuButtonActive.bounds.x && 
				camera.getInputInGameWorld().x <= menuButtonActive.bounds.x + menuButtonActive.width &&
				camera.getInputInGameWorld().y >= menuButtonActive.bounds.y + menuButtonActive.yDistance * 3 &&
				camera.getInputInGameWorld().y <= menuButtonActive.bounds.y + menuButtonActive.yDistance * 3 + menuButtonActive.height) {
			batch.draw(menuButtonActive.sprite, menuButtonActive.bounds.x, menuButtonActive.bounds.y - menuButtonActive.yDistance * 3f );
			// Go back to main menu
			if(Gdx.input.justTouched()) {
				Sfx.playClickSound();
				this.dispose();
				bad.setScreen(new MainMenuScreen(bad));
			}
		
		}
		else {
			batch.draw(menuButtonInactive.sprite, menuButtonInactive.bounds.x, menuButtonInactive.bounds.y - menuButtonActive.yDistance * 3f);
		}
		
	}
	
	public void goBackToMenu() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Sfx.playClickSound();
			this.dispose();
			bad.setScreen(new MainMenuScreen(bad));
		}
	}
	
	@Override
	public void resize(int width, int height) {
		camera.update(Bad.WIDTH, Bad.HEIGHT);
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		batch.dispose();
	}
	
	

}
