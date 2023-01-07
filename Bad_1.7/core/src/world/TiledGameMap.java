/*package world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledGameMap {

	public static TiledMap tiledMap;
	private static OrthogonalTiledMapRenderer tiledMapRenderer;
	public static OrthographicCamera camera;
	
	
	public TiledGameMap() {
		
		tiledMap = new TmxMapLoader().load("maps/map_test.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		camera = new OrthographicCamera();
		
	}
	
	
	public void mapRender() {
		 
		camera.setToOrtho(false);
		//camera.position.set()
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		
		
		
	}
	
	


	


	
	
	
	
}*/
