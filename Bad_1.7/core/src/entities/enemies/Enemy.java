package entities.enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import entities.Player;
import tools.CollisionRect;


public class Enemy extends Sprite {
	
	public static final int SPEED = 250;
	public static final int WIDTH = 32;
	public static final int HEIGHT = 32;
	private static Texture texture;
	
	float x, y;
	CollisionRect rect;
	public boolean remove = false;
	float hp;	
	public Vector2 velocity = new Vector2();
	private TiledMapTileLayer collisionLayer;
	private String blockedKey = "blocked";
	

	
	public Enemy(float x, float y, TiledMapTileLayer collisionLayer) {
		this.x = x;
		this.y = y;
		this.collisionLayer = collisionLayer;
		this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);
		hp(1f, 0f);
		
		if (texture == null) {
			texture = new Texture("entities/asteroid.png");
		}
	}
	
	public void update(float delta) {
		y -= SPEED * delta;
		if(y < 0 - HEIGHT){
			remove = true;
		}
		rect.move(x, y);
		
		
		// save old positions
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		
		// move on Y
		setY((getY() + velocity.y * delta));
	
	
		// Y COLLISION
		
		if(velocity.y < 0)
			collisionY = collidesBottom();
		else if(velocity.y > 0)
			collisionY = collidesTop();
			
	
		
		// y collision happens
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
		
		
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, WIDTH, HEIGHT);
		
	
	}
	
	private boolean isCellBlocked(float x, float y){
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}
	
	public boolean collidesRight() {
		boolean collides = false;
		
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(collides = isCellBlocked(getX() + getWidth(), getY() + step))
				break;
		
		return collides;
	}
	
	public boolean collidesLeft() {
		boolean collides = false;
		
		for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
			if(collides = isCellBlocked(getX(), getY() + step))
				break;
		
		return collides;
	}
	
	public boolean collidesTop() {
		boolean collides = false;
		
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(collides = isCellBlocked(getX() + step, getY() + getHeight()))
				break;
		
		return collides;
	}
	
	public boolean collidesBottom() {
		boolean collides = false;
		
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(collides = isCellBlocked(getX() + step, getY()))
				break;
		
		return collides;		
	}
	

	

	public void applyDamage(float damage) {
		Player.health -= damage;
	}
	
	public float hp(float hp, float damage) {
		return hp - damage;
	}
	
	public boolean dead() {
		return hp <= 0;
	}
	
	public CollisionRect getCollisionRect() {
		return rect;
	}
	


}
