package screens;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import audio.Sfx;
import bad.game.Bad;
import entities.Bullet;
import entities.Player;
import entities.Skill;
import entities.NPC.NPC_Kaue;
import entities.NPC.NPC_Lele;
import entities.NPC.NPC_Luigi;
import entities.NPC.NPC_Nicolas;
import entities.enemies.Enemy;
import entities.enemies.EnemySkeleton;
import tools.CollisionRect;

public class MainGameScreen implements Screen {

	Bad bad;
	SpriteBatch batch;
	//TiledGameMap tiledGameMap;
	
	// player
	Player player;
	private TextureAtlas playerAtlas;
	CollisionRect playerRect;
	
	// enemy
	EnemySkeleton enemySkeleton;
	private TextureAtlas enemySkeletonAtlas;
	CollisionRect enemySkeletonRect;
	CollisionRect enemySkeletonRadius;
	
	// NPC
	NPC_Luigi luigiNPC;
	private TextureAtlas luigiAtlas;
	CollisionRect luigiNPCRect;
	
	NPC_Nicolas nicolasNPC;
	private TextureAtlas nicolasAtlas;
	CollisionRect nicolasNPCRect;
	
	NPC_Kaue kaueNPC;
	private TextureAtlas kaueAtlas;
	CollisionRect kaueNPCRect;
	
	NPC_Lele leleNPC;
	private TextureAtlas leleAtlas;
	CollisionRect leleNPCRect;
	
	//map
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	public static OrthographicCamera camera;
	
	// shooting
	ArrayList<Bullet> bullets; 
	
	// skill
	ArrayList<Skill> skills; 
	
	// enemy
	public static final float MIN_ENEMY_SPAWN_TIME = 0.01f;
	public static final float MAX_ENEMY_SPAWN_TIME = 0.09f;
	float enemySpawnTimer;
	Random random;
	ArrayList<Enemy> enemies;
	
	// HUD
	Texture hpHUD;
	Texture skillHUD;
	Texture ultHUD;
	
	public boolean spawnEnemies;
	
	// i frames
	public static float iFrameTimer;
	public static final float MAX_IFRAME_TIME = 0.6f;
	
	// easter egg
	public static float runTime;
	public static final float EASTER_EGG_TIME = 3f;
	
