package bad.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MainMenuScreen;
import tools.GameCamera;

public class Bad extends Game {
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public SpriteBatch batch;
	public GameCamera camera;
		
	@Override
	public void create () {
		camera = new GameCamera(WIDTH, HEIGHT);
		batch = new SpriteBatch();
		
		this.setScreen(new MainMenuScreen(this));
	
	}

	@Override
	public void render () {
		batch.setProjectionMatrix(camera.combined());
		super.render();
	
	}
	
	@Override
	public void resize(int width, int height) {
		camera.update(WIDTH, HEIGHT);
		super.resize(width, height);
	}
	
	
	@Override
	public void dispose () {
		super.dispose(); 
	
	}
}
