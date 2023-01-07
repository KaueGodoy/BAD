package entities.NPC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import screens.MainGameScreen;

public class NPC_Lele extends Sprite {
	
	// movement
	private Vector2 velocity = new Vector2();
	private float speed = 60 * 2;
	private float gravity = 60 * 1.8f;
	private float increment;

	// collision
	private TiledMapTileLayer collisionLayer;
	private String blockedKey = "blocked";
	private String deathKey = "death";
	
	// animation
	private Animation<TextureRegion> leleIdle;
	private float animationTime = 0;
	
	// movement distance
	private float travelSpaceTimer;
	private final float TRAVEL_SPACE_TIME = 1.5f;
	
	// movement time control
	private float movingTimer;
	private final float movingCOOLDOWN = 5f;
	
	// hp
	public static float health = 1f; 

	public NPC_Lele(Animation<TextureRegion> leleIdle, TiledMapTileLayer collisionLayer) {
		super(leleIdle.getKeyFrame(0));
		this.leleIdle = leleIdle;
		this.collisionLayer = collisionLayer;
		
		setSize(getWidth() * 2, getHeight() * 2);
		
		travelSpaceTimer = 0;
		movingTimer = 0;
		
	}
	
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	public void update(float delta){
		movementUpdate(delta);		
		
		animationTime += delta;
		travelSpaceTimer += delta;
		movingTimer += delta;
		
		checkMovement();
	}
	
	public void movementUpdate(float delta) {
		// apply gravity 
		velocity.y -= gravity * delta;
		
		// clamp velocity 
		if(velocity.y > speed)
			velocity.y = speed;
		else if(velocity.y < -speed)
			velocity.y = -speed;
	
		// save old positions
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;
		
		// move on X
		setX((getX() + velocity.x * delta));
		
		// calculate the increment for step in #collidesLeft() and #collidesRight()
		increment = collisionLayer.getTileWidth();
		increment = getWidth() < increment ? getWidth() / 2 : increment / 2;
		
		// x collision
		if(velocity.x < 0) 
			collisionX = collidesLeft();
		else if(velocity.x > 0) 
			collisionX = collidesRight();
		
		// x collision happens
		if(collisionX) {
			setX(oldX);
		}
		
		// move on y
		setY((getY() + velocity.y * delta));
		
		// calculate the increment for step in #collidesBottom() and #collidesTop()
		increment = collisionLayer.getTileHeight();
		increment = getHeight() < increment ? getHeight() / 2 : increment / 2;
		
		// y collision
		if(velocity.y < 0) {
			collisionY = collidesBottom();
		}
		else if(velocity.y > 0)
			collisionY = collidesTop();
		
		// limbo collision - player receives damage
		if(MainGameScreen.isEnemyAlive()) {
			if(collidesBottomDeath()) {
				health = 0;
			}
		}
		// y collision happens
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
	}

// -------------------------------------- COLLISION ---------------------------------------	
	
	private boolean isCellBlocked(float x, float y){
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(blockedKey);
	}

	private boolean isCellDeath(float x, float y){
		Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey(deathKey);
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
	
	public boolean collidesBottomDeath() {
		boolean collides = false;
		
		for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
			if(collides = isCellDeath(getX() + step, getY()))
				break;
		
		return collides;		
	}

//----------------------------------- MOVEMENT -------------------------------------
	
	public void checkMovement() {
		
		isIdle();
		
	}
	
	public boolean isMoving() {
		return movingTimer > movingCOOLDOWN;
	}
	
	public boolean isMovingOnCooldown() {
		return movingTimer > 2 * movingCOOLDOWN;
	}
	
	public boolean canMoveRight() {
		return travelSpaceTimer > TRAVEL_SPACE_TIME;
	}
	
	public boolean canMoveRightIsOnCooldown() {
		return travelSpaceTimer > 2 * TRAVEL_SPACE_TIME;
	}
	
	public void isIdle() {
		setRegion(leleIdle.getKeyFrame(animationTime));
		velocity.x = 0;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}


}