	// enemy radius
	public static boolean attackingRadius;
	
	
	public MainGameScreen(Bad bad) {
		this.bad = bad;
		
		
		
		
		batch = new SpriteBatch();
		//tiledGameMap = new TiledGameMap();	
		
		tiledMap = new TmxMapLoader().load("maps/lavaMap/map_final.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		camera = new OrthographicCamera();
		
		// Player
		
		playerAtlas = new TextureAtlas("entities/Derildo/Derildo.pack"); 
		
		Animation<TextureRegion> idle, right, left, jump, attack;
		idle = new Animation<TextureRegion>(1 / 2f, playerAtlas.findRegions("idle"));
		right = new Animation<TextureRegion>(1 / 6f, playerAtlas.findRegions("right"));
		left = new Animation<TextureRegion>(1 / 6f, playerAtlas.findRegions("left"));
		jump = new Animation<TextureRegion>(1 / 4f, playerAtlas.findRegions("jump"));
		attack = new Animation<TextureRegion>(1 / 10f, playerAtlas.findRegions("attack"));
		
		idle.setPlayMode(Animation.PlayMode.LOOP);
		right.setPlayMode(Animation.PlayMode.LOOP);
		left.setPlayMode(Animation.PlayMode.LOOP);
		jump.setPlayMode(Animation.PlayMode.LOOP);
		attack.setPlayMode(Animation.PlayMode.LOOP);
		
		player = new Player(idle, right, left, jump, attack, (TiledMapTileLayer) tiledMap.getLayers().get(1));
		
			
		player.setPosition(38 * player.getCollisionLayer().getTileWidth(), 
						  (player.getCollisionLayer().getHeight() - 30) * player.getCollisionLayer().getTileHeight());
		
		playerRect = new CollisionRect(38 * player.getCollisionLayer().getTileWidth(), 
				  (player.getCollisionLayer().getHeight() - 30) * player.getCollisionLayer().getTileHeight(),
				   player.getWidth(), player.getHeight());
		
		// Enemy Skeleton setup
		enemySkeletonAtlas = new TextureAtlas("entities/EnemySkeleton/Skeleton.pack"); 
		
		Animation<TextureRegion> skeletonIdle, skeletonRight, skeletonLeft, skeletonAttack;
		skeletonIdle = new Animation<TextureRegion>(1 / 2f, enemySkeletonAtlas.findRegions("idle"));
		skeletonRight = new Animation<TextureRegion>(1 / 6f, enemySkeletonAtlas.findRegions("right"));
		skeletonLeft = new Animation<TextureRegion>(1 / 6f, enemySkeletonAtlas.findRegions("left"));
		skeletonAttack = new Animation<TextureRegion>(1 / 10f, enemySkeletonAtlas.findRegions("attack"));
		
		skeletonIdle.setPlayMode(Animation.PlayMode.LOOP);
		skeletonRight.setPlayMode(Animation.PlayMode.LOOP);
		skeletonLeft.setPlayMode(Animation.PlayMode.LOOP);
		skeletonAttack.setPlayMode(Animation.PlayMode.LOOP);
		
		enemySkeleton = new EnemySkeleton(skeletonIdle, skeletonRight, skeletonLeft, skeletonAttack, (TiledMapTileLayer) tiledMap.getLayers().get(1));
		
		enemySkeleton.setPosition(145 * enemySkeleton.getCollisionLayer().getTileWidth(), 
						  (enemySkeleton.getCollisionLayer().getHeight() - 35) * enemySkeleton.getCollisionLayer().getTileHeight());
		
		enemySkeletonRect = new CollisionRect(145 * enemySkeleton.getCollisionLayer().getTileWidth(), 
				  (enemySkeleton.getCollisionLayer().getHeight() - 35) * enemySkeleton.getCollisionLayer().getTileHeight(),
				  enemySkeleton.getWidth(), enemySkeleton.getHeight());
		
		enemySkeletonRadius = new CollisionRect(145 * enemySkeleton.getCollisionLayer().getTileWidth(), 
				  (enemySkeleton.getCollisionLayer().getHeight() - 35) * enemySkeleton.getCollisionLayer().getTileHeight(),
				   enemySkeleton.getWidth() + 60, enemySkeleton.getHeight());
		
		// NPC
		
		// luigi
		
		luigiAtlas = new TextureAtlas("entities/NPC/luigi/Luigi.pack"); 
		
		Animation<TextureRegion> luigiIdle, luigiRight, luigiLeft;
		luigiIdle = new Animation<TextureRegion>(1 / 2f, luigiAtlas.findRegions("idle"));
		luigiRight = new Animation<TextureRegion>(1 / 6f, luigiAtlas.findRegions("right"));
		luigiLeft = new Animation<TextureRegion>(1 / 6f, luigiAtlas.findRegions("left"));
	
		luigiIdle.setPlayMode(Animation.PlayMode.LOOP);
		luigiRight.setPlayMode(Animation.PlayMode.LOOP);
		luigiLeft.setPlayMode(Animation.PlayMode.LOOP);
		
		luigiNPC = new NPC_Luigi(luigiIdle, luigiRight, luigiLeft, (TiledMapTileLayer) tiledMap.getLayers().get(1));
		
		luigiNPC.setPosition(39 * luigiNPC.getCollisionLayer().getTileWidth(), 
						  (luigiNPC.getCollisionLayer().getHeight() - 35) * luigiNPC.getCollisionLayer().getTileHeight());
		
		luigiNPCRect = new CollisionRect(39  * luigiNPC.getCollisionLayer().getTileWidth(), 
				  (luigiNPC.getCollisionLayer().getHeight() - 35) * luigiNPC.getCollisionLayer().getTileHeight(),
				   luigiNPC.getWidth(), luigiNPC.getHeight());
		
		// nicolas
		
		nicolasAtlas = new TextureAtlas("entities/NPC/nicolas/Nicolas.pack"); 
		
		Animation<TextureRegion> nicolasIdle, nicolasRight, nicolasLeft;
		nicolasIdle = new Animation<TextureRegion>(1 / 2f, nicolasAtlas.findRegions("idle"));
		nicolasRight = new Animation<TextureRegion>(1 / 6f, nicolasAtlas.findRegions("right"));
		nicolasLeft = new Animation<TextureRegion>(1 / 6f, nicolasAtlas.findRegions("left"));
	
		nicolasIdle.setPlayMode(Animation.PlayMode.LOOP);
		nicolasRight.setPlayMode(Animation.PlayMode.LOOP);
		nicolasLeft.setPlayMode(Animation.PlayMode.LOOP);
		
		nicolasNPC = new NPC_Nicolas(nicolasIdle, nicolasRight, nicolasLeft, (TiledMapTileLayer) tiledMap.getLayers().get(1));
			
		nicolasNPC.setPosition(88 * nicolasNPC.getCollisionLayer().getTileWidth(), 
						  (nicolasNPC.getCollisionLayer().getHeight() - 33) * nicolasNPC.getCollisionLayer().getTileHeight());
		
		nicolasNPCRect = new CollisionRect(88  * nicolasNPC.getCollisionLayer().getTileWidth(), 
				  (nicolasNPC.getCollisionLayer().getHeight() - 33) * nicolasNPC.getCollisionLayer().getTileHeight(),
				   nicolasNPC.getWidth(), nicolasNPC.getHeight());
		
		// kaue
		
		kaueAtlas = new TextureAtlas("entities/NPC/kaue/Kaue.pack"); 
		
		Animation<TextureRegion> kaueIdle, kaueRight, kaueLeft;
		kaueIdle = new Animation<TextureRegion>(1 / 2f, kaueAtlas.findRegions("idle"));
		kaueRight = new Animation<TextureRegion>(1 / 6f, kaueAtlas.findRegions("right"));
		kaueLeft = new Animation<TextureRegion>(1 / 6f, kaueAtlas.findRegions("left"));
	
		kaueIdle.setPlayMode(Animation.PlayMode.LOOP);
		kaueRight.setPlayMode(Animation.PlayMode.LOOP);
		kaueLeft.setPlayMode(Animation.PlayMode.LOOP);
		
		kaueNPC = new NPC_Kaue(kaueIdle, kaueRight, kaueLeft, (TiledMapTileLayer) tiledMap.getLayers().get(1));
			
		kaueNPC.setPosition(140 * kaueNPC.getCollisionLayer().getTileWidth(), 
						  (kaueNPC.getCollisionLayer().getHeight() - 45) * kaueNPC.getCollisionLayer().getTileHeight());
		
		kaueNPCRect = new CollisionRect(140  * kaueNPC.getCollisionLayer().getTileWidth(), 
				  (kaueNPC.getCollisionLayer().getHeight() - 45) * kaueNPC.getCollisionLayer().getTileHeight(),
				   kaueNPC.getWidth(), kaueNPC.getHeight());
		
		
		// lele
		
		leleAtlas = new TextureAtlas("entities/NPC/lele/Lele.pack"); 
		
		Animation<TextureRegion> leleIdle;
		leleIdle = new Animation<TextureRegion>(1 / 2f, leleAtlas.findRegions("idle"));
		
		leleIdle.setPlayMode(Animation.PlayMode.LOOP);
			
		leleNPC = new NPC_Lele(leleIdle, (TiledMapTileLayer) tiledMap.getLayers().get(1));
			
		leleNPC.setPosition(58.5f * player.getCollisionLayer().getTileWidth(), 
						  (player.getCollisionLayer().getHeight() - 36) * player.getCollisionLayer().getTileHeight());
		
		leleNPCRect = new CollisionRect(58  * player.getCollisionLayer().getTileWidth(), 
				  (player.getCollisionLayer().getHeight() - 36) * player.getCollisionLayer().getTileHeight(),
				   player.getWidth(), player.getHeight());
		
		
		
		//Gdx.input.setInputProcessor(player);	
		
		// bullets
		bullets = new ArrayList<Bullet>();

		
		// skill
		skills = new ArrayList<Skill>();
		Skill.skillCooldown = 0;
		
		// enemy
		random = new Random();
		enemySpawnTimer = random.nextFloat() * (MAX_ENEMY_SPAWN_TIME - MIN_ENEMY_SPAWN_TIME ) + MIN_ENEMY_SPAWN_TIME;
		enemies = new ArrayList<Enemy>();
		
		// health
		hpHUD = new Texture("HUD/blank.png");
		skillHUD = new Texture("HUD/skill.png");
		ultHUD = new Texture("HUD/ult.png");
		
		
		spawnEnemies = true;
		
		iFrameTimer = 0;
		attackingRadius = false;
		
		Sfx.loadAudio();
		
		runTime = 0;
		
	
	}
	
	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 1, 1);
		
		
	
				
		
