package tools;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import bad.game.Bad;

public class Buttons {
	

	public Sprite sprite;
	public Rectangle bounds;
	
	public float width = 200f;
	public float height = 50f;
	
	public float x = Bad.WIDTH / 4 - width / 2;
	public float y = Bad.HEIGHT / 2 - height / 2;
	
	public float xDistance = 320;
	public float yDistance = 100;

	
	public Buttons(Sprite sprite){
		this.sprite = sprite;
		bounds = new Rectangle(x, y, width, height);
	}
	

}
