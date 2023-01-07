package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import assets.ButtonAssets;
import audio.Sfx;
import bad.game.Bad;
import tools.Buttons;
import tools.GameCamera;

public class MainMenuScreen implements Screen {

	Bad bad;
	SpriteBatch batch;
	BitmapFont menuFont;
	
	Buttons startButtonActive;
	Buttons startButtonInactive;
	
	Buttons exitButtonActive;
	Buttons exitButtonInactive;
	
	Buttons creditsButtonActive;
	Buttons creditsButtonInactive;
	
	GameCamera camera;
	
	public MainMenuScreen(Bad bad) {
		this.bad = bad;
		
		batch = new SpriteBatch();
		camera = new GameCamera(Bad.WIDTH, Bad.HEIGHT);
		
		ButtonAssets.menuBGLoad();
		ButtonAssets.menuButtonLoad();
		Sfx.screensLoadAudio();
		
		startButtonActive = new Buttons(ButtonAssets.startButtonActiveSprite);
		startButtonInactive = new Buttons(ButtonAssets.startButtonInactiveSprite);
		
		exitButtonActive = new Buttons(ButtonAssets.exitButtonActiveSprite);
		exitButtonInactive = new Buttons(ButtonAssets.exitButtonInactiveSprite);
		
		creditsButtonActive = new Buttons(ButtonAssets.creditsButtonActiveSprite);
		creditsButtonInactive = new Buttons(ButtonAssets.creditsButtonInactiveSprite);
		
		menuFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
		
	
		
	}
	
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);

		closeApplication();
		
		batch.begin();
		
	
		
		batch.draw(ButtonAssets.menuBG, 0, 0);
		buttonCollision();
	
		
		/*GlyphLayout playButttonLayout = new GlyphLayout(menuFont, "Play", Color.WHITE, Bad.WIDTH - 50, Align.left, true);
		menuFont.draw(batch, playButttonLayout, Bad.WIDTH / 2 - playButttonLayout.width / 2, Bad.HEIGHT / 2 - playButttonLayout.height);
		
		GlyphLayout exitButttonLayout = new GlyphLayout(menuFont, "Exit", Color.WHITE, Bad.WIDTH - 50, Align.left, true);
		menuFont.draw(batch, exitButttonLayout, Bad.WIDTH / 2 - exitButttonLayout.width / 2, Bad.HEIGHT / 2 - exitButttonLayout.height - 100);
		*/
		
		
		batch.end();
	}
	
	
	public void buttonCollision(){
		// start button
		if(		camera.getInputInGameWorld().x >= startButtonActive.bounds.x && 
				camera.getInputInGameWorld().x <= startButtonActive.bounds.x + startButtonActive.width &&
				camera.getInputInGameWorld().y >= startButtonActive.bounds.y &&
				camera.getInputInGameWorld().y <= startButtonActive.bounds.y + startButtonActive.height) {
			batch.draw(startButtonActive.sprite, startButtonActive.bounds.x, startButtonActive.bounds.y);
			// Go to game screen
			if(Gdx.input.justTouched()) {
				Sfx.playClickSound();
				this.dispose();
				bad.setScreen(new MainGameScreen(bad));
			}
		
		}
		else {
			batch.draw(startButtonInactive.sprite, startButtonActive.bounds.x, startButtonActive.bounds.y);
		}
		
		// credits button
		if(		camera.getInputInGameWorld().x >= creditsButtonActive.bounds.x && 
				camera.getInputInGameWorld().x <= creditsButtonActive.bounds.x + creditsButtonActive.width &&
				camera.getInputInGameWorld().y >= creditsButtonActive.bounds.y + creditsButtonActive.yDistance &&
				camera.getInputInGameWorld().y <= creditsButtonActive.bounds.y + + creditsButtonActive.yDistance + creditsButtonActive.height) {
			batch.draw(creditsButtonActive.sprite, creditsButtonActive.bounds.x, creditsButtonActive.bounds.y - creditsButtonActive.yDistance);
			// Go to game screen
			if(Gdx.input.justTouched()) {
				Sfx.playClickSound();
				this.dispose();
				bad.setScreen(new CreditsScreen(bad));
			}
		
		}
		else {
			batch.draw(creditsButtonInactive.sprite, creditsButtonActive.bounds.x, creditsButtonActive.bounds.y - creditsButtonActive.yDistance);
		}	
		
		// exit button
		if(		camera.getInputInGameWorld().x >= exitButtonActive.bounds.x && 
				camera.getInputInGameWorld().x <= exitButtonActive.bounds.x + exitButtonActive.width &&
				camera.getInputInGameWorld().y >= exitButtonActive.bounds.y + exitButtonActive.yDistance * 2 &&
				camera.getInputInGameWorld().y <= exitButtonActive.bounds.y + exitButtonActive.yDistance * 2 + exitButtonActive.height ) {
			batch.draw(exitButtonActive.sprite, exitButtonActive.bounds.x, exitButtonActive.bounds.y - exitButtonActive.yDistance * 2);
			// Close game
			if(Gdx.input.justTouched()) {
				Gdx.app.exit();
			}
		
		}
		else {
			batch.draw(exitButtonInactive.sprite, exitButtonActive.bounds.x, exitButtonActive.bounds.y - exitButtonActive.yDistance * 2);
		}	
	}
	
	public void closeApplication() {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Gdx.app.exit();
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
