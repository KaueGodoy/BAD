package tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Camera {

	OrthographicCamera cam;
	
	public Vector2 getInputInGameWorld() {
		Vector3 inputScreen = new Vector3(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
		Vector3 unprojected = cam.unproject(inputScreen);
		return new Vector2(unprojected.x, unprojected.y);
	}

}
