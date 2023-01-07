package entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.CollisionRect;

public class Bullet extends Player {
	
	public static final int SPEED = 500;
	public static final int DEFAULT_Y = 40;
	public static final int WIDTH = 12;
	public static final int HEIGHT = 3;
	private static Texture texture;
	
	// bullet duration
	float shootTravelTimer;
	final float SHOOT_TRAVEL_TIME = 0.3f;

	// bullet cooldown
	public static float shootCooldown;
	public static final float SHOOT_COOLDOWN_TIME = 0.4f;
	
	float x, y;
	CollisionRect rect;
	public boolean remove = false;
	
	public Bullet(float x, float y) {
		this.x = x;
		this.y = y;
		
		this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);
		
		if (texture == null) {
			texture = new Texture("entities/bullet.png");
		}
		
		shootTravelTimer = 0;
		shootCooldown = 0;
	}
	
	public void update(float deltaTime) {
		x += SPEED * deltaTime;

		shootTravelTimer += deltaTime;
		if(canRemoveBullet()) {
			remove = true;
		}
		
		rect.move(x, y);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y);
	}
	
	public static boolean canShoot() {
		return shootCooldown >= SHOOT_COOLDOWN_TIME;
	}
	
	public boolean canRemoveBullet() {
		return shootTravelTimer > SHOOT_TRAVEL_TIME;
	}
	
	public CollisionRect getCollisionRect() {
		return rect;
	}

}
