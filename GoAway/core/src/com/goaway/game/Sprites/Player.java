package com.goaway.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.goaway.game.GoAway;
import com.goaway.game.Screens.PlayScreen;

public class Player extends Sprite{
	public enum State {
		FALLING, JUMPING, STANDING, RUNNING
	};
	public State currentState;
	public State previousState;
	public World world;
	public Body b2body;
	private TextureRegion playerStand;
	private Animation<TextureRegion> playerRun;
	private Animation<TextureRegion> playerJump;
	private float stateTimer;
	private boolean runningRight;
	
	public Player(World world, PlayScreen screen) {
		super(screen.getAtlas().findRegion("otter_idle"));
		this.world = world;
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		runningRight = true;
		
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i = 7; i < 10; i++) {
			frames.add(new TextureRegion(getTexture(), i * 200, 0, 200, 200));
		}
		playerRun = new Animation<TextureRegion>(0.1f, frames);
		frames.clear();
		
		for(int i = 2; i < 6; i++) {
			frames.add(new TextureRegion(getTexture(), i * 200, 0, 200, 200));
		}
		playerJump =new Animation<TextureRegion>(0.1f, frames);
		
		playerStand = new TextureRegion(getTexture(), 0, 0, 200, 200);

		
		definePlayer();
		setBounds(0, 0, 60 / GoAway.PPM, 60 / GoAway.PPM);
		setRegion(playerStand); 
		
	}
	
	public void update(float dt) {
		setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
		setRegion(getFrame(dt));
	}
	
	public TextureRegion getFrame(float dt) {
		currentState = getState();
		
		TextureRegion region;
		switch (currentState) {
		case JUMPING:
			region = playerJump.getKeyFrame(stateTimer, true);
			break;
		case RUNNING:
			region =  playerRun.getKeyFrame(stateTimer, true);
			break;
		case FALLING:
		case STANDING:
			
		default:
			region = playerStand;
			break;
		}
		if((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
			region.flip(true, false);
			runningRight = false;
		}
		else if((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
			region.flip(true, false);
			runningRight = true;
		}
		stateTimer = currentState == previousState ? stateTimer + dt : 0;
		previousState = currentState;
		return region; 
	}
	
	public State getState() {
		if(b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING)) {
			return State.JUMPING;
		}
		else if(b2body.getLinearVelocity().y < 0) {
			return State.FALLING;
		}
		else if(b2body.getLinearVelocity().x != 0) {
			return State.RUNNING;
		}
		else {
			return State.STANDING;
		}
	}
	
	public void definePlayer() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(32 / GoAway.PPM , 32  / GoAway.PPM);
		bdef.type = BodyDef.BodyType.DynamicBody;
		b2body = world.createBody(bdef);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(18  / GoAway.PPM);
		
		
		fdef.shape = shape;
		b2body.createFixture(fdef);
	}
	
}
