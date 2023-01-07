package audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class Sfx {
	
	private static Sound jumpSfx;
	private static Sound skillSfx;
	private static Sound clickSfx;
	private static Sound hitmarkerSfx;
	private static Sound shootSfx;
	private static Sound gameOverSfx;
	
	public Sfx() {}
	
	public static void loadAudio() {
		jumpSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/Jump.wav"));
		skillSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/Skill.wav"));
		hitmarkerSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/Hitmarker.mp3"));
		clickSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/Click.wav"));
		shootSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/Shoot.wav"));
		gameOverSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/GameOver.wav"));
	}
	
	public static void screensLoadAudio() {
		clickSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/Click.wav"));
		gameOverSfx = Gdx.audio.newSound(Gdx.files.internal("audio/sfx/GameOver.wav"));
	}
	
	// entities
	
	public static void playJumpSound() {
		jumpSfx.play(0.05f);
	}

	public static void playSkillSound() {
		skillSfx.play(0.1f);
	}
	
	public static void playHitmarkerSound() {
		hitmarkerSfx.play(0.1f);
	}

	public static void playShootSound() {
		shootSfx.play(0.1f);
	}
	
	// screens
	
	public static void playClickSound() {
		clickSfx.play(0.1f);
	}
	
	public static void playGameOverSound() {
		gameOverSfx.play(0.6f);
	}
	
	
	public static void audioDispose(){
		jumpSfx.dispose();
		skillSfx.dispose();
		hitmarkerSfx.dispose();
		shootSfx.dispose();
		//clickSfx.dispose();
		//gameOverSfx.dispose(); // dispose in the game over screen
	}
	
	
}