		// UPDATE
		
		// map rendering
		//tiledGameMap.mapRender();
		//camera.setToOrtho(false);
		
		
		//camera.position.set(MathUtils.ceil(2.5f *(player.getX() + player.getWidth() / 2))/2.5f, MathUtils.ceil(2.5f * (player.getY() + player.getHeight() / 2 ) ) / 2.5f, 0); 
		
		//camera.position.set(player.getX(), player.getY(), 0);
		//camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

		//camera.update();
		
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();
		cameraUpdate();
		
		iFrameTimer+= delta;
		runTime += delta;
		
		// shooting
		Bullet.shootCooldown += delta;
		if(player.isAttacking() && Bullet.canShoot()) {
			Sfx.playShootSound();
			bullets.add(new Bullet(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2));
		}
		
		// update bullets
		ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
		for(Bullet bullet : bullets) {
			bullet.update(delta);
			if(bullet.remove) {
				bulletsToRemove.add(bullet);
			}
		}
		
		// skill
		Skill.skillCooldown += delta;
		if(Skill.canUseSkill()) {
			Sfx.playSkillSound();
			skills.add(new Skill(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2));
		}
		
		// update skill
		ArrayList<Skill> skillToRemove = new ArrayList<Skill>();
		for(Skill skill : skills) {
			skill.update(delta);
			if(skill.remove) {
				skillToRemove.add(skill);
			}
		}
		


		
		// enemy spawn
		enemySpawnTimer -= delta;
		if(player.getX() < 120 * player.getCollisionLayer().getTileWidth()) {
		if(enemySpawnTimer <= 0) {
			enemySpawnTimer = random.nextFloat() * (MAX_ENEMY_SPAWN_TIME - MIN_ENEMY_SPAWN_TIME ) + MIN_ENEMY_SPAWN_TIME;
			enemies.add(new Enemy(random.nextInt((int) (player.getX() + 200 - Enemy.WIDTH)), (int) player.getY() + player.getHeight() + 200, (TiledMapTileLayer) tiledMap.getLayers().get(1) ));
			//enemies.add(new Enemy(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() + 350, (TiledMapTileLayer) tiledMap.getLayers().get(1) ));
			
		}
		}
	
