package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;

import audio.Sfx;

public class Player extends Sprite {
	// movement
	public Vector2 velocity = new Vector2();
	private float speed = 60 * 2;
	private float gravity = 60 * 1.8f;
	
	// jump
	private boolean canJump;
	private int jumpCounter;

	// collision
	private TiledMapTileLayer collisionLayer;
	private String blockedKey = "blocked";
	private String deathKey = "death";
	private float increment;
	
	// animation
	private Animation<TextureRegion> idle, right, left, jump, attack;
	private float animationTime = 0;
	
	// hp
	public static float health = 2f;
	
	public Player(Animation<TextureRegion> idle, Animation<TextureRegion> right, Animation<TextureRegion> left, Animation<TextureRegion> jump, Animation<TextureRegion> attack, TiledMapTileLayer collisionLayer) {
		super(idle.getKeyFrame(0));
		this.idle = idle;
		this.right = right;
		this.left = left;
		this.jump = jump;
		this.attack = attack;
		this.collisionLayer = collisionLayer;
		
		jumpCounter = 0;
	
	}

	public Player() {}	
	
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	
	public void update(float delta){
		movementUpdate(delta);		
		
		animationTime += delta;
		
		checkUserInput();
	}
//------------------------------------------------ MOVEMENT ------------------------------
	
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
		
		// move on x
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
			canJump = collisionY = collidesBottom();
			jumpCounter = 0;
		}
		else if(velocity.y > 0)
			collisionY = collidesTop();
		
		// limbo collision
		if(collidesBottomDeath()) {
			health = 0;
		}
		
		// y collision happens
		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
		
	}
	
//---------------------------------------- COLLISION ----------------------------------------	
	
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
		
// ----------------------------------------- MOVEMENT ---------------------------------------
	public void checkUserInput() {
		
		if(isRight()) {
			velocity.x = speed;
		}
		else
			velocity.x = 0;
		
		if(isLeft()) {
			velocity.x = -speed;
		}
				
		if(isJumping() && canJump && jumpCounter <= 3) {
			Sfx.playJumpSound();
			jumpCounter++;
			
			velocity.y = speed;
			if(jumpCounter >= 3)
			canJump = false;	
		}
		
		if(isIdle())			
			setRegion(idle.getKeyFrame(animationTime));
	
		if(isAttacking()) {
			setRegion(attack.getKeyFrame(animationTime));
		}
		
	}
	
	public boolean isIdle() {
		return !isRight() && !isLeft() && !isJumping();
	}
	
	public boolean isRight() {
		setRegion(right.getKeyFrame(animationTime));
		return Gdx.input.isKeyPressed(Keys.D);
	}
	
	public boolean isLeft() {
		setRegion(left.getKeyFrame(animationTime));
		return Gdx.input.isKeyPressed(Keys.A);
	}
	
	public boolean isJumping() {
		setRegion(jump.getKeyFrame(animationTime));
		return Gdx.input.isKeyJustPressed(Keys.SPACE);
	}
	
	public boolean isAttacking() {
		return Gdx.input.isKeyPressed(Keys.J) && (isRight() || isIdle());
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

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}
	
}
