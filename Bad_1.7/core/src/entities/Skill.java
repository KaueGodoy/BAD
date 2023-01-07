package entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import tools.CollisionRect;

public class Skill {
	
	public static final int SPEED = 500;
	public static final int DEFAULT_Y = 40;
	public static final int WIDTH = 12 * 3;
	public static final int HEIGHT = 3 * 10;
	private static Texture texture;
	
	// skill duration
	float skillTimer;
	final float SKILL_TRAVEL_TIME = 0.9f;
	
	// skill cooldown
	public static float skillCooldown;
	public static final float SKILL_COOLDOWN_TIME = 2f; 
	

	float x, y;
	CollisionRect rect;
	public boolean remove = false;
	
	public Skill(float x, float y) {
		this.x = x;
		this.y = y;
		
		this.rect = new CollisionRect(x, y, WIDTH, HEIGHT);
		
		if (texture == null) 
			texture = new Texture("entities/skill.png");
		
		skillTimer = 0;
		skillCooldown = 0;
	}
	
	public void update(float deltaTime) {
		x += SPEED * deltaTime;
		//y += SPEED * deltaTime;
		
		skillTimer += deltaTime;
		if(canRemoveSkill()) {
			remove = true;
		}
		
		rect.move(x, y);
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, WIDTH, HEIGHT);
	}
	
	public static boolean canUseSkill() {
		return Gdx.input.isKeyJustPressed(Keys.I) && skillCooldown >= SKILL_COOLDOWN_TIME;
	}
	
	public boolean canRemoveSkill() {
		return skillTimer > SKILL_TRAVEL_TIME;
	}
	
	public static boolean showSkill() {
		return skillCooldown >= SKILL_COOLDOWN_TIME;
	}
	
	public CollisionRect getCollisionRect() {
		return rect;
	}

}