		/*
		if(spawnEnemies) {
			
			enemies.add(new Enemy(player.getX() + player.getWidth() / 2, 
								  player.getY() + player.getHeight() + 500,
								  (TiledMapTileLayer) tiledMap.getLayers().get(1)));
			
			enemies.add(new Enemy(player.getX() + player.getWidth() / 2, 
					  player.getY() + player.getHeight() + 250,
					  (TiledMapTileLayer) tiledMap.getLayers().get(1)));
			
			enemies.add(new Enemy(player.getX() + player.getWidth() / 2, 
					  player.getY() + player.getHeight() + 400,
					  (TiledMapTileLayer) tiledMap.getLayers().get(1)));


			spawnEnemies = false;
		}*/
		
	
		// enemy update
		ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
		for(Enemy enemy : enemies) {
			enemy.update(delta);
			if(enemy.remove) {
				enemiesToRemove.add(enemy);
			}
		}
		
	
		
		// movement update
		player.update(delta);
		
		enemySkeleton.update(delta);
		
		luigiNPC.update(delta);
		nicolasNPC.update(delta);
		kaueNPC.update(delta);
		leleNPC.update(delta);
		
		playerRect.move(player.getX(), player.getY());
		
		enemySkeletonRect.move(enemySkeleton.getX(), enemySkeleton.getY());
		enemySkeletonRadius.move(enemySkeleton.getX(), enemySkeleton.getY());
		
		luigiNPCRect.move(luigiNPC.getX(), luigiNPC.getY());
		nicolasNPCRect.move(luigiNPC.getX(), luigiNPC.getY());
		kaueNPCRect.move(kaueNPC.getX(), kaueNPC.getY());
		leleNPCRect.move(leleNPC.getX(), leleNPC.getY());
		
		// collision detection
		
		// bullet - enemy
		for(Bullet bullet : bullets) {
			
			if(bullet.getCollisionRect().CollidesWith(enemySkeletonRect)) {
				bulletsToRemove.add(bullet);
				enemySkeleton.health -= 0.1f;
			}
			for(Enemy enemy : enemies) {
				if(bullet.getCollisionRect().CollidesWith(enemy.getCollisionRect())) { // collision happened
					Sfx.playHitmarkerSound();
					bulletsToRemove.add(bullet);
					enemiesToRemove.add(enemy);
				}
			}
		}
		

		// skill - enemy
		for(Skill skill : skills) {
			if(skill.getCollisionRect().CollidesWith(enemySkeletonRect)) {
				enemySkeleton.health -= 1f;
			}
			for(Enemy enemy : enemies) {
				if(skill.getCollisionRect().CollidesWith(enemy.getCollisionRect())) { // collision happened
					//skillToRemove.add(skill);
					enemiesToRemove.add(enemy);
					
				}
			}
		}
		
		bullets.removeAll(bulletsToRemove);
		
		skills.removeAll(skillToRemove);
		
		// PLAYER - ENEMY COLLISION
		for(Enemy enemy : enemies) {
			if(enemy.getCollisionRect().CollidesWith(playerRect)) {
				enemiesToRemove.add(enemy);
				enemy.applyDamage(0.1f);
				//applyDamage(0.3f);
		
				
				}
			}
		
		// Skeleton - Player 

		if(enemySkeletonRadius.CollidesWith(playerRect))
			attackingRadius = true;
		else 
			attackingRadius = false;
			
		
		if(isEnemyAlive()) {
			if(enemySkeletonRect.CollidesWith(playerRect)) {
				if(iFrameTimer > MAX_IFRAME_TIME) {
					Player.health -= 0.1f;
					iFrameTimer = 0;
				}
			}
		}
		
		
		isPlayerDead();
		
		enemies.removeAll(enemiesToRemove);
		
		
		// DRAWING 
		
		batch.begin();
	
		// player
		player.draw(batch);
		
		// enemy		
		if(isEnemyAlive()) {
		enemySkeleton.draw(batch);
		}
		//player.render(batch);
		
		// NPC
		luigiNPC.draw(batch);
		nicolasNPC.draw(batch);
		kaueNPC.draw(batch);
		
		// easter egg
		if(runTime >= EASTER_EGG_TIME)
		leleNPC.draw(batch);
		
		// bullets drawing 
		for(Bullet bullet : bullets) {
			bullet.render(batch);
		}
		
		// asteroids drawing
		for(Enemy enemy : enemies) {
			enemy.render(batch);
		}
		
		//	skill drawing
		for(Skill skill : skills) {
			skill.render(batch);
		}
		
		// skill HUD
		if(Skill.showSkill()) {
			batch.draw(skillHUD, player.getX() - 600, player.getY() - 300, 16, 5);
		}

		// health
		if(Player.health > 0.6f) // 60-100% HP
			batch.setColor(Color.GREEN);
		else if(Player.health > 0.2f) // 20-60% HP
			batch.setColor(Color.ORANGE); 
		else 
			batch.setColor(Color.RED); // 0-20% HP
		
		batch.draw(hpHUD, player.getX(), (player.getY() + player.getHeight()) + player.getHeight(), 50 * Player.health, 3);
		
		if(isEnemyAlive()){
			batch.draw(hpHUD, enemySkeleton.getX(), (enemySkeleton.getY() + player.getHeight()) + player.getHeight(), 50 * EnemySkeleton.health, 3);
		}
		
		batch.setColor(Color.WHITE);
		batch.end();		
		
	}

	@Override
	public void resize(int width, int height) {
		camera.viewportWidth = width;	
		camera.viewportHeight = height;
		camera.update();
			
			
	}
	
	public static boolean isEnemyAlive() {
		return EnemySkeleton.health > 0;
	}
	
	public void isPlayerDead() {
		if(Player.health <= 0) {
			Sfx.playGameOverSound();
			dispose();
			bad.setScreen(new GameOverScreen(bad));
		}
	}

	
	public void cameraUpdate() {
		batch.setProjectionMatrix(camera.combined);
		Vector3 position = camera.position;
		position.x = camera.position.x + (player.getX() - camera.position.x) * .1f;
		position.y = camera.position.y + (player.getY() - camera.position.y) * .1f;
		camera.position.set(position);
	
		camera.update();
	}
	
	

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		batch.dispose();
		tiledMapRenderer.dispose();
		
		playerAtlas.dispose();
		enemySkeletonAtlas.dispose();
		luigiAtlas.dispose();
		nicolasAtlas.dispose();
		kaueAtlas.dispose();
		
		Sfx.audioDispose();
	}

}
